package com.miller.testcase.module.home.module.broadcast;

import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.http.HttpUtils;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Scenario(scenarioID = "01JWFWNA250A4C3ZW7PFCPM5CF", scenarioName = "已登陆用户-获取待领取任务"
        , author = "yancancan@hungrypandagroup.com", developmentTime = 15, maintenanceTime = 0, manualTestTime = 15)
public class BroadCastScenarioTests {
    private static final String uri = TestcaseConfig.HOST+"/api/app/user/index/broadcast";
    @DisplayName("已登陆用户-获取待领取任务")
    @Test
    void shouldReturnTaskSuccessfully(){
        var headers = TestCaseHelpful.getHeaders("module/headers.json");
        headers.put("authorization",TestCaseHelpful.login("17700000077","123456"));
        var requestBody=TestCaseHelpful.getJsonRequestBody("module/home/module/broadcast/request/success.json");
        var responseBody = HttpUtils.sendPostRequestReturnBody(uri, null, headers, requestBody, null);
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.indexTaskVO").isNotNull();

    }
}
