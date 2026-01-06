package com.miller.delivery.testcase.module.dispatch.order;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * 调度合单分配订单-完单
 *
 * @author auto-generated
 * @version 2.0
 * @since 2025/01/01 00:00:00
 */
@Scenario(
        scenarioID = "01K8W6VF3MR0E3297NZWPGNWYG",
        scenarioName = "调度系统-调度合单分配订单-完单",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 180, maintenanceTime = 0, manualTestTime = 90)
@DisplayName("调度合单分配订单-完单")
public class DispatchCombineAssignCompleteTests {

    @DisplayName("完整端到端流程-调度合单分配订单-完单")
    @Test
    void shouldCompleteCombineAssignAndCompleteFlow() {
        // ========== 第一部分：C侧下单流程（创建多个订单用于合单） ==========
        // 步骤1: C侧下单-用户登录
        String userAppAccessToken = userAppLogin();
        
        // 步骤2: C侧下单-获取店铺商品信息
        Long productId = getShopProductInfo(userAppAccessToken);
        
        // 步骤3: C侧下单-加购商品
        Long shopId = addToCart(userAppAccessToken, productId);
        
        // 步骤4: C侧下单-创建虚拟单
        createVirtualOrder(userAppAccessToken, shopId, productId);
        
        // 步骤5: C侧下单-创建第一个即时单-平台配送
        String userAppOrderSn1 = createOrder(userAppAccessToken, shopId, productId);
        
        // 步骤6: C侧下单-余额支付第一个订单
        balancePay(userAppAccessToken, userAppOrderSn1);
        
        // 延迟1秒
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // 步骤7: C侧下单-创建第二个即时单-平台配送（用于合单）
        String userAppOrderSn2 = createOrder(userAppAccessToken, shopId, productId);
        
        // 步骤8: C侧下单-余额支付第二个订单
        balancePay(userAppAccessToken, userAppOrderSn2);
        
        // ========== 第二部分：骑手操作流程 ==========
        // 步骤9: 骑手app-骑手登录
        Map<String, String> driverLoginInfo = driverLogin();
        String driverAccessToken = driverLoginInfo.get("accessToken");
        
        // 步骤10: 骑手app-司机上线操作
        driverOnline(driverAccessToken);
        
        // 步骤11: 上传骑手经纬度
        syncGps(driverAccessToken);
        
        // ========== 第三部分：调度合单分配流程 ==========
        // 步骤12: 司管登录获取token
        String siGuanToken = erpLogin();
        
        // 步骤13: 调度-合单分配（将两个订单合并分配给骑手）
        combineAssignOrders(siGuanToken, userAppOrderSn1, userAppOrderSn2);
        
        // 步骤14: 派单页面 - 获取packageId
        String packageId = getOrderPackage(driverAccessToken);
        
        // 步骤15: 骑手app-骑手接单（接合单）
        receiveOrder(driverAccessToken, packageId);
        
        // ========== 第四部分：骑手配送流程 ==========
        // 步骤16: 骑手app-修改骑手配送状态-到店
        modifyDeliveryStatus(driverAccessToken, userAppOrderSn1, 1);
        
        // 步骤17: 骑手app-骑手配送状态-未出餐
        modifyDeliveryStatus(driverAccessToken, userAppOrderSn1, 2);
        
        // 步骤18: 骑手app-骑手配送状态-已取餐
        modifyDeliveryStatus(driverAccessToken, userAppOrderSn1, 3);
        
        // 步骤19: 骑手app-修改订单配送状态-签收（第一个订单）
        completeOrder(driverAccessToken, userAppOrderSn1);
        
        // 步骤20: 骑手app-修改骑手配送状态-到店（第二个订单）
        modifyDeliveryStatus(driverAccessToken, userAppOrderSn2, 1);
        
        // 步骤21: 骑手app-骑手配送状态-未出餐（第二个订单）
        modifyDeliveryStatus(driverAccessToken, userAppOrderSn2, 2);
        
        // 步骤22: 骑手app-骑手配送状态-已取餐（第二个订单）
        modifyDeliveryStatus(driverAccessToken, userAppOrderSn2, 3);
        
        // 步骤23: 骑手app-修改订单配送状态-签收（第二个订单）
        completeOrder(driverAccessToken, userAppOrderSn2);
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

        // deliverableAction=15 表示平台配送
        var requestBody = String.format(
            "deliveryTime=尽快送达&deliverableAction=15&tablewareCount=1&userPhone=86+13251016327&orderReqType=1&deliveryType=1&platform=1&addressId=1398679458&productCartList=[{\\\"productId\\\":%d,\\\"skuId\\\":0,\\\"stability\\\":0,\\\"tagId\\\":[]}]&payType=16&verify=0&shopId=%d&superValueExchangeList=null&tipPrice=0.24&needNumberMasking=false&isOnlinePay=true",
            productId, shopId
        );
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
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
    private Map<String, String> driverLogin() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/auth/login";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        
        var requestBody = "{\"areaCode\":\"86\",\"password\":\"2c9341ca4cf3d87b9e4eb905d6a3ec45\",\"account\":\"13300010563\"}";
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
        
        String accessToken = TestCaseHelpful.extractValue(responseBody, "$.result.accessToken").toString();
        String userId = TestCaseHelpful.extractValue(responseBody, "$.result.userId").toString();
        
        return Map.of("accessToken", accessToken, "userId", userId);
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
     * 调度-合单分配（将多个订单合并分配给骑手）
     */
    private void combineAssignOrders(String siGuanToken, String orderSn1, String orderSn2) {
        String uri = TestcaseConfig.HOST_ERP + "/api/dispatch/dispatch/combineAssign";
        String method = "POST";
        Map<String, Object> headers = createErpHeaders();
        headers.put("token", siGuanToken);
        
        // 获取可分配的骑手
        Long assignDriverID = getAvailableDrivers(siGuanToken, orderSn1);
        
        var requestBody = String.format("{\"deliveryId\":%d,\"orderSnList\":[\"%s\",\"%s\"],\"rejectAble\":0}", 
                assignDriverID, orderSn1, orderSn2);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
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
     * 获取派单页面
     */
    private String getOrderPackage(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/orderPackage/list";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        headers.put("longitude", "120.2168986");
        headers.put("latitude", "30.2035028");
        
        var requestBody = "{\"pageNo\":1,\"pageSize\":10}";
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
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
        headers.put("operatingsystem", "2");
        headers.put("enableSign", "false");
        headers.put("content-type", "application/json");
        
        var requestBody = String.format("{\"orderPackageId\":\"%s\",\"type\":1}", packageId);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    /**
     * 修改骑手配送状态
     */
    private void modifyDeliveryStatus(String driverAccessToken, String orderSn, int operationType) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/modifyDeliveryStatus";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        headers.put("operatingsystem", "2");
        headers.put("enableSign", "false");
        headers.put("content-type", "application/json");
        
        var requestBody = String.format("{\"orderSn\":\"%s\",\"orderCompleteImageUrlList\":[],\"waitUserArrive\":0,\"operationType\":%d,\"orderSnList\":[\"%s\"],\"driverArriveType\":0}", 
                orderSn, operationType, orderSn);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
    }

    /**
     * 骑手签收订单
     */
    private void completeOrder(String driverAccessToken, String orderSn) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/modifyDeliveryStatus";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        headers.put("operatingsystem", "2");
        headers.put("enableSign", "false");
        headers.put("content-type", "application/json");
        
        var requestBody = String.format("{\"driverArriveType\":11,\"operationType\":6,\"arriveRemark\":\"留言备注内容-apifox自动化测试创建，图片默认写死资源地址，免去每次上传图片到oss\",\"waitUserArrive\":0,\"orderSn\":\"%s\",\"orderCompleteImageUrlList\":[\"http://panda-auth.oss-eu-central-1.aliyuncs.com/delivery-app/170174606688616113ac9a0a74ab29cdadf98ad4cf090.jpg\"],\"orderSnList\":[\"%s\"]}", 
                orderSn, orderSn);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
    }

    /**
     * 司管后台登录并返回token
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

    /**
     * 创建C侧app请求头
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
     * 创建骑手app请求头
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/delivery/auth/request/headers.json");
        headers.put("enableSign", "false");
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
        headers.put("enableSign", "false");
        return headers;
    }
}

