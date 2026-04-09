package com.miller.delivery.testcase.module.deliveryAdmin.orderManagement.specialOrder;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

@Scenario(
        scenarioID = "01JPSBW6QVB47F9DC0BRWMQMET",
        scenarioName = "司管后台-订单管理-特殊单列表-获取列表数据",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("获取特殊单列表")
public class SpecialOrderListTests {

    private Map<String, Object> createHeaders(String token) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", token);
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }

    @DisplayName("获取特殊单列表")
    @Test
    void shouldGetSpecialOrderList() {
        // 1) 司管登录获取 token
        String token = erpLogin();

        // 2) 获取特殊单列表
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/special-order/special-orders";
        String method = "POST";
        Map<String, Object> headers = createHeaders(token);
        String body = "{\r\n" +
                "    \"duty\": null,\r\n" +
                "    \"city\": null,\r\n" +
                "    \"startDate\": null,\r\n" +
                "    \"endDate\": null,\r\n" +
                "    \"specialNo\": null,\r\n" +
                "    \"orderSn\": null,\r\n" +
                "    \"operator\": null,\r\n" +
                "    \"orderBusinessType\": null,\r\n" +
                "    \"siteId\": null,\r\n" +
                "    \"pageNo\": 1,\r\n" +
                "    \"pageSize\": 10\r\n" +
                "}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("message").isEqualTo("成功");
    }
}

