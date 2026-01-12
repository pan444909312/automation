package com.miller.delivery.testcase.module.deliveryAdmin.equipment;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-骑手列表-查看装备打卡详情
 */
@Scenario(
        scenarioID = "01KDSXD1N23P77CRWNZGX7WE2Y",
        scenarioName = "骑手列表-查看装备打卡详情",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("查看装备打卡详情")
public class EquipmentCheckDetailTests {

    private static final Long DRIVER_ID = 1398714150L;

    @DisplayName("查看装备打卡详情")
    @Test
    void shouldGetEquipmentCheckDetail() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 查看装备打卡详情
        getEquipmentCheckDetail(token);
    }

    private void getEquipmentCheckDetail(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/equipmentCheck/detail";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);

        // 生成日期范围（最近一周）
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(6);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startDateStr = startDate.format(formatter);
        String endDateStr = endDate.format(formatter);

        String body = String.format("{"
                + "\"driverId\":%d,"
                + "\"driverEquipmentType\":1,"
                + "\"startDate\":\"%s\","
                + "\"endDate\":\"%s\""
                + "}", DRIVER_ID, startDateStr, endDateStr);

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    private String erpLogin() {
        String uri = TestcaseConfig.HOST_ERP + "/api/erp/auth/login/v2";
        String method = "POST";
        Map<String, Object> headers = new HashMap<>();

        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
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

        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

