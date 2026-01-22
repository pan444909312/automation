package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取离线地图
 *
 * Apifox: docs/d-apifox/获取离线地图路径.apifox-cli.json
 */
@Scenario(
        scenarioID = "01K5GY2EE3N8VHW3PCW8JYK2RC",
        scenarioName = "获取离线地图",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("获取离线地图")
public class OfflineMapRoutePlanTests {

    @DisplayName("获取离线地图-已登录")
    @Test
    void shouldGetOfflineMapWhenLoggedIn() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/map/route/plan";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        String body = "{\"point\":[{\"orderSn\":\"8781266234750707461304\",\"type\":1,\"toPoint\":{\"lon\":120.235878469,\"lat\":30.2039988763},\"fromPoint\":{\"lon\":120.216797,\"lat\":30.203492}}]}";
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    @DisplayName("获取离线地图-未登录")
    @Test
    void shouldFailGetOfflineMapWhenNotLoggedIn() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/map/route/plan";
        Map<String, Object> headers = createDriverAppHeaders();

        String body = "{\"point\":[{\"orderSn\":\"8781266234750707461304\",\"type\":1,\"toPoint\":{\"lon\":120.235878469,\"lat\":30.2039988763},\"fromPoint\":{\"lon\":120.216797,\"lat\":30.203492}}]}";
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(2015);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("未登录,请登录后操作");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(false);
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

