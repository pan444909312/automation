package com.miller.delivery.testcase.module.deliveryAdmin.orderManagement.dashboard.capacity;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

@Scenario(
        scenarioID = "01JPS9PVZ3QNHJV92D6BVTY8Z2",
        scenarioName = "司管后台-订单管理-实时看板-运力概览-运力概览",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("获取运力概览数据")
public class CapacityOverviewTests {

    @DisplayName("获取运力概览数据")
    @Test
    void shouldGetCapacityOverview() throws InterruptedException {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取运力概览-到城市级别
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryDashboard/capacity/dashBoard/listData";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body1 = "{\"city\":\"杭州市\",\"areaList\":[],\"cityList\":[\"杭州市\"]}";
        var responseBody1 = TestCaseHelpful.sendRequest(method, uri, null, headers, body1);
        TestCaseHelpful.assertThatJson(responseBody1)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody1)
                .node("message").isEqualTo("成功");

        // 3) 等待5秒
        Thread.sleep(5000);

        // 4) 获取运力概览-到商圈级别
        String body2 = "{\r\n" +
                "  \"city\": \"杭州市\",\r\n" +
                "  \"areaList\": [\r\n" +
                "    51\r\n" +
                "  ],\r\n" +
                "  \"cityList\": [\r\n" +
                "    \"杭州市\"\r\n" +
                "  ]\r\n" +
                "}";
        var responseBody2 = TestCaseHelpful.sendRequest(method, uri, null, headers, body2);
        TestCaseHelpful.assertThatJson(responseBody2)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody2)
                .node("message").isEqualTo("成功");

        // 5) 等待1秒
        Thread.sleep(1000);
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

