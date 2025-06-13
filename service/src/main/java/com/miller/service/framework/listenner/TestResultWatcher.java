package com.miller.service.framework.listenner;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.miller.common.util.DateUtils;
import com.miller.entity.constant.ExecutionStatusEnum;
import com.miller.entity.platform.User;
import com.miller.entity.report.AutomationCoverageApiEntity;
import com.miller.mapper.platform.UserMapper;
import com.miller.service.framework.annotation.ApiDoc;
import com.miller.service.framework.annotation.ApiDocs;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.apidoc.YApiUtils;
import com.miller.service.framework.depend.DependsOnClass;
import com.miller.service.framework.depend.DependsOnMethod;
import com.miller.service.framework.http.HTTPUtilsByRestAssured;
import com.miller.service.framework.lifecycle.LifecycleCallback;
import com.miller.service.framework.notification.dingtalk.DingTalkUtils;
import com.miller.service.framework.report.mapper.AutomationCoverageApiMapper;
import com.miller.service.framework.report.AutoDBUtils;
import com.miller.service.framework.util.TestCaseUtils;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.junit.jupiter.params.ParameterizedTest;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 测试执行结果观察者
 *
 * <p>
 * 监听{@link Test @Test}, {@link TestTemplate @TestTemplate}方法，
 * 比如{@link RepeatedTest @RepeatedTest},
 * {@link ParameterizedTest @ParameterizedTest},
 * 监听结果结果包括{@link Disabled}, SUCCESSFUL,FAILED,DISABLED,ABORTED
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @see ApiDoc
 * @see DependsOnMethod
 * @see DependsOnClass
 * @since 2023/10/16 21:22:41
 */
@Slf4j
public class TestResultWatcher implements TestWatcher, ExecutionCondition {
    private static final String SUCCESSFUL = "Successful";
    private static final String FAILED = "Failed";
    private static final String DISABLED = "Disabled";
    private static final String ABORTED = "Aborted";

    /**
     * 自动化测试执行通知消息开关
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
    /**
     * 存储场景执行结果
     */
    private static Map<String, ExecutionStatusEnum> scenarioResultMap = new HashMap<>();

    /**
     * 操作数据库
     */
    private SqlSession automationSession = AutoDBUtils.getDBOfAutomationTest();

    /**
     * 是否启自动化测试覆盖率统计
     */
    private static final Boolean isOpenCoverage = true;


    // 是否同步结果到 YAPI 平台的开关
    @Deprecated /* 已废弃，关闭开关 */ private static final Boolean yApiEnabled = false;

    /**
     * 存储所有测试类上的{@link ApiDoc @ApiDoc} 上的 value。用于测试执行完成之后更新平台的状态.
     *
     * @see LifecycleCallback
     */
    private Set<String> apiDocsValues = new HashSet<>();

    public static Map<String, ExecutionStatusEnum> getExecutionStatus() {
        return scenarioResultMap;
    }

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
        if (isSendNotification) sendExecuteNotification(context, SUCCESSFUL);
        updateAutoExecutionRecordTestResult(context, SUCCESSFUL);
        // 将 HTTP 协议数据存储到数据库中
        if (isOpenCoverage) updateAutomationCoverageResult(context, SUCCESSFUL, HTTPUtilsByRestAssured.httpInfoMap);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        System.out.println(this.getClass().getName() + " testFailed method invoked...");
        // 如果类中的某一个方法失败了，那么认为这个类也执行失败了
        String failedClassName = context.getTestClass().orElse(null).getName();
        failedTestClasses.add(failedClassName);
        if (isSendNotification) sendExecuteNotification(context, FAILED);
        updateAutoExecutionRecordTestResult(context, FAILED);
        // 将 HTTP 协议数据存储到数据库中
        if (isOpenCoverage) updateAutomationCoverageResult(context, FAILED, HTTPUtilsByRestAssured.httpInfoMap);

    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        System.out.println(this.getClass().getName() + " testDisabled method invoked...");
        if (isSendNotification) sendExecuteNotification(context, DISABLED);
        updateAutoExecutionRecordTestResult(context, DISABLED);
        // 将 HTTP 协议数据存储到数据库中
        if (isOpenCoverage) updateAutomationCoverageResult(context, DISABLED, HTTPUtilsByRestAssured.httpInfoMap);
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        System.out.println(this.getClass().getName() + " testAborted method invoked...");
        if (isSendNotification) sendExecuteNotification(context, ABORTED);
        updateAutoExecutionRecordTestResult(context, ABORTED);
        // 将 HTTP 协议数据存储到数据库中
        if (isOpenCoverage) updateAutomationCoverageResult(context, ABORTED, HTTPUtilsByRestAssured.httpInfoMap);
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
        String executor = TestCaseUtils.getExecutor(context.getRequiredTestClass());
        Optional<Scenario> scenarioAnnotation = Optional.ofNullable(context.getRequiredTestClass().getAnnotation(Scenario.class));
        String scenarioName = "";
        String scenarioID = "";
        String remark = "";
        String author = "";
        int expectTimes = 1;
        int maintenanceTime = 0;
        int developmentTime = 0;
        int manualTestTime = 0;
        if (scenarioAnnotation.isPresent()) {
            scenarioName = scenarioAnnotation.get().scenarioName();
            scenarioID = scenarioAnnotation.get().scenarioID();
            remark = scenarioAnnotation.get().remark();
            author = scenarioAnnotation.get().author();
            expectTimes = scenarioAnnotation.get().expectTimes();
            maintenanceTime = scenarioAnnotation.get().maintenanceTime();
            developmentTime = scenarioAnnotation.get().developmentTime();
            manualTestTime = scenarioAnnotation.get().manualTestTime();
        }

        // 用例名称
        String classDisplayName;
        Optional<DisplayName> optionalClassDisplayName = Optional.ofNullable(context.getRequiredTestClass().getAnnotation(DisplayName.class));
        if (optionalClassDisplayName.isPresent()) {
            classDisplayName = optionalClassDisplayName.get().value();
        } else {
            classDisplayName = context.getRequiredTestClass().getName();
        }
        // 测试方法名称
        String methodDisplayName;
        Optional<DisplayName> optionalMethodDisplayName = Optional.ofNullable(context.getRequiredTestMethod().getAnnotation(DisplayName.class));
        if (optionalMethodDisplayName.isPresent()) {
            methodDisplayName = optionalMethodDisplayName.get().value();
        } else {
            methodDisplayName = context.getRequiredTestMethod().getName();
        }

        // 测试用例执行结果
        // String testResult = TestResultWatcher.testcaseExecuteResult.get(context.getTestClass().orElseThrow().toGenericString());
        if (testResult.trim().contains(SUCCESSFUL)) {
            // ** 符号之前需要添加一个空格
            testResult = "✅" + " **<font color=blue>" + testResult + "</font>**";
        }
        if (testResult.trim().contains(FAILED)) {
            testResult = "❌" + " **<font color=red>" + testResult + "</font>**";
        }

        StringBuilder content = new StringBuilder();

        // 构建执行人员信息
        content.append("- **执行人员**: ").append(executor).append(" \n ");

        // 构建执行时间信息
        content.append("- **执行时间**: ").append(DateUtils.getCurrentDateTime()).append(" \n ");

        // 构建用例名称信息
        content.append("- **<font color=black>用例名称:</font>** ").append(classDisplayName).append(" \n ");

        // 构建测试名称信息
        content.append("- **<font color=black>测试名称:</font>** ").append(methodDisplayName).append(" \n ");

        // 构建执行结果信息
        content.append("- **<font color=black>执行结果:</font>** ").append(testResult).append(" \n ");

        // 构建用例ID信息
        if (!scenarioID.isEmpty()) {
            content.append("- **<font color=black>用例ID:</font>** ").append(scenarioID).append(" \n ");
        }

        DingTalkUtils.sendMarkdownMessage("自动化执行通知", content.toString());
    }

    private void updateAutoExecutionRecordTestResult(ExtensionContext context, String result) {
        Scenario scenario = context.getRequiredTestClass().getDeclaredAnnotation(Scenario.class);
        if (Objects.nonNull(scenario)) {
            updateAutoExecutionRecord(scenario, result);
        }
        ExtensionContext rootContext = context.getRoot();
        String uniqueCls = rootContext.getUniqueId();
        if (!uniqueCls.contains("suite:")) return;
        String begin = uniqueCls.substring(uniqueCls.indexOf("suite:") + "suite:".length());
        String suiteCls = begin.substring(0, begin.indexOf("]"));
        try {
            Class<?> cls = Class.forName(suiteCls); //拿到root父类
            Scenario scenarioSuite = cls.getDeclaredAnnotation(Scenario.class);
            updateAutoExecutionRecord(scenarioSuite, result);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * TestSuite 执行结果更新
     *
     * @param scenario Scenario
     * @param result   测试用例每次执行结果
     */
    private void updateAutoExecutionRecord(Scenario scenario, String result) {
        String scenarioId = scenario.scenarioID();
        ExecutionStatusEnum value = scenarioResultMap.get(scenarioId);
        switch (result) {
            case SUCCESSFUL:
                if (ExecutionStatusEnum.FAIL.equals(value)) {
                    return;
                }
                value = ExecutionStatusEnum.SUCCESS;
                break;
            case FAILED:
                value = ExecutionStatusEnum.FAIL;
                break;
            case DISABLED:
                if (ExecutionStatusEnum.FAIL.equals(value) || ExecutionStatusEnum.SUCCESS.equals(value)) {
                    return;
                }
                value = ExecutionStatusEnum.DISABLE;
                break;
            case ABORTED:
                if (Objects.nonNull(value)) {
                    return;
                }
                value = ExecutionStatusEnum.PASS;
                break;
        }
        if (Objects.nonNull(value)) scenarioResultMap.put(scenarioId, value);
        System.out.println(JSON.toJSON(scenarioResultMap));
    }

    /**
     * 将 HTTP 协议数据存储到数据库中,用于自动统计接口覆盖率
     * @param stringObjectMap {@link HTTPUtilsByRestAssured#processResponseResult(Response, RequestSpecification)}
     *
     */
    private void updateAutomationCoverageResult(ExtensionContext context, String executeResult, Map<String, Object> stringObjectMap) {
        // 如果请求都没有发出去，则不处理
        if (Objects.isNull(stringObjectMap)) {
            return;
        }
        String requestMethod = ((HashMap) stringObjectMap.get("requestMap")).get("requestMethod").toString();
        String requestURI = ((HashMap) stringObjectMap.get("requestMap")).get("requestURI").toString();
        String requestHeaders = ((HashMap) stringObjectMap.get("requestMap")).get("requestHeaders").toString();
        String requestBody = ((HashMap) stringObjectMap.get("requestMap")).get("requestBody").toString();
        String requestPath = ((HashMap) stringObjectMap.get("requestMap")).get("requestPath").toString();
        String responseBody = ((HashMap) stringObjectMap.get("body")).get("body").toString();
        String responseStatusCode = ((HashMap) stringObjectMap.get("status")).get("statusCode").toString();
        String responseStatusLine = ((HashMap) stringObjectMap.get("status")).get("statusLine").toString();
        String responseHeaders = ((HashMap) stringObjectMap.get("headers")).toString();
        String responseCookies = ((HashMap) stringObjectMap.get("cookies")).toString();

//        SqlSession automationSession = AutoDBUtils.getDBOfAutomationTest();
        AutomationCoverageApiMapper automationCoverageApiMapper = automationSession.getMapper(AutomationCoverageApiMapper.class);
        AutomationCoverageApiEntity automationCoverageApiEntity = new AutomationCoverageApiEntity();
        automationCoverageApiEntity.setIsAutomation(1);
        automationCoverageApiEntity.setLastExecuteTime(System.currentTimeMillis());
        automationCoverageApiEntity.setLastExecuteResult(executeResult);
        automationCoverageApiEntity.setLastExecutor(TestCaseUtils.getExecutor());
        automationCoverageApiEntity.setTestCaseRequestPath(requestPath);
        automationCoverageApiEntity.setTestCaseRequestMethod(requestMethod);
        automationCoverageApiEntity.setTestCaseRequestBody(requestBody);
        automationCoverageApiEntity.setTestCaseRequestUri(requestURI);
        automationCoverageApiEntity.setTestCaseRequestHeaders(requestHeaders);
        automationCoverageApiEntity.setTestCaseResponseBody(responseBody);
        automationCoverageApiEntity.setTestCaseResponseStatusCode(responseStatusCode);

        // 查询数据库中的接口测试负责人是否为空，如果为空则更新。不为空则应该是手工或已有负责人,不要更新数据库字段值
        boolean anyMatchApiTestAuthorIsBlack = automationCoverageApiMapper.selectByPath(requestPath).stream().anyMatch(item -> item.getApiTestAuthor().isBlank());
        if (anyMatchApiTestAuthorIsBlack) {
            // 获取测试用例的负责人字段用于查询对应接口的测试负责人
            Optional<Scenario> scenarioAnnotation = Optional.ofNullable(context.getRequiredTestClass().getAnnotation(Scenario.class));
            if (scenarioAnnotation.isPresent()) {
                String authorEmail = scenarioAnnotation.get().author();
                UserMapper userMapper = automationSession.getMapper(UserMapper.class);
                User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getEmail, authorEmail));
                if (Objects.nonNull(user)) {
                    automationCoverageApiEntity.setApiTestAuthor(user.getName());
                }
            }
        } else {
            log.warn("数据库中接口测试负责人不为空，无需更新数据库字段值。也有可能的原因为使用了 @TestFramework 注解但是缺少 @Scenario 注解，所以导致测试框架执行了用例，但是无法获取负责人");
        }

        // 测试用例执行结果
        automationCoverageApiEntity.setLastExecuteResult(executeResult);
        // 更新表记录
        automationCoverageApiMapper.updateByPath(requestPath, automationCoverageApiEntity);
    }
}
