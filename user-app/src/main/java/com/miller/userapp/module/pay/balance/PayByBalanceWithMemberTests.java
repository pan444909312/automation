package com.miller.userapp.module.pay.balance;

import com.hungrypanda.app.server.common.enums.payment.PaymentEnum;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.erp.login.flow.ERPLoginFlow;
import com.miller.erp.manage.member.delete.MemberDeleteFlow;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.cache.CacheUtils;
import com.miller.service.framework.util.ApplicationPropertiesUtils;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.module.pay.balance.flow.PayByBalanceFlow;
import com.miller.userapp.module.pay.balance.request.PayByBalanceRequestDTO;
import com.miller.userapp.module.pay.balance.response.PayByBalanceResponseDTO;
import com.miller.userapp.module.order.create.response.CreateOrderResponseDTO;
import com.miller.userapp.module.pay.checkout.flow.PaymentPatternCheckOutFlow;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试用例_余额支付-会员合单
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/23 11:47:37
 */
@EnvTag.Test
@TestFramework
@DisplayName("用户-支付-余额支付-会员合单")
public class PayByBalanceWithMemberTests {

    @MethodSource("payByBalanceWithCombined")
    @ParameterizedTest
    @DisplayName("正常流程_余额支付-会员合单")
    void shouldPayByBalanceWithMemberSuccessfully(PayByBalanceRequestDTO payByBalanceWithMemberRequestDTO) {
        PayByBalanceResponseDTO payByBalanceResponseDTO = PayByBalanceFlow.payByBalance(payByBalanceWithMemberRequestDTO);
        assertThat(payByBalanceResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(payByBalanceResponseDTO.getSuccess()).isTrue();
    }

    /*
     删除用户开通的会员，需要在支付完成的时候删除开通的会员，创建订单还未支付并不会创建会员
     */
    @AfterEach
    void afterEach() {
        ERPLoginFlow.loginByDefaultUser();
        MemberDeleteFlow.deleteMemberByUserId(
                ApplicationPropertiesUtils.loadProperties().getProperty("user.app.account.of.user002.account.id"));
    }

    /**
     * 余额支付-会员合单/代金券合单
     */
    static Stream<Arguments> payByBalanceWithCombined() {
        // 订单支付数据
        var payOfOrder = new PayByBalanceRequestDTO();
        // 从缓存中获取订单ID
        var orderSn = CacheUtils.get(TestCaseDataForUserConstant.ORDER_ID_OBJECT_KEY, CreateOrderResponseDTO.class).getResult().getOrderSn();
        payOfOrder.setPassword(TestCaseDataForUserConstant.payPassword); // 支付密码

        // 合单ID，而不是订单ID
        payOfOrder.setOrderSn(PaymentPatternCheckOutFlow.getOrderCombineSn(orderSn));
        // 会员合单
        payOfOrder.setPaymentType(String.valueOf(PaymentEnum.COMBINED_PAY.getValue()));
        return Stream.of(
                Arguments.of(
                        payOfOrder
                ));
    }

}
