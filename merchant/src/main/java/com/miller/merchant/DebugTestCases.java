package com.miller.merchant;

import com.miller.merchant.login.MerchantLoginTests;
import com.miller.merchant.order.list.OrderListTests;
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
@SelectClasses({
        // 商家登录APP
        MerchantLoginTests.class,
        // 订单列表
        OrderListTests.class,

})
@SuiteDisplayName("调试多个测试用例")
@Suite
public class DebugTestCases {
}
