package com.miller.userapp.module.pay.suite;

import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.userapp.module.home.login.UserLoginTests;
import com.miller.userapp.module.order.create.CreateOrderByPlatformDeliveryTests;
import com.miller.userapp.module.order.shopping.car.ShoppingCarTests;
import com.miller.userapp.module.pay.notify.braintree.BraintreeSubmitOrderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Scenario(scenarioID = "01J6RYK86AABDSVCB1RG2ZADPM",
        scenarioName = "支付_普通支付_Braintree支付_Braintree支付SubmitOrder接口",
        author = "luwei@hungrypandagroup.com", developmentTime = 60, maintenanceTime = 0, manualTestTime = 10)
@EnvTag.Test
@DisplayName("Braintree支付_SubmitOrder接口")
@SelectClasses({
        // 用户登录
        UserLoginTests.class,
        // 添加商品到购物车
        ShoppingCarTests.class,
        //创建订单-平台配送
        CreateOrderByPlatformDeliveryTests.class,
        //Braintree支付_SubmitOrder接口
        BraintreeSubmitOrderTest.class
})
@Suite
public class BraintreeSubmitOrderSuiteTest {

}
