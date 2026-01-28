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
 * 主干流程-更新订单状态
 *
 * Apifox: docs/d-apifox/checked/更新订单状态.apifox-cli.json
 */
@Scenario(
        scenarioID = "01K4BZNJ0HQ063FAW61XKE4NF8",
        scenarioName = "主干流程-更新订单状态",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 240, maintenanceTime = 0, manualTestTime = 35)
@DisplayName("更新订单状态")
public class OrderStatusUpdateTests {

    @DisplayName("正常：更新状态并签收")
    @Test
    void shouldUpdateOrderStatusAndSign() {
        CreateInstantOrderWithHandoverTests create = new CreateInstantOrderWithHandoverTests();
        String userAppOrderSn = create.orderFlow();

        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        driverOnOffline(driverAccessToken, 1);

        grabOrder(driverAccessToken, userAppOrderSn);
        modifyDeliveryStatus(driverAccessToken, userAppOrderSn, 1);
        modifyDeliveryStatus(driverAccessToken, userAppOrderSn, 2);
        modifyDeliveryStatus(driverAccessToken, userAppOrderSn, 3);

        signOrder(driverAccessToken, userAppOrderSn);
    }

    @DisplayName("异常：订单不存在不可签收")
    @Test
    void shouldFailSignWhenOrderNotExist() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        driverOnOffline(driverAccessToken, 1);

        var responseBody = signOrderRaw(driverAccessToken, "11111111");
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(5051);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("订单获取失败");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(false);
    }

    @DisplayName("异常：订单号为空不可签收")
    @Test
    void shouldFailSignWhenOrderSnEmpty() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        driverOnOffline(driverAccessToken, 1);

        var responseBody = signOrderRaw(driverAccessToken, "");
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(5051);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("订单获取失败");
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

    private void modifyDeliveryStatus(String driverAccessToken, String userAppOrderSn, int operationType) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/modifyDeliveryStatus";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        String body = String.format("{\"orderSn\":\"%s\",\"orderCompleteImageUrlList\":[],\"waitUserArrive\":0,\"operationType\":%d,\"orderSnList\":[\"%s\"],\"driverArriveType\":0}",
                userAppOrderSn, operationType, userAppOrderSn);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void signOrder(String driverAccessToken, String userAppOrderSn) {
        var responseBody = signOrderRaw(driverAccessToken, userAppOrderSn);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private String signOrderRaw(String driverAccessToken, String userAppOrderSn) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/modifyDeliveryStatus";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        String body = String.format("{\"driverArriveType\":11,\"operationType\":6,\"arriveRemark\":\"留言备注内容-apifox自动化测试创建，图片默认写死资源地址，免去每次上传图片到oss\",\"waitUserArrive\":0,\"orderSn\":\"%s\",\"orderSnList\":[\"%s\"],\"orderCompleteImageUrlList\":[\"https://cdn-gateway.hungrypanda.cn/1689592099285336450/1694242252451_1E8CB8D7-70B2-4B47-90D5-31023AC73C8E.png\"],\"latitude\":30.203616,\"longitude\":120.216878}",
                userAppOrderSn, userAppOrderSn);
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

