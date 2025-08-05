package com.miller.testcase.module.home.user;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;


@Scenario(scenarioID = "01K1T84GW8DBCRPPNTRT6R72H6",
        scenarioName = "定位地址获取成功-未登录",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 15, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("/api/user/location")
public class UserLocationSuccessNonLogin {
    private static final String uri = TestcaseConfig.HOST_APP + "/api/user/location";

    @DisplayName("定位地址获取成功-未登录")
    @Test
    void shouldReturnSuccessfully() {
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/headers.json");
        // 给请求头添加数据，例如这里添加token
        String responseBody = TestCaseHelpful.sendRequest("GET", uri, null, headers, null);
        String expectedStr = TestCaseHelpful.getFileContent("module/home/user/response/UserLocationNonLoginResp.json");

        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS,Option.IGNORING_EXTRA_ARRAY_ITEMS).isEqualTo(expectedStr);
    }
}
