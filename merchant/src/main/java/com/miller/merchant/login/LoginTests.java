package com.miller.merchant.login;

import com.miller.merchant.constants.ResponseConstant;
import com.miller.merchant.login.flow.LoginFlow;
import com.miller.merchant.login.request.LoginRequestDTO;
import com.miller.merchant.login.response.LoginResponseDTO;
import com.miller.merchant.util.RequestUtils;
import com.miller.service.framework.annotation.ApiDoc;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 登录接口测试用例
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/7 20:31:39
 */
@ApiDoc(value = "http://10.1.6.46:3000/project/60/interface/api/3288")
@EnvTag.Test
@TestFramework
@DisplayName("商家-登录")
public class LoginTests {
    private static String token;

    @AfterAll
    static void afterAll() {
        // 获取token
        var headers = new HashMap<String, Object>();
        headers.put("Content-Type", "application/json");
        headers.put("authorization", token);

        // 更新全局请求头参数。设置测试用例的默认用户。
        RequestUtils.setHeaders(headers);
        assertThat(RequestUtils.getHeaders().get("authorization")).isNotNull();
    }

    @MethodSource("com.miller.merchant.login.provider.LoginDataProvider#loginDataProviderFromDB")
    @ParameterizedTest
    @DisplayName("登录流程-正常登录正常")
    void shouldLoginSuccessfully(LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO loginResponseDTO = LoginFlow.loginReturnBodyObject(loginRequestDTO);

        assertThat(loginResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(loginResponseDTO.getResult().getAccessToken()).isNotNull();
        assertThat(loginResponseDTO.getSuccess()).isTrue();
        // 获取token
        token = loginResponseDTO.getResult().getAccessToken();
    }

}
