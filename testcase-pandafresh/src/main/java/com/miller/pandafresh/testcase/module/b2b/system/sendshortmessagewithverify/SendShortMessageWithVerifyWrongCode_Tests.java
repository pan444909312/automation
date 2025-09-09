package com.miller.pandafresh.testcase.module.b2b.system.sendshortmessagewithverify;

import com.miller.pandafresh.testcase.config.TestcaseConfig;
import com.miller.pandafresh.testcase.module.b2b.system.getvcode.GetVcode_Tests;
import com.miller.pandafresh.testcase.utils.RedisUtils;
import com.miller.pandafresh.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.JSONUtils;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * sendShortMessageWithVerify
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/09/09 15:44:02
 */
@SelectClasses({
        GetVcode_Tests.class,
        SendShortMessageWithVerifyWrongCode.class
})
@Scenario(
        scenarioID = "01K4PPX1FFMKP439CF836QZG9C", // 自动生成，不要修改
        scenarioName = "b2b-获取验证码失败：图形验证码错误",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 20, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("b2b-获取验证码成功：图形验证码错误")
@Suite
public class SendShortMessageWithVerifyWrongCode_Tests {
}