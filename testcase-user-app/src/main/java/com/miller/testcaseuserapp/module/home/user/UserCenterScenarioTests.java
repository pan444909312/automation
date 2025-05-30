package com.miller.testcaseuserapp.module.home.user;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcaseuserapp.config.TestcaseConfig;
import com.miller.testcaseuserapp.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Scenario(scenarioID = "01JWFWNA250A4C3ZW7PFCPM5CE", scenarioName = "用户个人中心接口"
        , author = "yancancan@hungrypandagroup.com", developmentTime = 15, maintenanceTime = 0, manualTestTime = 15)
public class UserCenterScenarioTests {
    private static final String uri = TestcaseConfig.HOST + "/api/user/center";

    @DisplayName("用户个人中心接口")
    @Test
    void shouldReturnSuccessfully() {
        var headers = TestCaseHelpful.getHeaders("module/headers.json");
        headers.put("authorization",TestCaseHelpful.login("15151990629","123456"));
        var responseBody = TestCaseHelpful.sendRequest("GET", uri, null, headers, null);
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.userName").isStringEqualTo("15151990629");
        TestCaseHelpful.assertThatJson(responseBody).inPath("$.result.entranceList").isNotNull();

    }
}
