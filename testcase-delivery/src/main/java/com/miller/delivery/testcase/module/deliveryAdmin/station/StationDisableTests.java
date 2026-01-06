package com.miller.delivery.testcase.module.deliveryAdmin.station;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-骑手列表-禁用骑手驿站
 */
@Scenario(
        scenarioID = "01KDSTRHPEE7N52SW9M3ZC6Q6C",
        scenarioName = "骑手列表-禁用骑手驿站",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("禁用骑手驿站")
public class StationDisableTests {

    private static final String CONFIG_NO = "123456"; // 假设一个configNo

    @DisplayName("禁用驿站")
    @Test
    void shouldDisableStation() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 禁用驿站
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/station/configStatus";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"configNo\":\"" + CONFIG_NO + "\",\"configStatus\":0}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言禁用成功
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
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

