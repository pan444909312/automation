package com.miller.demo.login;

import com.miller.demo.login.flow.LoginFlow;
import com.miller.demo.login.flow.LogoutFlow;
import com.miller.demo.login.request.LoginRequestDTO;
import com.miller.demo.login.response.LoginResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * 场景: 登入->登出
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/01 20:11:12
 */
@DisplayName("登陆场景_登入->登出")
public class SimpleLoginThenLogoutScenarioTests {

    @MethodSource("com.miller.demo.login.provider.LoginDataProvider#loginScenarioDataProvider")
    @ParameterizedTest
    @EnvTag.Test
    @DisplayName("登入->登出")
    void loginThenLogout(LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO responseUser =
                LoginFlow.loginReturnJavaObject(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
        // 比如：提取数据给下一个流程使用
        responseUser.getData().getToken();
        LogoutFlow.shouldLogoutSuccessful();
    }
}
