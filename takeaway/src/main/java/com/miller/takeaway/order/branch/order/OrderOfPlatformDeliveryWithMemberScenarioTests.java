package com.miller.takeaway.order.branch.order;

import com.miller.deliveryapp.driver.online.DriverOnlineTests;
import com.miller.deliveryapp.login.DeliveryLoginTests;
import com.miller.deliveryapp.order.neworder.grab.GrabOrderTests;
import com.miller.deliveryapp.order.status.ModifyOrderStatusTests;
import com.miller.merchant.login.MerchantLoginTests;
import com.miller.merchant.order.outing.OutingOrderTests;
import com.miller.merchant.order.waiting.receiving.ReceivingOrderTests;
import com.miller.service.framework.annotation.TestCase;
import com.miller.userapp.module.home.login.UserLoginTests;
import com.miller.userapp.order.confirm.ConfirmOrderStatusTests;
import com.miller.userapp.order.create.CreateOrderByPlatformDeliveryWithMemberTests;
import com.miller.userapp.module.pay.balance.PayByBalanceWithMemberTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * 【分支场景】【平台配送】【会员合单】用户下单时开通会员
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/23 10:53:01
 */
@SelectClasses({
        // 用户登录
        UserLoginTests.class,
        // 用户-创建订单-平台配送-会员合单
        CreateOrderByPlatformDeliveryWithMemberTests.class,
        // 用户-支付-余额支付-会员合单
        PayByBalanceWithMemberTests.class,

        // 商家登录APP
        MerchantLoginTests.class,
        // 商家接单
        ReceivingOrderTests.class,
        // 商家出餐
        OutingOrderTests.class,

        // 骑手登录
        DeliveryLoginTests.class,
        // 上线
        DriverOnlineTests.class,
        // 骑手抢单
        GrabOrderTests.class,
        // 骑手-已到店->等待商家出餐->正在取餐->已完成送餐并拍照送达
        ModifyOrderStatusTests.class,

        // 用户确认订单已送达。注意：需要订单被骑手派送完成之后执行
        ConfirmOrderStatusTests.class,
        // 用户评价订单
        com.miller.userapp.order.evaluate.EvaluateOrderTests.class,

        // 商家回复评论
        com.miller.merchant.order.evaluate.EvaluateOrderTests.class,
        // 订单流程结束
})
@Suite
@SuiteDisplayName("【分支场景】【平台配送】【会员合单】用户下单时开通会员")
@TestCase(testCaseID = "01HK72EK7B4JB80PM2NEHY9J5Y", name = "【分支场景】【平台配送】【会员合单】用户下单时开通会员")
public class OrderOfPlatformDeliveryWithMemberScenarioTests {
}
