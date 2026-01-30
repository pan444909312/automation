package com.miller.delivery.testcase.module.deliveryAdmin.driverManagement.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 骑手列表-查看骑手入驻日志与 W9 日志
 */
@Scenario(
        scenarioID = "01KDQ5465FDV67YZ5BT3JYHZT2",
        scenarioName = "骑手列表-查看骑手入驻和w9日志",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("查看骑手日志信息")
public class DriverLogInfoTests {

    @DisplayName("查看入驻日志与W9日志")
    @Test
    void shouldQueryDriverLogs() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 入驻日志获取
        fetchAuthLog(token);

        // 3) W9 日志获取
        fetchW9Log(token);
    }

    private void fetchAuthLog(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/driverAuthLogInfo";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"userId\":\"1398715578\"}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    private void fetchW9Log(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/delivery/tax/info/log";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"driverId\":\"1398715578\"}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    /**
     * 司管后台登录并返回 token
     */
    private String erpLogin() {
        String uri = TestcaseConfig.HOST_ERP + "/api/erp/auth/login/v2";
        String method = "POST";
        Map<String, Object> headers = new HashMap<>();

        headers.put("Content-Type", "application/json");
        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");

        String body = "{"
                + "\"password\": \"d9501f93554734ba83d19c9dc83ef4fb\","
                + "\"userName\": \"ding023660390221528503\""
                + "}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        return TestCaseHelpful.extractValue(responseBody, "$.data.token").toString();
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("priority", "u=1, i");

        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

