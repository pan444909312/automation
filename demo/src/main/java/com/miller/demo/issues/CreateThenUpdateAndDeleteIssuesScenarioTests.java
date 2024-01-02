package com.miller.demo.issues;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * 场景_新增一个缺陷，然后更新缺陷，最后删除该缺陷
 *
 * <p>
 * Step1: Create issue <br>
 * Step2: Update issue <br>
 * Step3: Delete issue <br>
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 */
@SelectClasses({IssueCreateTests.class, IssueUpdateTests.class, IssueDeleteTests.class})
@SuiteDisplayName("缺陷管理_场景_新增->更新->删除缺陷")
@Suite
public class CreateThenUpdateAndDeleteIssuesScenarioTests {
}
