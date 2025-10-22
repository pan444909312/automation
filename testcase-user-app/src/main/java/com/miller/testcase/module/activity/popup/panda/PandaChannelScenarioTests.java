package com.miller.testcase.module.activity.popup.panda;

import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.http.HttpUtils;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Scenario(scenarioID = "01JWFWNA250A4C3ZW7PFCPM5CG", scenarioName = "已登陆用户-联盟频道红包楼层"
        , author = "yancancan@hungrypandagroup.com", developmentTime = 15, maintenanceTime = 0, manualTestTime = 15)
public class PandaChannelScenarioTests {
    private static final String uri = TestcaseConfig.HOST_APP +"/api/app/user/panda/league/channel/module";
    @DisplayName("已登陆用户-联盟频道红包楼层")
    @Test
    void shouldReturnRuleSuccessfully(){
        var headers = TestCaseHelpful.getHeaders("module/headers.json");
        headers.put("authorization",TestCaseHelpful.login("17100000077","123456"));
        var requestBody=TestCaseHelpful.getJsonRequestBody("module/activity/popup/panda/request/success.json");
        var responseBody = HttpUtils.sendPostRequestReturnBody(uri, null, headers, requestBody, null);
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.redPacketList").isNotNull();

    }
}
