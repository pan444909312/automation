package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.module.deliveryUtils.order.CreateInstantOrderWithHandoverTests;
import com.miller.delivery.testcase.utils.PandaTestDBHelpful;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 收餐码校验
 *
 * @author TestingConsultant@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/07
 */
@Scenario(
        scenarioID = "01JXJ291A1Z9D6MRSRXWSHQFW7",
        scenarioName = "收餐码校验",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 2)
@DisplayName("收餐码校验")
public class FoodDeliveryCodeTests {

    @DisplayName("输入错误收餐码")
    @Test
    void shouldFailWithWrongFoodDeliveryCode() {
        // ========== 第一部分：C侧下单流程 ==========
        CreateInstantOrderWithHandoverTests createInstantOrderWithHandoverTests = new CreateInstantOrderWithHandoverTests();
        String userAppOrderSn = createInstantOrderWithHandoverTests.orderFlow();
        
        // ========== 第二部分：前置操作 ==========
        // 步骤7: 前置操作：更新订单的取餐码为12345678
        updateFoodDeliveryCode(userAppOrderSn, "12345678");
        
        // ========== 第三部分：收餐码校验 ==========
        // 步骤8: 骑手登录
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        
        // 步骤9: 输入错误收餐码
        checkFoodDeliveryCode(driverAccessToken, userAppOrderSn, "7840", 1000220, 
                "该收餐码与当前订单不符，请核对是否误送", false);
    }

    @DisplayName("输入正确收餐码")
    @Test
    void shouldSuccessWithCorrectFoodDeliveryCode() {
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
        
        // ========== 第二部分：前置操作 ==========
        // 步骤7: 前置操作：从数据库查询正确的收餐码
        String correctFoodDeliveryCode = getFoodDeliveryCodeFromDatabase(userAppOrderSn);
        
        // ========== 第三部分：收餐码校验 ==========
        // 步骤8: 骑手登录
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        
        // 步骤9: 输入正确收餐码
        checkFoodDeliveryCode(driverAccessToken, userAppOrderSn, correctFoodDeliveryCode, 1000, 
                "成功", true);
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
        
        String body = "deliveryTime=尽快送达&deliverableAction=15&tablewareCount=1" +
                "&userPhone=86+13251016327&orderReqType=1&deliveryType=1" +
                "&platform=1&addressId=1398679388" +
                String.format("&productCartList=[{\"productId\":%d,\"skuId\":0,\"stability\":0,\"tagId\":[]}]", productId) +
                "&payType=16&verify=0" +
                String.format("&shopId=%d", shopId) +
                "&superValueExchangeList=null&tipPrice=0.24" +
                "&needNumberMasking=false&isOnlinePay=true";
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
     * 更新订单的取餐码
     */
    private void updateFoodDeliveryCode(String orderSn, String foodDeliveryCode) {
        String sql = String.format("update panda_test.`hp_delivery_order_extra_info` " +
                "set food_delivery_code = '%s' where order_sn = '%s'", foodDeliveryCode, orderSn);
        PandaTestDBHelpful.executeInsertOrUpdateOrDelete(sql);
    }

    /**
     * 从数据库查询收餐码
     */
    private String getFoodDeliveryCodeFromDatabase(String orderSn) {
        String sql = String.format("select food_delivery_code from panda_test.`hp_delivery_order_extra_info` " +
                "where order_sn = '%s'", orderSn);
        List<Map<String, Object>> resultList = PandaTestDBHelpful.executeSelectListSql(sql);
        if (resultList != null && !resultList.isEmpty()) {
            Object foodDeliveryCode = resultList.get(0).get("food_delivery_code");
            return foodDeliveryCode != null ? foodDeliveryCode.toString() : "5678";
        }
        return "5678";
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
        headers.put("marketchannel", "googlePlay");
        headers.put("pandaappid", "com.hungrypanda.waimai");
        headers.put("zipcode", "");
        headers.put("timezoneoffset", "-480");
        headers.put("portalid", "3");
        headers.put("regionid", "3");
        headers.put("hpfcityname", "%E6%9D%AD%E5%B7%9E%E5%B8%82");
        headers.put("hpfcityid", "755");

        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }

    /**
     * 创建骑手app请求头
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("longitude", "120.216733");
        headers.put("latitude", "30.203834");
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

        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}
