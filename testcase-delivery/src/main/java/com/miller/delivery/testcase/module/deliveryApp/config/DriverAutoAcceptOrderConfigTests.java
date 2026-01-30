package com.miller.delivery.testcase.module.deliveryApp.config;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 骑手app-骑手修改自动接单配置
 *
 * Apifox: docs/d-apifox/修改骑手自动接单配置.apifox-cli.json
 */
@Scenario(
        scenarioID = "01JZ0C01AYZ5RA4JR2REM00TJF",
        scenarioName = "骑手app-骑手修改自动接单配置",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("修改骑手自动接单配置")
public class DriverAutoAcceptOrderConfigTests {

    @DisplayName("打开自动接单 -> 关闭自动接单")
    @Test
    void shouldToggleAutoAcceptOrder() throws InterruptedException {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        // apifox: /api/delivery/app/order/getInsteadOrderAmountInfo
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/getInsteadOrderAmountInfo";

        // 1) 打开自动接单 type=1
        var responseOpen = TestCaseHelpful.sendRequest("POST", uri, null, headers, "{\"type\":1}");
        TestCaseHelpful.assertThatJson(responseOpen).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseOpen).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseOpen).node("success").isEqualTo(true);

        // 2) 等待1秒（apifox delay）
        Thread.sleep(1000);

        // 3) 关闭自动接单 type=0
        var responseClose = TestCaseHelpful.sendRequest("POST", uri, null, headers, "{\"type\":0}");
        TestCaseHelpful.assertThatJson(responseClose).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseClose).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseClose).node("success").isEqualTo(true);
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

