package com.miller.userapp.pay.balance;

import com.miller.erp.login.flow.ERPLoginFlow;
import com.miller.erp.manage.member.delete.MemberDeleteFlow;
import com.miller.erp.manage.member.list.flow.MemberListFlow;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.login.flow.UserLoginFlow;
import com.miller.userapp.pay.balance.flow.PayByBalanceFlow;
import com.miller.userapp.pay.balance.request.PayByBalanceRequestDTO;
import com.miller.userapp.pay.balance.response.PayByBalanceResponseDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

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

    @MethodSource("com.miller.userapp.pay.balance.provider.PayByBalanceDataProvider#payByBalanceWithMember")
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
    void afterEach() throws InterruptedException {
        ERPLoginFlow.loginByDefaultUser();
        String memberID = MemberListFlow.getIDByMemberName(UserLoginFlow.getCurrentUserInfo().getResult().getUserName());
        MemberDeleteFlow.deleteMemberById(memberID);
    }

}
