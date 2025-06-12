package com.miller.testcase.module.home.login;

import com.miller.service.framework.annotation.Scenario;
import com.miller.testcase.config.TestcaseConfig;
import com.miller.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;


@Scenario(scenarioID = "01JVKR6DPZH7V8WK4B381AMV6B",
        scenarioName = "退出登录成功",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("/api/oauth2/logout")
public class AppLogoutTests {
    private static final String uri = TestcaseConfig.HOST_APP + "/api/oauth2/logout";

    @DisplayName("退出登录成功")
    @Test
    void shouldReturnSuccessfully() {
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/headers.json");
        // 给请求头添加数据，例如这里添加token
        headers.put("Authorization", TestCaseHelpful.login("13999900002", "123456"));
        String responseBody = TestCaseHelpful.sendRequest("GET", uri, null, headers, null);
        String expectedStr = TestCaseHelpful.getFileContent("module/home/login/response/LogoutResp.json");

        TestCaseHelpful.assertThatJson(responseBody).when(Option.IGNORING_EXTRA_FIELDS).isEqualTo(expectedStr);
    }
}
