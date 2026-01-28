package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 骑手app-登录后默认请求的接口-应用内推送-轮训
 *
 * Apifox: docs/d-apifox/应用内推送-轮询.apifox-cli.json
 */
@Scenario(
        scenarioID = "01JPP6MCPXK008RM3C5EB6TRBD",
        scenarioName = "骑手app-登录后默认请求的接口-应用内推送-轮训",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("应用内推送-轮询")
public class HomeSystemPushPollingTests {

    @DisplayName("应用内推送-轮询-成功 & 未登录")
    @Test
    void shouldPollSystemPushCases() {
        // 未登录
        systemPushRaw(null, 2015, "未登录,请登录后操作", false);

        // 登录
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        systemPushRaw(driverAccessToken, 1000, "成功", true);
    }

    private void systemPushRaw(String tokenOrNull, int resultCode, String reason, boolean success) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/home/systemPush";
        Map<String, Object> headers = createDriverAppHeaders();
        if (tokenOrNull != null) {
            headers.put("authorization", tokenOrNull);
        }

        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, "{}");
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

