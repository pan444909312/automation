package com.miller.delivery.testcase.module.dispatch.order;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.module.deliveryUtils.order.CreateInstantOrderWithHandoverTests;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * 调度系统-算法下派（一单下派-无需人工确认）
 *
 * Apifox: docs/d-apifox/算法下派-一单下派-无需人工确认.apifox-cli.json
 */
@Scenario(
        scenarioID = "01JPPN9K4H5ZYA8VFBKA03HZPW",
        scenarioName = "调度系统-算法下派",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("算法下派-一单下派-无需人工确认")
public class DispatchAlgorithmAssignOneOrderTests {

    @DisplayName("调度系统-算法下派（xly）-> 骑手接单 -> 配送并签收")
    @Test
    void shouldAlgorithmAssignAndCompleteOrder() {
        // 1) C侧下单
        CreateInstantOrderWithHandoverTests create = new CreateInstantOrderWithHandoverTests();
        String userAppOrderSn = create.orderFlow();

        // 2) 骑手登录 & 上线
        Map<String, String> driverLoginInfo = driverLogin();
        String driverAccessToken = driverLoginInfo.get("accessToken");
        String driverUserId = driverLoginInfo.get("userId");
        driverOnOffline(driverAccessToken, 1);

        // 3) 司管登录
        String siGuanToken = TestCaseHelpful.erpLogin();

        // 4) 算法下派（新）算法派一单
        algorithmAssignOneOrder(siGuanToken, userAppOrderSn, driverUserId);

        // 5) 派单页面获取packageId -> 骑手接单
        String packageId = getOrderPackage(driverAccessToken);
        receiveOrder(driverAccessToken, packageId);

        // 6) 获取待取餐列表（验证有单）
        waitPickUpList(driverAccessToken);

        // 7) 商家地址指引
        merchantAddressInfo(driverAccessToken, userAppOrderSn);

        // 8) 获取商品信息
        productInfo(driverAccessToken, userAppOrderSn);

        // 9) 配送状态：到店 -> 未出餐 -> 已取餐
        modifyDeliveryStatus(driverAccessToken, userAppOrderSn, 1);
        modifyDeliveryStatus(driverAccessToken, userAppOrderSn, 2);
        modifyDeliveryStatus(driverAccessToken, userAppOrderSn, 3);

        // 10) 配送中列表
        waitDeliveringList(driverAccessToken);

        // 11) 获取用户手机号（可选校验）
        customerPhone(driverAccessToken, userAppOrderSn);

        // 12) 获取离线地图
        offlineMap(driverAccessToken);

        // 13) 签收
        signOrder(driverAccessToken, userAppOrderSn);

        // 14) 下线
        driverOnOffline(driverAccessToken, 0);
    }

    private Map<String, String> driverLogin() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/auth/login";
        Map<String, Object> headers = createDriverAppHeaders();

        String body = "{\"areaCode\":\"86\",\"password\":\"2c9341ca4cf3d87b9e4eb905d6a3ec45\",\"account\":\"13300010015\"}";
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);

        String accessToken = TestCaseHelpful.extractValue(responseBody, "$.result.accessToken").toString();
        String userId = TestCaseHelpful.extractValue(responseBody, "$.result.userId").toString();
        return Map.of("accessToken", accessToken, "userId", userId);
    }

    private void driverOnOffline(String driverAccessToken, int isOnline) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/driver/onOffline";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        headers.put("longitude", "120.2168986");
        headers.put("latitude", "30.2035028");

        String body = isOnline == 1 ? "{\"isOnline\":1}" : "{\"continueDown\":0,\"isOnline\":0}";
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void algorithmAssignOneOrder(String siGuanToken, String orderSn, String driverUserId) {
        String uri = TestcaseConfig.HOST_ERP + "/api/dispatch/receipt/order/xly";
        Map<String, Object> headers = createErpHeaders();
        headers.put("token", siGuanToken);

        String body = String.format("[{\"score\":\"-9535912969.9295\",\"runId\":\"cmo8h3cg7sm0ok64dg50\",\"combinedOrderStatus\":0,\"detailDto\":[{\"orderNo\":\"%s\"},{\"orderNo\":\"\"},{\"orderNo\":\"\"},{\"orderNo\":\"\"}],\"driverId\":\"%s\",\"statics\":{\"newOrderDistance\":1.65,\"newOrderExtraDistance\":-0.42249,\"sameRouteRatio\":283.83393,\"sameRouteRatioMileage\":1.30078,\"totalDistance\":1.22751,\"totalPrice\":5846.25533}}]", orderSn, driverUserId);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        // Apifox未给出明确断言，这里仅做基础非空校验（防止500等异常）
        TestCaseHelpful.assertThat(responseBody).isNotNull();
    }

    private String getOrderPackage(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/orderPackage/list";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        headers.put("longitude", "120.2168986");
        headers.put("latitude", "30.2035028");

        String body = "{\"pageNo\":1,\"pageSize\":10}";
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        return TestCaseHelpful.extractValue(responseBody, "$.result.dataList[0].packageId").toString();
    }

    private void receiveOrder(String driverAccessToken, String packageId) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/orderPackage/receiveOrReject";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("Authorization", driverAccessToken);
        headers.put("operatingsystem", "2");
        headers.put("content-type", "application/json");

        String body = String.format("{\"orderPackageId\":\"%s\",\"type\":1}", packageId);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void waitPickUpList(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/waitPickUpList";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        String body = "{\"pageNo\":1,\"sortType\":0}";
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
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

    private void modifyDeliveryStatus(String driverAccessToken, String orderSn, int operationType) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/modifyDeliveryStatus";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        headers.put("operatingsystem", "2");
        headers.put("content-type", "application/json");

        String body = String.format("{\"orderSn\":\"%s\",\"orderCompleteImageUrlList\":[],\"waitUserArrive\":0,\"operationType\":%d,\"orderSnList\":[\"%s\"],\"driverArriveType\":0}", orderSn, operationType, orderSn);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
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

    private void signOrder(String driverAccessToken, String orderSn) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/modifyDeliveryStatus";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        headers.put("operatingsystem", "2");
        headers.put("content-type", "application/json");

        String body = String.format("{\"driverArriveType\":11,\"operationType\":6,\"arriveRemark\":\"留言备注内容-apifox自动化测试创建，图片默认写死资源地址，免去每次上传图片到oss\",\"waitUserArrive\":0,\"orderSn\":\"%s\",\"orderCompleteImageUrlList\":[\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/170174606688616113ac9a0a74ab29cdadf98ad4cf090.jpg\"],\"orderSnList\":[\"%s\"]}", orderSn, orderSn);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
    }

    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/delivery/auth/request/headers.json");
        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("Content-Type", "application/json");
        return headers;
    }

    private Map<String, Object> createErpHeaders() {
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/dispatch/order/orderAdjustmentPrice/request/headers.json");
        headers.put("accept", "application/json, text/plain, */*");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

