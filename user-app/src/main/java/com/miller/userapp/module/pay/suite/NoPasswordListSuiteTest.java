package com.miller.userapp.module.pay.suite;

import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.userapp.module.home.login.UserLoginTests;
import com.miller.userapp.module.pay.nopassword.NoPasswordListTest;
import com.miller.userapp.module.pay.nopassword.NoPasswordStatusTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Scenario(scenarioID = "01J6S155HWR3NKK66VBFZ1K4N3",
        scenarioName = "支付_免密支付_免密支付设置_免密支付列表",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 10)
@EnvTag.Test
@DisplayName("免密支付列表")
@SelectClasses({
        // 用户登录
        UserLoginTests.class,

        //免密支付列表
        NoPasswordListTest.class,
        NoPasswordStatusTest.class
})
@Suite
public class NoPasswordListSuiteTest {

}
