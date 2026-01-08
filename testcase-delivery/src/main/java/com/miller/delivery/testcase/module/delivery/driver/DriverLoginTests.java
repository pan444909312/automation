package com.miller.delivery.testcase.module.delivery.driver;

import com.miller.delivery.testcase.config.TestcaseConfig;
import com.miller.delivery.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 骑手登录
 *
 * @author TestingConsultant@hungrypandagroup.com
 * @version 2.0
 * @since 2025/01/06
 */
@Scenario(
        scenarioID = "01K9Y708AS9GBR842WZ97SEZ0A",
        scenarioName = "骑手登录",
        author = "TestingConsultant@hungrypandagroup.com",
        developmentTime = 120, maintenanceTime = 0, manualTestTime = 6)
@DisplayName("骑手登录")
public class DriverLoginTests {

    @DisplayName("骑手登录-正常登录")
    @Test
    void shouldLoginSuccessfully() {
        // 骑手登录
        String driverAccessToken = TestCaseHelpful.deliveryLogin("13300010015", "Test1234");
        
        // 断言已在deliveryLogin中完成
        assert driverAccessToken != null && !driverAccessToken.isEmpty();
    }

    @DisplayName("骑手登录-手机号格式不正确")
    @Test
    void shouldFailLoginWithInvalidPhoneFormat() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/auth/login";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        
        String body = "{\"areaCode\":\"86\",\"password\":\"2c9341ca4cf3d87b9e4eb905d6a3ec45\",\"account\":\"181\"}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("resultCode").isEqualTo(2026);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("reason").isEqualTo("手机号码格式错误");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("success").isEqualTo(false);
    }

    @DisplayName("骑手登录-验证码为空")
    @Test
    void shouldFailLoginWithEmptyAreaCode() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/auth/login";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        
        String body = "{\"password\":\"2c9341ca4cf3d87b9e4eb905d6a3ec45\",\"account\":\"13300010015\"}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("resultCode").isEqualTo(100018);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("reason").isEqualTo("验证码不能为空！");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("success").isEqualTo(false);
    }

    @DisplayName("骑手登录-密码为空")
    @Test
    void shouldFailLoginWithEmptyPassword() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/auth/login";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        
        String body = "{\"password\":\"\",\"account\":\"13300010015\"}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("resultCode").isEqualTo(102062);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("reason").isEqualTo("请填写密码");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("success").isEqualTo(false);
    }

    @DisplayName("骑手登录-账号为空")
    @Test
    void shouldFailLoginWithEmptyAccount() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/auth/login";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        
        String body = "{\"areaCode\":\"86\",\"password\":\"2c9341ca4cf3d87b9e4eb905d6a3ec45\",\"account\":\"\"}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("resultCode").isEqualTo(102061);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("reason").isEqualTo("请填写账号");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("success").isEqualTo(false);
    }

    @DisplayName("骑手登录-账号未注册")
    @Test
    void shouldFailLoginWithUnregisteredAccount() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/auth/login";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        
        String body = "{\"areaCode\":\"86\",\"password\":\"2c9341ca4cf3d87b9e4eb905d6a3ec45\",\"account\":\"19519419411\"}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("resultCode").isEqualTo(100006);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("reason").isEqualTo("账号未注册");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("success").isEqualTo(false);
    }

    @DisplayName("骑手登录-密码错误")
    @Test
    void shouldFailLoginWithWrongPassword() {
        String uri = TestcaseConfig.HOST_DELIVERY_APP + "/api/delivery/app/auth/login";
        String method = "POST";
        Map<String, Object> headers = createDriverAppHeaders();
        
        String body = "{\"areaCode\":\"86\",\"password\":\"111\",\"account\":\"13300010015\"}";
        var responseBody = TestCaseHelpful.sendRequest(method, uri, null, headers, body);

        // 断言
        TestCaseHelpful.assertThatJson(responseBody)
                .node("resultCode").isEqualTo(102010);
        TestCaseHelpful.assertThatJson(responseBody)
                .node("reason").isEqualTo("账号密码错误，请重新输入");
        TestCaseHelpful.assertThatJson(responseBody)
                .node("success").isEqualTo(false);
    }

    /**
     * 创建骑手app请求头
     */
    private Map<String, Object> createDriverAppHeaders() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("platform", "");
        headers.put("enableSign", "false");
        headers.put("content-type", "application/json");
        return headers;
    }
}

