package com.miller.userapp.login;

import com.miller.service.framework.annotation.ApiDoc;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.login.flow.UserLoginFlow;
import com.miller.userapp.login.request.UserLoginRequestDTO;
import com.miller.userapp.login.response.UserLoginResponseDTO;
import com.miller.userapp.util.RequestUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 测试用例_用户登录
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/7 20:31:39
 */
@ApiDoc("http://10.1.6.46:3000/project/60/interface/api/3288")
@EnvTag.Test
@TestFramework
@DisplayName("用户-登录")
public class UserLoginTests {
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

    @MethodSource("com.miller.userapp.login.provider.UserLoginDataProvider#loginData")
    @ParameterizedTest
    @DisplayName("正常流程_用户登录")
    void shouldLoginSuccessfully(UserLoginRequestDTO userLoginRequestDTO) {
        UserLoginResponseDTO userLoginResponseDTO = UserLoginFlow.loginReturnBodyObject(userLoginRequestDTO);

        assertThat(userLoginResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(userLoginResponseDTO.getResult().getAccessToken()).isNotNull();
        assertThat(userLoginResponseDTO.getSuccess()).isTrue();
        assertThat(userLoginResponseDTO.getResult().getUserName())
                .isNotNull()
                .isEqualTo(userLoginRequestDTO.getAccount());

        // 获取token
        token = userLoginResponseDTO.getResult().getAccessToken();
    }

}
