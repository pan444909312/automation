package com.miller.delivery.testcase.module.deliveryAdmin.dashboard;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 司管后台-订单管理-实时看板-新老骑手
 */
@Scenario(
        scenarioID = "01JPPPHMPY9T02Y1ZSD541JZSV",
        scenarioName = "司管后台-订单管理-实时看板-新老骑手",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("3mile内新老骑手报表")
public class ThreeMileNewOldDriverReportTests {

    @DisplayName("新老骑手报表")
    @Test
    void shouldGetNewOldDriverReport() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取新老骑手报表
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryDashboard/tailOrder/dashBoard/newDriver";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String body = "{\n" +
                "    \"cityList\": [\n" +
                "        \"杭州市\"\n" +
                "    ],\n" +
                "    \"deliveryAreaIdList\": [],\n" +
                "    \"runTypeList\": [],\n" +
                "    \"excludeShop\": 0,\n" +
                "    \"excludeWeather\": 0,\n" +
                "    \"excludeDuty\": 0,\n" +
                "    \"startDate\": \"" + todayDate + "\",\n" +
                "    \"endDate\": \"" + todayDate + "\",\n" +
                "    \"date\": \"\",\n" +
                "    \"distanceType\": 0\n" +
                "}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言获取成功
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

