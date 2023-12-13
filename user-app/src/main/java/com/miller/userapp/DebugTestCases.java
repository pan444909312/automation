package com.miller.userapp;

import com.miller.userapp.login.LoginTests;
import com.miller.userapp.order.create.CreateOrderTests;
import com.miller.userapp.pay.balance.PayByBalanceTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * 当需要执行多个测试类时可以通过此程序进行调试
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/06 16:57:00
 */
@SelectClasses({LoginTests.class, CreateOrderTests.class, PayByBalanceTests.class})
//@SelectPackages("com.miller.userapp")
@SuiteDisplayName("调试多个测试用例")
@Suite
public class DebugTestCases {
}
