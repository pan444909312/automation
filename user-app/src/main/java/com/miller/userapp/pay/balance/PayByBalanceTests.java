package com.miller.userapp.pay.balance;

import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.pay.balance.flow.PayByBalanceFlow;
import com.miller.userapp.pay.balance.request.PayByBalanceRequestDTO;
import com.miller.userapp.pay.balance.response.PayByBalanceResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试用例_余额支付
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/13 16:27:37
 */
@EnvTag.Test
@TestFramework
@DisplayName("用户-支付-余额支付")
public class PayByBalanceTests {

    @MethodSource("com.miller.userapp.pay.balance.provider.PayByBalanceDataProvider#payByBalanceDataProviderFromDB")
    @ParameterizedTest
    @DisplayName("正常流程-余额支付")
    void shouldPayByBalanceSuccessfully(PayByBalanceRequestDTO payByBalanceRequestDTO) {
        PayByBalanceResponseDTO payByBalanceResponseDTO = PayByBalanceFlow.payByBalance(payByBalanceRequestDTO);
        assertThat(payByBalanceResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(payByBalanceResponseDTO.getSuccess()).isTrue();
    }
}
