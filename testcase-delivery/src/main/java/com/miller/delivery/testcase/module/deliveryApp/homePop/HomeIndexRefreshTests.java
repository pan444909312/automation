package com.miller.delivery.testcase.module.deliveryApp.homePop;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 骑手app-登录后默认请求的接口-首页刷新
 *
 * Apifox: docs/d-apifox/首页刷新.apifox-cli.json
 */
@Scenario(
        scenarioID = "01JNQ7Y77JGC4NBW1D4BG6XBMC",
        scenarioName = "骑手app-登录后默认请求的接口-首页刷新",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("首页刷新")
public class HomeIndexRefreshTests {

    @DisplayName("首页刷新-成功 & 未登录")
    @Test
    void shouldRefreshHomeIndexCases() {
        // 未登录
        homeIndexRaw(null, 2015, "未登录,请登录后操作", false);

        // 登录后
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        homeIndexRaw(driverAccessToken, 1000, "成功", true);
    }

    private void homeIndexRaw(String tokenOrNull, int resultCode, String reason, boolean success) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/home/index";
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

