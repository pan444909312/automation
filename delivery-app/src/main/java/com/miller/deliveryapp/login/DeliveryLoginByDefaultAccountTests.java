package com.miller.deliveryapp.login;

import com.miller.deliveryapp.constants.ResponseConstant;
import com.miller.deliveryapp.login.flow.DeliveryLoginFlow;
import com.miller.deliveryapp.login.request.DeliveryLoginRequestDTO;
import com.miller.deliveryapp.login.response.DeliveryLoginResponseDTO;
import com.miller.deliveryapp.util.RequestUtils;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 测试用例_骑手登录
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/21 20:31:39
 */
// @ApiDoc(value = "http://10.1.6.46:3000/project/60/interface/api/3288")
@EnvTag.Test
@TestFramework
@DisplayName("骑手-登录")
public class DeliveryLoginByDefaultAccountTests {
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

    @MethodSource("com.miller.deliveryapp.login.provider.DeliveryLoginDataProvider#loginDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_骑手登录")
    void shouldLoginSuccessfully(DeliveryLoginRequestDTO deliveryLoginRequestDTO) {
        DeliveryLoginResponseDTO deliveryLoginResponseDTO = DeliveryLoginFlow.loginReturnBodyObject(deliveryLoginRequestDTO);

        assertThat(deliveryLoginResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(deliveryLoginResponseDTO.getResult().getAccessToken()).isNotNull();
        // 获取token
        token = deliveryLoginResponseDTO.getResult().getAccessToken();
    }

}
