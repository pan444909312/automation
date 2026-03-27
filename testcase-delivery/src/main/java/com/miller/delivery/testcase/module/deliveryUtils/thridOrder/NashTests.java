package com.miller.delivery.testcase.module.deliveryUtils.thridOrder;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * C侧下即时单-自动化平台页面一键创建Nash订单，需要参数构造
 *
 * @author auto-generated
 * @version 2.0
 * @since 2026/01/01 00:00:00
 */

public class NashTests {

    private static final long PRODUCT_ID = 82641488;
    private static final long SHOP_ID = 653864877;
    private static final long ADDRESS_ID = 1398667852;

    @DisplayName("Nash下单")
    @Test
    public void   createOrder() {

        Map<String, String> result = new HashMap<>();
        // 步骤1: C侧下单-用户登录
        String userAppAccessToken = userAppLogin();
        int num = 1;

                // 步骤3: C侧下单-加购商品1
                Long shopId = addToCart(userAppAccessToken);
                // 步骤4: C侧下单-创建虚拟单
                createVirtualOrder(userAppAccessToken, shopId);
                // 步骤5: C侧下单-创建即时单-平台配送 (deliverableAction=17 放在门口)
                String userAppOrderSn = createOrder(userAppAccessToken, shopId);
                // 步骤6: C侧下单-余额支付
                balancePay(userAppAccessToken, userAppOrderSn);
                System.out.println("Nash单号===="+userAppOrderSn);
                System.out.println("构造参数化成功！"+userAppOrderSn);

    }
    /**
     * 登录 用户19539027926/Test1234
     *
     *
     */
    public String userAppLogin() {
        String uri = TestcaseConfig.HOST_USER_APP + "/api/user/combine/login";
        String method = "POST";
        Map<String, Object> headers = createUserAppHeaders();
        
        var requestBody = "{\"areaCode\":\"86\",\"distinctId\":\"4dd9690f6a6b639c\",\"password\":\"2c9341ca4cf3d87b9e4eb905d6a3ec45\",\"channel\":0,\"type\":\"2\",\"account\":\"19539027926\",\"stability\":0}";
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        return TestCaseHelpful.extractValue(responseBody, "$.result.accessToken").toString();
    }
    /**
     * 商家菜单列表
     *
     *
     */
    public Long getShopProductInfo(String userAppAccessToken) {
        String uri = TestcaseConfig.HOST_USER_APP + "/api/app/user/v1/shop/menuList";
        String method = "POST";
        Map<String, Object> headers = createUserAppHeaders();
        headers.put("authorization", userAppAccessToken);
        headers.put("userid", "1398716700");
        
        var requestBody = String.format("{\"deliveryType\":1,\"shopId\":%d}", SHOP_ID);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        return Long.parseLong(TestCaseHelpful.extractValue(responseBody, "$.result.menuList[0].subMenuList[0].productList[0].productId").toString());
    }
    /**
     * 加入购物车
     *
     *
     */
    public Long addToCart(String userAppAccessToken) {
        String uri = TestcaseConfig.HOST_USER_APP + "/api/app/user/order/v3/shoppingCart";
        String method = "POST";
        Map<String, Object> headers = createUserAppHeaders();
        headers.put("authorization", userAppAccessToken);
        headers.put("userid", "1398720920");
        long nowTime = System.currentTimeMillis();
        var requestBody = String.format("{\"deliveryType\":1,\"shopId\":%d,\"items\":[{\"productId\":%d,\"purchaseTime\":%d,\"skuId\":0,\"stability\":0}]}", SHOP_ID, PRODUCT_ID, nowTime);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        return Long.parseLong(TestCaseHelpful.extractValue(responseBody, "$.result.cart.shopId").toString());
    }

    /**
     * 创建虚拟单
     *
     *
     */
    public void createVirtualOrder(String userAppAccessToken, Long shopId) {
        String uri = TestcaseConfig.HOST_USER_APP + "/api/user/v1/order/toCreateVirtual";
        String method = "POST";
        Map<String, Object> headers = createUserAppHeaders();
        headers.put("authorization", userAppAccessToken);
        headers.put("userid", "1398720920");
        
        var requestBody = String.format("{\"orderType\":1,\"openRedPacket\":0,\"autoUseRedPacketStatus\":1,\"orderReqType\":0,\"deliveryType\":0,\"platform\":1,\"addressId\":%d,\"productCartList\":\"[{\\\"productId\\\":%d,\\\"skuId\\\":0,\\\"stability\\\":0,\\\"tagId\\\":[]}]\",\"payType\":0,\"verify\":0,\"shopId\":%d,\"stability\":0,\"requestSourceType\":0}", ADDRESS_ID, PRODUCT_ID, shopId);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
    }
    /**
     * 创建订单
     *
     *
     */
    public String createOrder(String userAppAccessToken, Long shopId) {
        String uri = TestcaseConfig.HOST_USER_APP + "/api/user/order/create";
        String method = "POST";
        Map<String, Object> headers = createUserAppHeaders();
        headers.put("authorization", userAppAccessToken);
        headers.put("userid", "1398720920");
        headers.put("content-type", "application/x-www-form-urlencoded");
        
        // deliverableAction=17 表示放在门口, addressId=1398683792, tipPrice=4.39
        var requestBody = String.format("{\"deliveryTime\":\"尽快送达\",\"deliverableAction\":\"17\",\"tablewareCount\":\"1\",\"userPhone\":\"86 19539027926\",\"orderReqType\":\"1\",\"deliveryType\":\"1\",\"platform\":\"1\",\"addressId\":\"%d\",\"productCartList\":[{\"pickUpType\":0,\"productId\":%d,\"secKillFlag\":0,\"skuId\":0,\"tagId\":[]}],\"payType\":\"16\",\"saType\":\"0\",\"verify\":\"0\",\"shopId\":\"%d\",\"superValueExchangeList\":null,\"tipPrice\":4.39,\"needNumberMasking\":false,\"isOnlinePay\":true}", ADDRESS_ID, PRODUCT_ID, SHOP_ID);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        return TestCaseHelpful.extractValue(responseBody, "$.result.orderSn").toString();
    }
    /**
     * 余额支付
     *
     *
     */
    public void balancePay(String userAppAccessToken, String userAppOrderSn) {
        String uri = TestcaseConfig.HOST_USER_APP + "/api/user/pay/balance";
        String method = "POST";
        Map<String, Object> headers = createUserAppHeaders();
        headers.put("authorization", userAppAccessToken);
        headers.put("userid", "1398720920");
        headers.put("content-type", "application/x-www-form-urlencoded");
        
        var requestBody = String.format("orderSn=%s&password=027926&paymentType=2", userAppOrderSn);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
    }

    public Map<String, Object> createUserAppHeaders() {
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
}

