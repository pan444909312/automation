package com.miller.demo.loginv2;

import com.miller.demo.constants.ResponseConstant;
import com.miller.demo.dto.external.User;
import com.miller.demo.loginv2.flow.LoginV2Flow;
import com.miller.demo.loginv2.request.LoginV2RequestDTO;
import com.miller.demo.loginv2.response.LoginV2ResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.db.DBUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
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
    private static final String mySqlUrl = "jdbc:mysql://rm-3ns24734o9z8747d0jo.mysql.rds.aliyuncs.com:3306/ct_test";
    private static final String userName = "automation";
    private static final String passWord = "20AR@UJsobwLBdih";
    private static DBUtils dbUtils;

    private static String token;

    @BeforeAll
    static void beforeAll() {
        System.out.println("beforeAll invoked...");
        // 初始化，链接数据库
        dbUtils = new DBUtils(mySqlUrl, userName, passWord);
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
        LoginV2ResponseDTO response = LoginV2Flow.loginReturnJavaObject(loginV2RequestDTO.getEmail(), loginV2RequestDTO.getPassword());

        // Then. 通过 Java 对象操作数据，更加符合 OOP 原则，避免通过 JSON Path 解析字符串
        assertThat(response.getCode(), is(ResponseConstant.CODE_SUCCESS));
        assertThat(response.getData().getUser().getEmail(), is(loginV2RequestDTO.getEmail()));  // 断言接口响应字段与数据库字段值一致

        token = response.getData().getToken();
    }

    /**
     * 登陆测试用例数据提供者
     */
    private static Stream<Arguments> loginDataProvider() {
//        // 方式一：手工构造。废弃
//        LoginV2RequestDTO loginV2RequestDTO = new LoginV2RequestDTO();
//        loginV2RequestDTO.setEmail("miller.shan@aliyun.com");
//        loginV2RequestDTO.setPassword("123456");

        // 方式二：数据库构造
        String sql = "SELECT * FROM user where user_id = ?";
        LoginV2RequestDTO user = dbUtils.queryOneObjectReturnObject(sql, LoginV2RequestDTO.class, "Miller");
        user.setPassword("123456"); // 数据库加密字段，所以需要二次处理

        return Stream.of(
                arguments(user
                ));
    }
}
