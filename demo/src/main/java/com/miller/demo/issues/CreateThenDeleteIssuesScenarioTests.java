package com.miller.demo.issues;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * 场景_新增一个缺陷，然后删除
 *
 * <p>
 * Step1: Create issue <br>
 * Step2: Delete issue
 * </p>
 *
 * <p>
 *     通过 Suite 组装成场景比较适合相对而言业务本身闭环，测试用例设计时已经考虑场景闭环，且用例之间无序额外的操作。
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
