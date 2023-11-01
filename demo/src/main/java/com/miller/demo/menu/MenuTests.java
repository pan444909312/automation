package com.miller.demo.menu;

import com.miller.demo.constants.BusinessConstant;
import com.miller.demo.constants.SystemConfigConstant;
import com.miller.demo.login.flow.LoginFlow;
import com.miller.demo.menu.flow.MenuInfoFlow;
import com.miller.demo.menu.response.UserGetMenuListResponseDTO;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.http.HttpUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * 菜单栏测试用例集
 * <p>
 * 使用{@link TestFramework @TestFramework}注解.
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/1 20:53:32
 */
@TestFramework
@DisplayName("菜单栏")
public class MenuTests {
    @DisplayName("It should be contains issue menu because he is normal user")
    @Test
    void shouldContainsIssueMenu() {
        // Given
        Map<String, Object> cookies = LoginFlow.loginReturnCookies(BusinessConstant.USERNAME_MILLER, BusinessConstant.PASSWORD_MILLER);

        // When
        UserGetMenuListResponseDTO responseBody = MenuInfoFlow.getMenu(cookies, BusinessConstant.USERNAME_MILLER);

        // Then
        Integer returnCode = responseBody.getCode();
        String returnMessage = responseBody.getMessage();
        ArrayList<UserGetMenuListResponseDTO.MenuList> returnData = responseBody.getData();
        assertThat(returnCode, is(0));
        assertThat(returnMessage, is("success"));
        assertThat(returnData.size(), lessThanOrEqualTo(1));
        // 断言对象包含属性
        assertThat(returnData.get(0).getMenu(), hasProperty("permissionCode"));
        assertThat(returnData.get(0).getSubMenu().get(0), hasProperty("permissionCode", containsStringIgnoringCase("issues:reports")));
    }

    @DisplayName("It should be contains issue and manage menu because he is normal user")
    @Test
    void shouldContainsIssueAndManageMenu() {
        // Given
        Map<String, Object> cookies = LoginFlow.loginReturnCookies(BusinessConstant.USERNAME_ADMIN, BusinessConstant.PASSWORD_ADMIN);

        // When
        UserGetMenuListResponseDTO responseBody = MenuInfoFlow.getMenu(cookies, BusinessConstant.USERNAME_ADMIN);

        // Then
        Integer returnCode = responseBody.getCode();
        String returnMessage = responseBody.getMessage();
        ArrayList<UserGetMenuListResponseDTO.MenuList> returnData = responseBody.getData();
        assertThat(returnCode, is(0));
        assertThat(returnMessage, is("success"));
        // admin 应该至少包含两个菜单
        assertThat(returnData.size(), greaterThan(1));
    }
}
