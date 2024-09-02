package com.miller.userapp.module.pay.suite;

import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.userapp.module.home.login.UserLoginTests;
import com.miller.userapp.module.pay.card.stripe.AddCardRecordTest;
import com.miller.userapp.module.pay.card.stripe.CreatePaymentMethodTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Scenario(scenarioID = "01J6RZQPWZV4C13EG3TDH9197V",
        scenarioName = "支付_普通支付_Stripe支付_Stripe解绑卡",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 10)
@EnvTag.Test
@DisplayName("Stripe解绑卡")
@SelectClasses({
        // 用户登录
        UserLoginTests.class,

        //Stripe绑卡
        CreatePaymentMethodTest.class,
        AddCardRecordTest.class
})
@Suite
public class StripeCreateCardSuiteTest {

}
