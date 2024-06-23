package com.miller.userapp.module.pay.balance;

import com.hungrypanda.app.server.common.enums.payment.PaymentEnum;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.cache.CacheUtils;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.module.pay.balance.flow.PayByBalanceFlow;
import com.miller.userapp.module.pay.balance.request.PayByBalanceRequestDTO;
import com.miller.userapp.module.pay.balance.response.PayByBalanceResponseDTO;
import com.miller.userapp.module.order.create.response.CreateOrderResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

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

    @MethodSource("payByBalanceDataProviderFromDB")
    @ParameterizedTest
    @DisplayName("正常流程_余额支付")
    void shouldPayByBalanceSuccessfully(PayByBalanceRequestDTO payByBalanceRequestDTO) {
        PayByBalanceResponseDTO payByBalanceResponseDTO = PayByBalanceFlow.payByBalance(payByBalanceRequestDTO);
        assertThat(payByBalanceResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(payByBalanceResponseDTO.getSuccess()).isTrue();
    }

    /**
     * 余额支付数据提供者
     *
     * @return Stream<Arguments>
     */
    static Stream<Arguments> payByBalanceDataProviderFromDB() {
        // 订单支付数据
        PayByBalanceRequestDTO payOfOrder = new PayByBalanceRequestDTO();
        // String orderSn = CreateOrderFlow.createOrderResponseDTO.getResult().getOrderSn();
        // 从缓存中获取订单ID
        String orderSn = CacheUtils.get(TestCaseDataForUserConstant.ORDER_ID_OBJECT_KEY, CreateOrderResponseDTO.class).getResult().getOrderSn();
        payOfOrder.setOrderSn(orderSn);   // 订单 ID
        payOfOrder.setPassword(TestCaseDataForUserConstant.payPassword); // 支付密码
        payOfOrder.setPaymentType(String.valueOf(PaymentEnum.ORDER_PAY.getValue()));   // 类型：2:订单；1:会员支付；3:余额充值
        return Stream.of(
                Arguments.of(
                        payOfOrder
                ));
    }
}
