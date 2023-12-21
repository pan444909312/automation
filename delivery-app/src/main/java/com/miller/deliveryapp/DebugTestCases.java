package com.miller.deliveryapp;

import com.miller.deliveryapp.driver.online.DriverOnlineTests;
import com.miller.deliveryapp.login.LoginTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * 当需要执行多个测试类时可以通过此程序进行调试
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/06 18:57:00
 */
@SelectClasses({
        // 骑手登录
        LoginTests.class,
        // 上线
        DriverOnlineTests.class
})
//@SelectPackages("com.miller.deliveryapp")
@SuiteDisplayName("调试多个测试用例")
@Suite
public class DebugTestCases {
}
