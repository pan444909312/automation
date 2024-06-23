package com.miller.userapp.module.order.shopping.settlement;

import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.module.order.shopping.settlement.flow.DeliveryActionFlow;
import com.miller.userapp.module.order.shopping.settlement.request.DeliveryActionEditRequestDTO;
import com.miller.userapp.module.order.shopping.settlement.response.DeliveryActionConfigListResponseDTO;
import com.miller.userapp.module.order.shopping.settlement.response.DeliveryActionEditResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@DisplayName("设置地址的交付方式")
public class DeliveryActionListTest {

    @MethodSource("deliveryActionEdit")
    @ParameterizedTest
    @DisplayName("正常流程--设置成功地址的交付方式")
    void setDelivertActionSucc(DeliveryActionEditRequestDTO deliveryActionEditRequestDTO){
        DeliveryActionEditResponseDTO deliveryActionEditResponseDTO = DeliveryActionFlow.delivertActionEditFlow(deliveryActionEditRequestDTO);
        assertThat(deliveryActionEditResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
    }

    /**
     * 结算-交付方式
     * @return
     */
    static Stream<Arguments> deliveryActionEdit(){
        DeliveryActionConfigListResponseDTO deliveryActionConfigListResponseDTO = DeliveryActionFlow.deliveryActionListFlow();
        DeliveryActionEditRequestDTO deliveryActionEditRequestDTO = new DeliveryActionEditRequestDTO();
//        deliveryActionEditRequestDTO.setDeliverableAction(deliveryActionConfigListResponseDTO.getResult().getConfigList().get(0).getSubList().get(0).getId());
        deliveryActionEditRequestDTO.setAddressId(TestCaseDataForUserConstant.addressId);
//        deliveryActionEditRequestDTO.setDeliverableRemark("自动脚本设置交付方式");
        deliveryActionEditRequestDTO.setDeliverableRemark(TestCaseDataForUserConstant.deliverableRemark);
        deliveryActionEditRequestDTO.setDeliverableAction(TestCaseDataForUserConstant.deliverableAction);

        return Stream.of(Arguments.of(deliveryActionEditRequestDTO));
    }
}
