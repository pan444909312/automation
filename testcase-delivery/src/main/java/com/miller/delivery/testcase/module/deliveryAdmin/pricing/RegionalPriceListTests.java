package com.miller.delivery.testcase.module.deliveryAdmin.pricing;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

@Scenario(
        scenarioID = "01KDQJ00000000000000000001",
        scenarioName = "司管_区域加价_指定城市列表查询",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("司管_区域加价_指定城市列表查询")
public class RegionalPriceListTests {

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }

    @DisplayName("区域加价列表查询")
    @Test
    void shouldQueryRegionalPriceList() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 杭州市默认查询
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/reward/list";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body1 = "{\r\n" +
                "    \"mapShow\": \"\",\r\n" +
                "    \"city\": \"杭州市\",\r\n" +
                "    \"areaId\": null,\r\n" +
                "    \"areaName\": null,\r\n" +
                "    \"rewardSn\": null,\r\n" +
                "    \"rewardType\": null,\r\n" +
                "    \"deliveryOrderType\": null,\r\n" +
                "    \"orderTargetType\": null,\r\n" +
                "    \"currentStatus\": null,\r\n" +
                "    \"consumerAreaId\": null,\r\n" +
                "    \"subsidyAdjustmentType\": null,\r\n" +
                "    \"startDate\": \"2025-11-30\",\r\n" +
                "    \"endDate\": \"2025-12-31\",\r\n" +
                "    \"pageNo\": 1,\r\n" +
                "    \"pageSize\": 10,\r\n" +
                "    \"redPointType\": 0\r\n" +
                "}";
        var responseBody1 = TestCaseHelpful.sendRequest(method, uri, null, headers, body1);
        TestCaseHelpful.assertThatJson(responseBody1)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody1)
                .node("message").isEqualTo("成功");

        // 3) 杭州市指定条件查询
        String body2 = "{\r\n" +
                "    \"mapShow\": \"\",\r\n" +
                "    \"city\": \"杭州市\",\r\n" +
                "    \"areaId\": 51,\r\n" +
                "    \"areaName\": \"滨江区\",\r\n" +
                "    \"rewardSn\": null,\r\n" +
                "    \"rewardType\": null,\r\n" +
                "    \"deliveryOrderType\": \"0\",\r\n" +
                "    \"orderTargetType\": null,\r\n" +
                "    \"currentStatus\": \"2\",\r\n" +
                "    \"consumerAreaId\": null,\r\n" +
                "    \"subsidyAdjustmentType\": null,\r\n" +
                "    \"startDate\": \"2025-11-30\",\r\n" +
                "    \"endDate\": \"2025-12-31\",\r\n" +
                "    \"pageNo\": 1,\r\n" +
                "    \"pageSize\": 10,\r\n" +
                "    \"redPointType\": 0\r\n" +
                "}";
        var responseBody2 = TestCaseHelpful.sendRequest(method, uri, null, headers, body2);
        TestCaseHelpful.assertThatJson(responseBody2)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody2)
                .node("message").isEqualTo("成功");
    }
}

