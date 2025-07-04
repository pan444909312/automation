package com.miller.testcase.module.home.user;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Scenario(scenarioID = "01JWFWNA250A4C3ZW7PFCPM5CD", scenarioName = "查询是否接受推广信息"
        , author = "yancancan@hungrypandagroup.com", developmentTime = 15, maintenanceTime = 0, manualTestTime = 15)
public class PromoteQueryScenarioTests {
    private static final String uri = TestcaseConfig.HOST_APP + "/api/app/user/promote/query";

    @DisplayName("查询是否接受推广信息")
    @Test
    void shouldReturnSuccessfully() {
        var headers = TestCaseHelpful.getHeaders("module/headers.json");
        headers.put("authorization",TestCaseHelpful.login("15151990629","123456"));
        var requestBody = TestCaseHelpful.getJsonRequestBody("module/home/user/promote/request/should_success.json");
        var responseBody = TestCaseHelpful.sendRequest("GET", uri, null, headers, null);
        var expectedStr = TestCaseHelpful.getFileContent("module/home/user/promote/response/assert_some_fields.json");
        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);

    }
}
