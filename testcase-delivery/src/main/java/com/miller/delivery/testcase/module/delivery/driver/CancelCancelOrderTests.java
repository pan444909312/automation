package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.common.util.MD5Util;
import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.module.deliveryUtils.order.CreateInstantOrderWithHandoverTests;
import com.miller.delivery.testcase.utils.PandaTestDBHelpful;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.delivery.testcase.utils.driverOffline;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 【主干用例】骑手app-取消撤单
 *
 * @author chenchunxia@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/07
 */
@Scenario(
        scenarioID = "01JZ57ET1Q4ADCD18TF6GPRZE2",
        scenarioName = "【主干用例】骑手app-取消撤单",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("【主干用例】骑手app-取消撤单")
public class CancelCancelOrderTests {

    @DisplayName("取消撤单完整流程")
    @Test
    void shouldCompleteCancelCancelOrderFlow() {
        // ========== 第一部分：C侧下单流程 ==========
        CreateInstantOrderWithHandoverTests createInstantOrderWithHandoverTests = new CreateInstantOrderWithHandoverTests();
        String userAppOrderSn = createInstantOrderWithHandoverTests.orderFlow();
        
        // ========== 第二部分：骑手操作流程 ==========
        // 步骤7: 骑手app-骑手登录
        Map<String, String> loginResult = driverLogin("13300010676", "Test1234");
        String driverAccessToken = loginResult.get("accessToken");
        Long driverId = Long.parseLong(loginResult.get("userId"));

        driverOffline driverOffline = new driverOffline();
        driverOffline.cancelDispatchAndOffline("13300010676",driverAccessToken);
        
        // 步骤8: 骑手app-司机上线操作
        driverOnline(driverAccessToken);
        
        // 步骤9: 骑手抢单
        grabOrder(driverAccessToken, userAppOrderSn);
        
        // ========== 第三部分：撤单流程 ==========
        // 步骤10: 获取撤单选项
        getCancellationOptions(driverAccessToken, userAppOrderSn);
        
        // 步骤11: 骑手提交撤单
        submitCancellation(driverAccessToken, userAppOrderSn);
        
        // ========== 第四部分：取消撤单流程 ==========
        // 步骤12: 前置操作：从数据库获取撤单record_no
        String recordNo = getCancellationRecordNo(driverId, userAppOrderSn);
        
        // 步骤13: 取消撤单
        cancelCancellation(driverAccessToken, recordNo);
    }

    /**
     * 骑手登录并返回token和userId
     */
    private Map<String, String> driverLogin(String account, String password) {
        password = MD5Util.string2MD5(password);
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/auth/login";
        String method = "POST";
        Map<String, Object> headers = new HashMap<>();

        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("Content-Type", "application/json");
        
        String body = String.format("{\"areaCode\":\"86\",\"password\":\"%s\",\"account\":\"%s\"}",
                password, account);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
        
        String accessToken = TestCaseHelpful.extractValue(responseBody, "$.result.accessToken").toString();
        String userId = TestCaseHelpful.extractValue(responseBody, "$.result.userId").toString();
        
        return Map.of("accessToken", accessToken, "userId", userId);
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
        
        String body = "deliveryTime=尽快送达&deliverableAction=11&tablewareCount=1" +
                "&userPhone=86 13251016327&orderReqType=1&deliveryType=1" +
                "&platform=1&addressId=1398681476" +
                String.format("&productCartList=[{\"pickUpType\":0,\"productId\":%d,\"secKillFlag\":0,\"skuId\":0,\"tagId\":[]}]", productId) +
                "&payType=16&saType=0&verify=0" +
                String.format("&shopId=%d", shopId) +
                "&superValueExchangeList=null&tipPrice=4.39" +
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
     * 骑手app-司机上线操作
     */
    private void driverOnline(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/driver/onOffline";
        String method = "POST";
        Map<String, Object> headers = new HashMap<>();
        headers.put("authorization", driverAccessToken);
        headers.put("operatingsystem", "1");
        headers.put("longitude", "120.217095");
        headers.put("latitude", "30.203565");

        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("Content-Type", "application/json");
        
        String body = "{\"isOnline\":1}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    /**
     * 骑手抢单
     */
    private void grabOrder(String driverAccessToken, String userAppOrderSn) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/grabOrder";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = String.format("{\"orderSn\":\"%s\",\"confirmDiscount\":0}", userAppOrderSn);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    /**
     * 获取撤单选项
     */
    private void getCancellationOptions(String driverAccessToken, String userAppOrderSn) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/cancellation/cancellationV2";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = String.format("{\"orderSn\":\"%s\"}", userAppOrderSn);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    /**
     * 骑手提交撤单
     */
    private void submitCancellation(String driverAccessToken, String userAppOrderSn) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/cancellation/submitCancellation";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = String.format("{\"picUrl\":\"\",\"cancellationReasonId\":\"2\"," +
                "\"continueCancellation\":\"0\",\"otherReasonDesc\":\"\"," +
                "\"orderSn\":\"%s  \",\"cancellationConfigId\":\"78\",\"optType\":0}", userAppOrderSn);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    /**
     * 前置操作：从数据库获取撤单record_no
     */
    private String getCancellationRecordNo(Long driverId, String userAppOrderSn) {
        List<Map<String, Object>> resultList = PandaTestDBHelpful.executeSelectListSql(
                "select record_no from `panda_test`.`hp_delivery_cancellation_record` " +
                "where driver_id = ? and order_sn = ? order by id desc limit 1",
                driverId, userAppOrderSn);
        
        if (resultList != null && !resultList.isEmpty()) {
            return resultList.get(0).get("record_no").toString();
        }
        throw new RuntimeException("未找到撤单记录");
    }

    /**
     * 取消撤单
     */
    private void cancelCancellation(String driverAccessToken, String recordNo) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/cancellation/cancelCancellation";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = String.format("{\"recordNo\":\"%s\"}", recordNo);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    /**
     * 创建用户app请求头
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
        headers.put("marketchannel", "googlePlay");
        headers.put("pandaappid", "com.hungrypanda.waimai");
        headers.put("zipcode", "");
        headers.put("timezoneoffset", "-480");
        headers.put("portalid", "3");
        headers.put("regionid", "3");
        headers.put("hpfcityname", "杭州市");
        headers.put("hpfcityid", "755");

        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }

    /**
     * 创建骑手app请求头
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("operatingsystem", "2");
        headers.put("brand", "iPhone 11");
        headers.put("latitude", "30.203602");
        headers.put("locationstatus", "2");
        headers.put("locale", "zh-CN");
        headers.put("version", "5.64.0");
        headers.put("countrycode", "CN");
        headers.put("platform", "IOS");
        headers.put("uniquetoken", "9A95A874-6493-4DFC-A5E1-BCE3C7C265D0");
        headers.put("longitude", "120.216758");
        headers.put("devicesafetoken", "a0_b0_c0_h0_i0_j0_m0_n0_p0_s0");
        headers.put("apptypeid", "2");
        headers.put("accept-language", "zh-Hans;q=1");
        headers.put("accept", "*/*");

        headers.put("User-Agent", "PandaDelivery/5.64.0 (iPhone; iOS 18.3.1; Scale/2.00) OKPOS");
        headers.put("content-type", "application/json");
        return headers;
    }
}

