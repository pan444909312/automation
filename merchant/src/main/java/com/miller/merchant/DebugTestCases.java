package com.miller.merchant;

import com.miller.merchant.login.LoginTests;
import com.miller.merchant.order.list.OrderListTests;
import com.miller.merchant.order.receiving.ReceivingOrderTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * 当需要执行多个测试类时可以通过此程序进行调试
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/06 18:57:00
 */
@SelectClasses({LoginTests.class, OrderListTests.class, ReceivingOrderTests.class})
// @SelectPackages("com.miller.merchant")
@SuiteDisplayName("调试多个测试用例")
@Suite
public class DebugTestCases {
}
