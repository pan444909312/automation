package com.miller.service.framework.launcher;

import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.listenner.TestExecuteListener;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import java.io.File;
import java.io.PrintWriter;
import java.util.Collections;

import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;
import static org.junit.platform.engine.discovery.DiscoverySelectors.*;
import static org.junit.platform.launcher.EngineFilter.includeEngines;
import static org.junit.platform.launcher.TagFilter.includeTags;

/**
 * 自定义测试运行器
 *
 * <p>
 * 类用于查找测试用例，执行测试用例
 * </p>
 *
 * @author Miller Shan
 * @version 1.0.0
 * @since 2023/10/22 21:20:33
 */
@TestFramework
public class TestCaseRunnerLauncher {
    /**
     * 查找指定包下的所有以Tests结尾的类
     */
    public void discoverTestCase(String packagePath) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                // 用例选择器
                .selectors(
                        // 选择要运行的方法
                        // selectMethod("", ""),
                        // 选择要运行的类
                        // selectClass()
                        // 选择要扫描的包路径
                        selectPackage(packagePath))
                // 过滤模式
                .filters(includeClassNamePatterns(".*Tests"))
                // LauncherDiscoveryRequest
                .build();
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

    public void discoverTestCase(Class testcase) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                // 用例选择器
                .selectors(
                        // 选择要运行的类
                        selectClass(testcase)).build();
        Launcher launcher = LauncherFactory.create();
        launcher.discover(request);

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