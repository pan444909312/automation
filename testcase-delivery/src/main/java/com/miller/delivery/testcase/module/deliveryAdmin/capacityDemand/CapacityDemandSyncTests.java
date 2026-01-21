package com.miller.delivery.testcase.module.deliveryAdmin.capacityDemand;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Scenario(
        scenarioID = "01JPPNNJDA7Z1J5ZV6Z9VQYJYE",
        scenarioName = "司管后台-计划管理-同步运力",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("同步运力")
public class CapacityDemandSyncTests {

    @DisplayName("同步本周运力")
    @Test
    void shouldSyncCapacityDemand() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 计算本周周一和周日
        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        int dayOffset = dayOfWeek == DayOfWeek.SUNDAY ? -6 : 1 - dayOfWeek.getValue();
        LocalDate monday = today.plusDays(dayOffset);
        LocalDate sunday = monday.plusDays(6);
        String mondayStr = monday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String sundayStr = sunday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 4) 同步运力
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/capacityDemand/syncCapacityDemand";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\r\n" +
                "    \"areaIdList\": [\r\n" +
                "        51\r\n" +
                "    ],\r\n" +
                "    \"city\": \"杭州市\",\r\n" +
                "    \"endDate\": \"%s\",\r\n" +
                "    \"endTime\": \"01:00\",\r\n" +
                "    \"redundancyNum\": 1,\r\n" +
                "    \"startDate\": \"%s\",\r\n" +
                "    \"startTime\": \"00:00\",\r\n" +
                "    \"redundancy\": 0\r\n" +
                "}", sundayStr, mondayStr);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 5) 断言
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
        headers.put("sec-ch-ua", "\"Google Chrome\";v=\"131\", \"Chromium\";v=\"131\", \"Not_A Brand\";v=\"24\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"Windows\"");
        headers.put("sec-fetch-dest", "empty");
        headers.put("sec-fetch-mode", "cors");
        headers.put("sec-fetch-site", "same-site");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36");

        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

