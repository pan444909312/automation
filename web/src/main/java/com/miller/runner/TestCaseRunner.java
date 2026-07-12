package com.miller.runner;

import com.miller.service.framework.launcher.TestCaseRunnerLauncher;

/**
 * 测试用例运行器
 *
 * <p>
 * 通过调用{@link TestCaseRunnerLauncher#runTestsByPackagePath(String)}方法执行测试用例
 * </p>
 *
 * @author Miller Shan
 */
public class TestCaseRunner {

    public static void main(String[] args) {
        TestCaseRunnerLauncher testSuiteLauncher = new TestCaseRunnerLauncher();
        testSuiteLauncher.runTestsByPackagePath("xxx");
    }
}
