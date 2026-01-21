package com.miller.delivery.testcase.module.dispatch.control;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * 关闭AUTO
 *
 * @author auto-generated
 * @version 2.0
 * @since 2025/01/01 00:00:00
 */
@Scenario(
        scenarioID = "01JPPM645FJ0AJKTH2T484SC49",
        scenarioName = "调度系统-骑手管控-关闭AUTO",
        author = "chenchunxia@hungrypandagroup.com",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("关闭AUTO")
public class ControlSaveTests {

    @DisplayName("正向流程")
    @Test
    void shouldSaveControl() {
        // 步骤1: 先登录获取token
        String token = erpLogin();
        
        // 步骤2: 设置请求头
        Map<String, Object> headers = TestCaseHelpful.getHeaders("module/dispatch/control/controlSave/request/headers.json");
        headers.put("token", token);
        headers.put("accept", "application/json, text/plain, */*");
        headers.put("content-type", "application/json;charset=UTF-8");


        // 步骤3: 设置请求体
        var requestBody = TestCaseHelpful.getJsonRequestBody("module/dispatch/control/controlSave/request/body.json");
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.punishAction", 5);
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.controlReason", 6);
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.isSenMessage", 0);
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.driverId", 1398714150);
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.punishType", 1);
        requestBody = TestCaseHelpful.updateJsonValue(requestBody, "$.punishTime", 0.1);

        // 步骤4: 发起请求
        String uri = TestcaseConfig.HOST_ERP + "/api/dispatch/control/save";
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

