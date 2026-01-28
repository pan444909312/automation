package com.miller.delivery.testcase.module.deliveryAdmin.invoice;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 账期-获取账期详情页
 *
 * Apifox: docs/d-apifox/toCheck/获取账期详情.apifox-cli.json
 */
@Scenario(
        scenarioID = "01KDQBBNTJJA7SAEM49JYC6R9K",
        scenarioName = "账期-获取账期详情页",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("获取账期详情")
public class DriverInvoiceDetailTests {

    @DisplayName("获取账期详情页-已结算")
    @Test
    void shouldGetInvoiceDetailSettled() {
        String token = erpLogin();
        Map<String, Object> headers = createHeaders(token);

        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/driver/invoice/driverInvoiceDetail";
        String body = "{\"invoiceId\":\"1116\"}";
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        assert responseBody.contains("IN501407738175581344");
    }

    @DisplayName("获取账期详情页-已拒绝")
    @Test
    void shouldGetInvoiceDetailRejected() {
        String token = erpLogin();
        Map<String, Object> headers = createHeaders(token);

        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/driver/invoice/driverInvoiceDetail";
        String body = "{\"invoiceId\":\"1113\"}";
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        assert responseBody.contains("IN501406359795096640");
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

