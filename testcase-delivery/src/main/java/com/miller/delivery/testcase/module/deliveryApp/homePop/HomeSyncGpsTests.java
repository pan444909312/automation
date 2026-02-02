package com.miller.delivery.testcase.module.deliveryApp.homePop;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 骑手app-登录后默认请求的接口-获取骑手定位
 *
 * Apifox: docs/d-apifox/获取骑手定位.apifox-cli.json
 */
@Scenario(
        scenarioID = "01JPP6BJ6NYE6P101PWE06CMQT",
        scenarioName = "骑手app-登录后默认请求的接口-获取骑手定位",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 20, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("获取骑手定位")
public class HomeSyncGpsTests {

    @DisplayName("获取骑手定位-有经纬度 & 无经纬度")
    @Test
    void shouldSyncGpsCases() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        // 有经纬度：headers带经纬度
        syncGps(driverAccessToken, true, 1000, "成功", true);
        // 4) 等待2秒
        // 等待2秒
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 无经纬度：headers不带经纬度（模拟apifox用例）
        syncGps(driverAccessToken, false, 116005, "配送员实时经纬度获取失败", false);
    }

    private void syncGps(String token, boolean withLngLat, int resultCode, String reason, boolean success) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/home/syncGps";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", token);
        if (withLngLat) {
            headers.put("longitude", "120.21674955924935");
            headers.put("latitude", "30.20344076263413");


        }

        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, "{}");
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(resultCode);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo(reason);
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(success);
    }

    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
//        headers.put("longitude", "120.21674955924935");
//        headers.put("latitude", "30.20344076263413");
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

