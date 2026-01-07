package com.miller.delivery.testcase.module.deliveryAdmin.order;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

@Scenario(
        scenarioID = "01JPS9XTPN43Z16W1BNPQC4PS4",
        scenarioName = "【主干功能】司管后台-订单管理-PF订单列表-获取列表数据",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("获取pf订单列表数据")
public class PfOrderListTests {

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }

    private LocalDate getMonday(LocalDate date) {
        int dayOfWeek = date.getDayOfWeek().getValue();
        return date.minusDays(dayOfWeek - 1);
    }

    private LocalDate getSunday(LocalDate date) {
        LocalDate monday = getMonday(date);
        return monday.plusDays(6);
    }

    @DisplayName("获取pf订单列表数据")
    @Test
    void shouldGetPfOrderList() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 生成本周周一和周日日期
        LocalDate today = LocalDate.now();
        LocalDate monday = getMonday(today);
        LocalDate sunday = getSunday(today);
        String mondayStr = monday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String sundayStr = sunday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 3) 获取pf订单列表
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/order/listOrderPf";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = String.format("{\r\n" +
                "    \"arrived\": [\r\n" +
                "        \"%s\",\r\n" +
                "        \"%s\"\r\n" +
                "    ],\r\n" +
                "    \"city\": \"杭州市\",\r\n" +
                "    \"deliveryStatus\": null,\r\n" +
                "    \"driverId\": null,\r\n" +
                "    \"driverPhone\": null,\r\n" +
                "    \"orderSn\": null,\r\n" +
                "    \"orderType\": null,\r\n" +
                "    \"siteId\": null,\r\n" +
                "    \"userAreaId\": null,\r\n" +
                "    \"deliverableActionList\": [],\r\n" +
                "    \"orderArriveMethodList\": [],\r\n" +
                "    \"arrivedEnd\": \"%s\",\r\n" +
                "    \"arrivedStart\": \"%s\",\r\n" +
                "    \"pageNo\": 1,\r\n" +
                "    \"pageSize\": 10\r\n" +
                "}", mondayStr, sundayStr, sundayStr, mondayStr);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 4) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
    }
}

