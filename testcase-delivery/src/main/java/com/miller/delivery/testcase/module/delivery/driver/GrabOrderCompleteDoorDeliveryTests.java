package com.miller.delivery.testcase.module.delivery.driver;

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
 * 骑手抢单-完单流程(放在门口送达）
 *
 * @author TestingConsultant@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/07
 */
@Scenario(
        scenarioID = "01K1TBDXXB3R2YWN2XGX946TCX",
        scenarioName = "骑手抢单-完单流程(放在门口送达）",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 240, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("骑手抢单-完单流程(放在门口送达）")
public class GrabOrderCompleteDoorDeliveryTests {

    @DisplayName("骑手抢单-完单流程(放在门口送达）完整流程")
    @Test
    void shouldCompleteGrabOrderDoorDeliveryFlow() {
        // ========== 第一部分：C侧下单流程 ==========
        CreateInstantOrderWithHandoverTests createInstantOrderWithHandoverTests = new CreateInstantOrderWithHandoverTests();
        String userAppOrderSn = createInstantOrderWithHandoverTests.orderFlow();

        // ========== 第二部分：骑手操作流程 ==========
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        driverOnline(driverAccessToken);
        syncGps(driverAccessToken);
        
        // ========== 第三部分：调度分配流程 ==========
        String siGuanToken = erpLogin();
        Long assignDriverID = getAvailableDrivers(siGuanToken, userAppOrderSn);
        assignOrderToDriver(siGuanToken, userAppOrderSn, assignDriverID);
        String packageId = getOrderPackage(driverAccessToken);
        receiveOrder(driverAccessToken, packageId);
        
        // ========== 第四部分：完单流程（放在门口送达） ==========
        modifyDeliveryStatus(driverAccessToken, userAppOrderSn, 1); // 到店
        modifyDeliveryStatus(driverAccessToken, userAppOrderSn, 2); // 取餐
        completeOrderDoorDelivery(driverAccessToken, userAppOrderSn); // 放在门口送达
    }

    private void driverOnline(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/driver/onOffline";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        String body = "{\"isOnline\":1}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
    }

    private void syncGps(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/driver/syncGps";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        String body = "{\"longitude\":120.216727,\"latitude\":30.203499}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
    }

    private Long getAvailableDrivers(String siGuanToken, String orderSn) {
        String uri = TestcaseConfig.HOST_ERP + "/api/dispatch/driver/allList";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("token", siGuanToken);
        String body = String.format("{\"orderSn\":\"%s\"}", orderSn);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        return Long.parseLong(TestCaseHelpful.extractValue(responseBody, "$.data[0].userId").toString());
    }

    private void assignOrderToDriver(String siGuanToken, String orderSn, Long assignDriverID) {
        String uri = TestcaseConfig.HOST_ERP + "/api/dispatch/dispatch/assign";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("token", siGuanToken);
        String body = String.format("{\"deliveryId\":%d,\"orderSn\":\"%s\",\"rejectAble\":0}", 
                assignDriverID, orderSn);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
    }

    private String getOrderPackage(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/orderPackage/list";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        String body = "{}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        return TestCaseHelpful.extractValue(responseBody, "$.result.dataList[0].packageId").toString();
    }

    private void receiveOrder(String driverAccessToken, String packageId) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/orderPackage/receiveOrReject";
        String method = "POST";
        Map<String, Object> headers = createIOSDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        String body = String.format("{\"orderPackageId\":\"%s\",\"type\":1}", packageId);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
    }

    private void modifyDeliveryStatus(String driverAccessToken, String orderSn, int operationType) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/modifyDeliveryStatus";
        String method = "POST";
        Map<String, Object> headers = createIOSDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        String body = String.format("{\"orderSn\":\"%s\",\"orderCompleteImageUrlList\":[],\"waitUserArrive\":0," +
                "\"operationType\":%d,\"orderSnList\":[\"%s\"],\"driverArriveType\":0}", 
                orderSn, operationType, orderSn);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
    }

    /**
     * 放在门口送达
     */
    private void completeOrderDoorDelivery(String driverAccessToken, String orderSn) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/complete";
        String method = "POST";
        Map<String, Object> headers = createIOSDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        String body = String.format("{\"orderSn\":\"%s\",\"deliveryType\":2,\"orderCompleteImageUrlList\":[]}", orderSn);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
    }

    private Map<String, Object> createUserAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("Host", "app-test.hungrypanda.cn");
        headers.put("longitude", "120.216727");
        headers.put("latitude", "30.203499");
        headers.put("reallatitude", "30.203499");
        headers.put("reallongitude", "120.216727");
        headers.put("ismocklocation", "0");
        headers.put("version", "8.59.0");
        headers.put("platform", "ANDROID_USER");
        headers.put("type", "1");
        headers.put("apptypeid", "1");
        headers.put("user-agent", "8.59.0&OKPOS");
        headers.put("language", "CN");
        headers.put("countrycode", "CN");
        headers.put("uniquetoken", "4dd9690f6a6b639c");
        headers.put("device_safe_token", "a0_b1_c0_h0_i0_j0_m0_n0_p0_s0");

        headers.put("Content-Type", "application/json;charset=UTF-8");
        return headers;
    }

    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("Host", "app-deliverytest.hungrypanda.cn");
        headers.put("longitude", "120.216787");
        headers.put("latitude", "30.203426");
        headers.put("version", "5.66.0");
        headers.put("platform", "ANDROID_DELIVERY");
        headers.put("type", "3");
        headers.put("user-agent", "5.59.0");
        headers.put("locale", "zh-CN");
        headers.put("operatingsystem", "1");
        headers.put("brand", "HUAWEI");
        headers.put("uniquetoken", "dd9959880e28753f");
        headers.put("apptypeid", "2");
        headers.put("countrycode", "CN");
        headers.put("devicesafetoken", "a0_b1_c1_h0_i0_j0_m0_n0_p0_s0");

        headers.put("Content-Type", "application/json;charset=UTF-8");
        return headers;
    }

    private Map<String, Object> createIOSDriverAppHeaders() {
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("platform", "IOS_DELIVERY");
        return headers;
    }

    private Map<String, Object> createErpHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return headers;
    }
}
