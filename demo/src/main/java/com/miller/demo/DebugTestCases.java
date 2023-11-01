package com.miller.demo;

import com.miller.demo.issues.CreateThenDeleteIssuesScenarioTests;
import com.miller.demo.issues.CreateThenUpdateAndDeleteIssuesScenarioTests;
import com.miller.service.framework.launcher.TestCaseRunnerLauncher;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

import java.util.List;

/**
 * 当需要执行多个测试类时可以通过此程序进行调试
 *
 * @author Miller Shan
 * @version 1.0
 */
@SelectPackages("com.miller.demo")
@SelectClasses({CreateThenUpdateAndDeleteIssuesScenarioTests.class, CreateThenDeleteIssuesScenarioTests.class})
@SuiteDisplayName("调试多个测试用例")
@Suite
public class DebugTestCases {
    public static void main(String[] args) {
        TestCaseRunnerLauncher testCaseRunnerLauncher = new TestCaseRunnerLauncher();
        testCaseRunnerLauncher.runTestsByIncludeTags(List.of("test"));
    }
}
