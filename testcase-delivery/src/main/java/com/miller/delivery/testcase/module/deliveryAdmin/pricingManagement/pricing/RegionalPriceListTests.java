package com.miller.delivery.testcase.module.deliveryAdmin.pricingManagement.pricing;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

@Scenario(
        scenarioID = "01KEB9FGGXF5BBJHKDB5JKFXTM",
        scenarioName = "司管_区域加价_指定城市列表查询",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 180, maintenanceTime = 0, manualTestTime = 5)
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

        LocalDate weekStartDate = LocalDate.now().with(DayOfWeek.MONDAY);
        String weekStart = weekStartDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String weekEnd = weekStartDate.plusDays(6).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 2) 杭州市默认查询
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/reward/list";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body1 = String.format("{\"mapShow\":\"\",\"city\":\"杭州市\",\"areaId\":null,\"areaName\":null,\"rewardSn\":null,\"rewardType\":null,\"deliveryOrderType\":null,\"orderTargetType\":null,\"currentStatus\":null,\"consumerAreaId\":null,\"subsidyAdjustmentType\":null,\"startDate\":\"%s\",\"endDate\":\"%s\",\"pageNo\":1,\"pageSize\":10,\"redPointType\":0}",
                weekStart, weekStart);
        var responseBody1 = TestCaseHelpful.sendRequest(method, uri, null, headers, body1);
        TestCaseHelpful.assertThatJson(responseBody1).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody1).node("message").isEqualTo("成功");

        // 3) 杭州市指定条件查询
        String body2 = String.format("{\"mapShow\":\"\",\"city\":\"杭州市\",\"areaId\":51,\"areaName\":\"滨江区\",\"rewardSn\":null,\"rewardType\":null,\"deliveryOrderType\":\"0\",\"orderTargetType\":null,\"currentStatus\":\"2\",\"consumerAreaId\":null,\"subsidyAdjustmentType\":null,\"startDate\":\"%s\",\"endDate\":\"%s\",\"pageNo\":1,\"pageSize\":10,\"redPointType\":0}",
                weekStart, weekEnd);
        var responseBody2 = TestCaseHelpful.sendRequest(method, uri, null, headers, body2);
        TestCaseHelpful.assertThatJson(responseBody2).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody2).node("message").isEqualTo("成功");
    }
}

