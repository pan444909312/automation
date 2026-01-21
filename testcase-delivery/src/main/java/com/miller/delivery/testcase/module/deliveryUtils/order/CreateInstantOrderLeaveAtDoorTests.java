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
 * C侧下即时单-平台配送-杭州-滨江区（放在门口）
 *
 * @author auto-generated
 * @version 2.0
 * @since 2025/01/01 00:00:00
 */
@Scenario(
        scenarioID = "01JPPF8ZAFXN5PC1SMYMTPTJBY",
        scenarioName = "C侧下即时单-平台配送-杭州-滨江区（放在门口）",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("C侧下即时单-平台配送-杭州-滨江区（放在门口）")
public class CreateInstantOrderLeaveAtDoorTests {

    @DisplayName("完整下单流程-放在门口")
    @Test
    void shouldCreateInstantOrderLeaveAtDoor() {
        // 步骤1: C侧下单-用户登录
        String userAppAccessToken = userAppLogin();
        
        // 步骤2: C侧下单-获取店铺商品信息
        Long productId = getShopProductInfo(userAppAccessToken);
        
        // 步骤3: C侧下单-加购商品
        Long shopId = addToCart(userAppAccessToken, productId);
        
        // 步骤4: C侧下单-创建虚拟单
        createVirtualOrder(userAppAccessToken, shopId, productId);
        
        // 步骤5: C侧下单-创建即时单-平台配送 (deliverableAction=17 放在门口)
        String userAppOrderSn = createOrder(userAppAccessToken, shopId, productId);
        
        // 步骤6: C侧下单-余额支付
        balancePay(userAppAccessToken, userAppOrderSn);
        
        // 断言订单创建成功
        assertNotNull(userAppOrderSn);
        assertFalse(userAppOrderSn.isEmpty());
    }

    public String userAppLogin() {
        String uri = TestcaseConfig.HOST_USER_APP + "/api/user/combine/login";
        String method = "POST";
        Map<String, Object> headers = createUserAppHeaders();
        
        var requestBody = "{\"areaCode\":\"86\",\"distinctId\":\"4dd9690f6a6b639c\",\"password\":\"2c9341ca4cf3d87b9e4eb905d6a3ec45\",\"channel\":0,\"type\":\"2\",\"account\":\"13251016327\",\"stability\":0}";
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        return TestCaseHelpful.extractValue(responseBody, "$.result.accessToken").toString();
    }

    public Long getShopProductInfo(String userAppAccessToken) {
        String uri = TestcaseConfig.HOST_USER_APP + "/api/app/user/v1/shop/menuList";
        String method = "POST";
        Map<String, Object> headers = createUserAppHeaders();
        headers.put("authorization", userAppAccessToken);
        headers.put("userid", "1398716700");
        
        var requestBody = "{\"deliveryType\":1,\"shopId\":892716498}";
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        return Long.parseLong(TestCaseHelpful.extractValue(responseBody, "$.result.menuList[0].subMenuList[0].productList[0].productId").toString());
    }

    public Long addToCart(String userAppAccessToken, Long productId) {
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

    public void createVirtualOrder(String userAppAccessToken, Long shopId, Long productId) {
        String uri = TestcaseConfig.HOST_USER_APP + "/api/user/v1/order/toCreateVirtual";
        String method = "POST";
        Map<String, Object> headers = createUserAppHeaders();
        headers.put("authorization", userAppAccessToken);
        headers.put("userid", "1398716700");
        
        var requestBody = String.format("{\"orderType\":1,\"openRedPacket\":0,\"autoUseRedPacketStatus\":1,\"orderReqType\":0,\"deliveryType\":0,\"platform\":1,\"addressId\":1398679458,\"productCartList\":\"[{\\\"productId\\\":%d,\\\"skuId\\\":0,\\\"stability\\\":0,\\\"tagId\\\":[]}]\",\"payType\":0,\"verify\":0,\"shopId\":%d,\"stability\":0,\"requestSourceType\":0}", productId, shopId);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
    }

    public String createOrder(String userAppAccessToken, Long shopId, Long productId) {
        String uri = TestcaseConfig.HOST_USER_APP + "/api/user/order/create";
        String method = "POST";
        Map<String, Object> headers = createUserAppHeaders();
        headers.put("authorization", userAppAccessToken);
        headers.put("userid", "1398716700");
        headers.put("content-type", "application/x-www-form-urlencoded");
        
        // deliverableAction=17 表示放在门口, addressId=1398683380, tipPrice=4.39
        var requestBody = "{\"deliveryTime\":\"尽快送达\",\"deliverableAction\":\"17\",\"tablewareCount\":\"1\",\"userPhone\":\"86 13251016327\",\"orderReqType\":\"1\",\"deliveryType\":\"1\",\"platform\":\"1\",\"addressId\":\"1398683380\",\"productCartList\":[{\"pickUpType\":0,\"productId\":81871322,\"secKillFlag\":0,\"skuId\":0,\"tagId\":[]}],\"payType\":\"16\",\"saType\":\"0\",\"verify\":\"0\",\"shopId\":\"892716498\",\"superValueExchangeList\":null,\"tipPrice\":4.39,\"needNumberMasking\":false,\"isOnlinePay\":true}";
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        return TestCaseHelpful.extractValue(responseBody, "$.result.orderSn").toString();
    }

    public void balancePay(String userAppAccessToken, String userAppOrderSn) {
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

    public Map<String, Object> createUserAppHeaders() {
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
         
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}

