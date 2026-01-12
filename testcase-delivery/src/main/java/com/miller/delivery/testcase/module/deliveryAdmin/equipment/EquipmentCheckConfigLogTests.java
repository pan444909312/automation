package com.miller.delivery.testcase.module.deliveryAdmin.equipment;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-骑手列表-获取城市装备打卡配置日志
 */
@Scenario(
        scenarioID = "01KDSY37Y0HKAPYM2CGHWJNN8M",
        scenarioName = "骑手列表-获取城市装备打卡配置日志",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("查看城市装备打卡配置日志")
public class EquipmentCheckConfigLogTests {

    @DisplayName("获取城市装备打卡配置日志")
    @Test
    void shouldGetEquipmentCheckConfigLog() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取城市装备打卡配置列表，提取第一个configNo
        String configNo = getFirstConfigNo(token);

        // 3) 获取城市装备打卡配置日志
        if (configNo != null && !configNo.isEmpty()) {
            getConfigLog(token, configNo);
        }
    }

    private String getFirstConfigNo(String token) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/equipmentCheckConfig/list";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"cityList\":[],\"pageNo\":1,\"pageSize\":10}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        Object configNoObj = TestCaseHelpful.extractValue(responseBody, "$.data.list.[0].configNo");
        return configNoObj != null ? configNoObj.toString() : null;
    }

    private void getConfigLog(String token, String configNo) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/equipmentCheckConfig/configLog";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\"configNo\":\"%s\",\"pageNo\":1,\"pageSize\":10}", configNo);

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

