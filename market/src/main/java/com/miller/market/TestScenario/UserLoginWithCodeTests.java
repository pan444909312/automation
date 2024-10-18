package com.miller.market.TestScenario;

import com.miller.market.system.sendShortMessage.MarketSendShortMessageWithoutLoginTests;
import com.miller.market.user.login.MarketLoginWithCodeTests;
import com.miller.service.framework.annotation.Scenario;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;


@SelectClasses({
        //发送短信验证码
        MarketSendShortMessageWithoutLoginTests.class,
        // 用户登录
        MarketLoginWithCodeTests.class,



})

@Suite
@SuiteDisplayName("【主干场景】发送验证码 - 登录")
@Scenario(scenarioID = "01JA4ZPKGN7P6S2ZP8J8CTQ2KM",
        scenarioName = "【主干场景】发送验证码 - 登录",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 0)
public class UserLoginWithCodeTests {
}
