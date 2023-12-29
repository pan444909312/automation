package com.miller.userapp;

import com.miller.userapp.login.LoginTests;
import com.miller.userapp.order.confirm.ConfirmOrderStatusTests;
import com.miller.userapp.order.create.CreateOrderByMerchantDeliveryTests;
import com.miller.userapp.order.create.CreateOrderByPlatformDeliveryTests;
import com.miller.userapp.order.evaluate.EvaluateOrderTests;
import com.miller.userapp.order.refund.apply.ApplyRefundTests;
import com.miller.userapp.order.refund.submit.SubmitRefundTests;
import com.miller.userapp.pay.balance.PayByBalanceTests;
import org.junit.platform.suite.api.SelectClasses;
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
        // 创建订单-平台配送
//        CreateOrderByPlatformDeliveryTests.class,
        // 创建订单-商家配送
//        CreateOrderByMerchantDeliveryTests.class,
//        // 支付订单
//        PayByBalanceTests.class,

        // 用户确认订单已送达。注意：需要订单被骑手派送完成之后执行
//        ConfirmOrderStatusTests.class,
        // 用户评价订单
//        EvaluateOrderTests.class,
        // 用户预申请退款。这个是一个流程提供者，场景中用不上，直接使用 SubmitRefundTests 即可，已经包含了申请退款流程
        ApplyRefundTests.class,
        // 用户申请退款-提交
        SubmitRefundTests.class,
})
//@SelectPackages("com.miller.userapp")
@SuiteDisplayName("调试多个测试用例")
@Suite
public class DebugTestCases {
}
