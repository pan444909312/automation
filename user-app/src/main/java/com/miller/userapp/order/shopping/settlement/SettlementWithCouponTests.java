package com.miller.userapp.order.shopping.settlement;

import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.order.shopping.settlement.flow.SettlementFlow;
import com.miller.userapp.order.shopping.settlement.request.SettlementRequestDTO;
import com.miller.userapp.order.shopping.settlement.response.SettlementResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@DisplayName("代金券合单")
public class SettlementWithCouponTests {

    @ParameterizedTest
    @MethodSource("com.miller.userapp.order.shopping.settlement.provider.SettlementDataProvider#settlementProductCoupon")
    @DisplayName("正常流程-平台配送-代金券合单并使用代金券结算")
    void shouldSettlementWithCouponSuccessfully(SettlementRequestDTO settlementRequestDTO){

        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(settlementResponseDTO.getResult().getOrderOpt().getVoucher()).isNotNull();
        assertThat(settlementResponseDTO.getResult().getOrderOpt().getVoucher().getAvailableVouchers()).isNull();
    }
}
