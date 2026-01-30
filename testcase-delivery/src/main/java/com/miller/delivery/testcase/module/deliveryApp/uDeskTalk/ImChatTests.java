package com.miller.delivery.testcase.module.deliveryApp.uDeskTalk;

import com.miller.common.util.MD5Util;
import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * im聊天相关
 *
 * @author TestingConsultant@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/07
 */
@Scenario(
        scenarioID = "01JWBJW1ZV4W2328R0WGK12B69",
        scenarioName = "im聊天相关",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 240, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("im聊天相关")
public class ImChatTests {

    @DisplayName("获取订单对应groupid")
    @Test
    void shouldGetOrderGroupId() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/waitDeliveringList";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = "{\"sortType\":0,\"pageNo\":1,\"pageSize\":20}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    @DisplayName("进入im聊天界面")
    @Test
    void shouldGetImToken() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/im/getImToken";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = "{}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    @DisplayName("获取订单对应的im相关信息")
    @Test
    void shouldGetOrderImStatus() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        
        // 先获取imId和groupId
        String imId = getImId(driverAccessToken);
        String groupId = getGroupId(driverAccessToken);
        
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/im/getOrderStatus";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = String.format("{\"driverImId\":\"%s\",\"groupId\":\"%s\"}", imId, groupId);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    @DisplayName("发送订单数据")
    @Test
    void shouldSendOrderInfo() {
        Map<String, String> loginResult = driverLogin("13300010015", "Test1234");
        String driverAccessToken = loginResult.get("accessToken");
        String userId = loginResult.get("userId");
        
        // 先获取imId和groupId
        String imId = getImId(driverAccessToken);
        String groupId = getGroupId(driverAccessToken);
        
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/im/getOrderInfo";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeadersForIm();
        headers.put("authorization", driverAccessToken);
        
        String body = String.format("{\"driverImId\":\"%s\",\"groupId\":\"%s\"," +
                "\"orderSn\":\"623448144834971301340\",\"userId\":%s}", imId, groupId, userId);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    @DisplayName("未登录 -发送订单数据")
    @Test
    void shouldFailSendOrderInfoWithoutLogin() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/im/getOrderInfo";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeadersForIm();
        // 不设置authorization
        
        String body = "{\"driverImId\":\"\",\"groupId\":\"\",\"orderSn\":\"623448144834971301340\",\"userId\":\"\"}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(2015);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("未登录,请登录后操作");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(false);
    }

    @DisplayName("获取环信token--骑手未登录")
    @Test
    void shouldFailGetImTokenWithoutLogin() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/im/getImToken";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        // 不设置authorization
        
        String body = "{\"driverImId\":\"\",\"groupId\":\"\",\"orderSn\":\"623448144834971301340\",\"userId\":\"\"}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(2015);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("未登录,请登录后操作");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(false);
    }

    @DisplayName("获取环信token--无订单返回成功")
    @Test
    void shouldGetImTokenWithoutOrder() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/im/getImToken";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = "{\"driverImId\":\"\",\"groupId\":\"\",\"orderSn\":\"\",\"userId\":\"\"}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
        TestCaseHelpful.assertThatJson(responseBody).node("result.isClose").isEqualTo(0);
    }

    @DisplayName("查询通用聊天提示和快捷回复")
    @Test
    void shouldGetAllTipAndQuick() {
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/im/getAllTipAndQuick";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = "{\"driverImId\":\"null\",\"groupId\":\"297307705769985\"," +
                "\"orderSn\":\"null\",\"userId\":null}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    @DisplayName("未登录 - 查询通用聊天提示和快捷回复")
    @Test
    void shouldFailGetAllTipAndQuickWithoutLogin() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/im/getAllTipAndQuick";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        // 不设置authorization
        
        String body = "{\"driverImId\":\"null\",\"groupId\":\"297307705769985\"," +
                "\"orderSn\":\"null\",\"userId\":null}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(2015);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("未登录,请登录后操作");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(false);
    }

    /**
     * 骑手登录并返回accessToken和userId
     */
    private Map<String, String> driverLogin(String account, String password) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/auth/login";
        String method = "POST";
        Map<String, Object> headers = new HashMap<>();
         
        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("Content-Type", "application/json");
        
        // 对密码进行MD5加密
        String md5Password = MD5Util.string2MD5(password);
        String body = String.format("{\"areaCode\":\"86\",\"password\":\"%s\",\"account\":\"%s\"}",
                md5Password, account);
        
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
        
        String accessToken = TestCaseHelpful.extractValue(responseBody, "$.result.accessToken").toString();
        String userId = TestCaseHelpful.extractValue(responseBody, "$.result.userId").toString();
        
        return Map.of("accessToken", accessToken, "userId", userId);
    }

    /**
     * 获取imId
     */
    private String getImId(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/im/getImToken";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = "{}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        return TestCaseHelpful.extractValue(responseBody, "$.result.imId").toString();
    }

    /**
     * 获取groupId
     */
    private String getGroupId(String driverAccessToken) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/waitDeliveringList";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = "{\"sortType\":0,\"pageNo\":1,\"pageSize\":20}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        return TestCaseHelpful.extractValue(responseBody, "$.result.orderList[0].imInfo.groupId").toString();
    }

    /**
     * 创建骑手app请求头
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("longitude", "120.216787");
        headers.put("latitude", "30.203426");
        headers.put("version", "5.66.0");
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

    /**
     * 创建骑手app请求头（用于IM相关接口）
     */
    private Map<String, Object> createDriverAppHeadersForIm() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("platform", "ANDROID_DELIVERY");
        headers.put("apptypeid", "2");
         
        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("Content-Type", "application/json");
        return headers;
    }
}
