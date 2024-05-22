package com.miller.demo.loginv2;

import com.miller.demo.loginv2.flow.LoginV2Flow;
import com.miller.demo.loginv2.request.LoginV2RequestDTO;
import com.miller.demo.loginv2.response.LoginV2ResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * 测试用例_登录
 *
 * @author Miller Shan
 * @version 2.0
 * @since 2024/5/22 21:10:12
 */
@EnvTag.Test
@TestFramework
@DisplayName("登录V2")
public class LoginV2Tests {
    private static String token;

    @BeforeAll
    static void beforeAll() {
        System.out.println("beforeAll invoked...");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("afterAll invoked...");
        // 获取token
        var headers = new HashMap<String, Object>();
        headers.put("Content-Type", "application/json");
        headers.put("authorization", token);
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("beforeEach invoked...");
    }

    @AfterEach
    void afterEach() {
        System.out.println("afterEach invoked...");
    }

    @MethodSource("loginDataProvider")
    @ParameterizedTest
    @DisplayName("Should Login Successfully")
    void shouldLoginSuccessfully(LoginV2RequestDTO loginV2RequestDTO) {
        // Given

        // When. 将响应体映射为 Java Object
        LoginV2ResponseDTO responseUser = LoginV2Flow.loginReturnJavaObject(loginV2RequestDTO.getEmail(), loginV2RequestDTO.getPassword());

        // Then. 通过 Java 对象操作数据，更加符合 OOP 原则，避免通过 JSON Path 解析字符串，更加公用
        assertThat(responseUser.getCode(), Matchers.is(loginV2RequestDTO.getExpectCode()));

        token = responseUser.getData().getToken();
    }

    /**
     * 登陆测试用例数据提供者
     */
    static Stream<Arguments> loginDataProvider() {
        LoginV2RequestDTO loginV2RequestDTO = new LoginV2RequestDTO();
        loginV2RequestDTO.setEmail("miller.shan@aliyun.com");
        loginV2RequestDTO.setPassword("123456");
        loginV2RequestDTO.setExpectCode(0);
        loginV2RequestDTO.setExpectMessage("success");

        return Stream.of(arguments(loginV2RequestDTO));
    }
}
