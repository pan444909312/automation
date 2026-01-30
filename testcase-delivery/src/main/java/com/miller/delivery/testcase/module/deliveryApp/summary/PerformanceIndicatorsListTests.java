package com.miller.delivery.testcase.module.deliveryApp.summary;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取各个指标数据
 *
 * @author TestingConsultant@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/07
 */
@Scenario(
        scenarioID = "01JPP7CA0BK63JDVFNGHEE16BT",
        scenarioName = "骑手app-统计tab-获取各个指标数据",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("获取各个指标数据")
public class PerformanceIndicatorsListTests {

    @DisplayName("获取各个指标数据")
    @Test
    void shouldGetIndicatorNum() {
        // 1) 骑手登录获取 token
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        // 2) 获取各个指标数据
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/performanceIndicators/listIndicatorNum";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = "{}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("success").isEqualTo(true);
    }

    @DisplayName("获取各个指标数据--未登录")
    @Test
    void shouldGetIndicatorNumWithoutAuth() {
        // 注意：此用例测试未登录场景，不需要登录

        // 获取各个指标数据（无authorization）
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/performanceIndicators/listIndicatorNum";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        // 不设置authorization
        
        String body = "{}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("resultCode").isEqualTo(2015);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("reason").isEqualTo("未登录,请登录后操作");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("success").isEqualTo(false);
    }

    /**
     * 创建骑手app请求头
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("_pendingsign", "_ts1735902848305authorization77bd6b4f8d39379ff99b3663a0d6f901");
        headers.put("operatingsystem", "2");
        headers.put("brand", "iPhone X");
        headers.put("latitude", "30.203558");
        headers.put("locationstatus", "2");
        headers.put("locale", "zh-CN");
        headers.put("version", "5.54.1");
        headers.put("countrycode", "CN");
        headers.put("platform", "IOS");
        headers.put("uniquetoken", "BFEC8953-4F04-4AAC-9C06-7EB2C8CA1411");
        headers.put("longitude", "120.216858");
        headers.put("devicesafetoken", "a0_b0_c0_h0_i0_j0_m0_n0_p0_s0");
        headers.put("apptypeid", "2");

        headers.put("content-type", "application/json");
        return headers;
    }
}

