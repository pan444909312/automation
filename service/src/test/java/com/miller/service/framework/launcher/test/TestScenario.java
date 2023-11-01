package com.miller.service.framework.launcher.test;

import com.miller.service.framework.annotation.EnvTag;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * 如果根据 @Tag 筛选测试用例，那么 @Suite 里包含的测试用例如果符合条件也会被执行。@Suite 注解无论是否在上面添加 @Tag 注解都会被添加到测试执行计划中。
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/1 18:34:40
 */

@SelectClasses({Test01.class, Test02.class})
@EnvTag.Test    /* 无论是否添加此注解都不影响 Launcher 根据 Tag 进行扫描用例。 Suite 中的测试用例都会被包含到扫描中 */
@Suite
public class TestScenario {
}
