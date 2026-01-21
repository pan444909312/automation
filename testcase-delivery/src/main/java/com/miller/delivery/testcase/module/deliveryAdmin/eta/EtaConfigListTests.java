package com.miller.delivery.testcase.module.deliveryAdmin.eta;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * ETA配置列表
 *
 * @author 彭路路
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01K628B4XEYDPES67Q6N6FND1P",
        scenarioName = "司管-ETA列表",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 20)
@DisplayName("ETA配置列表")
public class EtaConfigListTests {

    private static final String CITY_ID = "729";

    @DisplayName("eta配置列表")
    @Test
    void shouldGetEtaConfigList() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取eta配置列表
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/eta/delivery/time/config/list";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\"pageNo\":1,\"pageSize\":100,\"cityId\":\"%s\"}", CITY_ID);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
        
        // 验证响应中包含"自动化新增ETA配送方案"
        String responseText = responseBody.toString();
        assert responseText.contains("自动化新增ETA配送方案") : "响应中未包含自动化新增ETA配送方案";
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

