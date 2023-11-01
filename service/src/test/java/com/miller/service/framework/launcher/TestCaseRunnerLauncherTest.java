package com.miller.service.framework.launcher;

import com.miller.service.framework.listenner.TestExecuteListener;
import com.miller.service.framework.listenner.TestResultWatcher;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;
import static org.junit.platform.launcher.EngineFilter.includeEngines;
import static org.junit.platform.launcher.TagFilter.includeTags;

/**
 * 测试用例运行器测试，发现测试用例，执行测试用例
 *
 * @author Miller Shan
 * @see LauncherDiscoveryRequestBuilder
 */
@Disabled
@ExtendWith({TestResultWatcher.class})
class TestCaseRunnerLauncherTest {

    @DisplayName("根据包路径执行测试用例")
    @Test
    void testRunTestByPackagePath() {
        TestCaseRunnerLauncher testCaseRunnerLauncher = new TestCaseRunnerLauncher();
        // 这里仅扫描的是 test/java 资源下的文件
        testCaseRunnerLauncher.runTestsByPackagePath("com.miller.service.framework.launcher.test");
    }

    @DisplayName("根据 Tag 执行包含的测试用例")
    @Test
    void testRunTestByIncludeTags() {
        TestCaseRunnerLauncher testCaseRunnerLauncher = new TestCaseRunnerLauncher();
        // 这里仅扫描的是 test/java 资源下的文件
        List<String> tags = Arrays.asList("test", "dev");
        testCaseRunnerLauncher.runTestsByIncludeTags(tags);
    }

    @DisplayName("根据 Tag 执行包含和排除测试用例")
    @Test
    void testRunTestsByIncludeTagsAndExcludeTags() {
        TestCaseRunnerLauncher testCaseRunnerLauncher = new TestCaseRunnerLauncher();
        // 这里仅扫描的是 test/java 资源下的文件
        List<String> tags = Arrays.asList("test", "dev");
        testCaseRunnerLauncher.runTestsByIncludeTagsAndExcludeTags(tags, List.of("dev"));
    }

    @DisplayName("执行测试用例")
    @Test
    void testRun() {
        TestCaseRunnerLauncher testCaseRunnerLauncher = new TestCaseRunnerLauncher();
        // 这里仅扫描的是 test/java 资源下的文件
        testCaseRunnerLauncher.runTest("com.miller.service.framework.launcher.test",
                List.of("test", "dev"), List.of("dev"), new ConcurrentHashMap<>());
    }

}