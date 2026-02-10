package com.miller.delivery.testcase.module.deliveryApp.uDeskTalk;

import com.miller.common.util.MD5Util;
import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.module.deliveryUtils.order.CreateInstantOrderWithHandoverTests;
import com.miller.delivery.testcase.utils.DriverOffline;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * im聊天相关
 *
 * @author TestingConsultant@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/07
 */
@Scenario(
        scenarioID = "01JWBJW1ZV4W2328R0WGK12B69",
        scenarioName = "im聊天相关",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 240, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("im聊天相关")
public class ImChatTests {

    @DisplayName("获取订单对应groupid")
    @Test
    void shouldGetOrderGroupId() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/waitDeliveringList";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = "{\"sortType\":0,\"pageNo\":1,\"pageSize\":20}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    @DisplayName("进入im聊天界面")
    @Test
    void shouldGetImToken() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/im/getImToken";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = "{}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    @DisplayName("获取订单对应的im相关信息")
    @Test
    void shouldGetOrderImStatus() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        
//        // 先获取imId和groupId
//        String imId = getImId(driverAccessToken);
//        String groupId = getGroupId(driverAccessToken);
//
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/im/getOrderStatus";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);


        String body = String.format("{\"driverImId\":\"\",\"groupId\":\"{{ImGroup_id}}\"}");
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    @DisplayName("发送订单数据")
    @Test
    void shouldSendOrderInfo() {
        // ========== 第一部分：C侧下单流程 ==========
        CreateInstantOrderWithHandoverTests createInstantOrderWithHandoverTests = new CreateInstantOrderWithHandoverTests();
        String userAppOrderSn = createInstantOrderWithHandoverTests.orderFlow();
        Map<String, String> driverLoginInfo = TestCaseHelpful.deliveryLoginReturndriverId("13300010676", "Test1234");
        String driverAccessToken = driverLoginInfo.get("accessToken");
        Long driverId = Long.valueOf(driverLoginInfo.get("userId"));
        DriverOffline driverOffline = new DriverOffline();
        driverOffline.cancelDispatchAndOffline("13300010676",driverAccessToken);

        // 步骤8: 骑手app-司机上线操作
        driverOnline(driverAccessToken);

        // ========== 第三部分：调度分配流程 ==========
        // 步骤9: 司管登录获取token
        String siGuanToken = erpLogin();

        // 步骤10: 调度-获取订单下可分配的骑手
        Long assignDriverID = getAvailableDrivers(siGuanToken, userAppOrderSn);

        // 步骤11: 调度-分配订单-正常分配（非强制分配）
        assignOrderToDriver(siGuanToken, userAppOrderSn, driverId);

        // 步骤12: 派单页面 - 获取packageId
        String packageId = getOrderPackage(driverAccessToken);

//

        // 步骤14: 骑手app-骑手接单
        receiveOrder(driverAccessToken, packageId);

        // ========== 第四部分：骑手配送流程 ==========

        // 步骤8: 开启到店距离限制和送达距离限制
        switchMealCollectionCode(siGuanToken, 0,"city_function_on_shop_take_meal_distance");
        switchMealCollectionCode(siGuanToken, 0,"city_function_deliver_distance");
        // 步骤19: 修改骑手配送状态-到店
        modifyDeliveryStatus(driverAccessToken, userAppOrderSn, 1);

        // 步骤20: 骑手配送状态-未出餐
        modifyDeliveryStatus(driverAccessToken, userAppOrderSn, 2);

        // 步骤21: 骑手配送状态-已取餐
        modifyDeliveryStatus(driverAccessToken, userAppOrderSn, 3);


        // 先获取imId和groupId
//        String imId = getImId(driverAccessToken);
//        String groupId = getGroupId(driverAccessToken);
        
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/im/getOrderInfo";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeadersForIm();
        headers.put("authorization", driverAccessToken);
        
        String body = String.format("{\"driverImId\":\"\",\"groupId\":\"{{ImGroup_id}}\"," +
                "\"orderSn\":\"%s\",\"userId\":%s}", userAppOrderSn,driverId);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
        driverOffline.cancelDispatchAndOffline("13300010676",driverAccessToken);
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

    @DisplayName("未登录 -发送订单数据")
    @Test
    void shouldFailSendOrderInfoWithoutLogin() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/im/getOrderInfo";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeadersForIm();
        // 不设置authorization
        
        String body = "{\"driverImId\":\"\",\"groupId\":\"\",\"orderSn\":\"623448144834971301340\",\"userId\":1398723146}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(2015);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("未登录,请登录后操作");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(false);
        // 等待2秒
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @DisplayName("获取环信token--骑手未登录")
    @Test
    void shouldFailGetImTokenWithoutLogin() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/im/getImToken";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        // 不设置authorization
        
        String body = "{\"driverImId\":\"\",\"groupId\":\"\",\"orderSn\":\"623448144834971301340\",\"userId\":\"\"}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(2015);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("未登录,请登录后操作");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(false);
        // 等待2秒
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @DisplayName("获取环信token--无订单返回成功")
    @Test
    void shouldGetImTokenWithoutOrder() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/im/getImToken";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = "{\"driverImId\":\"\",\"groupId\":\"\",\"orderSn\":\"\",\"userId\":\"\"}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
        TestCaseHelpful.assertThatJson(responseBody).node("result.isClose").isEqualTo(0);
        // 等待2秒
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @DisplayName("查询通用聊天提示和快捷回复")
    @Test
    void shouldGetAllTipAndQuick() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/im/getAllTipAndQuick";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = "{\"driverImId\":\"null\",\"groupId\":\"297307705769985\"," +
                "\"orderSn\":\"null\",\"userId\":null}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
        // 等待2秒
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @DisplayName("未登录 - 查询通用聊天提示和快捷回复")
    @Test
    void shouldFailGetAllTipAndQuickWithoutLogin() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/im/getAllTipAndQuick";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        // 不设置authorization
        
        String body = "{\"driverImId\":\"null\",\"groupId\":\"297307705769985\"," +
                "\"orderSn\":\"null\",\"userId\":null}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(2015);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("未登录,请登录后操作");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(false);
        // 等待2秒
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 骑手登录并返回accessToken和userId
     */
    private Map<String, String> driverLogin(String account, String password) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/auth/login";
        String method = "POST";
        Map<String, Object> headers = new HashMap<>();
         
        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("Content-Type", "application/json");
        
        // 对密码进行MD5加密
        String md5Password = MD5Util.string2MD5(password);
        String body = String.format("{\"areaCode\":\"86\",\"password\":\"%s\",\"account\":\"%s\"}",
                md5Password, account);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
        
        String accessToken = TestCaseHelpful.extractValue(responseBody, "$.result.accessToken").toString();
        String userId = TestCaseHelpful.extractValue(responseBody, "$.result.userId").toString();
        
        return Map.of("accessToken", accessToken, "userId", userId);
    }

    /**
     * 获取imId
     */
    private String getImId(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/im/getImToken";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = "{}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        return TestCaseHelpful.extractValue(responseBody, "$.result.imId").toString();
    }

    /**
     * 获取groupId
     */
    private String getGroupId(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/waitDeliveringList";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = "{\"sortType\":0,\"pageNo\":1,\"pageSize\":20}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        return TestCaseHelpful.extractValue(responseBody, "$.result.orderList[0].imInfo.groupId").toString();
    }

    /**
     * 创建骑手app请求头
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("longitude", "120.216787");
        headers.put("latitude", "30.203426");
        headers.put("version", "5.66.0");
        headers.put("platform", "ANDROID_DELIVERY");
        headers.put("type", "3");
        headers.put("locale", "zh-CN");
        headers.put("operatingsystem", "1");
        headers.put("brand", "HUAWEI");
        headers.put("uniquetoken", "dd9959880e28753f");
        headers.put("apptypeid", "2");
        headers.put("countrycode", "CN");
        headers.put("devicesafetoken", "a0_b1_c1_h0_i0_j0_m0_n0_p0_s0");
         
        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }

    /**
     * 创建骑手app请求头（用于IM相关接口）
     */
    private Map<String, Object> createDriverAppHeadersForIm() {
        Map<String, Object> headers = new HashMap<>();

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

        return headers;
    }
    /**
     * 司机上线操作
     */
    private void driverOnline(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/driver/onOffline";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        headers.put("operatingsystem", "1");
        headers.put("longitude", "120.217095");
        headers.put("latitude", "30.203565");

        var requestBody = "{\"isOnline\":1}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
    }

    /**
     * 获取可分配的骑手
     */
    private Long getAvailableDrivers(String siGuanToken, String userAppOrderSn) {
        String uri = TestcaseConfig.HOST_ERP + "/api/dispatch/driver/allList";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("token", siGuanToken);

        var requestBody = String.format("{\"orderSn\":\"%s\"}", userAppOrderSn);

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        return Long.parseLong(TestCaseHelpful.extractValue(responseBody, "$.data[0].userId").toString());
    }

    /**
     * 分配订单给骑手
     */
    private void assignOrderToDriver(String siGuanToken, String userAppOrderSn, Long assignDriverID) {
        String uri = TestcaseConfig.HOST_ERP + "/api/dispatch/dispatch/assign";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("token", siGuanToken);

        var requestBody = String.format("{\"deliveryId\":%d,\"orderSn\":\"%s\",\"rejectAble\":1}", assignDriverID, userAppOrderSn);

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    /**
     * 获取派单页面
     */
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

//    /**
//     * 骑手app-获取骑手待接单列表
//     */
//    private void getOrderPackageList(String driverAccessToken) {
//        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/orderPackage/list";
//        String method = "POST";
//        Map<String, Object> headers = createIOSDriverAppHeaders();
//        headers.put("Authorization", driverAccessToken);
//
//        var requestBody = "{}";
//
//        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
//        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
//        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
//        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
//    }

    /**
     * 骑手接单
     */
    private void receiveOrder(String driverAccessToken, String packageId) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/orderPackage/receiveOrReject";
        String method = "POST";
        Map<String, Object> headers = createIOSDriverAppHeaders();
        headers.put("Authorization", driverAccessToken);

        var requestBody = String.format("{\"orderPackageId\":\"%s\",\"type\":1}", packageId);

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    /**
     * 获取待取餐列表
     */
    private void getWaitPickUpList(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/waitPickUpList";
        String method = "POST";
        Map<String, Object> headers = createIOSDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        var requestBody = "{\"pageNo\":1,\"sortType\":0}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
        // 断言orderList不为空
        Object orderList = TestCaseHelpful.extractValue(responseBody, "$.result.orderList");
        assert orderList != null : "orderList不应为空";
    }
    /**
     * 修改骑手配送状态
     */
    private void modifyDeliveryStatus(String driverAccessToken, String orderSn, int operationType) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/modifyDeliveryStatus";
        String method = "POST";
        Map<String, Object> headers = createIOSDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        var requestBody = String.format("{\"orderSn\":\"%s\",\"orderCompleteImageUrlList\":[],\"waitUserArrive\":0,\"operationType\":%d,\"orderSnList\":[\"%s\"],\"driverArriveType\":0}",
                orderSn, operationType, orderSn);

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        if (operationType == 1) {
            // 到店时，断言estimateOutMealMsg不为空
            Object estimateOutMealMsg = TestCaseHelpful.extractValue(responseBody, "$.result.driverMerchantEstimateOutMealResp.estimateOutMealMsg");
            assert estimateOutMealMsg != null : "estimateOutMealMsg不应为空";
        }
    }

    /**
     * 获取骑手配送中订单列表
     */
    private void getDeliveringList(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/waitDeliveringList";
        String method = "POST";
        Map<String, Object> headers = createIOSDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        var requestBody = "{\"pageNo\":1,\"sortType\":0}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
        // 断言orderList不为空
        Object orderList = TestCaseHelpful.extractValue(responseBody, "$.result.orderList");
        assert orderList != null : "orderList不应为空";
    }

    /**
     * 获取用户手机号
     */
    private void getCustomerPhone(String driverAccessToken, String userAppOrderSn) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/customerInfo/customerPhone";
        String method = "POST";
        Map<String, Object> headers = createIOSDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        var requestBody = String.format("{\"orderSn\":\"%s\"}", userAppOrderSn);

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    /**
     * 获取离线地图
     */
    private void getOfflineMap(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/map/route/plan";
        String method = "POST";
        Map<String, Object> headers = createIOSDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        var requestBody = "{\"point\":[{\"type\":1,\"toPoint\":{\"lat\":30.1965002066,\"lon\":120.2165139689},\"fromPoint\":{\"lat\":30.203602097957489,\"lon\":120.21675840570194}},{\"fromPoint\":{\"lat\":30.1965002066,\"lon\":120.2165139689},\"toPoint\":{\"lon\":120.244367,\"lat\":30.183143999999999},\"type\":1}]}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    /**
     * 修改订单配送状态-签收
     */
    private void completeOrder(String driverAccessToken, String userAppOrderSn) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/modifyDeliveryStatus";
        String method = "POST";
        Map<String, Object> headers = createIOSDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        var requestBody = String.format("{\"driverArriveType\":11,\"operationType\":6,\"arriveRemark\":\"留言备注内容-apifox自动化测试创建，图片默认写死资源地址，免去每次上传图片到oss\",\"waitUserArrive\":0,\"orderSn\":\"%s\",\"orderCompleteImageUrlList\":[\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/170174606688616113ac9a0a74ab29cdadf98ad4cf090.jpg\"],\"orderSnList\":[\"%s\"]}",
                userAppOrderSn, userAppOrderSn);

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        // 等待2秒
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 完单后-司机下线操作
     */
    private void driverOffline(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/driver/onOffline";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        headers.put("longitude", "120.2168953");
        headers.put("latitude", "30.2035072");
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

        headers.put("content-type", "application/json;charset=UTF-8");

        var requestBody = "{\"continueDown\":1,\"isOnline\":0}";

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    /**
     * 创建C侧app请求头
     */
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

        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }



    /**
     * 创建骑手app请求头（iOS）
     */
    private Map<String, Object> createIOSDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();

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
        return headers;
    }

    /**
     * 创建ERP请求头
     */
    private Map<String, Object> createErpHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("accept", "application/json, text/plain, */*");
        headers.put("accept-language", "zh-CN,zh;q=0.9");
        headers.put("content-type", "application/json;charset=UTF-8");

        return headers;
    }
}
