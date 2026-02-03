package com.miller.delivery.testcase.module.dispatch.order;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.module.deliveryUtils.order.CreateInstantOrderLeaveAtDoorTests;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * 挂起订单
 *
 * @author auto-generated
 * @version 2.0
 * @since 2025/01/01 00:00:00
 */
@Scenario(
        scenarioID = "01JPPMTFXATNF3MQ1RFWK55GR5",
        scenarioName = "调度系统-订单挂起-挂起订单",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
@DisplayName("挂起订单")
public class PutOnDispatchOrderTests {
    public   String deliveryOrderSn;

    @DisplayName("正向流程")
    @Test
    void shouldPutOnDispatchOrder() {
        // 步骤1: 先登录获取token
        String token = erpLogin();
        
        // 步骤2: 设置请求头
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/dispatch/order/putOnDispatchOrder/request/headers.json");
        headers.put("token", token);
        headers.put("accept", "application/json, text/plain, */*");
        headers.put("content-type", "application/json;charset=UTF-8");


        // 步骤3: 设置请求体
        var requestBody = TestCaseHelpful.getJsonRequestBody("module/dispatch/order/putOnDispatchOrder/request/body.json");

        //创建C订单，获取userAppOrderSn
        CreateInstantOrderLeaveAtDoorTests creaateOrderLeaveAtDoorTests = new CreateInstantOrderLeaveAtDoorTests();
        // 步骤1: C侧下单-用户登录
        String userAppAccessToken = creaateOrderLeaveAtDoorTests.userAppLogin();
        // 步骤2: C侧下单-获取店铺商品信息
        Long productId = creaateOrderLeaveAtDoorTests.getShopProductInfo(userAppAccessToken);
        // 步骤3: C侧下单-加购商品
        Long shopId = creaateOrderLeaveAtDoorTests.addToCart(userAppAccessToken, productId);
        // 步骤4: C侧下单-创建虚拟单
        creaateOrderLeaveAtDoorTests.createVirtualOrder(userAppAccessToken, shopId, productId);
        // 步骤5: C侧下单-创建即时单-平台配送 (deliverableAction=17 放在门口)
        String userAppOrderSn = creaateOrderLeaveAtDoorTests.createOrder(userAppAccessToken, shopId, productId);
        // 步骤6: C侧下单-余额支付
        creaateOrderLeaveAtDoorTests.balancePay(userAppAccessToken, userAppOrderSn);
        deliveryOrderSn=userAppOrderSn;
        // 断言订单创建成功
        assertNotNull(userAppOrderSn);
        assertFalse(userAppOrderSn.isEmpty());

        // 等待2秒
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.orderSn", userAppOrderSn);
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.putOnTimeMinutes", "10");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.putOnType", 0);

        // 步骤4: 发起请求
        String uri = TestcaseConfig.HOST_ERP + "/api/dispatch/order/putOnDispatchOrder";
        String method = "POST";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);

        // 步骤5: 断言响应结果
        TestCaseHelpful.assertThatJson(responseBody).node("code").isEqualTo(1);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
    }

    /**
     * 司管后台登录并返回token
     *
     * @return token
     */
    private String erpLogin() {
        String uri = TestcaseConfig.HOST_ERP + "/api/erp/auth/login/v2";
        String method = "POST";
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/erp/auth/request/headers.json");

        headers.put("Content-Type", "application/json");

        var requestBody = TestCaseHelpful.getJsonRequestBody("module/erp/auth/request/body.json");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.password", "4f2142904392cbef6974ad0260caeb33");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.userName", "ding17058431144045523");

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        return TestCaseHelpful.extractValue(responseBody, "$.data.token").toString();
    }
}

