package com.miller.testcase.module.home.common;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;


@Scenario(scenarioID = "01JVKR6DPY3AY792BTB6AE5DDG",
        scenarioName = "强制更新接口调用成功",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("/api/page/update/forceUpdate")
public class ForceUpdateSuccess {
    private static final String uri = TestcaseConfig.HOST + "/api/page/update/forceUpdate";

    @DisplayName("强制更新接口调用成功")
    @Test
    void shouldReturnSuccessfully() {
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/headersForceUpdate.json");

        String responseBody = TestCaseHelpful.sendRequest("GET", uri, null, headers,null);
        String expectedStr = TestCaseHelpful.getFileContent("module/home/common/response/ForceUpdateResp.json");

        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
    }
}
