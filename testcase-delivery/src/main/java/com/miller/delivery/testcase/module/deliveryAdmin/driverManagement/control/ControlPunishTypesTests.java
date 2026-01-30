package com.miller.delivery.testcase.module.deliveryAdmin.driverManagement.control;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-骑手管控-获取管控惩罚类型
 */
@Scenario(
        scenarioID = "01KDQCYX2EVG84BPQFW8JS5RH3",
        scenarioName = "【主干场景】司管后台-骑手管控-获取管控惩罚类型",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 20, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("获取管控惩罚类型")
public class ControlPunishTypesTests {

    @DisplayName("获取所有惩罚类型")
    @Test
    void shouldFetchPunishTypes() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取所有惩罚类型
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/control/punish";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, "{}");
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

