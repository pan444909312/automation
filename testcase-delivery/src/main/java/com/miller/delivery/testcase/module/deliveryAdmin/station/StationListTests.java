package com.miller.delivery.testcase.module.deliveryAdmin.station;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-骑手列表-骑手驿站列表
 */
@Scenario(
        scenarioID = "01KDST8JGRF09YYA3WEVAG9Y9J",
        scenarioName = "骑手列表-骑手驿站列表",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("获取骑手驿站配置列表")
public class StationListTests {

    @DisplayName("获取骑手驿站列表")
    @Test
    void shouldGetStationList() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取骑手驿站列表
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/station/list";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"cityList\":[],\"pageNo\":1,\"pageSize\":15}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        // 提取configno用于后续操作（如果需要）
        // String configno = TestCaseHelpful.extractValue(responseBody, "$.data.list.[0].configNo").toString();
    }

    private String erpLogin() {
        return TestCaseHelpful.erpLogin();
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

