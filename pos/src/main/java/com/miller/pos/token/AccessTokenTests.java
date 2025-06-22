package com.miller.pos.token;

import com.miller.pos.token.flow.AccessTokenFlow;
import com.miller.pos.token.request.AccessTokenRequestDTO;
import com.miller.pos.token.response.AccessTokenResponseDTO;
import com.miller.pos.util.RequestUtils;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 测试用例_登录
 *
 * @author zhangli
 * @version 1.0
 * @since 2024/4/3 13:51:39
 */
@EnvTag.Test
@Slf4j
@TestFramework
@DisplayName("pos-登录获取token")
public class AccessTokenTests {
    private static String token;

    @AfterAll
    static void afterAll() {
        // 获取token
        var headers = new HashMap<String, Object>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", token);

        // 更新全局请求头参数。设置测试用例的默认用户。
        RequestUtils.setHeaders(headers);
//        assertThat(RequestUtils.getHeaders().get("Authorization")).isNotNull();
    }

    @MethodSource("com.miller.pos.token.provider.PosLoginDataProvider#loginDataProviderFromDB")
    @ParameterizedTest
    @DisplayName("正常流程_POS商家登录")
    void shouldLoginSuccessfully(AccessTokenRequestDTO accessTokenRequestDTO) {
        AccessTokenResponseDTO accessTokenResponseDTO = AccessTokenFlow.getAccessToken(accessTokenRequestDTO);
        assertThat(accessTokenResponseDTO.getCode()).isEqualTo(0);
        assertThat(accessTokenResponseDTO.getData().getAccessToken()).isNotNull();

        // 获取token
        token = accessTokenResponseDTO.getData().getAccessToken();
    }

}
