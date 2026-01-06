package com.miller.delivery.testcase.module.deliveryAdmin.driverLayer;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-新增分层
 */
@Scenario(
        scenarioID = "01K601Y3C7D32HJ6W14CRKYTDP",
        scenarioName = "司管-新增分层",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 20)
@DisplayName("新增分层")
public class DriverLayerAddTests {

    @DisplayName("新增分层-成功")
    @Test
    void shouldAddDriverLayer() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 新增分层
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/driverLayer/add";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\n" +
                "    \"cityList\": [\n" +
                "        \"日照市\"\n" +
                "    ],\n" +
                "    \"workTypeList\": [\n" +
                "        0,\n" +
                "        1\n" +
                "    ],\n" +
                "    \"levelCount\": 1,\n" +
                "    \"levelRules\": [\n" +
                "        {\n" +
                "            \"layerLevel\": \"D\",\n" +
                "            \"efficiencyMin\": 1,\n" +
                "            \"efficiencyMax\": 2,\n" +
                "            \"attendanceDaysMin\": 2,\n" +
                "            \"attendanceDaysMax\": 3,\n" +
                "            \"peakDaysMin\": 4,\n" +
                "            \"peakDaysMax\": 5,\n" +
                "            \"weekendHoursMin\": 6,\n" +
                "            \"weekendHoursMax\": 7\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言新增成功
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
    }

    @DisplayName("新增分层-工作类型为空")
    @Test
    void shouldFailToAddDriverLayerWithEmptyWorkType() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 新增分层-工作类型为空
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/driverLayer/add";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\n" +
                "    \"cityList\": [\n" +
                "        \"日照市\"\n" +
                "    ],\n" +
                "    \"workTypeList\": [\n" +
                "\n" +
                "    ],\n" +
                "    \"levelCount\": 1,\n" +
                "    \"levelRules\": [\n" +
                "        {\n" +
                "            \"layerLevel\": \"D\",\n" +
                "            \"efficiencyMin\": 1,\n" +
                "            \"efficiencyMax\": 2,\n" +
                "            \"attendanceDaysMin\": 2,\n" +
                "            \"attendanceDaysMax\": 3,\n" +
                "            \"peakDaysMin\": 4,\n" +
                "            \"peakDaysMax\": 5,\n" +
                "            \"weekendHoursMin\": 6,\n" +
                "            \"weekendHoursMax\": 7\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言接口返回包含：请选择工作类型
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(9999);
        String message = TestCaseHelpful.extractValue(responseBody, "$.message").toString();
        assert message.contains("请选择工作类型") : "应该返回请选择工作类型";
    }

    private String erpLogin() {
        return TestCaseHelpful.erpLogin();
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("Accept", "*/*");
        headers.put("Accept-Language", "zh-CN,zh;q=0.9");
        headers.put("Authorization", token);
        headers.put("Connection", "keep-alive");
        headers.put("Origin", "https://hp-delivery-admin-f2e-test.hungrypanda.cn");
        headers.put("Referer", "https://hp-delivery-admin-f2e-test.hungrypanda.cn/");
        headers.put("Sec-Fetch-Dest", "empty");
        headers.put("Sec-Fetch-Mode", "cors");
        headers.put("Sec-Fetch-Site", "same-site");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36");
        headers.put("sec-ch-ua", "\"Chromium\";v=\"140\", \"Not=A?Brand\";v=\"24\", \"Google Chrome\";v=\"140\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"Windows\"");
        headers.put("enableSign", "false");
        headers.put("Content-Type", "application/json;charset=UTF-8");
        return headers;
    }
}

