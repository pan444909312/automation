package com.miller.bdm.app.privateShop.callback;

import com.miller.bdm.app.privateShop.callback.flow.SignCallBackFlow;

import com.miller.bdm.constants.ResponseConstantOfERP;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.panda.common.base.Result;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * bdm-签约回调
 *
 * @author lipan
 * @version 1.0
 * @since 2024/3/21
 */
@EnvTag.Test
@TestFramework
@DisplayName("bdm-签约回调")
public class SignCallBackTests {
    @MethodSource("com.miller.bdm.app.privateShop.callback.provider.SignCallBackDataProvider#SignCallBackSign")
    @ParameterizedTest
    @DisplayName("bdm-签署人签署文档")
    void signCallBackSign() {
        Result<Object> signCallBackResponseDTO = SignCallBackFlow.signCallBackSign();
        assertThat(signCallBackResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
    }


    @Test
    @DisplayName("bdm-发送文档给签署人")
    void signCallBackSend() {
        Result<Object> signCallBackResponseDTO = SignCallBackFlow.signCallBackSend();
        assertThat(signCallBackResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
    }

    @Test
    @DisplayName("bdm-签署人查看文档")
    void SignCallBackViewed() {
        Result<Object> signCallBackResponseDTO = SignCallBackFlow.SignCallBackViewed();
        assertThat(signCallBackResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
    }

    @Test
    @DisplayName("bdm-文档签署完成")
    void SignCallBackCompleted() {
        Result<Object> signCallBackResponseDTO = SignCallBackFlow.SignCallBackCompleted();
        assertThat(signCallBackResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
    }

}
