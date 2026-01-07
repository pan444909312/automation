package com.miller.delivery.testcase.module.deliveryAdmin.cancellationConfig;

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
        scenarioID = "01JPSC1DTNS93KDECBAF7WS631",
        scenarioName = "司管后台-订单管理-撤单详情-或者单详情列表",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("获取撤单详情列表")
public class CancellationDetailListTests {

    @DisplayName("获取撤单详情列表")
    @Test
    void shouldGetCancellationDetailList() {
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

        // 3) 获取撤单详情列表
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/cancellationConfig/cancellationRecordList";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\r\n" +
                "    \"auditStatus\": null,\r\n" +
                "    \"cityList\": [\r\n" +
                "        \"九江市\",\r\n" +
                "        \"杭州市\"\r\n" +
                "    ],\r\n" +
                "    \"driverName\": null,\r\n" +
                "    \"driverId\": null,\r\n" +
                "    \"driverTel\": null,\r\n" +
                "    \"endDate\": \"%s\",\r\n" +
                "    \"fakeStatus\": null,\r\n" +
                "    \"startDate\": \"%s\",\r\n" +
                "    \"pageNo\": 1,\r\n" +
                "    \"pageSize\": 15\r\n" +
                "}", sundayStr, mondayStr);
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
        headers.put("enableSign", "false");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

