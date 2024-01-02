package com.miller.demo.issues;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * 场景集_组装多个测试场景。可以通过{@link SelectClasses @SelectClasses}注解控制场景执行顺序，默认按照数组顺序执行
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/2 19:57:00
 */
@SelectClasses({CreateThenUpdateAndDeleteIssuesScenarioTests.class, CreateThenDeleteIssuesScenarioTests.class})
@SuiteDisplayName("场景集-缺陷管理")
@Suite
public class IssueScenariosTests {
}
