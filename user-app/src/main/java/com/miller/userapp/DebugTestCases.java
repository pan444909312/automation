package com.miller.userapp;

import com.miller.userapp.login.LoginTests;
import com.miller.userapp.order.confirm.ConfirmOrderStatusTests;
import com.miller.userapp.order.create.CreateOrderTests;
import com.miller.userapp.order.evaluate.EvaluateOrderTests;
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
@SelectClasses({
        // 用户登录
        LoginTests.class,
        // 创建订单
//        CreateOrderTests.class,
//        // 支付订单
//        PayByBalanceTests.class,
        // 用户确认订单已送达。注意：需要订单被骑手派送完成之后执行
//        ConfirmOrderStatusTests.class,
        // 用户评价订单
        EvaluateOrderTests.class,
})
//@SelectPackages("com.miller.userapp")
@SuiteDisplayName("调试多个测试用例")
@Suite
public class DebugTestCases {
}
