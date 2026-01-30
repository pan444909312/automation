package com.miller.delivery.testcase.module.dispatch.order;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.module.deliveryUtils.order.CreateInstantOrderWithHandoverTests;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.delivery.testcase.utils.driverOffline;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 调度分单-骑手接单-完单
 *
 * @author auto-generated
 * @version 2.0
 * @since 2025/01/01 00:00:00
 */
@Scenario(
        scenarioID = "01JPPFP66G3CTQ065G223KY07P",
        scenarioName = "【主干用例】调度系统-分配订单-调度分单骑手完单",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 180, maintenanceTime = 0, manualTestTime = 90)
@DisplayName("调度分单-骑手接单-完单")
public class DispatchAssignReceiveCompleteTests {

    @DisplayName("骑手app-骑手接单-多case：包裹不存在")
    @Test
    void shouldFailReceiveWhenPackageNotBelongToDriver() {
        // 步骤7: 骑手app-骑手登录
        Map<String, String> driverLoginInfo = TestCaseHelpful.deliveryLoginReturndriverId("13300010676", "Test1234");
        String driverAccessToken = driverLoginInfo.get("accessToken");
        Long driverId = Long.valueOf(driverLoginInfo.get("userId"));

        driverOffline driverOffline = new driverOffline();
        driverOffline.cancelDispatchAndOffline("13300010676",driverAccessToken);

        // 2) 接单：包裹不存在/不属于当前司机
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/orderPackage/receiveOrReject";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("Authorization", driverAccessToken);
        headers.put("operatingsystem", "2");
        headers.put("content-type", "application/json");

        String requestBody = "{\"orderPackageId\":\"11111\",\"type\":1}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);

        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(130060);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("包裹不是当前司机");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(false);
    }

    @DisplayName("骑手app-骑手拒单-多case：未登录拒单")
    @Test
    void shouldFailRejectWhenNotLoggedIn() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/orderPackage/receiveOrReject";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("operatingsystem", "2");
        headers.put("content-type", "application/json");

        // 不传 Authorization
        String requestBody = "{\"orderPackageId\":\"11111\",\"type\":2,\"rejectReason\":\"订单不顺路\"}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);

        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(2015);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("未登录,请登录后操作");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(false);
    }

    @DisplayName("完整端到端流程-调度分单-骑手接单-完单")
    @Test
    void shouldCompleteAssignReceiveAndCompleteFlow() {
        // ========== 第一部分：C侧下单流程 ==========
        CreateInstantOrderWithHandoverTests createInstantOrderWithHandoverTests = new CreateInstantOrderWithHandoverTests();
        String userAppOrderSn = createInstantOrderWithHandoverTests.orderFlow();
        
        // ========== 第二部分：骑手操作流程 ==========
        // 步骤7: 骑手app-骑手登录
        Map<String, String> driverLoginInfo = TestCaseHelpful.deliveryLoginReturndriverId("13300010676", "Test1234");
        String driverAccessToken = driverLoginInfo.get("accessToken");
        Long driverId = Long.valueOf(driverLoginInfo.get("userId"));

        driverOffline driverOffline = new driverOffline();
        driverOffline.cancelDispatchAndOffline("13300010676",driverAccessToken);
        
        // 步骤8: 骑手app-司机上线操作
        driverOnline(driverAccessToken);
        
        // 步骤9: 上传骑手经纬度
        syncGps(driverAccessToken);
        
        // ========== 第三部分：调度分配流程 ==========
        // 步骤10: 司管登录获取token
        String siGuanToken = erpLogin();
        
        // 步骤11: 调度-获取订单下可分配的骑手
        Long assignDriverID = getAvailableDrivers(siGuanToken, userAppOrderSn);
        
        // 步骤12: 调度-分配订单给骑手（强制分配）
        assignOrderToDriver(siGuanToken, userAppOrderSn, driverId, false);
        
        // 步骤13: 派单页面 - 获取packageId
        String packageId = getOrderPackage(driverAccessToken);
        
        // 步骤14: 骑手app-骑手接单
        receiveOrder(driverAccessToken, packageId);
        
        // ========== 第四部分：骑手配送流程 ==========
        // 步骤15: 骑手app-修改骑手配送状态-到店
        modifyDeliveryStatus(driverAccessToken, userAppOrderSn, 1);
        
        // 步骤16: 骑手app-骑手配送状态-未出餐
        modifyDeliveryStatus(driverAccessToken, userAppOrderSn, 2);
        
        // 步骤17: 骑手app-骑手配送状态-已取餐
        modifyDeliveryStatus(driverAccessToken, userAppOrderSn, 3);
        
        // 步骤18: 骑手app-修改订单配送状态-签收
        completeOrder(driverAccessToken, userAppOrderSn);
        // 步骤26: 完单后-司机下线操作
        driverOffline(driverAccessToken);
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
     * 司机上线操作
     */
    private void driverOnline(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/driver/onOffline";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("Authorization", driverAccessToken);
        headers.put("operatingsystem", "1");
        headers.put("longitude", "120.217095");
        headers.put("latitude", "30.203565");
        
        var requestBody = "{\"isOnline\":1}";
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
    }

    /**
     * 上传骑手经纬度
     */
    private void syncGps(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/home/syncGps";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        headers.put("operatingsystem", "2");
        headers.put("longitude", "120.216893");
        headers.put("latitude", "30.203568");
        
        var requestBody = "{}";
        
        TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
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
    private void assignOrderToDriver(String siGuanToken, String userAppOrderSn, Long assignDriverID, boolean rejectAble) {
        String uri = TestcaseConfig.HOST_ERP + "/api/dispatch/dispatch/assign";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("token", siGuanToken);
        
        int rejectAbleValue = rejectAble ? 1 : 0;
        var requestBody = String.format("{\"deliveryId\":%d,\"orderSn\":\"%s\",\"rejectAble\":%d}", assignDriverID, userAppOrderSn, rejectAbleValue);
        
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
     * 骑手签收订单
     */
    private void completeOrder(String driverAccessToken, String orderSn) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/modifyDeliveryStatus";
        String method = "POST";
        Map<String, Object> headers = createIOSDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        var requestBody = String.format("{\"driverArriveType\":11,\"operationType\":6,\"arriveRemark\":\"留言备注内容-apifox自动化测试创建，图片默认写死资源地址，免去每次上传图片到oss\",\"waitUserArrive\":0,\"orderSn\":\"%s\",\"orderCompleteImageUrlList\":[\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/170174606688616113ac9a0a74ab29cdadf98ad4cf090.jpg\"],\"orderSnList\":[\"%s\"]}",
                orderSn, orderSn);

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
     * 司管后台登录并返回token
     */
    private String erpLogin() {
        String uri = TestcaseConfig.HOST_ERP + "/api/erp/auth/login/v2";
        String method = "POST";
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/erp/auth/request/headers.json");
         
        headers.put("Content-Type", "application/json");

        var requestBody = TestCaseHelpful.getJsonRequestBody("module/erp/auth/request/body.json");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.password", "4f2142904392cbef6974ad0260caeb33");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.userName", "ding17058431144045523");

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        return TestCaseHelpful.extractValue(responseBody, "$.data.token").toString();
    }

    /**
     * 创建骑手app请求头
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/delivery/auth/request/headers.json");
         
        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("Content-Type", "application/json");
        return headers;
    }

    /**
     * 创建ERP请求头
     */
    private Map<String, Object> createErpHeaders() {
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/dispatch/order/orderAdjustmentPrice/request/headers.json");
        headers.put("accept", "application/json, text/plain, */*");
        headers.put("content-type", "application/json;charset=UTF-8");
         
        return headers;
    }
}

