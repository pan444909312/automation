package com.miller.delivery.testcase.module.dispatch.order;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * 复制订单信息
 *
 * @author auto-generated
 * @version 2.0
 * @since 2025/01/01 00:00:00
 */
@Scenario(
        scenarioID = "01JPPFD6JCBK96S7232ME8TAWV", // 自动生成，不要修改
        scenarioName = "调度系统-订单列表-复制订单信息",
        author = "chenchunxia@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("复制订单信息")
public class CopyOrderInfoTests {
    String uri = TestcaseConfig.HOST_ERP + "/api/dispatch/order/copyOrderInfo";
    String method = "POST";
    String params = null;
    String assertFullField = "module/dispatch/order/copyOrderInfo/response/assert_full_field.json";

    @DisplayName("正向流程")
    @Test
    void shouldSuccess() {
        // 步骤1: 先登录获取token
        String token = erpLogin();
        
        // 步骤2: 设置请求头
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/dispatch/order/copyOrderInfo/request/headers.json");
        headers.put("token", token);
        headers.put("accept", "application/json, text/plain, */*");
        headers.put("content-type", "application/json;charset=UTF-8");
        headers.put("enableSign", "false");

        // 步骤3: 设置请求体
        var requestBody = TestCaseHelpful.getJsonRequestBody("module/dispatch/order/copyOrderInfo/request/body.json");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.orderSn", "1711368125760701831272");

        // 步骤4: 发起请求
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
        headers.put("enableSign", "false");
        headers.put("Content-Type", "application/json");

        var requestBody = TestCaseHelpful.getJsonRequestBody("module/erp/auth/request/body.json");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.password", "4f2142904392cbef6974ad0260caeb33");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.userName", "ding17058431144045523");

        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, requestBody);
        TestCaseHelpful.assertThatJson(responseBody).node("message").isEqualTo("成功");
        return TestCaseHelpful.extractValue(responseBody, "$.data.token").toString();
    }
}

