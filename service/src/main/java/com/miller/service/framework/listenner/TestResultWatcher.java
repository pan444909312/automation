package com.miller.service.framework.listenner;

import com.miller.common.util.DateUtils;
import com.miller.service.framework.annotation.ApiDoc;
import com.miller.service.framework.annotation.ApiDocs;
import com.miller.service.framework.apidoc.YApiUtils;
import com.miller.service.framework.depend.DependsOnClass;
import com.miller.service.framework.depend.DependsOnMethod;
import com.miller.service.framework.notification.dingtalk.DingTalkUtils;
import com.miller.service.framework.util.JGitUtils;
import com.miller.service.framework.util.OSUtils;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 测试执行结果观察者
 *
 * <p>
 * 监听{@link org.junit.jupiter.api.Test @Test}, {@link org.junit.jupiter.api.TestTemplate @TestTemplate}方法，
 * 比如{@link org.junit.jupiter.api.RepeatedTest @RepeatedTest},
 * {@link org.junit.jupiter.params.ParameterizedTest @ParameterizedTest},
 * 监听结果结果包括{@link org.junit.jupiter.api.Disabled}, Successful, Aborted, Failed
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @see ApiDoc
 * @see DependsOnMethod
 * @see DependsOnClass
 * @since 2023/10/16 21:22:41
 */
public class TestResultWatcher implements TestWatcher, ExecutionCondition {
    /**
     * 自动化测试执行通知开关
     */
    private static final Boolean isSendNotification = true;

    /**
     * 存储成功的测试方法
     */
    private Set<String> successfulTestMethods = new HashSet<>();
    /**
     * 存储失败的类
     */
    private Set<String> failedTestClasses = new HashSet<>();

    // 是否同步结果到 YAPI 平台的开关
    @Deprecated /* 已废弃，关闭开关 */
    private static final Boolean yApiEnabled = false;

    /**
     * 存储所有测试类上的{@link ApiDoc @ApiDoc} 上的 value。用于测试执行完成之后更新平台的状态.
     *
     * @see com.miller.service.framework.lifecycle.LifecycleCallback
     */
    private Set<String> apiDocsValues = new HashSet<>();

    @Override
    public void testSuccessful(ExtensionContext context) {
        System.out.println(this.getClass().getName() + " testSuccessful method invoked...");
        // 记录成功的方法
        context.getTestMethod().ifPresent(method -> successfulTestMethods.add(method.getName()));

        // 执行成功则更新 ApiDoc 平台的状态
        if (yApiEnabled) {
            // 获取测试类上的ApiDoc注解中的值
            getAnnotationValueOfApiDoc(context);

            // 更新 YAPI 平台的状态
            for (String element : apiDocsValues) {
                String yApiId = YApiUtils.getYApiId(element);
                YApiUtils.updateYApiData(element);
            }
        }
        if (isSendNotification) sendExecuteNotification(context, "Successful");
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        System.out.println(this.getClass().getName() + " testFailed method invoked...");
        // 如果类中的某一个方法失败了，那么认为这个类也执行失败了
        String failedClassName = context.getTestClass().orElse(null).getName();
        failedTestClasses.add(failedClassName);
        if (isSendNotification) sendExecuteNotification(context, "Failed");
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        System.out.println(this.getClass().getName() + " testDisabled method invoked...");
        if (isSendNotification) sendExecuteNotification(context, "Disabled");
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        System.out.println(this.getClass().getName() + " testAborted method invoked...");
        if (isSendNotification) sendExecuteNotification(context, "Aborted");
    }

    /**
     * 处理{@link DependsOnMethod @DependsOnMethod} 和 {@link DependsOnClass @DependsOnClass} 在依赖的链中，中间有一个失败则后面都标识为禁用。
     */
    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        // 处理测试方法依赖
        Method method = context.getTestMethod().orElse(null);
        if (method != null) {
            DependsOnMethod annotation = method.getAnnotation(DependsOnMethod.class);
            if (annotation != null) {
                // 执行失败的方法
                Optional<String> unsuccessfulMethod = Arrays.stream(annotation.value()).filter(name -> !successfulTestMethods.contains(name)).findAny();
                if (unsuccessfulMethod.isPresent()) {
                    // 标识方法为 disabled
                    return ConditionEvaluationResult.disabled(String.format("'%s()' 不能执行，因为它依赖的测试方法 '%s()' 执行失败或者未执行!", method.getName(), unsuccessfulMethod.get()));
                }
            }
        }

        // 处理测试类依赖
        Class<?> currentExecuteClass = context.getTestClass().orElse(null);
        if (currentExecuteClass != null) {
            // 获取当前执行类上的 DependsOnClass 注解
            DependsOnClass annotation = currentExecuteClass.getAnnotation(DependsOnClass.class);
            if (annotation != null) {
                // 获取注解上的所有类
                Class[] classes = annotation.value();
                // 遍历注解里面的类
                for (Class classInAnnotation : classes) {
                    // 如果失败类集合中包含了当前依赖的类，则将此类设置为 disabled
                    if (failedTestClasses.contains(classInAnnotation.getName())) {
                        // 将不能执行的类添加到失败类的集合当中
                        failedTestClasses.add(currentExecuteClass.getName());
                        return ConditionEvaluationResult.disabled(String.format("'%s()' 不能执行，因为它依赖的测试类 '%s()' 执行失败或者未执行!", currentExecuteClass.getName(), classInAnnotation.getName()));
                    }
                }
            }
        }
        // 继续执行
        return ConditionEvaluationResult.enabled("Enable by Default");
    }

    /**
     * 获取 {@link  ApiDoc @ApiDoc} 注解的值
     */
    private void getAnnotationValueOfApiDoc(ExtensionContext context) {
        // 处理方法上的注解
        // ApiDoc apiDocAnnotation = context.getTestMethod().get().getDeclaredAnnotation(ApiDoc.class);
        // 处理类上的注解
        ApiDoc apiDocAnnotation = context.getTestClass().get().getDeclaredAnnotation(ApiDoc.class);
        if (Objects.nonNull(apiDocAnnotation)) {
            apiDocsValues.add(apiDocAnnotation.value());
        }
        ApiDocs apiDocsAnnotation = context.getTestClass().get().getDeclaredAnnotation(ApiDocs.class);
        if (Objects.nonNull(apiDocsAnnotation)) {
            ApiDoc[] apiDocs = apiDocsAnnotation.value();
            for (ApiDoc apiDoc : apiDocs) {
                apiDocsValues.add(apiDoc.value());
            }
        }
    }

    /**
     * 发送自动化测试执行消息
     */
    private void sendExecuteNotification(ExtensionContext context, String testResult) {
        // 获取执行人员
        String executor = "";
        String hostNameOfOS = OSUtils.getHostNameOfOS();
        // 如果是测试环境，则执行人员为DevOps平台
        if (hostNameOfOS.contains("hk-test-")) {
            executor = "DevOps Platform";
        } else {
            // 获取git用户名
            executor = JGitUtils.getGitEmail().split("@")[0];
        }
        // 用例名称
        String classDisplayName = context.getParent().orElseThrow().getParent().orElseThrow().getDisplayName();
        String methodDisplayName = context.getParent().orElseThrow().getDisplayName();


        // 测试用例执行结果
        // String testResult = TestResultWatcher.testcaseExecuteResult.get(context.getTestClass().orElseThrow().toGenericString());
        if (testResult.trim().contains("Successful")) {
            // ** 符号之前需要添加一个空格
            testResult = "✅" + " **<font color=blue>" + testResult + "</font>**";
        }
        if (testResult.trim().contains("Failed")) {
            testResult = "❌" + " **<font color=red>" + testResult + "</font>**";
        }
        // 记录执行测试的事件
        String content =
                "- **执行人员**: " + executor + " \n " +
                        "- **执行时间**:\t" + DateUtils.getCurrentDateTime() + " \n " +
                        "- **<font color=black>用例名称:</font>**\t" + classDisplayName + " \n " +
                        "- **<font color=black>测试名称:</font>**\t" + methodDisplayName + " \n " +
                        "- **<font color=black>执行结果:</font>**\t" + testResult + " \n ";
        DingTalkUtils.sendMarkdownMessage("自动化执行通知", content);
    }
}
