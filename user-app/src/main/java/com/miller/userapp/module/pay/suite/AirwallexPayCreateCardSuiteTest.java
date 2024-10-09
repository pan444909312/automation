package com.miller.userapp.module.pay.suite;

import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.userapp.module.home.login.UserLoginTests;
import com.miller.userapp.module.pay.card.general.AirwallexCreateCardTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Scenario(scenarioID = "01J6S14M60RZXN4J0D8YR0CRJ4",
        scenarioName = "支付_普通支付_AirwallexPay支付_AirwallexPay绑卡",
        developmentTime = 60, maintenanceTime = 15, manualTestTime = 10)
@EnvTag.Test
@DisplayName("AirwallexPay绑卡")
@SelectClasses({
        // 用户登录
        UserLoginTests.class,

        //AirwallexPay绑卡
        AirwallexCreateCardTest.class,
})
@Suite
public class AirwallexPayCreateCardSuiteTest {

}
