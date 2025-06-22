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
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectMethod;
import static org.junit.platform.launcher.TagFilter.excludeTags;
import static org.junit.platform.launcher.TagFilter.includeTags;

/**
 * 自定义测试运行器
 *
 * <p>
 * 用于查找测试用例，执行测试用例
 * </p>
 *
 * @author Miller Shan
 * @version 1.0.0
 * @see LauncherDiscoveryRequestBuilder
 * @since 2023/10/22 21:20:33
 */
@Component
@TestFramework
public class TestCaseRunnerLauncher {
    /**
     * 运行测试用例，根据包路径
     *
     * @param packagePath 包路径，例如:com.xxx
     */
    public void runTestsByPackagePath(String packagePath) {
        LauncherDiscoveryRequest request =
                LauncherDiscoveryRequestBuilder.request()
                        .selectors(selectPackage(packagePath))
                        .build();
        executeRequest(request);
    }

    /**
     * 运行测试用例，根据包含的 Tags
     *
     * @param includeTags Junit中的 @Tag("xxx")标签列表
     */
    public void runTestsByIncludeTags(List<String> includeTags) {
        includeTags = includeTags.stream().map(String::toUpperCase).collect(Collectors.toList());
        LauncherDiscoveryRequest request =
                LauncherDiscoveryRequestBuilder.request()
                        // 可以为空字符串，但是必须要有，为空字符串则扫描所有路径，效率较低
                        .selectors(selectPackage(""))
                        .filters(
                                includeTags(includeTags)
                        ).build();
        executeRequest(request);
    }

    /**
     * 运行测试用例，根据包含的 Tags 并排除指定的 Tags
     *
     * @param includeTags Junit中的 @Tag("xxx")标签列表
     * @param excludeTags Junit中的 @Tag("xxx")标签列表
     */
    public void runTestsByIncludeTagsAndExcludeTags(List<String> includeTags, List<String> excludeTags) {
        includeTags = includeTags.stream().map(String::toUpperCase).collect(Collectors.toList());
        excludeTags = excludeTags.stream().map(String::toUpperCase).collect(Collectors.toList());
        LauncherDiscoveryRequest request =
                LauncherDiscoveryRequestBuilder.request()
                        // 可以为空字符串，但是必须要有，为空字符串则扫描所有路径，效率较低
                        .selectors(selectPackage(""))
                        .filters(
                                includeTags(includeTags),
                                excludeTags(excludeTags)
                        ).build();
        executeRequest(request);
    }

    /**
     * 运行测试用例，根据包路径，然后在过滤 包含的 Tags 并排除指定的 Tags
     *
     * @param packagePath             包路径，例如:com.xxx
     * @param includeTags             Junit中的 @Tag("xxx")标签列表
     * @param excludeTags             Junit中的 @Tag("xxx")标签列表
     * @param configurationParameters 配置参数，接受一个Map
     */
    public void runTest(String packagePath, List<String> includeTags, List<String> excludeTags, Map<String, String> configurationParameters) {
        includeTags = includeTags.stream().map(String::toUpperCase).collect(Collectors.toList());
        excludeTags = excludeTags.stream().map(String::toUpperCase).collect(Collectors.toList());
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                // 用例选择器
                .selectors(
                        // 选择要扫描的包路径,例如：com.miller.demo
                        selectPackage(packagePath))
                // selectPackage("org.example.user"),
                // selectClass("org.example.payment.PaymentTests"),
                // selectClass(ShippingTests.class),
                // selectMethod("org.example.order.OrderTests#test1"),
                // selectMethod("org.example.order.OrderTests#test2()"),
                // selectMethod("org.example.order.OrderTests#test3(java.lang.String)"),
                // selectMethod("org.example.order.OrderTests", "test4"),
                // selectMethod(OrderTests.class, "test5"),
                // selectMethod(OrderTests.class, testMethod),
                // selectClasspathRoots(Collections.singleton(new File("/my/local/path1"))),
                // selectUniqueId("unique-id-1"),
                // selectUniqueId("unique-id-2")
                // 过滤模式
                .filters(
                        includeTags(includeTags),
                        excludeTags(excludeTags)
                        // includeClassNamePatterns(".*Test[s]?")
                        // includeClassNamePatterns("org\.example\.tests.*")
                        // includeEngines("junit-jupiter", "spek"),
                        // excludeEngines("junit-vintage")
                ).configurationParameters(configurationParameters).build();
        executeRequest(request);
    }

    /**
     * 运行指定类的特定测试方法
     *
     * @param testClass 测试类
     * @param testMethodName 测试方法名
     */
    public void runTestMethod(Class<?> testClass, String testMethodName) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(selectMethod(testClass, testMethodName))
                .build();
        executeRequest(request);
    }

    /**
     * 运行指定类的特定测试方法（通过类名和方法名字符串）
     *
     * @param className 测试类的全限定名，例如: com.miller.test.UserTest
     * @param testMethodName 测试方法名
     */
    public void runTestMethod(String className, String testMethodName) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(selectMethod(className + "#" + testMethodName))
                .build();
        executeRequest(request);
    }

    /**
     * 运行指定类的特定测试方法（通过类名和方法名字符串），并支持配置参数
     *
     * @param className 测试类的全限定名，例如: com.miller.test.UserTest
     * @param testMethodName 测试方法名
     * @param configurationParameters 配置参数
     */
    public void runTestMethod(String className, String testMethodName, Map<String, String> configurationParameters) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(selectMethod(className + "#" + testMethodName))
                .configurationParameters(configurationParameters)
                .build();
        executeRequest(request);
    }

    /**
     * 运行指定类的特定测试方法（通过Class对象和方法名），并支持配置参数
     *
     * @param testClass 测试类
     * @param testMethodName 测试方法名
     * @param configurationParameters 配置参数
     */
    public void runTestMethod(Class<?> testClass, String testMethodName, Map<String, String> configurationParameters) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(selectMethod(testClass, testMethodName))
                .configurationParameters(configurationParameters)
                .build();
        executeRequest(request);
    }

    public SummaryGeneratingListener executeRequest(LauncherDiscoveryRequest request) {
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

        return summaryGeneratingListener;
    }
}