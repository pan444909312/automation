package com.miller.service.framework.launcher;

import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.listenner.TestExecuteListener;
import com.miller.service.framework.listenner.TestResultWatcher;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import java.io.PrintWriter;

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

    @Test
    void testRunTest() {
        TestCaseRunnerLauncher testCaseRunnerLauncher = new TestCaseRunnerLauncher();
        testCaseRunnerLauncher.discoverTestCase("");
    }
}