package com.miller.demo.issues;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * 场景: 新增一个缺陷，然后删除
 *
 * <p>
 * Step1: Create issue <br>
 * Step2: Delete issue
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 */
@SelectClasses({IssueDeleteTests.class, IssueCreateTests.class})
@SuiteDisplayName("缺陷管理_场景_新增->删除缺陷")
@Suite
public class CreateThenDeleteIssuesScenarioTests {
}
