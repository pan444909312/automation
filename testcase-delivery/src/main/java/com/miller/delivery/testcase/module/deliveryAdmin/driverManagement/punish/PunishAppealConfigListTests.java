package com.miller.delivery.testcase.module.deliveryAdmin.driverManagement.punish;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-禁止骑手上线配置
 */
@Scenario(
        scenarioID = "01KDQN1S13G37DXAE7RRGNHPNS",
        scenarioName = "禁止骑手上线配置",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("获取禁止上线配置列表信息")
public class PunishAppealConfigListTests {

    @DisplayName("获取禁止骑手上线配置列表")
    @Test
    void shouldGetPunishAppealConfigList() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取禁止骑手上线配置列表
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/punishAppealContentConfig/list";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"cityList\":[\"奥克兰\"],\"pageNo\":1,\"pageSize\":10}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        // 提取configNo用于后续操作（如果需要）
        // String configNo = TestCaseHelpful.extractValue(responseBody, "$.data.[0].configNo").toString();
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

