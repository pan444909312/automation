package com.miller.delivery.testcase.module.deliveryAdmin.capacityScheduling;

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
        scenarioID = "01JPPP121M3ACXY7EM9RZESF8W",
        scenarioName = "司管后台-排班计划-进入操作台发布计划",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("进入操作台-发布计划")
public class CapacitySchedulingManualReleaseTests {

    @DisplayName("操作台发布当周计划")
    @Test
    void shouldReleaseCurrentWeekPlan() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 计算本周周一和周日
        LocalDate now = LocalDate.now();
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        int dayOffset = dayOfWeek == DayOfWeek.SUNDAY ? -6 : 1 - dayOfWeek.getValue();
        LocalDate monday = now.plusDays(dayOffset);
        LocalDate sunday = monday.plusDays(6);
        String mondayStr = monday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String sundayStr = sunday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 3) 操作台发布当周计划
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/capacityScheduling/manualRelease";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\r\n" +
                "    \"deadLineDate\": \"\",\r\n" +
                "    \"deadLineTime\": \"\",\r\n" +
                "    \"releaseType\": 2,\r\n" +
                "    \"startDate\": \"%s\",\r\n" +
                "    \"endDate\": \"%s\",\r\n" +
                "    \"driverReqList\": [\r\n" +
                "        {\r\n" +
                "            \"dateList\": [\r\n" +
                "                {\r\n" +
                "                    \"areaInfoList\": [\r\n" +
                "                        {\r\n" +
                "                            \"areaId\": 51,\r\n" +
                "                            \"areaName\": \"滨江区-不要改围栏\",\r\n" +
                "                            \"timeList\": [\r\n" +
                "                                {\r\n" +
                "                                    \"startTime\": \"00:00\",\r\n" +
                "                                    \"endTime\": \"23:30\"\r\n" +
                "                                }\r\n" +
                "                            ]\r\n" +
                "                        }\r\n" +
                "                    ],\r\n" +
                "                    \"schedulingDate\": \"%s\"\r\n" +
                "                }\r\n" +
                "            ],\r\n" +
                "            \"driverId\": 1398714150\r\n" +
                "        }\r\n" +
                "    ],\r\n" +
                "    \"city\": \"杭州市\"\r\n" +
                "}", mondayStr, sundayStr, sundayStr);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 4) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
    }

    @DisplayName("操作台发布下周计划")
    @Test
    void shouldReleaseNextWeekPlan() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 计算下周周一和周日
        LocalDate now = LocalDate.now();
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        int dayOffset = dayOfWeek == DayOfWeek.SUNDAY ? -6 : 1 - dayOfWeek.getValue();
        LocalDate monday = now.plusDays(dayOffset);
        LocalDate nextMonday = monday.plusDays(7);
        LocalDate nextSunday = monday.plusDays(13);
        String nextMondayStr = nextMonday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String nextSundayStr = nextSunday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 3) 操作台发布下周计划
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/capacityScheduling/manualRelease";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\r\n" +
                "    \"deadLineDate\": \"\",\r\n" +
                "    \"deadLineTime\": \"\",\r\n" +
                "    \"releaseType\": 2,\r\n" +
                "    \"startDate\": \"%s\",\r\n" +
                "    \"endDate\": \"%s\",\r\n" +
                "    \"driverReqList\": [\r\n" +
                "        {\r\n" +
                "            \"dateList\": [\r\n" +
                "                {\r\n" +
                "                    \"areaInfoList\": [\r\n" +
                "                        {\r\n" +
                "                            \"areaId\": 51,\r\n" +
                "                            \"areaName\": \"滨江区-不要改围栏\",\r\n" +
                "                            \"timeList\": [\r\n" +
                "                                {\r\n" +
                "                                    \"startTime\": \"00:00\",\r\n" +
                "                                    \"endTime\": \"23:30\"\r\n" +
                "                                }\r\n" +
                "                            ]\r\n" +
                "                        }\r\n" +
                "                    ],\r\n" +
                "                    \"schedulingDate\": \"%s\"\r\n" +
                "                }\r\n" +
                "            ],\r\n" +
                "            \"driverId\": 1398714150\r\n" +
                "        }\r\n" +
                "    ],\r\n" +
                "    \"city\": \"杭州市\"\r\n" +
                "}", nextMondayStr, nextSundayStr, nextSundayStr);
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

