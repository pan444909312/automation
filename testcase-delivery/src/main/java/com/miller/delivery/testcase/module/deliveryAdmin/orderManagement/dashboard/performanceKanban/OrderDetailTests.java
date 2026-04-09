package com.miller.delivery.testcase.module.deliveryAdmin.orderManagement.dashboard.performanceKanban;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static com.miller.delivery.testcase.utils.DeliveryTestCaseUtils.createErpHeaders;
import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

@Scenario(
        scenarioID = "01KNS2ZZBNNS08VVQ7KNQ0VZYD",
        scenarioName = "司管后台-订单管理-实时看板-订单数据-订单详情页",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("订单详情页")
public class OrderDetailTests {

    @DisplayName("订单详情页")
    @Test
    void shouldGetOrderDataList() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 生成当天日期
        LocalDate today = LocalDate.now();
        String todayDate = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 3) 获取订单数据列表
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/order/orderDetail";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", token);
        String body = String.format("{\"orderSn\":\"7721709905174783131829\"}");
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 4) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
    }



}

