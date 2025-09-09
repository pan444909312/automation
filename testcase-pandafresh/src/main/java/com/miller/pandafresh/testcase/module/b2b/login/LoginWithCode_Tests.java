package com.miller.pandafresh.testcase.module.b2b.login;

import com.miller.pandafresh.testcase.module.b2b.system.getvcode.GetVcode_Tests;
import com.miller.pandafresh.testcase.module.b2b.system.sendshortmessagewithverify.SendShortMessageWithVerify;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * login
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/09/09 15:53:40
 */
@SelectClasses({
        GetVcode_Tests.class,
        SendShortMessageWithVerify.class,
        LoginWithCode.class
})
@Scenario(
        scenarioID = "01JY0JQJ9SEC35RYKDC8XVXGML", // 自动生成，不要修改
        scenarioName = "B2Blogin：使用验证码",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 20, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("B2Blogin：使用验证码")
@Suite
public class LoginWithCode_Tests {

} 