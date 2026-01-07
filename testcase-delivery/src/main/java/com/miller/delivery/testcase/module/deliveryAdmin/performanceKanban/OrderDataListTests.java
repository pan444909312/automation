package com.miller.delivery.testcase.module.deliveryAdmin.performanceKanban;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Scenario(
        scenarioID = "01JPS9R9KAHNWJX4YRB9ZHBP2S",
        scenarioName = "司管后台-订单管理-实时看板-订单数据-获取订单数据列表页",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("获取订单数据列表页")
public class OrderDataListTests {

    @DisplayName("获取订单数据列表")
    @Test
    void shouldGetOrderDataList() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 生成当天日期
        LocalDate today = LocalDate.now();
        String todayDate = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 3) 获取订单数据列表
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/performanceKanban/queryData";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\r\n" +
                "    \"cityList\": [\r\n" +
                "        \"杭州市\"\r\n" +
                "    ],\r\n" +
                "    \"startDate\": \"%s\",\r\n" +
                "    \"endDate\": \"%s\",\r\n" +
                "    \"dateType\": 0,\r\n" +
                "    \"gradeList\": [],\r\n" +
                "    \"oneDataType\": 0,\r\n" +
                "    \"orderSn\": null,\r\n" +
                "    \"orderType\": 0,\r\n" +
                "    \"orderTypeList\": [],\r\n" +
                "    \"deliveryTypeList\": [],\r\n" +
                "    \"shopName\": null,\r\n" +
                "    \"sortType\": null,\r\n" +
                "    \"twoDataType\": null,\r\n" +
                "    \"userLabelList\": [],\r\n" +
                "    \"sortColumType\": null,\r\n" +
                "    \"deliveryPlatformList\": [],\r\n" +
                "    \"activityTypeList\": [],\r\n" +
                "    \"customizeTypeList\": [],\r\n" +
                "    \"transportationList\": [],\r\n" +
                "    \"pageNo\": 1,\r\n" +
                "    \"pageSize\": 15\r\n" +
                "}", todayDate, todayDate);
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

