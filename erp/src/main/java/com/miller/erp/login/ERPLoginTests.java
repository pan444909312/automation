package com.miller.erp.login;

import com.miller.erp.constants.ResponseConstantOfERP;
import com.miller.erp.login.flow.ERPLoginFlow;
import com.miller.erp.login.request.ERPLoginRequestDTO;
import com.miller.erp.login.response.ERPLoginResponseDTO;
import com.miller.erp.util.RequestUtils;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 测试用例_ERP登录
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/21 20:31:39
 */
@EnvTag.Test
@TestFramework
@DisplayName("ERP-登录")
public class ERPLoginTests {
    private static String token;

    @AfterAll
    static void afterAll() {
        // 获取token
        var headers = new HashMap<String, Object>();
        headers.put("Content-Type", "application/json");
        headers.put("token", token);

        // 更新全局请求头参数。设置测试用例的默认用户。
        RequestUtils.setHeaders(headers);
        assertThat(RequestUtils.getHeaders().get("token")).isNotNull();
    }

    @MethodSource("com.miller.erp.login.provider.ERPLoginDataProvider#loginDataProviderFromDB")
    @ParameterizedTest
    @DisplayName("正常流程_登录成功")
    void shouldLoginSuccessfully(ERPLoginRequestDTO ERPLoginRequestDTO) {
        ERPLoginResponseDTO ERPLoginResponseDTO = ERPLoginFlow.loginReturnBodyObject(ERPLoginRequestDTO);

        assertThat(ERPLoginResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
        assertThat(ERPLoginResponseDTO.getData().getToken()).isNotNull();
        // 获取token
        token = ERPLoginResponseDTO.getData().getToken();
    }

}
