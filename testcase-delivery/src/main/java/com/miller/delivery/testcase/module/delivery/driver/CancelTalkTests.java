package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 骑手app-三方会话-取消会话
 *
 * Apifox: docs/d-apifox/checked/取消会话.apifox-cli.json
 */
@Scenario(
        scenarioID = "01JWB3D09A9A4A6HHRE83KGVBW",
        scenarioName = "骑手app-三方会话-取消回话",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("取消会话")
public class CancelTalkTests {

    @DisplayName("取消会话")
    @Test
    void shouldCancelTalk() {
        // 1) 骑手登录获取 token
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        // 2) 取消会话
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/uDesk/cancelTalk";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        String body = "{}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    @DisplayName("未登录-取消会话")
    @Test
    void shouldFailWhenNotLoggedIn() {
        // 未登录时调用接口
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/uDesk/cancelTalk";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        // 不设置 authorization

        String body = "{}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(2015);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("未登录,请登录后操作");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(false);
    }

    /**
     * 创建骑手app请求头
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("longitude", "120.216878");
        headers.put("latitude", "30.203616");
        headers.put("locationstatus", "2");
        headers.put("locale", "zh-CN");
        headers.put("version", "5.64.0");
        headers.put("countrycode", "CN");
        headers.put("platform", "IOS");
        headers.put("uniquetoken", "9A95A874-6493-4DFC-A5E1-BCE3C7C265D0");
        headers.put("operatingsystem", "2");
        headers.put("brand", "iPhone 11");
        headers.put("devicesafetoken", "a0_b0_c0_h0_i0_j0_m0_n0_p0_s0");
        headers.put("apptypeid", "2");
        headers.put("accept-language", "zh-Hans;q=1");
        headers.put("accept", "*/*");

        headers.put("content-type", "application/json");
        return headers;
    }
}

