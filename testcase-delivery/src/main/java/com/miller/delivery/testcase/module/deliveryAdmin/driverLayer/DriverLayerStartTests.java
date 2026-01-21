package com.miller.delivery.testcase.module.deliveryAdmin.driverLayer;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-开启/关闭分层
 */
@Scenario(
        scenarioID = "01K628CS8XPMBBXKNFD6QNQ5B1",
        scenarioName = "司管-开启/关闭分层",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("启动分层")
public class DriverLayerStartTests {

    private static final Long LAYER_CONFIG_ID = 12345L; // 假设一个layer_config_id

    @DisplayName("开启分层")
    @Test
    void shouldStartDriverLayer() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 开启分层
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/driverLayer/configStatus";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"id\":" + LAYER_CONFIG_ID + ",\"status\":1}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言开启成功
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
    }

    @DisplayName("关闭分层")
    @Test
    void shouldStopDriverLayer() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 关闭分层
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/driverLayer/configStatus";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\"id\":" + LAYER_CONFIG_ID + ",\"status\":0}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言关闭成功
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
        headers.put("accept", "*/*");
        headers.put("accept-language", "zh-CN,zh;q=0.9");
        headers.put("authorization", token);
        headers.put("origin", "https://hp-delivery-admin-f2e-test.hungrypanda.cn");
        headers.put("priority", "u=1, i");
        headers.put("referer", "https://hp-delivery-admin-f2e-test.hungrypanda.cn/");
        headers.put("sec-ch-ua", "\"Chromium\";v=\"140\", \"Not=A?Brand\";v=\"24\", \"Google Chrome\";v=\"140\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"Windows\"");
        headers.put("sec-fetch-dest", "empty");
        headers.put("sec-fetch-mode", "cors");
        headers.put("sec-fetch-site", "same-site");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36");

        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

