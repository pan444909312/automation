package com.miller.takeaway.order.master.foodcity;

import com.miller.deliveryapp.driver.online.DriverOnlineTests;
import com.miller.deliveryapp.order.neworder.grab.GrabOrderOfFoodCityTests;
import com.miller.deliveryapp.order.neworder.grab.GrabOrderTests;
import com.miller.deliveryapp.order.status.ModifyOrderStatusTests;
import com.miller.merchant.order.outing.OutingOrderTests;
import com.miller.merchant.order.waiting.receiving.ReceivingOrderOfFoodCityTests;
import com.miller.service.framework.annotation.TestCase;
import com.miller.userapp.order.confirm.ConfirmOrderStatusTests;
import com.miller.userapp.order.create.CreateOrderByFoodCityTests;
import com.miller.userapp.pay.balance.PayByBalanceTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * 美食城订单场景
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/15 21:03:13
 */
@SelectClasses({
        // 用户登录
        com.miller.userapp.login.LoginTests.class,
        // 创建订单-美食城
        CreateOrderByFoodCityTests.class,
        // 支付订单
        PayByBalanceTests.class,

        // 商家-接单并备餐-美食城订单-美食城账号接单
        ReceivingOrderOfFoodCityTests.class,
        // 商家出餐
        OutingOrderTests.class,

        // 骑手登录
        com.miller.deliveryapp.login.LoginTests.class,
        // 上线
        DriverOnlineTests.class,
        // 骑手抢单-美食城订单
        GrabOrderOfFoodCityTests.class,
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
@TestCase(testCaseID = "01HK72EK76QYGFPH5W6KQJ5VNZ", name = "【主干场景】【美食城订单】用户在美食城下单，美食城账号接单，商家出餐，骑手接单后取餐完成送达，用户确认送达，订单主流程结束")
@SuiteDisplayName("【主干场景】【美食城订单】用户在美食城下单，美食城账号接单，商家出餐，骑手接单后取餐完成送达，用户确认送达，订单主流程结束")
@Suite
public class OrderOfFoodCityScenario {
}
