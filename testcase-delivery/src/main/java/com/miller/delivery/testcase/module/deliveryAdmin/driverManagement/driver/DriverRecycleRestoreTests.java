package com.miller.delivery.testcase.module.deliveryAdmin.driverManagement.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 骑手列表-骑手回收站（恢复骑手账号）
 */
@Scenario(
        scenarioID = "01KDSJMYYF8JNSQN3ZD5MQCEYM",
        scenarioName = "骑手列表-骑手回收站",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 180, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("恢复骑手账号")
public class DriverRecycleRestoreTests {

    @DisplayName("回收站列表并恢复骑手账号")
    @Test
    void shouldRestoreDriverAccount() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取配送回收站列表，提取一个 userId
        String userId = fetchRecycleList(token);

        // 3) 恢复骑手账号
        restoreDriver(token, userId);
    }

    private String fetchRecycleList(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/recycleDriverList";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"city\":null,\"realName\":null,\"userName\":null,\"userId\":null,\"pageNo\":1,\"pageSize\":10}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        // 提取第一个 userId
        Object uid = TestCaseHelpful.extractValue(responseBody, "$.data.records[0].userId");
        return uid == null ? "1398715578" : uid.toString();
    }

    private void restoreDriver(String token, String userId) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/unDelDriver";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\n" +
                "    \"driverId\": "+userId+",\n" +
                "    \"op\": \"undelete\"\n" +
                "}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
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

