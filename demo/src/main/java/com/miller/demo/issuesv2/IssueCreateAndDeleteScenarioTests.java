package com.miller.demo.issuesv2;

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
 * Suite 仅仅是用于组装测试用例，至于测试用例内部是否独立、是否依赖都不再Suite层处理了。
 * </p>
 * <p>
 * 注意：通过这种方式虽然测试用例设计满足独立性，但是可能会产生副作用。比如这个例子中IssueDeleteTests执行
 * 创建的数据并不会被销毁，使得测试执行完之后会产生可能不必需要的数据。测试框架本身并不会对于这种执行做限制，
 * 而是将用例设计是否合理性交给用例设计人员自身去考量。
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/3 10:24:20
 */
@SelectClasses({IssueDeleteTests.class, IssueCreateTests.class})
@SuiteDisplayName("缺陷管理_场景_新增->删除缺陷")
@Suite
public class IssueCreateAndDeleteScenarioTests {
}
