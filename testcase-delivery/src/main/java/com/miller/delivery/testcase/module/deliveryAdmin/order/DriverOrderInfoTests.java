package com.miller.delivery.testcase.module.deliveryAdmin.order;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 骑手列表-查看骑手订单信息（HP & PF）
 */
@Scenario(
        scenarioID = "01KDQ2PG09GNRJTPTS066SDYV1",
        scenarioName = "骑手列表-查看骑手订单信息",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("查看骑手订单信息")
public class DriverOrderInfoTests {

    @DisplayName("查看骑手HP/PF订单信息")
    @Test
    void shouldQueryDriverOrders() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 查看骑手 HP 订单信息
        queryHpOrders(token);

        // 3) 查看骑手 PF 订单信息
        queryPfOrders(token);
    }

    private void queryHpOrders(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/order/driverOrderHp";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);

        String body = "{"
                + "\"arrivedEnd\": null,"
                + "\"arrivedStart\": null,"
                + "\"deliveryStatus\": null,"
                + "\"deliveryType\": null,"
                + "\"driverPhone\": null,"
                + "\"orderSn\": null,"
                + "\"orderType\": null,"
                + "\"riderOrderTimeEnd\": \"2025-12-30\","
                + "\"riderOrderTimeStart\": \"2025-12-30\","
                + "\"shopName\": null,"
                + "\"pageNo\": 1,"
                + "\"pageSize\": 15,"
                + "\"driverId\": \"1398715578\""
                + "}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    private void queryPfOrders(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/order/driverOrderPf";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);

        String body = "{"
                + "\"arrivedEnd\":\"2025-12-30\","
                + "\"arrivedStart\":\"2025-12-30\","
                + "\"deliveryStatus\":null,"
                + "\"orderSn\":null,"
                + "\"pageNo\":1,"
                + "\"pageSize\":10,"
                + "\"driverId\":\"1398715578\""
                + "}";

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
        headers.put("enableSign", "false");
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
        headers.put("enableSign", "false");
        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

