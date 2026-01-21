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
        scenarioID = "01JPPNYXFFH9DSW7FSH64G8RCH",
        scenarioName = "司管后台-计划排班-获取排班列表数据",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 60)
@DisplayName("获取班表列表数据")
public class CapacitySchedulingListTests {

    @DisplayName("获取当周列表数据")
    @Test
    void shouldGetCurrentWeekList() {
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

        // 3) 获取当周列表数据
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/capacityScheduling/list";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\r\n" +
                "    \"city\": \"杭州市\",\r\n" +
                "    \"dataType\": \"1\",\r\n" +
                "    \"areaIdList\": [],\r\n" +
                "    \"driverGroupIdList\": [],\r\n" +
                "    \"driverIdStr\": \"\",\r\n" +
                "    \"driverName\": \"\",\r\n" +
                "    \"driverPhone\": \"\",\r\n" +
                "    \"firstDayOfWeek\": \"%s\",\r\n" +
                "    \"transportation\": \"\",\r\n" +
                "    \"lackTime\": \"\",\r\n" +
                "    \"schedulingDateNum\": \"\",\r\n" +
                "    \"schedulingTime\": \"\",\r\n" +
                "    \"startDate\": \"%s\",\r\n" +
                "    \"endDate\": \"%s\",\r\n" +
                "    \"pageNo\": 1,\r\n" +
                "    \"pageSize\": 30\r\n" +
                "}", tuesdayStr, mondayStr, sundayStr);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 4) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
    }

    @DisplayName("获取下周列表数据")
    @Test
    void shouldGetNextWeekList() {
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

        // 3) 获取下周列表数据
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/capacityScheduling/list";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\r\n" +
                "    \"city\": \"杭州市\",\r\n" +
                "    \"dataType\": \"1\",\r\n" +
                "    \"areaIdList\": [],\r\n" +
                "    \"driverGroupIdList\": [],\r\n" +
                "    \"driverIdStr\": \"\",\r\n" +
                "    \"driverName\": \"\",\r\n" +
                "    \"driverPhone\": \"\",\r\n" +
                "    \"firstDayOfWeek\": \"%s\",\r\n" +
                "    \"transportation\": \"\",\r\n" +
                "    \"lackTime\": \"\",\r\n" +
                "    \"schedulingDateNum\": \"\",\r\n" +
                "    \"schedulingTime\": \"\",\r\n" +
                "    \"startDate\": \"%s\",\r\n" +
                "    \"endDate\": \"%s\",\r\n" +
                "    \"pageNo\": 1,\r\n" +
                "    \"pageSize\": 30\r\n" +
                "}", nextTuesdayStr, nextMondayStr, nextSundayStr);
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

