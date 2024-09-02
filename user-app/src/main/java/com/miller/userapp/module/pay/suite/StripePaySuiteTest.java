package com.miller.userapp.module.pay.suite;

import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.userapp.module.home.login.UserLoginTests;
import com.miller.userapp.module.order.create.CreateOrderByPlatformDeliveryTests;
import com.miller.userapp.module.order.shopping.car.ShoppingCarTests;
import com.miller.userapp.module.pay.payment.StripePaymentOnlyTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Scenario(scenarioID = "01J6RZH6DFCDW0814JP6A34Q3V",
        scenarioName = "支付_普通支付_Stripe支付_Stripe支付",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 10)
@EnvTag.Test
@DisplayName("Stripe支付")
@SelectClasses({
        // 用户登录
        UserLoginTests.class,
        // 添加商品到购物车
        ShoppingCarTests.class,
        //创建订单-平台配送
        CreateOrderByPlatformDeliveryTests.class,
        //Stripe支付
        StripePaymentOnlyTest.class
})
@Suite
public class StripePaySuiteTest {

}
