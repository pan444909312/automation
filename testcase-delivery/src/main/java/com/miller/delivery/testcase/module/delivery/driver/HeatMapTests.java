package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 热力图
 */
@Scenario(
        scenarioID = "01JPP7E6A59ZFTMD3MT9J5K83C",
        scenarioName = "骑手app-热力图-获取热力图",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("热力图")
public class HeatMapTests {

    @DisplayName("热力图-正常")
    @Test
    void shouldGetHeatMap() {
        String token = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/heatMap/heatAreaOrderNumber";
        Map<String, Object> headers = createHeaders();
        headers.put("authorization", token);

        var response = TestCaseHelpful.sendRequest("POST", uri, null, headers, "{}");

        TestCaseHelpful.assertThatJson(response).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(response).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(response).node("success").isEqualTo(true);
    }

    @DisplayName("热力图-token过期")
    @Test
    void shouldFailWhenTokenExpired() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/heatMap/heatAreaOrderNumber";
        Map<String, Object> headers = createHeaders();
        headers.put("authorization", "9cb7036709b8a961190b60dcf3f328f5");

        var response = TestCaseHelpful.sendRequest("POST", uri, null, headers, "{}");

        TestCaseHelpful.assertThatJson(response).node("resultCode").isEqualTo(2015);
        TestCaseHelpful.assertThatJson(response).node("reason").isEqualTo("未登录,请登录后操作");
        TestCaseHelpful.assertThatJson(response).node("success").isEqualTo(false);
    }

    private Map<String, Object> createHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("longitude", "120.2168892");
        headers.put("latitude", "30.2035084");
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


