package com.miller.service.framework.launcher;

import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.listenner.TestExecuteListener;
import org.junit.jupiter.api.Test;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import java.io.PrintWriter;

import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;
import static org.junit.platform.launcher.EngineFilter.includeEngines;
import static org.junit.platform.launcher.TagFilter.excludeTags;
import static org.junit.platform.launcher.TagFilter.includeTags;

/**
 * 测试用例发现、执行
 *
 * @author Miller Shan
 * @version 1.0
 * @see LauncherDiscoveryRequestBuilder
 * @since 2023/10/23 16:46:54
 */
public class DiscoveryTestCaseTests {
    public static void main(String[] args) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(selectPackage(""))
                .filters(
                        // includeEngines("junit-jupiter", "spek"),
                        // excludeEngines("junit-vintage"),
                        includeTags("test", "dev"),
                        excludeTags("prod")
                        // includeClassNamePatterns(".*Test[s]?")
                        // includeClassNamePatterns("com\.miller\.tests.*")
                ).build();
        Launcher launcher = LauncherFactory.create();

        // 概要结果监听器
        SummaryGeneratingListener summaryGeneratingListener = new SummaryGeneratingListener();
        // 自定义的结果监听器
        TestExecutionListener listener = new TestExecuteListener();
        // 注册监听器
//        launcher.registerTestExecutionListeners(listener, summaryGeneratingListener);
        launcher.registerTestExecutionListeners(summaryGeneratingListener);
        // 执行启动器
        launcher.execute(request);
        // 获取执行结果
        summaryGeneratingListener.getSummary().printTo(new PrintWriter(System.out));
    }

    static class TempTest {
        @EnvTag.Test
        @Test
        void test01() {
            System.out.println("test01 invoked. Test");
        }

        @EnvTag.Dev
        @Test
        void test02() {
            System.out.println("test02 invoked. Dev");
        }

        @EnvTag.Prod
        @Test
        void test03() {
            System.out.println("test03 invoked. Prod");
        }

        @EnvTag.ALL
        @Test
        void test04() {
            System.out.println("test04 invoked. ALL");
        }
    }
}
