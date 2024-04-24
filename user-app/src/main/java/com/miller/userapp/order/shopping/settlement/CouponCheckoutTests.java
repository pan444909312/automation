package com.miller.userapp.order.shopping.settlement;

import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.order.shopping.settlement.flow.CouponCheckoutFlow;
import com.miller.userapp.order.shopping.settlement.request.CouponCheckOutRequestDTO;
import com.miller.userapp.order.shopping.settlement.response.CouponCheckoutResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

@TestFramework
@EnvTag.Test
@DisplayName("代金券合单")
public class CouponCheckoutTests {

    @MethodSource("com.miller.userapp.order.shopping.settlement.provider.CouponCheckoutDataProvider#couponCheckout")
    @ParameterizedTest
    @DisplayName("消费券正常结算")
    void shouldCouponCheckoutSuccessfully(CouponCheckOutRequestDTO couponCheckOutRequestDTO){
        CouponCheckoutResponseDTO couponCheckoutResponseDTO= CouponCheckoutFlow.couponCheckoutFlow(couponCheckOutRequestDTO);
        assertThat(couponCheckoutResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(couponCheckoutResponseDTO.getResult().getShopOrderInfoList().size()>0);
    }
}
