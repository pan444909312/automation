package com.miller.userapp.module.order.create;

import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.module.order.create.flow.CouponSubmitOrderFlow;
import com.miller.userapp.module.order.create.request.CouponSubmitOrderRequestDTO;
import com.miller.userapp.module.order.create.response.CouponSubmitOrderResponsetDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@TestFramework
@EnvTag.Test
@DisplayName("消费券下单")
public class CreatCouponOrderTests {

    @MethodSource("couponCheckout")
    @ParameterizedTest
    @DisplayName("消费券单独下单-正常下单")
    void shouleCreateCouponOrderSuccessfully(CouponSubmitOrderRequestDTO couponSubmitOrderRequestDTO){
        CouponSubmitOrderResponsetDTO couponSubmitOrderResponsetDTO= CouponSubmitOrderFlow.couponCheckoutFlow(couponSubmitOrderRequestDTO);
        assertThat(couponSubmitOrderResponsetDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(couponSubmitOrderResponsetDTO.getResult().getVoucherOrderSn()).isNotNull();
    }
    static Stream<Arguments> couponCheckout(){
        CouponSubmitOrderRequestDTO couponSubmitOrderRequestDTO=new CouponSubmitOrderRequestDTO();
        couponSubmitOrderRequestDTO.setVoucherSn(TestCaseDataForUserConstant.voucherSn);
        couponSubmitOrderRequestDTO.setBuyNum(1);
        return Stream.of(Arguments.of(couponSubmitOrderRequestDTO));
    }
}
