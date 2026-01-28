package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.module.deliveryUtils.order.CreateInstantOrderWithHandoverTests;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 主干流程-获取订单详情页
 *
 * Apifox: docs/d-apifox/checked/获取订单详情页.apifox-cli.json
 */
@Scenario(
        scenarioID = "01K4C0RTWY0BYPT01KC2K00N4W",
        scenarioName = "主干流程-获取订单详情页",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 35)
@DisplayName("获取订单详情页")
public class OrderDetailPageTests {

    @DisplayName("正常：获取订单详情页")
    @Test
    void shouldGetOrderDetail() {
        CreateInstantOrderWithHandoverTests create = new CreateInstantOrderWithHandoverTests();
        String userAppOrderSn = create.orderFlow();

        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        driverOnOffline(driverAccessToken, 1);

        grabOrder(driverAccessToken, userAppOrderSn);

        var responseBody = orderDetailRaw(driverAccessToken, userAppOrderSn);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    @DisplayName("异常：订单不存在/非法")
    @Test
    void shouldFailWhenOrderSnIllegal() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        driverOnOffline(driverAccessToken, 1);

        var responseBody = orderDetailRaw(driverAccessToken, "33333333");
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(105002);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("订单号非法");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(false);
    }

    @DisplayName("异常：订单号为空")
    @Test
    void shouldFailWhenOrderSnEmpty() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        driverOnOffline(driverAccessToken, 1);

        var responseBody = orderDetailRaw(driverAccessToken, "");
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(101011);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("参数错误");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(false);
    }

    private void driverOnOffline(String driverAccessToken, int isOnline) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/driver/onOffline";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        String body = String.format("{\"isOnline\":%d}", isOnline);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void grabOrder(String driverAccessToken, String userAppOrderSn) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/grabOrder";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        String body = String.format("{\"orderSn\":\"%s\",\"confirmDiscount\":0}", userAppOrderSn);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private String orderDetailRaw(String driverAccessToken, String orderSn) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/orderDetail";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        String body = String.format("{\"orderSn\":\"%s\"}", orderSn);
        return TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
    }

    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("longitude", "120.216878");
        headers.put("latitude", "30.203616");
        headers.put("locationstatus", "2");
        headers.put("locale", "zh-CN");
        headers.put("version", "5.64.0");
        headers.put("countrycode", "CN");
        headers.put("platform", "IOS");
        headers.put("uniquetoken", "9A95A874-6493-4DFC-A5E1-BCE3C7C265D0");
        headers.put("operatingsystem", "2");
        headers.put("brand", "iPhone 11");
        headers.put("devicesafetoken", "a0_b0_c0_h0_i0_j0_m0_n0_p0_s0");
        headers.put("apptypeid", "2");
        headers.put("accept-language", "zh-Hans;q=1");
        headers.put("accept", "*/*");
        headers.put("user-agent", "PandaDelivery/5.64.0 (iPhone; iOS 18.3.1; Scale/2.00) OKPOS");
        headers.put("content-type", "application/json");
        return headers;
    }
}

