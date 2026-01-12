package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * PF骑手扫描抢单
 *
 * @author chenchunxia@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01K18EE0F4TBDFMBP48MFBRHY0",
        scenarioName = "骑手app-PF骑手扫描抢单",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("PF骑手扫描抢单")
public class PfDriverScanGrabOrderTests {

    // 注意：需要在实际使用时替换为真实的订单号
    private static final String ORDER_SN = "PFZC2507231947575790"; // 请从质量平台或实际业务中获取订单号

    @DisplayName("PF骑手扫描抢单")
    @Test
    void shouldGrabOrderByScan() {
        // 1) 骑手登录获取 token
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");

        // 2) PF骑手扫描抢单
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/fresh/takeOrder";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = String.format("{\"hasChangeAddress\":0,\"orderSn\":\"%s\"}", ORDER_SN);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 3) 断言
        // 注意：JSON中主接口没有test脚本，只验证基本响应结构
        TestCaseHelpful.assertThatJson(responseBody)
                .node("resultCode").isNotNull();
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

