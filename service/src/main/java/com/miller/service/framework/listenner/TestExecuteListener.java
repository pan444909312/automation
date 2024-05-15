package com.miller.service.framework.listenner;

import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.reporting.ReportEntry;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试执行监听器，这个监听器需要配合 Launcher 一起使用，作为监听测试结果的自定义监听器注入到 Launcher 中。
 *
 * @author Miller Shan
 * @version 1.0
 * @see com.miller.service.framework.launcher.TestCaseRunnerLauncher
 * @since 2023/10/16 20:56:49
 */
public class TestExecuteListener implements TestExecutionListener {
    // 测试结果收集
    private List<Map<String, Object>> testCases = new ArrayList();

    @Override
    public void testPlanExecutionStarted(TestPlan testPlan) {
        System.out.println(this.getClass().getName() + " testPlanExecutionStarted() invoked!!!");
    }

    @Override
    public void executionStarted(TestIdentifier testIdentifier) {
        System.out.println(this.getClass().getName() + " executionStarted() invoked!!!");
        // 判断TestIdentifier，测试标识符，是否是一个 Container。注意: Container 会存在多个，所以此方法会执行多次。
        if (testIdentifier.isContainer()) {/* do not nothing... */}
        // 判断 TestIdentifier 是否是一个测试方法(@Test)
        if (testIdentifier.isTest()) {
        }
    }

    @Override
    public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
        System.out.println(this.getClass().getName() + " executionFinished() invoked!!!");
        if (testIdentifier.isTest()) {
             System.out.println("Execution finished: " + testIdentifier.getDisplayName() + " " + testExecutionResult.toString());
            String result = testExecutionResult.getStatus().toString();
            // Tesults requires result to be one of: [pass, fail, unknown]
            if (result == "SUCCESSFUL") {
                result = "pass";
            } else if (result == "FAILED") {
                result = "fail";
            } else {
                result = "unknown";
            }
            String reason = "";
            if (testExecutionResult.getThrowable().isPresent()) {
                reason = testExecutionResult.getThrowable().get().getMessage();
            }
            String suite = "";
            String separator = "class:";
            if (testIdentifier.getParentId().isPresent()) {
                suite = testIdentifier.getParentId().get();
                suite = suite.substring(suite.indexOf(separator) + separator.length(), suite.lastIndexOf("]"));
            }
            Map<String, Object> testCase = new HashMap<String, Object>();
            String name = testIdentifier.getDisplayName();
            if (name.indexOf("(") != -1) {
                name = name.substring(0, name.lastIndexOf("("));
            }
            testCase.put("name", name);
            testCase.put("result", result);
            testCase.put("suite", suite);
            testCase.put("desc", testIdentifier.getDisplayName());
            testCase.put("reason", reason);
            // (Optional) For uploading files:
            //List<String> files = new ArrayList<String>();
            //files.add("/path-to-files/test-name/img1.png");
            //files.add("/path-to-files/test-name/img2.png");
            //testCase.put("files", files);
            testCases.add(testCase);
        }
    }

    @Override
    public void dynamicTestRegistered(TestIdentifier testIdentifier) {
        System.out.println(this.getClass().getName() + " dynamicTestRegistered() invoked!!!");
    }

    @Override
    public void executionSkipped(TestIdentifier testIdentifier, String reason) {
        System.out.println(this.getClass().getName() + " executionSkipped() invoked!!!");
    }

    @Override
    public void reportingEntryPublished(TestIdentifier testIdentifier, ReportEntry entry) {
        System.out.println(this.getClass().getName() + " reportingEntryPublished() invoked!!!");
    }

    @Override
    public void testPlanExecutionFinished(TestPlan testPlan) {
        System.out.println(this.getClass().getName() + " testPlanExecutionFinished() invoked!!!");
    }
}
