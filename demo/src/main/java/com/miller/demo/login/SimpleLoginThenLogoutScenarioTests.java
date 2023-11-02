package com.miller.demo.login;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * 场景: 登入->登出
 * <p>
 *     简单场景：通过 Suite 组装成场景比较适合相对而言业务本身闭环，测试用例设计时已经考虑场景闭环，
 *     且用例之间无序额外的操作，业务之间相对独立没有混合在一起的情况。
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/01 19:10:12
 */
@SuiteDisplayName("登陆场景_登入->登出")
@SelectClasses({LoginTests.class, LogoutTests.class})
@Suite
public class SimpleLoginThenLogoutScenarioTests {

}
