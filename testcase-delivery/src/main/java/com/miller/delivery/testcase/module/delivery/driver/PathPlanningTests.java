package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取算法规划
 *
 * @author chenchunxia@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/07
 */
@Scenario(
        scenarioID = "01JPP7PNYGNEETQQVZT4Q66V5A",
        scenarioName = "骑手app-路径规划-获取算法规划",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("获取算法规划")
public class PathPlanningTests {

    @DisplayName("路径规划")
    @Test
    void shouldGetDeliveringPlaning() {
        // 1) 骑手登录获取 token
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        // 2) 路径规划
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/deliveringPlaning";
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
        
        // JSON中还有一个断言：Body matches string "系统智能已计算出最佳配送路线"
        String responseText = responseBody.toString();
        assert responseText.contains("系统智能已计算出最佳配送路线");
    }

    @DisplayName("路径规划-未登录")
    @Test
    void shouldGetDeliveringPlaningWithoutAuth() {
        // 注意：此用例测试未登录场景，不需要登录

        // 路径规划（authorization为"11"）
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/deliveringPlaning";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", "11");
        
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

    @DisplayName("路径规划意见反馈")
    @Test
    void shouldSavePathPlanningVersion() {
        // 1) 骑手登录获取 token
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        // 2) 路径规划意见反馈
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/feedback/savePathPlanningVersion";
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

    /**
     * 创建骑手app请求头
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("longitude", "120.2168894");
        headers.put("latitude", "30.2035081");
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

