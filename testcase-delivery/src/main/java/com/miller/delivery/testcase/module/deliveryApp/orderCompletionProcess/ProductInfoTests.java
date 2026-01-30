package com.miller.delivery.testcase.module.deliveryApp.orderCompletionProcess;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.module.deliveryUtils.order.CreateInstantOrderWithHandoverTests;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.delivery.testcase.utils.driverOffline;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.miller.delivery.testcase.utils.TestCaseHelpful.erpLogin;

/**
 * 获取商品详情
 *
 * @author chenchunxia@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/07
 */
@Scenario(
        scenarioID = "01KF08DFK2HRPEG8G49FTCG4R3",
        scenarioName = "骑手app-获取商品详情",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 180, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("获取商品详情")
public class ProductInfoTests {

    @DisplayName("骑手app-获取商品详情（productInfo）")
    @Test
    void shouldGetProductInfo() {
        // 1) C侧下单（复用公共下单流程）
        CreateInstantOrderWithHandoverTests create = new CreateInstantOrderWithHandoverTests();
        String userAppOrderSn = create.orderFlow();
        
        // 2) 骑手登录
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010676", "Test1234");
        driverOffline driverOffline = new driverOffline();
        driverOffline.cancelDispatchAndOffline("13300010676",driverAccessToken);
        // 3) 司机上线操作
        driverOnline(driverAccessToken);
        
        // 4) 骑手抢单
        grabOrder(driverAccessToken, userAppOrderSn);
        
        // 5) 获取商品信息
        getProductInfo(driverAccessToken, userAppOrderSn);
// 步骤9: 司管登录获取token
        String siGuanToken = erpLogin();
        // ========== 第五部分：后置操作 ==========
        // 步骤15: 调度取消配送
        cancelDispatch(siGuanToken, userAppOrderSn);
    }

    /**
     * 调度取消配送
     */
    private void cancelDispatch(String siGuanToken, String orderSn) {
        String uri = TestcaseConfig.HOST_ERP + "/api/dispatch/dispatch/cancelDispatch";
        String method = "POST";
        Map<String, Object> headers = createDispatchHeaders();
        headers.put("token", siGuanToken);

        String body = String.format("{\"orderSn\":\"%s\"}", orderSn);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }
    /**
     * 创建调度系统请求头
     */
    private Map<String, Object> createDispatchHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("accept", "application/json, text/plain, */*");
        headers.put("accept-language", "zh-CN,zh;q=0.9");
        headers.put("origin", "https://hp-dispatch-admin-f2e-test.hungrypanda.cn");
        headers.put("priority", "u=1, i");
        headers.put("referer", "https://hp-dispatch-admin-f2e-test.hungrypanda.cn/");
        headers.put("sec-ch-ua", "\"Google Chrome\";v=\"131\", \"Chromium\";v=\"131\", \"Not_A Brand\";v=\"24\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"Windows\"");
        headers.put("sec-fetch-dest", "empty");
        headers.put("sec-fetch-mode", "cors");
        headers.put("sec-fetch-site", "same-site");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36");

        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
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
        Map<String, Object> headers = createIOSDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = String.format("{\"orderSn\":\"%s\",\"confirmDiscount\":0}", userAppOrderSn);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    /**
     * 获取商品信息
     */
    private void getProductInfo(String driverAccessToken, String orderSn) {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/order/productInfo";
        String method = "POST";
        Map<String, Object> headers = createIOSDriverAppHeaders();
        headers.put("authorization", driverAccessToken);
        
        String body = String.format("{\"orderSn\":\"%s\"}", orderSn);
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isEqualTo(1000);
        TestCaseHelpful.assertThatJson(responseBody).node("reason").isEqualTo("成功");
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(true);
    }

    /**
     * 创建IOS骑手app请求头
     */
    private Map<String, Object> createIOSDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("Host", "app-deliverytest.hungrypanda.cn");
        headers.put("operatingsystem", "2");
        headers.put("user-agent", "PandaDelivery/5.64.0 (iPhone; iOS 18.3.1; Scale/2.00) OKPOS");
        headers.put("brand", "iPhone 11");
        headers.put("latitude", "30.203602");
        headers.put("locationstatus", "2");
        headers.put("locale", "zh-CN");
        headers.put("version", "5.64.0");
        headers.put("countrycode", "CN");
        headers.put("platform", "IOS");
        headers.put("uniquetoken", "9A95A874-6493-4DFC-A5E1-BCE3C7C265D0");
        headers.put("longitude", "120.216758");
        headers.put("accept-language", "zh-Hans;q=1");
        headers.put("devicesafetoken", "a0_b0_c0_h0_i0_j0_m0_n0_p0_s0");
        headers.put("apptypeid", "2");
        headers.put("accept", "*/*");

        headers.put("content-type", "application/json");
        return headers;
    }
}
