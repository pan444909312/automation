package com.miller.userapp.module.pay.suite;

import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.userapp.module.home.login.UserLoginTests;
import com.miller.userapp.module.order.create.CreateOrderByPlatformDeliveryTests;
import com.miller.userapp.module.order.shopping.car.ShoppingCarTests;
import com.miller.userapp.module.pay.notify.ronghan.RonghanPayNotificationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;


import java.util.UUID;

@Scenario(scenarioID = "01J6RQB636FQ6SMJ6E2DYS4HYQ",
        scenarioName = "支付_普通支付_Ronghan支付_Ronghan支付回调",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 10)
@EnvTag.Test
@DisplayName("Ronghan支付回调")
@SelectClasses({
        // 用户登录
        UserLoginTests.class,
        // 添加商品到购物车
        ShoppingCarTests.class,
        //创建订单-平台配送
        CreateOrderByPlatformDeliveryTests.class,
        //Ronghan支付回调
        RonghanPayNotificationTest.class
})
@Suite
public class RonghanPayNotificationSuiteTest {

}
