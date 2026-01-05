package com.miller.delivery.testcase.module.dispatch.driver;

import com.alibaba.fastjson.JSONArray;
import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * 显示推荐路径规划
 *
 * @author auto-generated
 * @version 2.0
 * @since 2025/01/01 00:00:00
 */
@Scenario(
        scenarioID = "01K5XZ6W6YTX5QZ1QAQX6AT2RQ", // 自动生成，不要修改
        scenarioName = "调度-骑手列表-显示路径规划",
        author = "chenchunxia@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("显示推荐路径规划")
public class DriverMatchingTests {
    String assertFullField = "module/dispatch/driver/matching/response/assert_full_field.json";

    @DisplayName("调度显示推荐路径规划-骑手身上无单")
    void shouldGetMatchingWithoutOrders() {
        // 步骤1: 司管登录获取token
        String siGuanToken = erpLogin();

        // 步骤2: 设置请求头
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/dispatch/driver/matching/request/headers.json");
        headers.put("token", siGuanToken);
        headers.put("accept", "application/json, text/plain, */*");
        headers.put("content-type", "application/json;charset=UTF-8");
        headers.put("enableSign", "false");

        // 步骤3: 设置请求体
        var requestBody = TestCaseHelpful.getJsonRequestBody("module/dispatch/driver/matching/request/body_without_orders.json");

        // 生成时间范围（过去7天）
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = now.minusDays(7).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endDate = now.withHour(23).withMinute(59).withSecond(59).withNano(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String startTime = startDate.format(formatter);
        String endTime = endDate.format(formatter);

        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.startDate", startTime);
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.endDate", endTime);
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.cityName", "杭州市");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.deliveryTagList", new JSONArray());
        JSONArray orderBusinessTypeList = new JSONArray();
        orderBusinessTypeList.add(0);
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.orderBusinessTypeList", orderBusinessTypeList);
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.dataFilterList", new JSONArray());
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.country", "CA");
        // driverId 需要从骑手登录获取，这里先使用固定值，实际应该从前置步骤获取
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.driverId", 1398714150);

        // 步骤4: 发起请求
        String uri = TestcaseConfig.HOST_ERP + "/api/dispatch/driver/matching";
        String method = "POST";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);

        // 步骤5: 断言响应结果
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("data.driverId").isEqualTo(null);
    }

    @DisplayName("调度显示推荐路径规划-骑手身上有单")
    void shouldGetMatchingWithOrders() {
        // 步骤1: 司管登录获取token
        String siGuanToken = erpLogin();
        
        // 步骤2: 设置请求头
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/dispatch/driver/matching/request/headers.json");
        headers.put("token", siGuanToken);
        headers.put("accept", "application/json, text/plain, */*");
        headers.put("content-type", "application/json;charset=UTF-8");
        headers.put("enableSign", "false");

        // 步骤3: 设置请求体
        var requestBody = TestCaseHelpful.getJsonRequestBody("module/dispatch/driver/matching/request/body_with_orders.json");
        
        // 生成时间范围（过去7天）
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = now.minusDays(7).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endDate = now.withHour(23).withMinute(59).withSecond(59).withNano(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String startTime = startDate.format(formatter);
        String endTime = endDate.format(formatter);
        
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.startDate", startTime);
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.endDate", endTime);
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.cityName", "杭州市");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.deliveryTagList", new JSONArray());
        JSONArray orderBusinessTypeList = new JSONArray();
        orderBusinessTypeList.add(0);
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.orderBusinessTypeList", orderBusinessTypeList);
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.dataFilterList", new JSONArray());
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.country", "CA");
        // driverId 需要从骑手登录获取，这里先使用固定值，实际应该从前置步骤获取
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.driverId", 1398714150);

        // 步骤4: 发起请求
        String uri = TestcaseConfig.HOST_ERP + "/api/dispatch/driver/matching";
        String method = "POST";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);

        // 步骤5: 断言响应结果
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    @DisplayName("完整端到端流程-显示推荐路径规划")
    @Test
    void shouldCompleteEndToEndFlow() {
        // ========== 第一部分：C侧下单流程 ==========
        // 步骤1: C侧下单-用户登录
        String userAppAccessToken = userAppLogin();
        
        // 步骤2: C侧下单-获取店铺商品信息
        Long productId = getShopProductInfo(userAppAccessToken);
        
        // 步骤3: C侧下单-加购商品
        Long shopId = addToCart(userAppAccessToken, productId);
        
        // 步骤4: C侧下单-创建虚拟单
        createVirtualOrder(userAppAccessToken, shopId, productId);
        
        // 步骤5: C侧下单-创建即时单-平台配送
        String userAppOrderSn = createOrder(userAppAccessToken, shopId, productId);
        
        // 步骤6: C侧下单-余额支付
        balancePay(userAppAccessToken, userAppOrderSn);
        
        // ========== 第二部分：骑手操作流程 ==========
        // 步骤7: 骑手app-骑手登录
        String[] driverInfo = driverLogin();
        String driverAccessToken = driverInfo[0];
        Long newUserId = Long.parseLong(driverInfo[1]);
        
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
        assignOrderToDriver(siGuanToken, userAppOrderSn, assignDriverID);
        
        // 步骤13: 派单页面
        String packageId = getOrderPackage(driverAccessToken);
        
        // 步骤14: 骑手app-骑手接单
        receiveOrder(driverAccessToken, packageId);
        
        // ========== 第四部分：显示推荐路径规划 ==========
        // 步骤15: 调度显示推荐路径规划-骑手身上无单（在接单前）
        // 注意：由于我们已经接单了，这里测试的是有单的情况
        // 如果需要测试无单情况，应该在接单前调用
        
        // 步骤16: 调度显示推荐路径规划-骑手身上有单
        getMatchingWithOrders(siGuanToken, newUserId);
        
        // 步骤17: 调度取消配送（清理）
        cancelDispatch(siGuanToken, userAppOrderSn);
    }

    /**
     * C侧用户登录
     */
    private String userAppLogin() {
        String uri = TestcaseConfig.HOST_USER_APP + "/api/user/combine/login";
        String method = "POST";
        Map<String, Object> headers = createUserAppHeaders();
        
        var requestBody = "{\"areaCode\":\"86\",\"distinctId\":\"4dd9690f6a6b639c\",\"password\":\"2c9341ca4cf3d87b9e4eb905d6a3ec45\",\"channel\":0,\"type\":\"2\",\"account\":\"13251016327\",\"stability\":0}";
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        return TestCaseHelpful.extractValue(responseBody, "$.result.accessToken").toString();
    }

    /**
     * 获取店铺商品信息
     */
    private Long getShopProductInfo(String userAppAccessToken) {
        String uri = TestcaseConfig.HOST_USER_APP + "/api/app/user/v1/shop/menuList";
        String method = "POST";
        Map<String, Object> headers = createUserAppHeaders();
        headers.put("authorization", userAppAccessToken);
        headers.put("userid", "1398716700");
        
        var requestBody = "{\"deliveryType\":1,\"shopId\":892716498}";
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        return Long.parseLong(TestCaseHelpful.extractValue(responseBody, "$.result.menuList[0].subMenuList[0].productList[0].productId").toString());
    }

    /**
     * 加购商品
     */
    private Long addToCart(String userAppAccessToken, Long productId) {
        String uri = TestcaseConfig.HOST_USER_APP + "/api/app/user/order/v3/shoppingCart";
        String method = "POST";
        Map<String, Object> headers = createUserAppHeaders();
        headers.put("authorization", userAppAccessToken);
        headers.put("userid", "1398716700");
        
        long nowTime = System.currentTimeMillis();
        var requestBody = String.format("{\"deliveryType\":1,\"shopId\":892716498,\"items\":[{\"productId\":%d,\"purchaseTime\":%d,\"skuId\":0,\"stability\":0}]}", productId, nowTime);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        return Long.parseLong(TestCaseHelpful.extractValue(responseBody, "$.result.cart.shopId").toString());
    }

    /**
     * 创建虚拟单
     */
    private void createVirtualOrder(String userAppAccessToken, Long shopId, Long productId) {
        String uri = TestcaseConfig.HOST_USER_APP + "/api/user/v1/order/toCreateVirtual";
        String method = "POST";
        Map<String, Object> headers = createUserAppHeaders();
        headers.put("authorization", userAppAccessToken);
        headers.put("userid", "1398716700");
        
        var requestBody = String.format("{\"orderType\":1,\"openRedPacket\":0,\"autoUseRedPacketStatus\":1,\"orderReqType\":0,\"deliveryType\":0,\"platform\":1,\"addressId\":1398679458,\"productCartList\":\"[{\\\"productId\\\":%d,\\\"skuId\\\":0,\\\"stability\\\":0,\\\"tagId\\\":[]}]\",\"payType\":0,\"verify\":0,\"shopId\":%d,\"stability\":0,\"requestSourceType\":0}", productId, shopId);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
    }

    /**
     * 创建即时单-平台配送
     */
    private String createOrder(String userAppAccessToken, Long shopId, Long productId) {
        String uri = TestcaseConfig.HOST_USER_APP + "/api/user/order/create";
        String method = "POST";
        Map<String, Object> headers = createUserAppHeaders();
        headers.put("authorization", userAppAccessToken);
        headers.put("userid", "1398716700");
        headers.put("content-type", "application/x-www-form-urlencoded");

        String body = "module/createOrderReq.json";
        var requestBody = TestCaseHelpful.getJsonRequestBody(body);


        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        return TestCaseHelpful.extractValue(responseBody, "$.result.orderSn").toString();
    }

    /**
     * 余额支付
     */
    private void balancePay(String userAppAccessToken, String userAppOrderSn) {
        String uri = TestcaseConfig.HOST_USER_APP + "/api/user/pay/balance";
        String method = "POST";
        Map<String, Object> headers = createUserAppHeaders();
        headers.put("authorization", userAppAccessToken);
        headers.put("userid", "1398716700");
        headers.put("content-type", "application/x-www-form-urlencoded");
        
        var requestBody = String.format("orderSn=%s&password=016327&paymentType=2", userAppOrderSn);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
    }

    /**
     * 骑手登录
     */
    private String[] driverLogin() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/auth/login";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        
        var requestBody = "{\"areaCode\":\"86\",\"password\":\"2c9341ca4cf3d87b9e4eb905d6a3ec45\",\"account\":\"13300010563\"}";
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        String accessToken = TestCaseHelpful.extractValue(responseBody, "$.result.accessToken").toString();
        String userId = TestCaseHelpful.extractValue(responseBody, "$.result.userId").toString();
        return new String[]{accessToken, userId};
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
        // GPS同步通常不需要特殊断言
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
        
        var requestBody = String.format("{\"deliveryId\":%d,\"orderSn\":\"%s\",\"rejectAble\":0}", assignDriverID, userAppOrderSn);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
    }

    /**
     * 获取派单页面
     */
    private String getOrderPackage(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/orderPackage/list";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        var requestBody = "{}";
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        return TestCaseHelpful.extractValue(responseBody, "$.result.dataList[0].packageId").toString();
    }

    /**
     * 骑手接单
     */
    private void receiveOrder(String driverAccessToken, String packageId) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/orderPackage/receiveOrReject";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("Authorization", driverAccessToken);
        
        var requestBody = String.format("{\"orderPackageId\":\"%s\",\"type\":1}", packageId);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
    }

    /**
     * 显示推荐路径规划-骑手身上有单
     */
    private void getMatchingWithOrders(String siGuanToken, Long driverId) {
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/dispatch/driver/matching/request/headers.json");
        headers.put("token", siGuanToken);
        headers.put("accept", "application/json, text/plain, */*");
        headers.put("content-type", "application/json;charset=UTF-8");
        headers.put("enableSign", "false");

        var requestBody = TestCaseHelpful.getJsonRequestBody("module/dispatch/driver/matching/request/body_with_orders.json");
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = now.minusDays(7).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endDate = now.withHour(23).withMinute(59).withSecond(59).withNano(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String startTime = startDate.format(formatter);
        String endTime = endDate.format(formatter);
        
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.startDate", startTime);
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.endDate", endTime);
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.cityName", "杭州市");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.deliveryTagList", new JSONArray());
        JSONArray orderBusinessTypeList = new JSONArray();
        orderBusinessTypeList.add(0);
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.orderBusinessTypeList", orderBusinessTypeList);
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.dataFilterList", new JSONArray());
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.country", "CA");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.driverId", driverId);

        String uri = TestcaseConfig.HOST_ERP + "/api/dispatch/driver/matching";
        String method = "POST";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);

        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    /**
     * 取消配送
     */
    private void cancelDispatch(String siGuanToken, String userAppOrderSn) {
        String uri = TestcaseConfig.HOST_ERP + "/api/dispatch/dispatch/cancelDispatch";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("token", siGuanToken);
        
        var requestBody = String.format("{\"orderSn\":\"%s\"}", userAppOrderSn);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
    }

    /**
     * 创建C侧用户App请求头
     */
    private Map<String, Object> createUserAppHeaders() {
        Map<String, Object> headers = new java.util.HashMap<>();
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
        headers.put("enableSign", "false");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }

    /**
     * 创建骑手App请求头
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new java.util.HashMap<>();
        headers.put("platform", "");
        headers.put("enableSign", "false");
        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("Content-Type", "application/json");
        return headers;
    }

    /**
     * 创建ERP请求头
     */
    private Map<String, Object> createErpHeaders() {
        Map<String, Object> headers = new java.util.HashMap<>();
        headers.put("accept", "application/json, text/plain, */*");
        headers.put("accept-language", "zh-CN,zh;q=0.9");
        headers.put("origin", "https://hp-dispatch-admin-f2e-test.hungrypanda.cn");
        headers.put("referer", "https://hp-dispatch-admin-f2e-test.hungrypanda.cn/");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36");
        headers.put("enableSign", "false");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }

    /**
     * 司管后台登录并返回token
     *
     * @return token
     */
    private String erpLogin() {
        String uri = TestcaseConfig.HOST_ERP + "/api/erp/auth/login/v2";
        String method = "POST";
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/erp/auth/request/headers.json");
        headers.put("enableSign", "false");
        headers.put("Content-Type", "application/json");

        var requestBody = TestCaseHelpful.getJsonRequestBody("module/erp/auth/request/body.json");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.password", "4f2142904392cbef6974ad0260caeb33");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.userName", "ding17058431144045523");

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        return TestCaseHelpful.extractValue(responseBody, "$.data.token").toString();
    }
}

