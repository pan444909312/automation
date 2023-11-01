package com.miller.demo.login;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * 场景: 登入->登出
 * <p>
 *     这种场景组合的方式适合测试用例在设计时相对比较独立的情况可以直接组合成场景。
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/01 19:10:12
 */
@SuiteDisplayName("登陆场景_登入->登出")
@SelectClasses({LoginTests.class, LogoutTests.class})
@Suite
public class LoginThenLogoutScenarioTests {

}
