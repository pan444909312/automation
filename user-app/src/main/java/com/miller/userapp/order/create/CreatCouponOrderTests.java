package com.miller.userapp.order.create;

import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.order.create.flow.CouponSubmitOrderFlow;
import com.miller.userapp.order.create.request.CouponSubmitOrderRequestDTO;
import com.miller.userapp.order.create.response.CouponSubmitOrderResponsetDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

@TestFramework
@EnvTag.Test
@DisplayName("消费券下单")
public class CreatCouponOrderTests {

    @MethodSource("com.miller.userapp.order.create.provider.CouponSubmitORdertDataProvider#couponCheckout")
    @ParameterizedTest
    @DisplayName("消费券单独下单-正常下单")
    void shouleCreateCouponOrderSuccessfully(CouponSubmitOrderRequestDTO couponSubmitOrderRequestDTO){
        CouponSubmitOrderResponsetDTO couponSubmitOrderResponsetDTO= CouponSubmitOrderFlow.couponCheckoutFlow(couponSubmitOrderRequestDTO);
        assertThat(couponSubmitOrderResponsetDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(couponSubmitOrderResponsetDTO.getResult().getVoucherOrderSn()).isNotNull();
    }
}
