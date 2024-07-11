package com.miller.service.framework.lifecycle;

import com.miller.common.util.DateUtils;
import com.miller.service.framework.annotation.MethodInvoked;
import com.miller.service.framework.listenner.TestResultWatcher;
import com.miller.service.framework.notification.dingtalk.DingTalkUtils;
import com.miller.service.framework.util.JGitUtils;
import com.miller.service.framework.util.OSUtils;
import com.miller.service.framework.util.ReflectionUtils;
import org.junit.jupiter.api.extension.*;

import java.util.Objects;

/**
 * JUnit 生命周期回调。
 * <p>
 * <pre>
 *         BeforeAllCallback,
 *             BeforeEachCallback,
 *                 // @Test 方法执行之前回调
 *                 BeforeTestExecutionCallback,
 *                     // @Test 执行执行发生异常之后回调
 *                     TestExecutionExceptionHandler,
 *                 // @Test 方法执行之后回调
 *                 AfterTestExecutionCallback,
 *             AfterEachCallback,
 *         AfterAllCallback,
 *         // 下面这三个不太常用，用于测试类创建、处理、销毁时回调
 *         TestInstanceFactory,
 *         TestInstancePostProcessor,
 *         TestInstancePreDestroyCallback
 * </pre>
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/10/18 11:03:13
 */
public class LifecycleCallback implements BeforeAllCallback, BeforeEachCallback, BeforeTestExecutionCallback, TestExecutionExceptionHandler, AfterTestExecutionCallback, AfterEachCallback, AfterAllCallback {

    /**
     * 自动化测试执行通知开关
     */
    private static final Boolean isSendNotification = true;

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        System.out.println(this.getClass().getName() + " beforeAll() callback invoked.");
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        System.out.println(this.getClass().getName() + " afterAll() callback invoked.");
        if (isSendNotification) sendExecuteNotification(extensionContext);
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        System.out.println(this.getClass().getName() + " beforeEach() callback invoked.");
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        System.out.println(this.getClass().getName() + " afterEach() callback invoked.");
    }

    /**
     * 在 @Test 方法执行之前调用此方法
     */
    @Override
    public void beforeTestExecution(ExtensionContext extensionContext) throws Exception {
        // @Test方法执行之前调用
        System.out.println(this.getClass().getName() + " beforeTestExecution() callback invoked.");
        MethodInvoked methodInvokedAnnotation = extensionContext.getTestMethod().get().getDeclaredAnnotation(MethodInvoked.class);
        if (Objects.nonNull(methodInvokedAnnotation)) {
            Class<?> clazz = methodInvokedAnnotation.clazz();
            String methodName = methodInvokedAnnotation.methodName();
            String[] args = methodInvokedAnnotation.parameterArgs();
            Class<?>[] parameterTypes = methodInvokedAnnotation.parameterTypes();

            ReflectionUtils.invokeMethod(clazz, methodName, args, parameterTypes);
        }
    }

    /**
     * 在 @Test 方法执行之后调用此方法
     */
    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        //@Test方法执行之后调用
        System.out.println(this.getClass().getName() + " afterTestExecution() callback invoked.");
    }

    /**
     * 当 @Test 方法执行时发生异常后会执行此方法.
     * 调用顺序为: @Test -> handleTestExecutionException()
     */
    @Override
    public void handleTestExecutionException(ExtensionContext extensionContext, Throwable throwable) throws Throwable {
        System.out.println(this.getClass().getName() + " handleTestExecutionException() callback invoked.");
        // 执行发生异常时把异常抛出来
        throw throwable;
    }

    /**
     * 发送自动化测试执行消息
     */
    private static void sendExecuteNotification(ExtensionContext extensionContext) {
        TestResultWatcher.testCaseCountOfSuccessful = 0;
        TestResultWatcher.testCaseCountOfFailed = 0;
        TestResultWatcher.testCaseCountOfDisabled = 0;
        TestResultWatcher.testCaseCountOfAborted = 0;
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
        String displayName = extensionContext.getDisplayName();

        // 记录执行测试的事件
        String content =
                "- **执行人员**: " + executor + " \n " +
                        "- **执行时间**:\t" + DateUtils.getCurrentDateTime() + " \n " +
                        // 统计用例执行情况，目前是一次运行期间的累积数量之和，如果需要单独统计每次运行结果，则需要先设置为0
                        "- **<font color=black>用例总数:</font>**\t" + (
                        TestResultWatcher.testCaseCountOfSuccessful +
                                TestResultWatcher.testCaseCountOfFailed +
                                TestResultWatcher.testCaseCountOfDisabled +
                                TestResultWatcher.testCaseCountOfAborted) + " \n " +
                        "- **<font color=black>用例名称:</font>**\t" + displayName + " \n " +
                        "- **<font color=blue>成功用例:</font>**\t" + TestResultWatcher.testCaseCountOfSuccessful + " \n " +
                        "- **<font color=red>失败用例:</font>**\t" + TestResultWatcher.testCaseCountOfFailed + " \n " +
                        "- **<font color=green>禁用用例:</font>**\t" + TestResultWatcher.testCaseCountOfDisabled + " \n " +
                        "- **<font color=grey>中断用例:</font>**\t" + TestResultWatcher.testCaseCountOfAborted + " \n ";
        DingTalkUtils.sendMarkdownMessage("自动化执行通知", content);
    }
}
