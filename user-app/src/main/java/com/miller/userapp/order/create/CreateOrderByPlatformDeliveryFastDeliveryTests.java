package com.miller.userapp.order.create;

import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.order.create.flow.CreateOrderFlow;
import com.miller.userapp.order.create.request.CreateOrderRequestDTO;
import com.miller.userapp.order.create.response.CreateOrderResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@DisplayName("用户-创建订单-平台配送-优速达合单")
public class CreateOrderByPlatformDeliveryFastDeliveryTests {

    @MethodSource("com.miller.userapp.order.create.provider.CreateOrderDataProvider#createOrderByPlatformDeliveryFastDelivery")
    @ParameterizedTest
    @DisplayName("正常流程_创建订单-平台配送-优速达合单")
    void shouldCreateOrderWithFastDeliverySuccessfully(CreateOrderRequestDTO createOrderRequestDTO) {
        CreateOrderResponseDTO createOrderResponseDTO = CreateOrderFlow.createOrder(createOrderRequestDTO);
        assertThat(createOrderResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(createOrderResponseDTO.getSuccess()).isTrue();
        assertThat(createOrderResponseDTO.getResult().getOrderSn()).isNotNull();
        // 代金券合单计算逻辑在结算测试用例中进行校验
    }
}
