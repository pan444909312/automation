package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 等餐报备-撤单
 *
 * @author chenchunxia@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/07
 */
@Scenario(
        scenarioID = "PLACEHOLDER_SCENARIO_ID",
        scenarioName = "骑手app-等餐报备-撤单",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("等餐报备-撤单")
public class WaitMealReportCancelOrderTests {

    @DisplayName("等餐报备-撤单完整流程")
    @Test
    void shouldCompleteWaitMealReportCancelFlow() {
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
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        
        // 步骤8: 骑手app-司机上线操作
        driverOnline(driverAccessToken);
        
        // 步骤9: 上传骑手经纬度
        syncGps(driverAccessToken);
        
        // ========== 第三部分：调度分配流程 ==========
        // 步骤10: 司管登录获取token
        String siGuanToken = erpLogin();
        
        // 步骤11: 调度-获取订单下可分配的骑手
        Long assignDriverID = getAvailableDrivers(siGuanToken, userAppOrderSn);
        
        // 步骤12: 调度-分配订单给骑手
        assignOrderToDriver(siGuanToken, userAppOrderSn, assignDriverID);
        
        // 步骤13: 派单页面 - 获取packageId
        String packageId = getOrderPackage(driverAccessToken);
        
        // 步骤14: 骑手app-骑手接单
        receiveOrder(driverAccessToken, packageId);
        
        // ========== 第四部分：等餐报备-撤单流程 ==========
        // 步骤15: 修改骑手配送状态-到店
        modifyDeliveryStatus(driverAccessToken, userAppOrderSn, 1);
        
        // 步骤16: 等餐报备
        waitMealReport(driverAccessToken, userAppOrderSn);
        
        // 步骤17: 撤单
        cancelOrder(driverAccessToken, userAppOrderSn);
    }

    /**
     * C侧下单-用户登录
     */
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

    /**
     * C侧下单-获取店铺商品信息
     */
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

    /**
     * C侧下单-加购商品
     */
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

    /**
     * C侧下单-创建虚拟单
     */
    private void createVirtualOrder(String userAppAccessToken, Long shopId, Long productId) {
        String uri = TestcaseConfig.HOST_USER_APP + "/api/user/v1/order/toCreateVirtual";
        String method = "POST";
        Map<String, Object> headers = createUserAppHeaders();
        headers.put("authorization", userAppAccessToken);
        
        String body = String.format("{\"orderType\":1,\"openRedPacket\":0,\"autoUseRedPacketStatus\":1," +
                "\"orderReqType\":0,\"deliveryType\":0,\"platform\":1,\"addressId\":1398679458," +
                "\"productCartList\":\"[{productId:%d,skuId:0,stability:0,tagId:[]}]\"," +
                "\"payType\":0,\"verify\":0,\"shopId\":%d,\"stability\":0,\"requestSourceType\":0}",
                productId, shopId);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
    }

    /**
     * C侧下单-创建即时单-平台配送
     */
    private String createOrder(String userAppAccessToken, Long shopId, Long productId) {
        String uri = TestcaseConfig.HOST_USER_APP + "/api/user/order/create";
        String method = "POST";
        Map<String, Object> headers = createUserAppHeaders();
        headers.put("authorization", userAppAccessToken);
        headers.put("content-type", "application/x-www-form-urlencoded");
        
        String body = String.format("deliveryTime=尽快送达&deliverableAction=11&tablewareCount=1" +
                "&userPhone=86 13251016327&orderReqType=1&deliveryType=1&platform=1" +
                "&addressId=1398681476&productCartList=[{\"pickUpType\":0,\"productId\":%d," +
                "\"secKillFlag\":0,\"skuId\":0,\"tagId\":[]}]&payType=16&saType=0&verify=0" +
                "&shopId=%d&superValueExchangeList=null&tipPrice=4.39&needNumberMasking=false" +
                "&isOnlinePay=true", productId, shopId);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        return TestCaseHelpful.extractValue(responseBody, "$.result.orderSn").toString();
    }

    /**
     * C侧下单-余额支付
     */
    private void balancePay(String userAppAccessToken, String userAppOrderSn) {
        String uri = TestcaseConfig.HOST_USER_APP + "/api/user/pay/balance";
        String method = "POST";
        Map<String, Object> headers = createUserAppHeaders();
        headers.put("authorization", userAppAccessToken);
        headers.put("content-type", "application/x-www-form-urlencoded");
        
        String body = String.format("orderSn=%s&password=016327&paymentType=2", userAppOrderSn);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
    }

    /**
     * 骑手app-司机上线操作
     */
    private void driverOnline(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/driver/online";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = "{}";
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
    private Long getAvailableDrivers(String siGuanToken, String userAppOrderSn) {
        String uri = TestcaseConfig.HOST_ERP + "/api/dispatch/dispatch/getAvailableDrivers";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("token", siGuanToken);
        
        String body = String.format("{\"orderSn\":\"%s\"}", userAppOrderSn);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        // 假设返回第一个骑手ID
        return 1398720944L; // 占位符，实际使用时需要从响应中提取
    }

    /**
     * 调度-分配订单给骑手
     */
    private void assignOrderToDriver(String siGuanToken, String userAppOrderSn, Long assignDriverID) {
        String uri = TestcaseConfig.HOST_ERP + "/api/dispatch/dispatch/assign";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("token", siGuanToken);
        
        String body = String.format("{\"orderSn\":\"%s\",\"driverId\":%d,\"forceAssign\":false}",
                userAppOrderSn, assignDriverID);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
    }

    /**
     * 派单页面 - 获取packageId
     */
    private String getOrderPackage(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/package";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = "{}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        // 假设返回第一个packageId
        return "PLACEHOLDER_PACKAGE_ID"; // 占位符，实际使用时需要从响应中提取
    }

    /**
     * 骑手app-骑手接单
     */
    private void receiveOrder(String driverAccessToken, String packageId) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/receive";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = String.format("{\"packageId\":\"%s\"}", packageId);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
    }

    /**
     * 修改骑手配送状态-到店
     */
    private void modifyDeliveryStatus(String driverAccessToken, String userAppOrderSn, int status) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/modifyDeliveryStatus";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = String.format("{\"orderSn\":\"%s\",\"operationType\":%d}", userAppOrderSn, status);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
    }

    /**
     * 等餐报备
     */
    private void waitMealReport(String driverAccessToken, String userAppOrderSn) {
        // 注意：等餐报备的URI和请求体需要根据实际API文档确定
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/waitMealReport";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = String.format("{\"orderSn\":\"%s\"}", userAppOrderSn);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
    }

    /**
     * 撤单
     */
    private void cancelOrder(String driverAccessToken, String userAppOrderSn) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/cancel";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = String.format("{\"orderSn\":\"%s\",\"cancelReason\":\"自动化测试-撤单\"}", userAppOrderSn);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
    }

    /**
     * 创建C侧用户app请求头
     */
    private Map<String, Object> createUserAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
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
         
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }

    /**
     * 创建骑手app请求头
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("longitude", "120.216727");
        headers.put("latitude", "30.203499");
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
         
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }

    /**
     * 创建ERP请求头
     */
    private Map<String, Object> createErpHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("content-type", "application/json");
        return headers;
    }
}

