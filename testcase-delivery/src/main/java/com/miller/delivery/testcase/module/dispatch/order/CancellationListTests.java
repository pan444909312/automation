package com.miller.delivery.testcase.module.dispatch.order;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * 撤单记录接口
 *
 * @author auto-generated
 * @version 2.0
 * @since 2025/01/01 00:00:00
 */
@Scenario(
        scenarioID = "01JPPN56556SAH4J9S6TAJVCVM",
        scenarioName = "调度系统-撤单-撤单记录接口",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("撤单记录接口")
public class CancellationListTests {

    @DisplayName("正向流程")
    @Test
    void shouldGetCancellationList() {
        // 步骤1: 先登录获取token
        String token = erpLogin();
        
        // 步骤2: 设置请求头
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/dispatch/order/cancellationList/request/headers.json");
        headers.put("token", token);
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36");
        headers.put("enableSign", "false");
        headers.put("Content-Type", "application/json");

        // 步骤3: 设置请求体
        var requestBody = TestCaseHelpful.getJsonRequestBody("module/dispatch/order/cancellationList/request/body.json");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.orderSn", "APIFOXTEST1736150813484");

        // 步骤4: 发起请求
        String uri = TestcaseConfig.HOST_ERP + "/api/dispatch/cancellation/listByOrder";
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

