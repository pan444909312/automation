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
 * C侧下即时单-平台配送-郑州市-二七区
 *
 * @author auto-generated
 * @version 2.0
 * @since 2025/01/01 00:00:00
 */
@Scenario(
        scenarioID = "01JPPF8ZAFXN5PC1SMYMTPTJBY",
        scenarioName = "C侧下即时单-平台配送-郑州市-二七区",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("C侧下即时单-平台配送-郑州市-二七区")
public class CreateInstantOrderZhengzhouErqiTests {

    @DisplayName("完整下单流程-郑州市-二七区")
    @Test
    void shouldCreateInstantOrderZhengzhouErqi() {
        // 步骤1: C侧下单-用户登录
        String userAppAccessToken = userAppLogin();
        
        // 步骤2: C侧下单-获取店铺商品信息 (shopId=124702053)
        Long productId = getShopProductInfo(userAppAccessToken);
        
        // 步骤3: C侧下单-加购商品
        Long shopId = addToCart(userAppAccessToken, productId);
        
        // 步骤4: C侧下单-创建虚拟单
        String subTotalAmount = createVirtualOrder(userAppAccessToken, shopId, productId);
        
        // 步骤5: C侧下单-创建即时单-平台配送 (deliverableAction=11, addressId=1398680297)
        String userAppOrderSn = createOrder(userAppAccessToken, shopId, productId, subTotalAmount);
        
        // 步骤6: C侧下单-余额支付
        balancePay(userAppAccessToken, userAppOrderSn);
        
        // 断言订单创建成功
        assertNotNull(userAppOrderSn);
        assertFalse(userAppOrderSn.isEmpty());
    }

    private String userAppLogin() {
        String uri = TestcaseConfig.HOST_USER_APP + "/api/user/combine/login";
        String method = "POST";
        Map<String, Object> headers = createUserAppHeaders();
        
        var requestBody = "{\"riskToken\":\"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\",\"areaCode\":\"86\",\"distinctId\":\"4dd9690f6a6b639c\",\"password\":\"2c9341ca4cf3d87b9e4eb905d6a3ec45\",\"channel\":0,\"type\":\"2\",\"account\":\"13251016327\",\"stability\":0}";
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        return TestCaseHelpful.extractValue(responseBody, "$.result.accessToken").toString();
    }

    private Long getShopProductInfo(String userAppAccessToken) {
        String uri = TestcaseConfig.HOST_USER_APP + "/api/app/user/v1/shop/menuList";
        String method = "POST";
        Map<String, Object> headers = createUserAppHeaders();
        headers.put("authorization", userAppAccessToken);
        headers.put("userid", "1398716700");
        
        // shopId=124702053 (郑州市-二七区)
        var requestBody = "{\"deliveryType\":1,\"shopId\":124702053}";
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        return Long.parseLong(TestCaseHelpful.extractValue(responseBody, "$.result.menuList[0].subMenuList[0].productList[0].productId").toString());
    }

    private Long addToCart(String userAppAccessToken, Long productId) {
        String uri = TestcaseConfig.HOST_USER_APP + "/api/app/user/order/v3/shoppingCart";
        String method = "POST";
        Map<String, Object> headers = createUserAppHeaders();
        headers.put("authorization", userAppAccessToken);
        headers.put("userid", "1398716700");
        
        long nowTime = System.currentTimeMillis();
        // shopId=124702053 (郑州市-二七区)
        var requestBody = String.format("{\"deliveryType\":1,\"shopId\":124702053,\"items\":[{\"productId\":%d,\"purchaseTime\":%d,\"skuId\":0,\"stability\":0}]}", productId, nowTime);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        return Long.parseLong(TestCaseHelpful.extractValue(responseBody, "$.result.cart.shopId").toString());
    }

    private String createVirtualOrder(String userAppAccessToken, Long shopId, Long productId) {
        String uri = TestcaseConfig.HOST_USER_APP + "/api/user/v1/order/toCreateVirtual";
        String method = "POST";
        Map<String, Object> headers = createUserAppHeaders();
        headers.put("authorization", userAppAccessToken);
        headers.put("userid", "1398716700");
        
        // addressId=1398680297 (郑州市-二七区)
        var requestBody = String.format("{\"orderType\":1,\"openRedPacket\":0,\"autoUseRedPacketStatus\":1,\"orderReqType\":0,\"deliveryType\":0,\"platform\":1,\"addressId\":1398680297,\"productCartList\":\"[{\\\"productId\\\":%d,\\\"skuId\\\":0,\\\"stability\\\":0,\\\"tagId\\\":[]}]\",\"payType\":0,\"verify\":0,\"shopId\":%d,\"stability\":0,\"requestSourceType\":0}", productId, shopId);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        return TestCaseHelpful.extractValue(responseBody, "$.result.priceInfo.subTotalAmount").toString();
    }

    private String createOrder(String userAppAccessToken, Long shopId, Long productId, String subTotalAmount) {
        String uri = TestcaseConfig.HOST_USER_APP + "/api/user/order/create";
        String method = "POST";
        Map<String, Object> headers = createUserAppHeaders();
        headers.put("authorization", userAppAccessToken);
        headers.put("userid", "1398716700");
        headers.put("content-type", "application/x-www-form-urlencoded");
        
        // deliverableAction=11, addressId=1398680297, tipPrice=1.00 (郑州市-二七区)
        var requestBody = "{\"deliveryTime\":\"尽快送达\",\"deliverableAction\":\"11\",\"tablewareCount\":\"1\",\"userPhone\":\"86+13251016327\",\"orderReqType\":\"1\",\"deliveryType\":\"1\",\"fixedPrice\":\"4335\",\"platform\":\"1\",\"addressId\":\"1398680297\",\"productCartList\":[{\"productId\":82334770,\"skuId\":0,\"stability\":0,\"tagId\":[]}],\"payType\":\"16\",\"saType\":\"0\",\"verify\":\"0\",\"shopId\":\"124702053\",\"superValueExchangeList\":null,\"tipPrice\":1.00,\"needNumberMasking\":false,\"isOnlinePay\":true}";
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        return TestCaseHelpful.extractValue(responseBody, "$.result.orderSn").toString();
    }

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

