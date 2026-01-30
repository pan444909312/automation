package com.miller.delivery.testcase.module.deliveryAdmin.planManagement.capacityPlan;

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
        scenarioID = "01JPPP4BVR8ZSHKZVMPQKDK1ZJ",
        scenarioName = "司管后台-排班计划-自行发布本周",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("自行发布-本周")
public class CapacityPlanManualReleaseTests {

    private static final Long PLAN_ID = 1076L; // 假设一个planId

    @DisplayName("本周")
    @Test
    void shouldReleaseCurrentWeek() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 计算本周周一、周二和周日
        LocalDate now = LocalDate.now();
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        int dayOffset = dayOfWeek == DayOfWeek.SUNDAY ? -6 : 1 - dayOfWeek.getValue();
        LocalDate monday = now.plusDays(dayOffset);
        LocalDate tuesday = monday.plusDays(1);
        LocalDate sunday = monday.plusDays(6);
        String mondayStr = monday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String tuesdayStr = tuesday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String sundayStr = sunday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 3) 自行发布本周
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/capacityPlan/manualRelease";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\r\n" +
                "    \"deadLineDate\": \"\",\r\n" +
                "    \"deadLineTime\": \"\",\r\n" +
                "    \"endDate\": \"%s\",\r\n" +
                "    \"recId\": %d,\r\n" +
                "    \"releaseType\": 2,\r\n" +
                "    \"schedulingStatusList\": [\r\n" +
                "        99,\r\n" +
                "        2,\r\n" +
                "        1,\r\n" +
                "        0,\r\n" +
                "        10\r\n" +
                "    ],\r\n" +
                "    \"week\": \"%s\",\r\n" +
                "    \"startDate\": \"%s\"\r\n" +
                "}", sundayStr, PLAN_ID, tuesdayStr, mondayStr);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 4) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
    }

    @DisplayName("下周")
    @Test
    void shouldReleaseNextWeek() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 计算下周周一、周二和周日
        LocalDate now = LocalDate.now();
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        int dayOffset = dayOfWeek == DayOfWeek.SUNDAY ? -6 : 1 - dayOfWeek.getValue();
        LocalDate monday = now.plusDays(dayOffset);
        LocalDate nextMonday = monday.plusDays(7);
        LocalDate nextTuesday = monday.plusDays(8);
        LocalDate nextSunday = monday.plusDays(13);
        String nextMondayStr = nextMonday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String nextTuesdayStr = nextTuesday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String nextSundayStr = nextSunday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 3) 自行发布下周
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/capacityPlan/manualRelease";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\r\n" +
                "    \"deadLineDate\": \"%s\",\r\n" +
                "    \"deadLineTime\": \"23:00\",\r\n" +
                "    \"endDate\": \"%s\",\r\n" +
                "    \"recId\": %d,\r\n" +
                "    \"releaseType\": 0,\r\n" +
                "    \"schedulingStatusList\": [\r\n" +
                "        99,\r\n" +
                "        2,\r\n" +
                "        1,\r\n" +
                "        0,\r\n" +
                "        10\r\n" +
                "    ],\r\n" +
                "    \"week\": \"%s\",\r\n" +
                "    \"startDate\": \"%s\"\r\n" +
                "}\r\n\r\n\r\n", nextSundayStr, nextSundayStr, PLAN_ID, nextTuesdayStr, nextMondayStr);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 4) 断言
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

