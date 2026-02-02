package com.miller.delivery.testcase.module.deliveryApp.orderCompletionProcess;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.module.deliveryUtils.order.CreateInstantOrderWithHandoverTests;
import com.miller.delivery.testcase.utils.DriverOffline;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 主干流程-骑手抢单-联系不到用户送达】
 *
 * Apifox: docs/d-apifox/todo/骑手取餐-完单流程 (联系不到用户送达).apifox-cli.json
 */
@Scenario(
        scenarioID = "01K1TA1HZGVY90CMMGZBN979CH",
        scenarioName = "主干流程-骑手抢单-联系不到用户送达】",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 240, maintenanceTime = 0, manualTestTime = 35)
@DisplayName("骑手取餐-完单流程 (联系不到用户送达)")
public class PickupCompleteContactFailTests {

    @DisplayName("主干流程-联系不到用户送达（按Apifox步骤）")
    @Test
    void shouldCompleteContactFailDeliverFlow() {
        // 1) C侧下单
        CreateInstantOrderWithHandoverTests create = new CreateInstantOrderWithHandoverTests();
        String userAppOrderSn = create.orderFlow();

        // 2) 骑手登录 & 上线
        Map<String, String> driverLoginInfo = TestCaseHelpful.deliveryLoginReturndriverId("13300010676", "Test1234");
        String driverAccessToken = driverLoginInfo.get("accessToken");
        Long driverId = Long.valueOf(driverLoginInfo.get("userId"));
        DriverOffline driverOffline = new DriverOffline();
        driverOffline.cancelDispatchAndOffline("13300010676",driverAccessToken);
        onOffline(driverAccessToken, true);

        // 3) 新订单列表
        newOrderList(driverAccessToken);

        // 4) 抢单
        grabOrder(driverAccessToken, userAppOrderSn);

        // 5) 待取餐列表（正常）
        waitPickUpList(driverAccessToken, 0);

        // 6) 待取餐列表（异常case：未登录）
        waitPickUpListNotLoggedIn(26);

        // 7) 商家地址指引
        merchantAddressInfo(driverAccessToken, userAppOrderSn);

        // 8) 获取商品信息
        productInfo(driverAccessToken, userAppOrderSn);

        // 9) 到店 -> 未出餐
        modifyDeliveryStatusSimple(driverAccessToken, userAppOrderSn, 1);
        modifyDeliveryStatusSimple(driverAccessToken, userAppOrderSn, 2);

        // 10) 订单已取消不可取餐（多case）
        modifyDeliveryStatusOrderCanceledCase(driverAccessToken);

        // 11) 已取餐
        modifyDeliveryStatusPickedUp(driverAccessToken, userAppOrderSn);

        // 12) 配送中列表
        waitDeliveringList(driverAccessToken);

        // 13) 获取用户手机号
        customerPhone(driverAccessToken, userAppOrderSn);

        // 14) 获取离线地图
        offlineMap(driverAccessToken);

        // 15) 拨打用户电话（联系不到用户）
        orderWaitUser(driverAccessToken, userAppOrderSn);

        // 16) 联系不到用户完成送达
        completeContactFailDeliver(driverAccessToken, userAppOrderSn);

        // 17) 下线
        onOffline(driverAccessToken, false);
    }

    private void newOrderList(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/newOrderList";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        String body = "{\"pageNo\":1,\"pageSize\":10}";
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void grabOrder(String driverAccessToken, String orderSn) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/grabOrder";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        String body = String.format("{\"orderSn\":\"%s\",\"confirmDiscount\":0}", orderSn);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void waitPickUpList(String driverAccessToken, int sortType) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/waitPickUpList";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        String body = String.format("{\"pageNo\":1,\"sortType\":%d}", sortType);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void waitPickUpListNotLoggedIn(int sortType) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/waitPickUpList";
        Map<String, Object> headers = createDriverAppHeaders();
        // 不带 authorization

        String body = String.format("{\"pageNo\":1,\"sortType\":%d}", sortType);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(2015);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("未登录,请登录后操作");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(false);
    }

    private void merchantAddressInfo(String driverAccessToken, String orderSn) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/orderHelp/merchantAddressInfo";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        String body = String.format("{\"shopId\":892716498,\"orderSn\":\"%s\"}", orderSn);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void productInfo(String driverAccessToken, String orderSn) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/productInfo";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        String body = String.format("{\"orderSn\":\"%s\"}", orderSn);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void modifyDeliveryStatusSimple(String driverAccessToken, String orderSn, int operationType) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/modifyDeliveryStatus";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        headers.put("content-type", "application/json");

        String body = String.format("{\"operationType\":%d,\"orderSnList\":[\"%s\"]}", operationType, orderSn);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void modifyDeliveryStatusOrderCanceledCase(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/modifyDeliveryStatus";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        headers.put("content-type", "application/json");

        String body = "{\"orderSn\":\"5731667404750703651018\",\"orderCompleteImageUrlList\":[],\"waitUserArrive\":0,\"operationType\":3,\"orderSnList\":[\"5731667404750703651018\"],\"driverArriveType\":0}";
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(5107);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("订单已取消");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(false);
    }

    private void modifyDeliveryStatusPickedUp(String driverAccessToken, String orderSn) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/modifyDeliveryStatus";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        headers.put("content-type", "application/json");

        String body = String.format("{\"orderSn\":\"%s\",\"orderCompleteImageUrlList\":[],\"waitUserArrive\":0,\"operationType\":3,\"orderSnList\":[\"%s\"],\"driverArriveType\":0}", orderSn, orderSn);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void waitDeliveringList(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/waitDeliveringList";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        String body = "{\"pageNo\":1,\"sortType\":0}";
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void customerPhone(String driverAccessToken, String orderSn) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/customerInfo/customerPhone";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        String body = String.format("{\"orderSn\":\"%s\"}", orderSn);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void offlineMap(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/map/route/plan";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        String body = "{\"point\":[{\"type\":1,\"toPoint\":{\"lat\":30.1965002066,\"lon\":120.2165139689},\"fromPoint\":{\"lat\":30.203602097957489,\"lon\":120.21675840570194}},{\"fromPoint\":{\"lat\":30.1965002066,\"lon\":120.2165139689},\"toPoint\":{\"lon\":120.244367,\"lat\":30.183143999999999},\"type\":1}]}";
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void orderWaitUser(String driverAccessToken, String orderSn) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/orderWaitUser";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        String body = String.format("{\"orderSn\":\"%s\",\"recordType\":0}", orderSn);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        // apifox未给明确断言，做基础校验
        TestCaseHelpful.assertThat(responseBody).isNotNull();
    }

    private void completeContactFailDeliver(String driverAccessToken, String orderSn) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/modifyDeliveryStatus";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        headers.put("content-type", "application/json");

        String body = String.format("{\"waitUserArrive\":1,\"driverArriveType\":110,\"operationType\":6,\"orderCompleteImageUrlList\":[\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/1758540236253860df498ab604628af1ab5f09d6fd2a9.jpg\"],\"arriveRemark\":\"beuzhu\",\"orderSn\":\"%s\",\"orderSnList\":[\"%s\"]}", orderSn, orderSn);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void onOffline(String driverAccessToken, boolean online) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/driver/onOffline";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        String body = online ? "{\"isOnline\":1}" : "{\"isOnline\":0}";
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

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
        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("content-type", "application/json");
        return headers;
    }
}

