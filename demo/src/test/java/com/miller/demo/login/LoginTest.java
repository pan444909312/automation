package com.miller.demo.login;

import com.miller.demo.login.flow.LoginFlow;
import com.miller.service.framework.util.JSONPathUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;

/**
 * @author Miller Shan
 * @version 1.0
 */
@DisplayName("Login TestCases")
class LoginTest {
    @Test
    @DisplayName("Get login result like body, headers, cookies, cookies.")
    void shouldLoginSuccessThenReturnAllResponse() {
        // Given
        var username = "miller.shan@aliyun.com";
        var password = "123456";

        // When
        var responseMap = LoginFlow.login(username, password);

        // Then
        //  获取响应 Body
        var responseBodyMap = (HashMap<String, Object>) responseMap.get("body");
        var responseBody = String.valueOf(responseBodyMap.get("body"));
        var code = JSONPathUtils.parseJsonStringToInteger(responseBody, "code");
        assertThat(code, is(0));
        // 获取响应 headers
        var cookies = (HashMap<String, Object>) responseMap.get("headers");
        assertThat(cookies.size(), greaterThanOrEqualTo(1));
    }

    @Test
    @DisplayName("Login then return Java Object.")
    void shouldReturnJavaObject() {
        // Given
        var username = "miller.shan@aliyun.com";
        var password = "123456";

        // When
        var userLoginResponseDTO = LoginFlow.loginReturnJavaObject(username, password);

        // Then
        var code = userLoginResponseDTO.getCode();
        assertThat(code, is(0));
    }
}