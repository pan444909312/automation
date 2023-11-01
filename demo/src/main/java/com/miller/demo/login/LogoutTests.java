package com.miller.demo.login;


import com.miller.demo.constants.SystemConfigConstant;
import com.miller.demo.login.flow.LogoutFlow;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.http.HttpUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * 登出接口测试用例
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/01 19:20:12
 */
@EnvTag.Test
@TestFramework
@DisplayName("登出")
public class LogoutTests {

    @DisplayName("It should be logout successfully")
    @Test
    void shouldLogoutSuccessful() {
        // Given

        // When. 这个接口返回的不是 JSON 数据格
        String responseBody = LogoutFlow.shouldLogoutSuccessful();

        // Then
        assertThat(responseBody, Matchers.containsStringIgnoringCase("成功退出"));
    }
}
