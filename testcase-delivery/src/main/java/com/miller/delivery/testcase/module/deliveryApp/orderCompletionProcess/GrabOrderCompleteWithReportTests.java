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

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 骑手抢单-完单流程(到店报备送达报备）
 *
 * @author TestingConsultant@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/07
 */
@Scenario(
        scenarioID = "01K1TBDXXB3R2YWN2XGX946TCX",
        scenarioName = "骑手抢单-完单流程(到店报备送达报备）",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 240, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("骑手抢单-完单流程(到店报备送达报备）")
public class GrabOrderCompleteWithReportTests {

    @DisplayName("骑手抢单-完单流程(到店报备送达报备）完整流程")
    @Test
    void shouldCompleteGrabOrderWithReportFlow() {
        // ========== 第一部分：C侧下单流程 ==========
        CreateInstantOrderWithHandoverTests createInstantOrderWithHandoverTests = new CreateInstantOrderWithHandoverTests();
        String userAppOrderSn = createInstantOrderWithHandoverTests.orderFlow();

        // ========== 第二部分：司管操作流程 ==========
        // 步骤7: 司管登录获取token
        String siGuanToken = erpLogin();
        
        // 步骤8: 开启到店距离限制和送达距离限制
        switchMealCollectionCode(siGuanToken, 1,"city_function_on_shop_take_meal_distance");
        switchMealCollectionCode(siGuanToken, 1,"city_function_deliver_distance");
        
        // ========== 第三部分：骑手操作流程 ==========
        // 步骤9: 骑手app-骑手登录
        Map<String, String> driverLoginInfo = TestCaseHelpful.deliveryLoginReturndriverId("13300010676", "Test1234");
        String driverAccessToken = driverLoginInfo.get("accessToken");
        Long driverId = Long.valueOf(driverLoginInfo.get("userId"));
        DriverOffline driverOffline = new DriverOffline();
        driverOffline.cancelDispatchAndOffline("13300010676",driverAccessToken);
        
        // 步骤10: 骑手app-司机上线操作
        driverOnline(driverAccessToken);
        

        
        // ========== 第四部分：调度分配流程 ==========
        // 步骤12: 调度-获取订单下可分配的骑手
        Long assignDriverID = getAvailableDrivers(siGuanToken, userAppOrderSn);
        
        // 步骤13: 调度-分配订单给骑手（强制分配）
        assignOrderToDriver(siGuanToken, userAppOrderSn, driverId);
        
        // 步骤14: 派单页面 - 获取packageId
        String packageId = getOrderPackage(driverAccessToken);
        
        // 步骤15: 骑手app-骑手接单
        receiveOrder(driverAccessToken, packageId);
        
        // ========== 第五部分：到店报备流程 ==========
        // 步骤16: 到店距离不满足给提示
        checkArriveDistance(driverAccessToken, userAppOrderSn);
        
        // 步骤17: 到店距离不满足进行报备
        reportArriveDistance(driverAccessToken, userAppOrderSn);
        
        // 步骤18: 修改骑手配送状态-到店
        modifyDeliveryStatus(driverAccessToken, userAppOrderSn, 1);
        
        // 步骤19: 骑手配送状态-未出餐
        modifyDeliveryStatus(driverAccessToken, userAppOrderSn, 2);
        
        // 步骤20: 骑手配送状态-已取餐
        modifyDeliveryStatus(driverAccessToken, userAppOrderSn, 3);
        
        // ========== 第六部分：送达报备流程 ==========
        // 步骤21: 获取骑手配送中订单列表
        getDeliveringList(driverAccessToken);
        
        // 步骤22: 获取用户手机号
        getUserPhone(driverAccessToken, userAppOrderSn);
        
        // 步骤23: 获取离线地图
        getOfflineMap(driverAccessToken);
        
        // 步骤24: 送达距离不满足报备
        reportDeliveryDistance(driverAccessToken, userAppOrderSn);
        
        // 步骤25: 订单完成送达
        completeOrder(driverAccessToken, userAppOrderSn);
        

        
        // 步骤27: 司机下线操作
        driverOffline(driverAccessToken);

        switchMealCollectionCode(siGuanToken, 0,"city_function_on_shop_take_meal_distance");
        switchMealCollectionCode(siGuanToken, 0,"city_function_deliver_distance");
    }

    /**
     * 开启到店距离限制和送达距离限制
     */
    private void enableDistanceLimit(String siGuanToken) {
        String uri = TestcaseConfig.HOST_ERP + "/api/dispatch/config/enableDistanceLimit";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("token", siGuanToken);
        
        String body = "{\"enableArriveDistanceLimit\":true,\"enableDeliveryDistanceLimit\":true}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
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

    /**
     * 骑手app-司机上线操作
     */
    private void driverOnline(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/driver/onOffline";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = "{\"isOnline\":1}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
    }

    /**
     * 上传骑手经纬度
     */
    private void syncGps(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/driver/syncGps";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = "{\"longitude\":120.216727,\"latitude\":30.203499}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
    }

    /**
     * 调度-获取订单下可分配的骑手
     */
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

    /**
     * 调度-分配订单给骑手（强制分配）
     */
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

    /**
     * 派单页面 - 获取packageId
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
        System.out.println("此处打印" + responseBody);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        return TestCaseHelpful.extractValue(responseBody, "$.result.dataList[0].packageId").toString();
    }

    /**
     * 骑手app-骑手接单
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
     * 到店距离不满足给提示
     */
    private void checkArriveDistance(String driverAccessToken, String orderSn) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/modifyDeliveryStatus";
        String method = "POST";
        Map<String, Object> headers = createIOSDriverAppHeaders();
        headers.put("authorization", driverAccessToken);


        String body = String.format("{\"operationType\":1,\"orderSnList\":[\"%s\"]}", orderSn);

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isNotNull();
    }

    /**
     * 到店距离不满足进行报备
     */
    private void reportArriveDistance(String driverAccessToken, String orderSn) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/driverOrderReport";
        String method = "POST";
        Map<String, Object> headers = createIOSDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = String.format("{\n" +
                "    \"orderSn\": \"%s\",\n" +
                "    \"operationType\": 1,\n" +
                "    \"type\": 0,\n" +
                "    \"url\": \"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/1754360171624b7bd0b21a49449efb6cceb63f01dace0.jpg\"\n" +
                "}",orderSn);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
    }

    /**
     * 修改骑手配送状态
     * @param operationType 1-到店, 2-取餐, 3-送达
     */
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
     * 获取骑手配送中订单列表
     */
    private void getDeliveringList(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/waitDeliveringList";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = "{\"sortType\":0,\"pageNo\":1,\"pageSize\":20}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
    }

    /**
     * 获取用户手机号
     */
    private void getUserPhone(String driverAccessToken, String orderSn) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/customerInfo/customerPhone";
        String method = "POST";
        Map<String, Object> headers = createIOSDriverAppHeaders();
        headers.put("authorization", driverAccessToken);

        var requestBody = String.format("{\"orderSn\":\"%s\"}", orderSn);

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
     * 送达距离不满足报备
     */
    private void reportDeliveryDistance(String driverAccessToken, String orderSn) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/deliveryCheckDistanceAbnormal";
        String method = "POST";
        Map<String, Object> headers = createIOSDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = String.format("{\n" +
                "    \"orderSn\": \"%s\",\n" +
                "    \"operationType\": 6,\n" +
                "    \"url\": \"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/17543601028608e78eeaf211b4bdba4577f5b9a8c4ef2.jpg\"\n" +
                "}",orderSn);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
    }

    /**
     * 订单完成送达
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
     * 关闭到店距离限制和送达距离限制
     */
    private void disableDistanceLimit(String siGuanToken) {
        String uri = TestcaseConfig.HOST_ERP + "/api/dispatch/config/enableDistanceLimit";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("token", siGuanToken);
        
        String body = "{\"enableArriveDistanceLimit\":false,\"enableDeliveryDistanceLimit\":false}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
    }

    /**
     * 司机下线操作
     */
    private void driverOffline(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/driver/onOffline";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = "{\"isOnline\":0}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
    }

    /**
     * 创建C侧用户app请求头
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
        headers.put("device_safe_token", "a0_b1_c0_h0_i0_j0_m0_n0_p0_s0");

        headers.put("Content-Type", "application/json;charset=UTF-8");
        return headers;
    }

    /**
     * 创建骑手app请求头
     */
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

    /**
     * 创建iOS骑手app请求头
     */
    private Map<String, Object> createIOSDriverAppHeaders() {
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("platform", "IOS_DELIVERY");
        return headers;
    }

    /**
     * 创建ERP请求头
     */
    private Map<String, Object> createErpHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return headers;
    }
}
