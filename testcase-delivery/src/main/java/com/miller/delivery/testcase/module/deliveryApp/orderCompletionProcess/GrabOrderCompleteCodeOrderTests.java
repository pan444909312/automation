package com.miller.delivery.testcase.module.deliveryApp.orderCompletionProcess;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.module.deliveryUtils.order.CreateInstantOrderWithHandoverTests;
import com.miller.delivery.testcase.utils.DriverOffline;
import com.miller.delivery.testcase.utils.PandaTestDBHelpful;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.miller.delivery.testcase.module.deliveryAdmin.systemManagement.SwitchCityCollectionCodeTests.switchCityCollectionCode;
import static com.miller.delivery.testcase.module.deliveryAdmin.systemManagement.SwitchCountryCollectionCodeTests.switchCountryCollectionCode;
import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 骑手抢单-完单流程(收餐码订单）
 *
 * @author TestingConsultant@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/07
 */
@Scenario(
        scenarioID = "01KEH3ZHF6PX08DCXM4FDNKCSW",
        scenarioName = "骑手抢单-完单流程(收餐码订单）",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 240, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("骑手抢单-完单流程(收餐码订单）")
public class GrabOrderCompleteCodeOrderTests {

    @DisplayName("骑手抢单-完单流程(收餐码订单）完整流程")
    @Test
    void shouldCompleteGrabOrderCodeOrderFlow() {

        // ========== 第一部分：C侧下单流程 ==========
        CreateInstantOrderWithHandoverTests createInstantOrderWithHandoverTests = new CreateInstantOrderWithHandoverTests();
        String userAppOrderSn = createInstantOrderWithHandoverTests.orderFlow();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        String siGuanToken = erpLogin();
        switchMealCollectionCode(siGuanToken, 1,"city_function_meal_collection_code_switch");
        // ========== 第二部分：前置操作 ==========
        // 从数据库查询正确的收餐码
        String correctFoodDeliveryCode = getFoodDeliveryCodeFromDatabase(userAppOrderSn);
        
        // ========== 第三部分：骑手操作流程 ==========
        Map<String, String> driverLoginInfo = TestCaseHelpful.deliveryLoginReturndriverId("13300010676", "Test1234");
        String driverAccessToken = driverLoginInfo.get("accessToken");
        Long driverId = Long.valueOf(driverLoginInfo.get("userId"));
        DriverOffline driverOffline = new DriverOffline();
        driverOffline.cancelDispatchAndOffline("13300010676",driverAccessToken);

        //到店和送达开关关闭
        switchCityCollectionCode(siGuanToken, 0,"city_function_on_shop_take_meal_distance");
        switchCityCollectionCode(siGuanToken, 0,"city_function_deliver_distance");
        //报税开关，0关闭，1开启
        switchCountryCollectionCode(siGuanToken, "hp-delivery-server.us.uk.tax.config",0);
        driverOnline(driverAccessToken);
//        syncGps(driverAccessToken);
        
        // ========== 第四部分：调度分配流程 ==========

        Long assignDriverID = getAvailableDrivers(siGuanToken, userAppOrderSn);
        assignOrderToDriver(siGuanToken, userAppOrderSn, driverId);
        String packageId = getOrderPackage(driverAccessToken);
        receiveOrder(driverAccessToken, packageId);
        
        // ========== 第五部分：完单流程（收餐码订单） ==========
        // 6) 到店 -> 未出餐 -> 已取餐
        modifyDeliveryStatus(driverAccessToken, userAppOrderSn, 1);
        modifyDeliveryStatus(driverAccessToken, userAppOrderSn, 2);
        modifyDeliveryStatus(driverAccessToken, userAppOrderSn, 3);
        
        // 校验收餐码（正确）
        checkFoodDeliveryCode(driverAccessToken, userAppOrderSn, correctFoodDeliveryCode, 1000, 
                "成功", true);
        
        // 订单完成送达
        completeOrder(driverAccessToken, userAppOrderSn);
        switchMealCollectionCode(siGuanToken, 0,"city_function_meal_collection_code_switch");
    }

    private void switchMealCollectionCode(String siGuanToken, int switchType,String switchCode) {
        String uri = TestcaseConfig.HOST_ERP + "/api/deliveryAdmin/sysCityConfig/switch";
        Map<String, Object> headers = createErpHeaders();
        headers.put("authorization", siGuanToken);

        String body = String.format("{\"city\":\"杭州市\",\"functionKey\":\"%s\",\"switchType\":%d}", switchCode,switchType);


        var responseBody = TestCaseHelpful.sendRequest("POST", uri, null, headers, body);
        // apifox未给明确code断言，这里按通用message=成功
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }
    private String userAppLogin() {
        String uri = TestcaseConfig.HOST_USER_APP + "/api/user/combine/login";
        String method = "POST";
        Map<String, Object> headers = createUserAppHeaders();
        String body = "{\"areaCode\":\"86\",\"distinctId\":\"4dd9690f6a6b639c\"," +
                "\"password\":\"2c9341ca4cf3d87b9e4eb905d6a3ec45\",\"channel\":0," +
                "\"type\":\"2\",\"account\":\"13251016327\",\"stability\":0}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        return TestCaseHelpful.extractValue(responseBody, "$.result.accessToken").toString();
    }

    private Long getShopProductInfo(String userAppAccessToken) {
        String uri = TestcaseConfig.HOST_USER_APP + "/api/app/user/v1/shop/menuList";
        String method = "POST";
        Map<String, Object> headers = createUserAppHeaders();
        headers.put("authorization", userAppAccessToken);
        String body = "{\"deliveryType\":1,\"shopId\":892716498}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        return Long.parseLong(TestCaseHelpful.extractValue(responseBody, 
                "$.result.menuList[0].subMenuList[0].productList[0].productId").toString());
    }

    private Long addToCart(String userAppAccessToken, Long productId) {
        String uri = TestcaseConfig.HOST_USER_APP + "/api/app/user/order/v3/shoppingCart";
        String method = "POST";
        Map<String, Object> headers = createUserAppHeaders();
        headers.put("authorization", userAppAccessToken);
        long nowTime = System.currentTimeMillis();
        String body = String.format("{\"deliveryType\":1,\"shopId\":892716498," +
                "\"items\":[{\"productId\":%d,\"purchaseTime\":%d,\"skuId\":0,\"stability\":0}]}",
                productId, nowTime);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        return Long.parseLong(TestCaseHelpful.extractValue(responseBody, "$.result.cart.shopId").toString());
    }

    private void createVirtualOrder(String userAppAccessToken, Long shopId, Long productId) {
        String uri = TestcaseConfig.HOST_USER_APP + "/api/user/v1/order/toCreateVirtual";
        String method = "POST";
        Map<String, Object> headers = createUserAppHeaders();
        headers.put("authorization", userAppAccessToken);
        String body = String.format("{\"orderType\":1,\"openRedPacket\":0,\"autoUseRedPacketStatus\":1," +
                "\"shopId\":%d,\"deliveryType\":1}", shopId);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
    }

    private String createOrder(String userAppAccessToken, Long shopId, Long productId) {
        String uri = TestcaseConfig.HOST_USER_APP + "/api/user/v1/order/create";
        String method = "POST";
        Map<String, Object> headers = createUserAppHeaders();
        headers.put("authorization", userAppAccessToken);
        String body = String.format("{\"orderType\":1,\"shopId\":%d,\"deliveryType\":1," +
                "\"payType\":1,\"items\":[{\"productId\":%d,\"skuId\":0,\"quantity\":1}]}", 
                shopId, productId);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        return TestCaseHelpful.extractValue(responseBody, "$.result.orderSn").toString();
    }

    private void balancePay(String userAppAccessToken, String orderSn) {
        String uri = TestcaseConfig.HOST_USER_APP + "/api/user/v1/order/pay";
        String method = "POST";
        Map<String, Object> headers = createUserAppHeaders();
        headers.put("authorization", userAppAccessToken);
        String body = String.format("{\"orderSn\":\"%s\",\"payType\":1}", orderSn);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
    }

    /**
     * 从数据库查询收餐码
     */
    private String getFoodDeliveryCodeFromDatabase(String orderSn) {
//        String sql = String.format("select food_delivery_code from panda_test.`hp_delivery_order_extra_info` " +
//                "where order_sn = '%s'", orderSn);
        // 等待2秒
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        List<Map<String, Object>> resultList = PandaTestDBHelpful.executeSelectListSql(
                "select * from panda_test.`hp_delivery_order_extra_info` " +
                        "where order_sn = ?", orderSn);
        if (resultList != null && !resultList.isEmpty()) {
            Object foodDeliveryCode = resultList.get(0).get("food_delivery_code");


            String str = String.valueOf(foodDeliveryCode);
            String last4 = str.substring(str.length() - 4);

            return foodDeliveryCode != null ? last4 : "5678";
        }
        return "5678";
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
        headers.put("longitude", "120.216774");
        headers.put("latitude", "30.203453");
        headers.put("version", "5.71.0");
        headers.put("platform", "ANDROID_DELIVERY");
        headers.put("type", "3");
        headers.put("locale", "zh-CN");
        headers.put("operatingsystem", "1");
        headers.put("brand", "HUAWEI");
        headers.put("uniquetoken", "7b0169d78de40e6e");
        headers.put("apptypeid", "2");
        headers.put("countrycode", "CN");
        headers.put("devicesafetoken", "a0_b1_c1_h0_i0_j0_m0_n0_p0_s0");
        headers.put("enableSign", "false");
        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("content-type", "application/json;charset=UTF-8");
        headers.put("_sig", "a6887b8bb369138b43b5ea61b65c24ef1321a1bd");
        headers.put("_sign", "94d5b19105c1cf32c750d1b63c30ab99");
        headers.put("_ts", "1769682765876");
        headers.put("Accept", "*/*");
        headers.put("Cache-Control", "no-cache");
        headers.put("Host", "app-deliverytest.hungrypanda.cn");
        headers.put("Connection", "keep-alive");


        var requestBody = "{}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        System.out.println("此处打印"+responseBody);
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
     * 校验收餐码
     */
    private void checkFoodDeliveryCode(String driverAccessToken, String orderSn, 
                                       String foodDeliveryCode, int expectedResultCode,
                                       String expectedReason, boolean expectedSuccess) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/checkFoodDeliveryCode";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        String body = String.format("{\"orderSn\":\"%s\",\"foodDeliveryCode\":\"%s\"}", 
                orderSn, foodDeliveryCode);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(expectedResultCode);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo(expectedReason);
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(expectedSuccess);
    }

    /**
     * 订单完成送达
     */
    private void completeOrder(String driverAccessToken, String orderSn) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/modifyDeliveryStatus";
        String method = "POST";
        Map<String, Object> headers = createIOSDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        String body = String.format("{\"driverArriveType\":1,\"operationType\":6,\"orderCompleteImageUrlList\":[]," +
                "\"arriveRemark\":\"测试\",\"orderSnList\":[\"%s\"]}", orderSn);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
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
