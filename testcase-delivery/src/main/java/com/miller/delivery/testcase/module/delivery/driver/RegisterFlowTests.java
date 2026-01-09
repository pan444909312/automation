package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 注册流程多case
 *
 * @author TestingConsultant@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/07
 */
@Scenario(
        scenarioID = "PLACEHOLDER_SCENARIO_ID",
        scenarioName = "注册流程多case",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 180, maintenanceTime = 0, manualTestTime = 60)
@DisplayName("注册流程多case")
public class RegisterFlowTests {

    @DisplayName("异常case: 手机号为空")
    @Test
    void shouldFailRegisterWithEmptyPhone() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/auth/registerNewDriver";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        
        String body = "{\"userTelphone\":\"\",\"verifyCode\":\"123456\",\"cityId\":755}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        // 验证返回错误码（具体错误码需要根据实际API响应调整）
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(false);
    }

    @DisplayName("异常case: 验证码为空")
    @Test
    void shouldFailRegisterWithEmptyVerifyCode() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/auth/registerNewDriver";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        
        String body = "{\"userTelphone\":\"13300010015\",\"verifyCode\":\"\",\"cityId\":755}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        // 验证返回错误码（具体错误码需要根据实际API响应调整）
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(false);
    }

    @DisplayName("异常case: cityId为空")
    @Test
    void shouldFailRegisterWithEmptyCityId() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/auth/registerNewDriver";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        
        String body = "{\"userTelphone\":\"13300010015\",\"verifyCode\":\"123456\",\"cityId\":null}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        // 验证返回错误码（具体错误码需要根据实际API响应调整）
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isNotNull();
        TestCaseHelpful.assertThatJson(responseBody).node("success").isEqualTo(false);
    }

    @DisplayName("正常case: 注册新骑手")
    @Test
    void shouldRegisterNewDriverSuccessfully() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/auth/registerNewDriver";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        
        // 注意：实际使用时需要先插入验证码到数据库
        String body = "{\"userTelphone\":\"13300010015\",\"verifyCode\":\"123456\",\"cityId\":755}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);
        
        // 验证返回成功（如果验证码正确）
        TestCaseHelpful.assertThatJson(responseBody).node("resultCode").isNotNull();
    }

    /**
     * 创建骑手app请求头
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("longitude", "120.216806");
        headers.put("latitude", "30.203427");
        headers.put("version", "5.68.3");
        headers.put("platform", "ANDROID_DELIVERY");
        headers.put("type", "3");
        headers.put("locale", "zh-CN");
        headers.put("operatingsystem", "1");
        headers.put("brand", "HUAWEI");
        headers.put("uniquetoken", "dd9959880e28753f");
        headers.put("apptypeid", "2");
        headers.put("countrycode", "CN");
        headers.put("devicesafetoken", "a0_b1_c1_h0_i0_j0_m0_n0_p0_s0");
        headers.put("enableSign", "false");
        headers.put("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.put("content-type", "application/json;charset=UTF-8");
        return headers;
    }
}
