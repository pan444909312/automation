package com.miller.service.framework.depend;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

/**
 * 通过 Suite 测试多个类之间互相依赖，按照依赖顺序执行
 */
@Suite
@SelectPackages("com.miller.service.framework.depend.clazz")
//@SelectPackages("com.miller.service.framework.depend.method")
public class DependsOnClassAnnotationTestBySuiteTests {
}
