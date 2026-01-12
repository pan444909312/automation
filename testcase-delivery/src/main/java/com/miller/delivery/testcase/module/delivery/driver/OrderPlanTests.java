package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 联系不到用户处理进度
 *
 * @author chenchunxia@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01JWB4HG1T3TXQY5SSTB9YN8M3",
        scenarioName = "骑手app-联系不到用户处理进度",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("联系不到用户处理进度")
public class OrderPlanTests {

    // 注意：需要在实际使用时替换为真实的订单号
    private static final String ORDER_SN = "5711469418740708521811"; // 请从质量平台或实际业务中获取订单号

    @DisplayName("联系不到用户处理进度")
    @Test
    void shouldGetOrderPlan() {
        // 1) 骑手登录获取 token
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        // 2) 联系不到用户处理进度
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/orderPlan";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = String.format("{\"orderSn\":\"%s\"}", ORDER_SN);
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
        headers.put("longitude", "120.216758");
        headers.put("latitude", "30.203602");
        headers.put("version", "5.64.0");
        headers.put("platform", "IOS");
        headers.put("type", "3");
        headers.put("locale", "zh-CN");
        headers.put("operatingsystem", "2");
        headers.put("brand", "iPhone 11");
        headers.put("uniquetoken", "9A95A874-6493-4DFC-A5E1-BCE3C7C265D0");
        headers.put("apptypeid", "2");
        headers.put("countrycode", "CN");
        headers.put("devicesafetoken", "a0_b0_c0_h0_i0_j0_m0_n0_p0_s0");
        headers.put("locationstatus", "2");
        headers.put("accept-language", "zh-Hans;q=1");
        headers.put("accept", "*/*");

        headers.put("content-type", "application/json");
        return headers;
    }
}

