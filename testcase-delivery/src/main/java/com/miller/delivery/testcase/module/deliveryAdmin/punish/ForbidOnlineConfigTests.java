package com.miller.delivery.testcase.module.deliveryAdmin.punish;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 禁止上线配置
 *
 * @author 陈春霞
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01KEDNG43262W23GSYBG299HE3", // 需要从质量平台获取实际的 scenarioID
        scenarioName = "禁止上线",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 20)
@DisplayName("禁止上线")
public class ForbidOnlineConfigTests {

    @DisplayName("添加禁止上线配置")
    @Test
    void shouldAddForbidOnlineConfig() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 添加禁止上线配置
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/punishAppealContentConfig/save";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\n" +
                "    \"cityList\": [\n" +
                "        \"杭州16\"\n" +
                "    ],\n" +
                "    \"canAppeal\": 0,\n" +
                "    \"controlReason\": 0,\n" +
                "    \"controlAudit\": 0,\n" +
                "    \"personRemind\": 0,\n" +
                "    \"controlContent\": \"3\",\n" +
                "    \"controlContentEn\": \"4\"\n" +
                "}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言（可能成功或已存在配置）
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isIn(1, 9999);
        // message 可能是"成功"或"已存在配置"
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isNotNull();
    }

    /**
     * 创建请求头
     */
    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("accept", "*/*");
        headers.put("accept-language", "zh-CN,zh;q=0.9");
        headers.put("authorization", token);
        headers.put("origin", "https://hp-delivery-admin-f2e-test.hungrypanda.cn");
        headers.put("priority", "u=1, i");
        headers.put("referer", "https://hp-delivery-admin-f2e-test.hungrypanda.cn/");
        headers.put("sec-ch-ua", "\"Google Chrome\";v=\"143\", \"Chromium\";v=\"143\", \"Not A(Brand\";v=\"24\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"Windows\"");
        headers.put("sec-fetch-dest", "empty");
        headers.put("sec-fetch-mode", "cors");
        headers.put("sec-fetch-site", "same-site");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36");

        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

