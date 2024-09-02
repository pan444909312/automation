package com.miller.userapp.module.pay.suite;

import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.userapp.module.home.login.UserLoginTests;
import com.miller.userapp.module.order.create.CreateOrderByPlatformDeliveryTests;
import com.miller.userapp.module.order.shopping.car.ShoppingCarTests;
import com.miller.userapp.module.pay.notify.ele.ElePayNotificationTest;
import com.miller.userapp.module.pay.payment.DefaultPaymentTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Scenario(scenarioID = "01J6RVDWT6K7T2X5ET7FFVFKB6",
        scenarioName = "支付_普通支付_日本ElePay支付_ElePay支付回调",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 10)
@EnvTag.Test
@DisplayName("日本ele支付回调")
@SelectClasses({
        // 用户登录
        UserLoginTests.class,
        // 添加商品到购物车
        ShoppingCarTests.class,
        //创建订单-平台配送
        CreateOrderByPlatformDeliveryTests.class,
        //默认收银台接口
        DefaultPaymentTest.class,
        //日本ele支付回调
        ElePayNotificationTest.class
})
@Suite
public class ElePayNotificationSuiteTest {

}
