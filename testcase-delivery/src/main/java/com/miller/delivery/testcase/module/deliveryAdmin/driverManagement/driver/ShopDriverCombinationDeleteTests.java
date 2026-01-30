package com.miller.delivery.testcase.module.deliveryAdmin.driverManagement.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-指定骑手-删除骑手指定配置
 */
@Scenario(
        scenarioID = "01KDSPKBZ1JV58K6VNMJH3G2BD",
        scenarioName = "指定骑手-删除骑手指定配置",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 180, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("删除指定骑手")
public class ShopDriverCombinationDeleteTests {

    @DisplayName("删除骑手配置")
    @Test
    void shouldDeleteShopDriverCombination() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取指定骑手列表，提取第一个配置ID
        Integer id = getFirstCombinationId(token);

        // 3) 删除骑手配置
        deleteCombination(token, id);
    }

    private Integer getFirstCombinationId(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/shopDriverCombination";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"name\":null,\"city\":null,\"shopId\":null,\"shopName\":null,\"pageNo\":1,\"pageSize\":10}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        Object idObj = TestCaseHelpful.extractValue(responseBody, "$.data.list.[0].id");
        return idObj != null ? Integer.valueOf(idObj.toString()) : null;
    }

    private void deleteCombination(String token, Integer id) {
        if (id == null) {
            throw new RuntimeException("无法获取配置ID，无法执行删除操作");
        }
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/new/driver/deleteShopDriverCombination";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\"id\":%d}", id);

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    private String erpLogin() {
        String uri = TestcaseConfig.HOST_ERP + "/api/erp/auth/login/v2";
        String method = "POST";
        Map<String, Object> headers = new HashMap<>();

        headers.put("Content-Type", "application/json");
        String body = "{\"password\":\"d9501f93554734ba83d19c9dc83ef4fb\",\"userName\":\"ding023660390221528503\"}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        return TestCaseHelpful.extractValue(responseBody, "$.data.token").toString();
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("priority", "u=1, i");
        headers.put("Content-Type", "application/json;charset=UTF-8");
        return headers;
    }
}

