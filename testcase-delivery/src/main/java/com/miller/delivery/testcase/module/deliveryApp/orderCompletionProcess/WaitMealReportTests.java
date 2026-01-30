package com.miller.delivery.testcase.module.deliveryApp.orderCompletionProcess;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.module.deliveryUtils.order.CreateInstantOrderWithHandoverTests;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 骑手等餐报备
 *
 * Apifox: docs/d-apifox/等餐报备.apifox-cli.json
 */
@Scenario(
        scenarioID = "01K9VRC0X5EHGDCHVR1XG8T623",
        scenarioName = "骑手等餐报备",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 240, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("等餐报备")
public class WaitMealReportTests {

    @DisplayName("等餐报备-完整流程（按Apifox步骤）")
    @Test
    void shouldCompleteWaitMealReportFlow() {
        // 1) C侧下单（统一走现有订单流）
        CreateInstantOrderWithHandoverTests create = new CreateInstantOrderWithHandoverTests();
        String userAppOrderSn = create.orderFlow();

        // 2) 骑手登录（apifox: account=15156678765, password=md5(Test1234)=2c934...）
        Map<String, String> loginResult = driverLogin();
        String driverAccessToken = loginResult.get("accessToken");
        String driverUserId = loginResult.get("userId");

        // 3) 司机上线
        onOffline(driverAccessToken, true);

        // 4) 新订单列表 -> 抢单
        newOrderList(driverAccessToken);
        grabOrder(driverAccessToken, userAppOrderSn);

        // 5) 等餐报备（成功）
        checkReportWindow(driverAccessToken, userAppOrderSn, driverUserId, 1000, "成功", true);

        // 6) 等餐报备--订单号为空（异常）
        checkReportWindow(driverAccessToken, "", "1398720903", 101011, "参数错误", false);

        // 7) 等餐报备--骑手不存在（异常：apifox给的body里 orderSn 也为空，这里保持一致）
        checkReportWindow(driverAccessToken, "", "111991", 101011, "参数错误", false);

        // 8) 等餐报备--未登录
        checkReportWindowWithoutLogin(userAppOrderSn, driverUserId);

        // 9) 司管登录 & 关闭到店/送达距离限制
        String siGuanToken = erpLogin();
        switchCityConfig(siGuanToken, "city_function_on_shop_take_meal_distance", 0);
        switchCityConfig(siGuanToken, "city_function_deliver_distance", 0);

        // 10) 修改骑手配送状态-到店 -> 未出餐 -> 已取餐
        modifyDeliveryStatus(driverAccessToken, userAppOrderSn, 1, 0, null);
        modifyDeliveryStatus(driverAccessToken, userAppOrderSn, 2, 0, null);
        modifyDeliveryStatus(driverAccessToken, userAppOrderSn, 3, 0, null);

        // 11) 放在门口送达完成订单（driverArriveType=1）
        completeOrder(driverAccessToken, userAppOrderSn);

        // 12) 下线
        onOffline(driverAccessToken, false);
    }

    private Map<String, String> driverLogin() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/auth/login";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("enableSign", "false");

        // apifox 环境变量 driverPassword = 2c9341...（md5(Test1234)）
        String body = "{\"areaCode\":\"86\",\"password\":\"2c9341ca4cf3d87b9e4eb905d6a3ec45\",\"account\":\"15156678765\"}";
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);

        Map<String, String> result = new HashMap<>();
        result.put("accessToken", TestCaseHelpful.extractValue(responseBody, "$.result.accessToken").toString());
        result.put("userId", TestCaseHelpful.extractValue(responseBody, "$.result.userId").toString());
        return result;
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

    private void checkReportWindow(String driverAccessToken, String orderSn, String driverId,
                                   int expectedResultCode, String expectedReason, boolean expectedSuccess) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/report/checkReportWindow";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        String body = String.format("{\"driverId\":%s,\"orderSn\":\"%s\"}", driverId, orderSn);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(expectedResultCode);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo(expectedReason);
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(expectedSuccess);
    }

    private void checkReportWindowWithoutLogin(String orderSn, String driverId) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/report/checkReportWindow";
        Map<String, Object> headers = createDriverAppHeaders();
        String body = String.format("{\"driverId\":%s,\"orderSn\":\"%s\"}", driverId, orderSn);

        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(2015);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("未登录,请登录后操作");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(false);
    }

    private void switchCityConfig(String siGuanToken, String functionKey, int switchType) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/sysCityConfig/switch";
        Map<String, Object> headers = createErpHeaders();
        headers.put("token", siGuanToken);

        String body = String.format("{\"city\":\"杭州市\",\"functionKey\":\"%s\",\"switchType\":%d}", functionKey, switchType);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    private void modifyDeliveryStatus(String driverAccessToken, String orderSn, int operationType, int driverArriveType, String imageUrl) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/modifyDeliveryStatus";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        headers.put("content-type", "application/json");

        String imgList = imageUrl == null ? "[]" : "[\"" + imageUrl + "\"]";
        String body = String.format("{\"orderSn\":\"%s\",\"orderCompleteImageUrlList\":%s,\"waitUserArrive\":0,\"operationType\":%d,\"orderSnList\":[\"%s\"],\"driverArriveType\":%d}",
                orderSn, imgList, operationType, orderSn, driverArriveType);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private void completeOrder(String driverAccessToken, String orderSn) {
        String imageUrl = "http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/175430399326336635673da4a42629745f01c84eb79c8.jpg";
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/modifyDeliveryStatus";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        headers.put("content-type", "application/json");

        String body = String.format("{\"driverArriveType\":1,\"operationType\":6,\"orderCompleteImageUrlList\":[\"%s\"],\"arriveRemark\":\"测试\",\"orderSnList\":[\"%s\"]}",
                imageUrl, orderSn);
        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("longitude", "120.216775");
        headers.put("latitude", "30.203361");
        headers.put("version", "5.64.0");
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

    private Map<String, Object> createErpHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

