package com.miller.delivery.testcase.module.deliveryAdmin.orderManagement.dashboard.performanceKanban;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.DeliveryTestCaseUtils.createErpHeaders;
import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

@Scenario(
        scenarioID = "01KNS2FAR9K2WWRSANM3QQEWTG",
        scenarioName = "司管后台-订单管理-实时看板-订单数据-获取订单数据列表页-待商家接单tab",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("待商家接单tab")
public class OrderDataPendingMerchantOrderListTests {

    @DisplayName("待商家接单tab")
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
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", token);
        String body = String.format("{\r\n" +
                "    \"cityList\": [\r\n" +
                "        \"杭州市\"\r\n" +
                "    ],\r\n" +
                "    \"startDate\": \"%s\",\r\n" +
                "    \"endDate\": \"%s\",\r\n" +
                "    \"dateType\": 0,\r\n" +
                "    \"gradeList\": [],\r\n" +
                "    \"oneDataType\": 1,\r\n" +
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



}

