package com.miller.testcaseuserapp.module.home.user;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcaseuserapp.config.TestcaseConfig;
import com.miller.testcaseuserapp.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Scenario(scenarioID = "01JWFWNA250A4C3ZW7PFCPM5CK", scenarioName = "查询我的收藏店铺列表"
        , author = "yancancan@hungrypandagroup.com", developmentTime = 15, maintenanceTime = 0, manualTestTime = 15)
public class UserCollectListScenarioTests {
    private static final String uri = TestcaseConfig.HOST + "/api/user/collect";

    @DisplayName("查询我的收藏店铺列表")
    @Test
    void shouldReturnSuccessfully() {
        var headers = TestCaseHelpful.getHeaders("module/headers.json");
        headers.put("authorization",TestCaseHelpful.login("17700000077","123456"));
        var responseBody = TestCaseHelpful.sendRequest("GET", uri, null, headers, null);
        assert TestCaseHelpful.extractValue(responseBody, "$.result").toString().contains("59750820");

    }
}
