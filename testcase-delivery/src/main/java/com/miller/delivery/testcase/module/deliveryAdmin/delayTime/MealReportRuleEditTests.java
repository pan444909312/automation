package com.miller.delivery.testcase.module.deliveryAdmin.delayTime;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

@Scenario(
        scenarioID = "01JPSC36H213D418WH9P3Y2ZV2",
        scenarioName = "司管后台-订单管理-等餐报备-查看/编辑取餐报备规则",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("编辑取餐报备规则")
public class MealReportRuleEditTests {

    @DisplayName("查看并编辑取餐报备规则")
    @Test
    void shouldViewAndEditMealReportRule() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 查看取餐报备配置
        String uri1 = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/delivery/delay/time/detail";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body1 = "{\"configId\":62}";
        var responseBody1 = TestCaseHelpful.sendRequest(method, uri1, null, headers, body1);
        TestCaseHelpful.assertThatJson(responseBody1)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody1)
                .node("message").isEqualTo("成功");

        // 3) 修改取餐报备
        String uri2 = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/delivery/delay/time/add";
        String body2 = "{\r\n" +
                "    \"configId\": 62,\r\n" +
                "    \"configName\": \"自动化测试规则\",\r\n" +
                "    \"city\": \"杭州市\",\r\n" +
                "    \"configType\": 1,\r\n" +
                "    \"groupInfoList\": [\r\n" +
                "        {\r\n" +
                "            \"groupId\": 886,\r\n" +
                "            \"groupName\": \"自动化群组\"\r\n" +
                "        }\r\n" +
                "    ],\r\n" +
                "    \"shopInfoList\": null,\r\n" +
                "    \"arriveShopTime\": 1,\r\n" +
                "    \"reportNumber\": 30,\r\n" +
                "    \"delayTime\": 10,\r\n" +
                "    \"delayType\": 0,\r\n" +
                "    \"idList\": [\r\n" +
                "        886\r\n" +
                "    ]\r\n" +
                "}";
        var responseBody2 = TestCaseHelpful.sendRequest(method, uri2, null, headers, body2);
        TestCaseHelpful.assertThatJson(responseBody2)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody2)
                .node("message").isEqualTo("成功");
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("accept", "*/*");
        headers.put("accept-language", "zh-CN,zh;q=0.9");
        headers.put("authorization", token);
        headers.put("origin", "https://hp-delivery-admin-f2e-test.hungrypanda.cn");
        headers.put("priority", "u=1, i");
        headers.put("referer", "https://hp-delivery-admin-f2e-test.hungrypanda.cn/");
        headers.put("sec-ch-ua", "\"Not(A:Brand\";v=\"99\", \"Google Chrome\";v=\"133\", \"Chromium\";v=\"133\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"Windows\"");
        headers.put("sec-fetch-dest", "empty");
        headers.put("sec-fetch-mode", "cors");
        headers.put("sec-fetch-site", "same-site");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36");
         
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

