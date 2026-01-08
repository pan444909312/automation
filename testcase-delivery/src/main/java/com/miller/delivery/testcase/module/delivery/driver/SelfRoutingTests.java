package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 路径规划-自行规划
 */
@Scenario(
        scenarioID = "01JPP7PRQQJRJNTEMDM211NHPN",
        scenarioName = "骑手app-路径规划-自行规划",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("路径规划-自行规划")
public class SelfRoutingTests {

    @DisplayName("自行规划-正常")
    @Test
    void shouldGetRoutingPlan() {
        String token = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/deliveringOrderRouting";
        Map<String, Object> headers = createHeaders();
        headers.put("authorization", token);

        var response = TestCaseHelpful.sendRequest("POST", uri, null, headers, "{}");

        TestCaseHelpful.assertThatJson(response).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(response).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(response).node("success").isEqualTo(true);
    }

    @DisplayName("自行规划-未登录")
    @Test
    void shouldFailWithoutLogin() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/deliveringOrderRouting";
        Map<String, Object> headers = createHeaders();
        // intentionally no authorization

        var response = TestCaseHelpful.sendRequest("POST", uri, null, headers, "{}");

        TestCaseHelpful.assertThatJson(response).node("resultCode").isEqualTo(2015);
        TestCaseHelpful.assertThatJson(response).node("reason").isEqualTo("未登录,请登录后操作");
        TestCaseHelpful.assertThatJson(response).node("success").isEqualTo(false);
    }

    private Map<String, Object> createHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("longitude", "120.2168888");
        headers.put("latitude", "30.2035086");
        headers.put("version", "5.55.0");
        headers.put("platform", "ANDROID_DELIVERY");
        headers.put("type", "3");
        headers.put("locale", "zh-CN");
        headers.put("operatingsystem", "1");
        headers.put("brand", "samsung");
        headers.put("uniquetoken", "34ea70ca94766bbc");
        headers.put("apptypeid", "2");
        headers.put("countrycode", "CN");
        headers.put("devicesafetoken", "a0_b1_c0_h0_i0_j0_m0_n0_p0_s0");
        headers.put("enableSign", "false");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}


