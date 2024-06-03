package com.miller.service.framework.listenner;

import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.launcher.TestCaseRunnerLauncher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import java.io.PrintWriter;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectMethod;

/**
 * 自定义监听器、测试结果监听器、生命周期监听器 组合测试。自
 *
 * <p>
 * 定义监听器需要通过 Launcher 注入，测试结果监听器、生命周期监听器目前通过 {@link TestFramework}注解注入
 * </p>
 *
 * @see TestExecuteListener
 * @see TestResultWatcher
 * @see TestCaseRunnerLauncher
 */
@TestFramework
@DisplayName("TestExecutionListener")
class TestExecuteListenerTest {
    @Test
    @DisplayName("自定义监听器、测试结果监听器、生命周期监听器组合测试")
    void testMyLauncher() {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request().selectors(
                // 运行指定的方法
                selectMethod("com.miller.service.framework.launcher.test.Test01#test01")).build();
        Launcher launcher = LauncherFactory.create();
        TestPlan testPlan = launcher.discover(request);

        // 概要结果监听器
        SummaryGeneratingListener summaryGeneratingListener = new SummaryGeneratingListener();
        // 自定义的结果监听器
        TestExecutionListener listener = new TestExecuteListener();
        // 注册监听器
        launcher.registerTestExecutionListeners(listener, summaryGeneratingListener);
        // 执行启动器
        launcher.execute(request);
        // 获取执行结果
        summaryGeneratingListener.getSummary().printTo(new PrintWriter(System.out));
    }
}