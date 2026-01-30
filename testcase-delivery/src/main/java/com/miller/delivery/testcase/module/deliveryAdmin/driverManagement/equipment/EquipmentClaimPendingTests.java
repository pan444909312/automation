package com.miller.delivery.testcase.module.deliveryAdmin.driverManagement.equipment;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-骑手列表-装备管理审核列表-待领取装备
 */
@Scenario(
        scenarioID = "01KDT0D17S49HGST87GZ6NAW2D",
        scenarioName = "骑手列表-装备管理审核列表-待领取装备",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("待领取装备")
public class EquipmentClaimPendingTests {

    @DisplayName("装备管理审核列表-待领取装备")
    @Test
    void shouldGetPendingEquipmentClaimList() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取待领取装备列表
        getPendingEquipmentClaimList(token);
    }

    private void getPendingEquipmentClaimList(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/equipmentClaim/list";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"pageNo\":1,\"pageSize\":10,\"recordStatus\":4000}";

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

