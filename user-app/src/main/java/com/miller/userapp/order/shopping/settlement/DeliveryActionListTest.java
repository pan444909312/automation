package com.miller.userapp.order.shopping.settlement;

import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.order.shopping.settlement.flow.DeliveryActionFlow;
import com.miller.userapp.order.shopping.settlement.request.DeliveryActionEditRequestDTO;
import com.miller.userapp.order.shopping.settlement.response.DeliveryActionEditResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@DisplayName("设置地址的交付方式")
public class DeliveryActionListTest {

    @MethodSource("com.miller.userapp.order.shopping.settlement.provider.SettlementDataProvider#deliveryActionEdit")
    @ParameterizedTest
    @DisplayName("正常流程--设置成功地址的交付方式")
    void setDelivertActionSucc(DeliveryActionEditRequestDTO deliveryActionEditRequestDTO){
        DeliveryActionEditResponseDTO deliveryActionEditResponseDTO = DeliveryActionFlow.delivertActionEditFlow(deliveryActionEditRequestDTO);
        assertThat(deliveryActionEditResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
    }
}
