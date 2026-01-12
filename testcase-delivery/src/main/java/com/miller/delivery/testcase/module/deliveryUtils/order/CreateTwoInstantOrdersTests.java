package com.miller.delivery.testcase.module.deliveryUtils.order;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Map;

/**
 * C侧下即时单-平台配送-杭州-滨江区 -两个订单
 *
 * @author auto-generated
 * @version 2.0
 * @since 2025/01/01 00:00:00
 */
@Scenario(
        scenarioID = "01JPPF8ZAFXN5PC1SMYMTPTJBY", // 自动生成，不要修改
        scenarioName = "C侧下即时单-平台配送-杭州-滨江区 -两个订单",
        author = "chenchunxia@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("C侧下即时单-平台配送-杭州-滨江区 -两个订单")
public class CreateTwoInstantOrdersTests {
    String assertFullField = "module/user/order/createTwoInstantOrders/response/assert_full_field.json";

    @DisplayName("完整下单流程-创建两个订单")
    @Test
    void shouldCreateTwoInstantOrders() {
        // 步骤1: C侧下单-用户登录
        String userAppAccessToken = userAppLogin();
        
        // ========== 第一个订单 ==========
        // ========== 第一个订单 ==========
        // 步骤2: C侧下单-获取店铺商品信息
        Long productId = getShopProductInfo(userAppAccessToken);
        
        // 步骤3: C侧下单-加购商品
        Long shopId = addToCart(userAppAccessToken, productId);
        
        // 步骤4: C侧下单-创建虚拟单
        String subTotalAmount = createVirtualOrder(userAppAccessToken, shopId, productId, 1398679458L);
        
        // 步骤5: C侧下单-创建即时单-平台配送
        String userAppOrderSn1 = createOrder(userAppAccessToken, shopId, productId, subTotalAmount);
        
        // 步骤6: C侧下单-余额支付
        balancePay(userAppAccessToken, userAppOrderSn1);
        
        // 延迟1秒
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // ========== 第二个订单 ==========
        // 步骤7: C侧下单-获取店铺商品信息（重复）
        productId = getShopProductInfo(userAppAccessToken);
        
        // 步骤8: C侧下单-加购商品（重复）
        shopId = addToCart(userAppAccessToken, productId);
        
        // 步骤9: C侧下单-创建虚拟单（重复）
        subTotalAmount = createVirtualOrder(userAppAccessToken, shopId, productId, 1398679458L);
        
        // 步骤10: C侧下单-创建即时单-平台配送（重复）
        String userAppOrderSn2 = createOrder(userAppAccessToken, shopId, productId, subTotalAmount);
        
        // 步骤11: C侧下单-余额支付（重复）
        balancePay(userAppAccessToken, userAppOrderSn2);
        
        // 断言两个订单都创建成功
        assertNotNull(userAppOrderSn1);
        assertFalse(userAppOrderSn1.isEmpty());
        assertNotNull(userAppOrderSn2);
        assertFalse(userAppOrderSn2.isEmpty());
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
    private String createVirtualOrder(String userAppAccessToken, Long shopId, Long productId, Long addressId) {
        String uri = TestcaseConfig.HOST_USER_APP + "/api/user/v1/order/toCreateVirtual";
        String method = "POST";
        Map<String, Object> headers = createUserAppHeaders();
        headers.put("authorization", userAppAccessToken);
        headers.put("userid", "1398716700");
        
        var requestBody = String.format("{\"orderType\":1,\"openRedPacket\":0,\"autoUseRedPacketStatus\":1,\"orderReqType\":0,\"deliveryType\":0,\"platform\":1,\"addressId\":%d,\"productCartList\":\"[{productId:%d,skuId:0,stability:0,tagId:[]}]\",\"payType\":0,\"verify\":0,\"shopId\":%d,\"stability\":0,\"requestSourceType\":0}", addressId, productId, shopId);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        return TestCaseHelpful.extractValue(responseBody, "$.result.priceInfo.subTotalAmount").toString();
    }

    /**
     * 创建即时单-平台配送
     * 注意：deliverableAction=15
     */
    private String createOrder(String userAppAccessToken, Long shopId, Long productId, String subTotalAmount) {
        String uri = TestcaseConfig.HOST_USER_APP + "/api/user/order/create";
        String method = "POST";
        Map<String, Object> headers = createUserAppHeaders();
        headers.put("authorization", userAppAccessToken);
        headers.put("userid", "1398716700");
        headers.put("content-type", "application/x-www-form-urlencoded");
        
        // 构建表单数据，注意 deliverableAction=15
        var requestBody = "{\"deliveryTime\":\"尽快送达\",\"deliverableAction\":\"15\",\"tablewareCount\":\"1\",\"userPhone\":\"86+13251016327\",\"orderReqType\":\"1\",\"deliveryType\":\"1\",\"fixedPrice\":\"32114\",\"platform\":\"1\",\"addressId\":\"1398679388\",\"productCartList\":[{\"productId\":81865046,\"skuId\":0,\"stability\":0,\"tagId\":[]}],\"payType\":\"16\",\"verify\":\"0\",\"shopId\":\"892716498\",\"superValueExchangeList\":null,\"tipPrice\":0.24,\"needNumberMasking\":false,\"isOnlinePay\":true}";
        
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
}

