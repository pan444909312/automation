package com.miller.delivery.testcase.module.deliveryAdmin.punish;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-新增骑手禁止上线配置
 */
@Scenario(
        scenarioID = "01KDQN7QFSYBVRMA82VZ12H1WM",
        scenarioName = "新增骑手禁止上线配置",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("新增骑手禁止上线配置")
public class PunishAppealConfigAddTests {

    @DisplayName("新增骑手禁止上线配置")
    @Test
    void shouldAddPunishAppealConfig() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 新增骑手禁止上线配置
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/punishAppealContentConfig/save";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\n" +
                "    \"cityList\": [\n" +
                "        \"奥克兰\"\n" +
                "    ],\n" +
                "    \"canAppeal\": 0,\n" +
                "    \"controlReason\": 0,\n" +
                "    \"controlAudit\": 0,\n" +
                "    \"personRemind\": 0,\n" +
                "    \"controlContent\": \"测试路路\",\n" +
                "    \"controlContentEn\": \"测试路路\"\n" +
                "}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言可能成功或已存在配置
        Integer code = Integer.valueOf(TestCaseHelpful.extractValue(responseBody, "$.code").toString());
        assert code == 1 || code == 9999 : "code应该是1或9999";
        String message = TestCaseHelpful.extractValue(responseBody, "$.message").toString();
        assert message.equals("成功") || message.contains("已存在配置") : "message应该是成功或已存在配置";
    }

    private String erpLogin() {
        return TestCaseHelpful.erpLogin();
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

