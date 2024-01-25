package com.miller.userapp.pay.balance;

import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.pay.balance.flow.PayByBalanceFlow;
import com.miller.userapp.pay.balance.request.PayByBalanceRequestDTO;
import com.miller.userapp.pay.balance.response.PayByBalanceResponseDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试用例_余额支付-代金券合单
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/24 20:47:37
 */
@EnvTag.Test
@TestFramework
@DisplayName("用户-支付-余额支付-代金券合单")
public class PayByBalanceWithVoucherTests {

    @MethodSource("com.miller.userapp.pay.balance.provider.PayByBalanceDataProvider#payByBalanceWithCombined")
    @ParameterizedTest
    @DisplayName("正常流程_余额支付-代金券合单")
    void shouldPayByBalanceWithMemberSuccessfully(PayByBalanceRequestDTO payByBalanceWithMemberRequestDTO) {
        PayByBalanceResponseDTO payByBalanceResponseDTO = PayByBalanceFlow.payByBalance(payByBalanceWithMemberRequestDTO);
        assertThat(payByBalanceResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(payByBalanceResponseDTO.getSuccess()).isTrue();
        // 代金券购买之后使用就失效了，所以无需清除购买的代金券
    }

    @AfterEach
    void afterEach(){
        // 校验订单列表中存在一个代金券购买的订单

    }
}
