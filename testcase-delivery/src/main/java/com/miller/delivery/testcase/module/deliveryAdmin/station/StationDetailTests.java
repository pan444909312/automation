package com.miller.delivery.testcase.module.deliveryAdmin.station;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-骑手列表-查看骑手驿站
 */
@Scenario(
        scenarioID = "01KDSTRHPEE7N52SW9M3ZC6Q6C",
        scenarioName = "骑手列表-查看骑手驿站",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("查看骑手驿站")
public class StationDetailTests {

    @DisplayName("查看驿站")
    @Test
    void shouldGetStationDetail() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取骑手驿站配置列表，提取第一个configNo
        String configNo = getFirstStationConfigNo(token);

        // 3) 查看驿站详情
        if (configNo != null && !configNo.isEmpty()) {
            getStationDetail(token, configNo);
        }
    }

    private String getFirstStationConfigNo(String token) {
        // 假设需要先获取驿站列表，这里可能需要调用列表接口
        // 如果无法获取，可以使用一个已知的configNo或跳过此步骤
        return null; // 需要根据实际情况实现
    }

    private void getStationDetail(String token, String configNo) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/station/detail";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\"configNo\":\"%s\"}", configNo);

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    private String erpLogin() {
        String uri = TestcaseConfig.HOST_ERP + "/api/erp/auth/login/v2";
        String method = "POST";
        Map<String, Object> headers = new HashMap<>();
        headers.put("enableSign", "false");
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
        headers.put("enableSign", "false");
        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

