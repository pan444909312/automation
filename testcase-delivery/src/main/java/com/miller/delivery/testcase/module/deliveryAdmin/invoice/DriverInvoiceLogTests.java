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
 * 账期-获取账期详情页-日志
 *
 * Apifox: docs/d-apifox/toCheck/获取账期详情 -日志.apifox-cli.json
 */
@Scenario(
        scenarioID = "01KDQBHVS44NNRWRFXWY1AA8YV",
        scenarioName = "账期-获取账期详情页-日志",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("获取账期详情-日志")
public class DriverInvoiceLogTests {

    @DisplayName("获取账期详情页-日志")
    @Test
    void shouldGetInvoiceLog() {
        String token = erpLogin();
        Map<String, Object> headers = createHeaders(token);

        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/driver/invoice/driverInvoiceLog";
        String body = "{\"invoiceId\":\"1113\"}";
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        assert responseBody.contains("提交薪资");
    }

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

