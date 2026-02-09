package com.miller.delivery.testcase.module.deliveryApp.capacityScheduling;

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

/**
 * 预约排班确认
 */
@Scenario(
        scenarioID = "01JPP75R07CDQ60SMFBXV3Z9B2",
        scenarioName = "骑手app-计划tab-预约排班",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("预约排班-确认同意")
public class ReserveSchedulingConfirmTests {
    private static final Long PLAN_ID = 1076L; // 假设一个planId

    @DisplayName("预约排班-确认同意")
    @Test
    void shouldReserveScheduling() {

        String token = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        String s = erpLogin();
        shouldReleaseCurrentWeek(s);
        // 等待2秒
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/capacityScheduling/reserveSchedulingConfirm";
        Map<String, Object> headers = createHeaders();
        headers.put("authorization", token);

        String nowDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String body = String.format("{\"areaId\":51,\"schedulingDateList\":[\"%s\"],\"startTime\":\"22:30\",\"endTime\":\"24:00\"}", nowDate);

        var response = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(response).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(response).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(response).node("success").isEqualTo(true);
        // 等待2秒
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @DisplayName("预约排班-参数为空")
    @Test
    void shouldFailWithEmptyTime() {
        String token = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/capacityScheduling/reserveSchedulingConfirm";
        Map<String, Object> headers = createHeaders();
        headers.put("authorization", token);

        String nowDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-M-d"));
        String body = String.format("{\"areaId\":51,\"schedulingDateList\":[\"%s\"],\"startTime\":\"\",\"endTime\":\"\"}", nowDate);

        var response = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(response).node("resultCode").isEqualTo(101011);
        TestCaseHelpful.assertThatJson(response).node("reason").isEqualTo("参数错误");
        TestCaseHelpful.assertThatJson(response).node("success").isEqualTo(false);
        // 等待2秒
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @DisplayName("预约排班-未登录")
    @Test
    void shouldRejectWithoutAuth() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/capacityScheduling/reserveSchedulingConfirm";
        Map<String, Object> headers = createHeaders();

        String nowDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-M-d"));
        String body = String.format("{\"areaId\":51,\"schedulingDateList\":[\"%s\"],\"startTime\":\"22:30\",\"endTime\":\"24:00\"}", nowDate);

        var response = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(response).node("resultCode").isEqualTo(2015);
        TestCaseHelpful.assertThatJson(response).node("reason").isEqualTo("未登录,请登录后操作");
        TestCaseHelpful.assertThatJson(response).node("success").isEqualTo(false);
        // 等待2秒
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private Map<String, Object> createHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("platform", "");

        headers.put("Content-Type", "application/json");
        return headers;
    }




    private void shouldReleaseCurrentWeek(String token) {
        // 1) 司管登录获取 token


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
        // 等待2秒
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
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


