package com.miller.takeaway.order.master.foodcity;

import com.miller.deliveryapp.driver.online.DriverOnlineTests;
import com.miller.deliveryapp.login.DeliveryLoginTests;
import com.miller.deliveryapp.order.neworder.grab.GrabOrderOfFoodCityTests;
import com.miller.deliveryapp.order.status.ModifyOrderStatusTests;
import com.miller.merchant.order.outing.OutingOrderTests;
import com.miller.merchant.order.waiting.receiving.ReceivingOrderOfFoodCityUseStallAccountTests;
import com.miller.service.framework.annotation.Scenario;
import com.miller.userapp.module.home.login.UserLoginTests;
import com.miller.userapp.module.order.confirm.ConfirmOrderStatusTests;
import com.miller.userapp.module.order.create.CreateOrderByFoodCityTests;
import com.miller.userapp.module.order.evaluate.EvaluateOrderTests;
import com.miller.userapp.module.pay.balance.PayByBalanceTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * 【主干场景】【美食城订单】美食城下的所有档口账号接单之后，美食城订单状态更新为完成
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/19 15:48:13
 */
@SelectClasses({
        // 用户登录
        UserLoginTests.class,
        // 创建订单-美食城
        CreateOrderByFoodCityTests.class,
        // 支付订单
        PayByBalanceTests.class,

        // 商家-接单并备餐-美食城订单-美食城档口账号接单
        ReceivingOrderOfFoodCityUseStallAccountTests.class,
        // 商家出餐
        OutingOrderTests.class,

        // 骑手登录
        DeliveryLoginTests.class,
        // 上线
        DriverOnlineTests.class,
        // 骑手抢单-美食城订单
        GrabOrderOfFoodCityTests.class,
        // 骑手-已到店->等待商家出餐->正在取餐->已完成送餐并拍照送达
        ModifyOrderStatusTests.class,

        // 用户确认订单已送达。注意：需要订单被骑手派送完成之后执行
        ConfirmOrderStatusTests.class,
        // 用户评价订单
        EvaluateOrderTests.class,

        // 商家回复评论
        com.miller.merchant.order.evaluate.EvaluateOrderTests.class,
        // 订单流程结束
})
@Scenario(scenarioID = "01HK72EK76QYGFPH5W6KQJ5VNZ",
        scenarioName = "【主干场景】【美食城订单】美食城下的所有档口账号接单之后，美食城订单状态更新为完成",
        developmentTime = 16 * 60, maintenanceTime = 0, manualTestTime = 2 * 60)
@SuiteDisplayName("【主干场景】【美食城订单】美食城下的所有档口账号接单之后，美食城订单状态更新为完成")
@Suite
public class OrderOfFoodCityAllStallReceivingOrderThenMasterOrderStatusIsDoneScenarioTests {
}
