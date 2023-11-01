package com.miller.demo.login;

import com.miller.demo.login.flow.LoginFlow;
import com.miller.demo.login.request.LoginRequestDTO;
import com.miller.demo.login.response.LoginResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.JSONPathUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * 登录接口测试用例
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/01 14:10:12
 */
@EnvTag.Test
@TestFramework
@DisplayName("登录")
public class LoginTests {

    @MethodSource("com.miller.demo.login.provider.LoginDataProvider#loginDataProvider")
    @ParameterizedTest
    @DisplayName("login testing by data provider")
    void loginTest(String username, String password, Integer code, String message) {
        // Given.
        // 数据提供者使用数据驱动

        // When
        String returnBody = LoginFlow.loginReturnBody(username, password);

        // Then. 通过 JSON Path 解析响应结果
        Integer returnCode = JSONPathUtils.parseJsonStringToInteger(returnBody, "code");
        String returnMessage = JSONPathUtils.parseJsonStringToString(returnBody, "message");
        assertThat(returnCode, Matchers.is(code));
        assertThat(returnMessage, Matchers.is(message));
    }

    @MethodSource("com.miller.demo.login.provider.LoginDataProvider#loginDataProvider")
    @ParameterizedTest
    @DisplayName("test response body string to Java object")
    void loginTestByJavaObject(String username, String password, Integer code, String message) {

        // When. 将响应体映射为 Java Object
        LoginResponseDTO responseUser = LoginFlow.loginReturnJavaObject(username, password);

        // Then. 通过 Java 对象操作数据，更加符合 OOP 原则，避免通过 JSON Path 解析字符串，更加公用
        Integer returnCode = responseUser.getCode();
        String returnMessage = responseUser.getMessage();
        assertThat(returnCode, Matchers.is(code));
        assertThat(returnMessage, Matchers.is(message));
    }

    @MethodSource("com.miller.demo.login.provider.LoginDataProvider#loginDataProviderFromDB")
    @ParameterizedTest(name = "[{index}] {arguments}")
    @DisplayName("test data from DB")
    void loginTestByDataBase(LoginRequestDTO loginRequestDTO) {
        // Given

        // When. 将响应体映射为 Java Object
        LoginResponseDTO responseUser = LoginFlow.loginReturnJavaObject(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());

        // Then. 通过 Java 对象操作数据，更加符合 OOP 原则，避免通过 JSON Path 解析字符串，更加公用
        Integer returnCode = responseUser.getCode();
        // String returnMessage = responseUser.getMessage();
        assertThat(returnCode, Matchers.is(0));
    }
}
