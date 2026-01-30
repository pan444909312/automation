package com.miller.delivery.testcase.module.deliveryApp.homePop;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 骑手app-登录后默认请求的接口-绑定极光
 *
 * Apifox: docs/d-apifox/绑定极光.apifox-cli.json
 */
@Scenario(
        scenarioID = "01JPP6HRY5S3GZD75M5TJP7KCD",
        scenarioName = "骑手app-登录后默认请求的接口-绑定极光",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 20, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("绑定极光")
public class JPushBindAliasTests {

    @DisplayName("绑定极光-成功 & 参数校验 & 未登录")
    @Test
    void shouldBindAliasCases() {
        // 未登录
        bindAliasRaw(null, "{\"registrationId\":\"160a3797c931bc4ada7\",\"alias\":\"fd72c4097be9e36661dac3180eca91a0\",\"type\":2}",
                2015, "未登录,请登录后操作", false);

        // 登录
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        // 成功
        bindAliasRaw(driverAccessToken, "{\"registrationId\":\"160a3797c931bc4ada7\",\"alias\":\"fd72c4097be9e36661dac3180eca91a0\",\"type\":2}",
                1000, "成功", true);

        // registrationId为空
        bindAliasRaw(driverAccessToken, "{\"registrationId\":\"\",\"alias\":\"fd72c4097be9e36661dac3180eca91a0\",\"type\":2}",
                101011, "参数错误", false);

        // alias为空
        bindAliasRaw(driverAccessToken, "{\"registrationId\":\"160a3797c931bc4ada7\",\"alias\":\"\",\"type\":2}",
                101011, "参数错误", false);
    }

    @DisplayName("iOS绑定极光（APNS）-成功 & registrationId为空")
    @Test
    void shouldBindApnsAliasCases() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/jPush/bindApnsAlias";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        // success
        String body1 = "{\"registrationId\":\"54e27a18988aa6c50f71db011dba883de55e51d67738affdb4ace838ba196600\"}";
        var responseBody1 = TestCaseHelpful.sendRequest("POST", uri, null, headers, body1);
        TestCaseHelpful.assertThatJson(responseBody1).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody1).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody1).node("success").isEqualTo(true);

        // registrationId empty
        String body2 = "{\"registrationId\":\"\"}";
        var responseBody2 = TestCaseHelpful.sendRequest("POST", uri, null, headers, body2);
        TestCaseHelpful.assertThatJson(responseBody2).node("resultCode").isEqualTo(101011);
        TestCaseHelpful.assertThatJson(responseBody2).node("reason").isEqualTo("参数错误");
        TestCaseHelpful.assertThatJson(responseBody2).node("success").isEqualTo(false);
    }

    private void bindAliasRaw(String tokenOrNull, String body, int resultCode, String reason, boolean success) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/jPush/bindAlias";
        Map<String, Object> headers = createDriverAppHeaders();
        if (tokenOrNull != null) {
            headers.put("authorization", tokenOrNull);
        }

        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(resultCode);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo(reason);
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(success);
    }

    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("longitude", "120.21674955924935");
        headers.put("latitude", "30.20344076263413");
        headers.put("version", "5.56.1");
        headers.put("platform", "ANDROID_DELIVERY");
        headers.put("type", "3");
        headers.put("locale", "zh-CN");
        headers.put("operatingsystem", "1");
        headers.put("brand", "HUAWEI");
        headers.put("uniquetoken", "dd9959880e28753f");
        headers.put("apptypeid", "2");
        headers.put("countrycode", "CN");
        headers.put("devicesafetoken", "a0_b1_c1_h0_i0_j0_m0_n0_p0_s0");
        headers.put("enableSign", "false");
        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

