package com.miller.service.framework.depend;


import com.miller.service.framework.depend.clazz.A_Tests;
import com.miller.service.framework.depend.clazz.B_Tests;
import com.miller.service.framework.depend.clazz.C_Tests;
import com.miller.service.framework.depend.clazz.D_Tests;
import com.miller.service.framework.listenner.TestExecuteListener;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import java.io.PrintWriter;
import java.util.List;

import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

/**
 * 通过 Launcher 测试多个类之间互相依赖，按照依赖顺序执行
 */
public class DependsOnClassAnnotationTestByLauncherTests {
    public static void main(String[] args) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(
                        // 选择要运行的类
                        List.of(
                                selectClass(A_Tests.class),
                                selectClass(B_Tests.class),
                                selectClass(C_Tests.class),
                                selectClass(D_Tests.class),
                                selectClass(DependsOnClassAnnotation.class)
                        )
                )
                // 过滤模式
                .filters(includeClassNamePatterns(".*Tests"))
                // LauncherDiscoveryRequest
                .build();
        Launcher launcher = LauncherFactory.create();
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
