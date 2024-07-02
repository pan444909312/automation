package com.miller.deliveryapp.login;

import com.miller.common.util.MD5Util;
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
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;


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
public class DeliveryLoginTests {
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

    @MethodSource("loginDataProviderForOrder")
    @ParameterizedTest
    @DisplayName("正常流程_骑手登录")
    void shouldLoginSuccessfully(DeliveryLoginRequestDTO deliveryLoginRequestDTO) {
        DeliveryLoginResponseDTO deliveryLoginResponseDTO = DeliveryLoginFlow.loginReturnBodyObject(deliveryLoginRequestDTO);

        assertThat(deliveryLoginResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(deliveryLoginResponseDTO.getResult().getAccessToken()).isNotNull();
        // 获取token
        token = deliveryLoginResponseDTO.getResult().getAccessToken();
    }

    /**
     * 登陆测试用例数据提供者，数据来自于DB
     */
    static Stream<Arguments> loginDataProviderForOrder() {
        // TODO 假设这里的数据是从数据库或Redis查询出来的数据。后续会提供数据自动注入，这样就不用自己set数据了。
        DeliveryLoginRequestDTO user = new DeliveryLoginRequestDTO();
        user.setAreaCode("86");
        user.setAccount("18733330001");
        user.setPassword(MD5Util.string2MD5("Test123456"));

        return Stream.of(
                arguments(user)
        );
    }

}
