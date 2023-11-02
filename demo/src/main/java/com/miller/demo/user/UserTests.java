package com.miller.demo.user;

import com.miller.demo.constants.BusinessConstant;
import com.miller.demo.login.flow.LoginFlow;
import com.miller.demo.user.flow.UserFlow;
import com.miller.demo.user.response.UserGetUserListResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.miller.service.framework.asserts.AssertUtils.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

/**
 * 用户管理用例集
 * <p>
 * 测试用例中使用{@link BeforeAll @BeforeAll}注解完成前置条件，而不是通过继承父类的方式获取cookies。
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/2 15:57:00
 */
@EnvTag.Test
@TestFramework
@DisplayName("用户管理")
public class UserTests {
    private static Map<String, Object> token;

    @BeforeAll
    static void beforeAll() {
        token = LoginFlow.loginReturnCookies(BusinessConstant.USERNAME_MILLER, BusinessConstant.PASSWORD_MILLER);
    }

    @DisplayName("It should be successfully because system user must greater than one")
    @Test
    void shouldGraterThanOne() {
        // Given

        // When
        UserGetUserListResponseDTO response = UserFlow.getUserList(token);
        // Then
        assertThat(response.getData().size(), greaterThanOrEqualTo(1));
    }
}
