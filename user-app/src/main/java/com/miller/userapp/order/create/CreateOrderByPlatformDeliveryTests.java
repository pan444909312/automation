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

/**
 * 测试用例_创建订单-平台配送，也叫Panda配送
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/9 18:07:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("用户-创建订单-平台配送")
public class CreateOrderByPlatformDeliveryTests {

    @MethodSource("com.miller.userapp.order.create.provider.CreateOrderDataProvider#createOrderByPlatformDelivery")
    @ParameterizedTest
    @DisplayName("正常流程-创建订单-平台配送")
    void shouldCreateOrderSuccessfully(CreateOrderRequestDTO createOrderRequestDTO) {
        CreateOrderResponseDTO createOrderResponseDTO = CreateOrderFlow.createOrder(createOrderRequestDTO);
        assertThat(createOrderResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(createOrderResponseDTO.getSuccess()).isTrue();
        assertThat(createOrderResponseDTO.getResult().getOrderSn()).isNotNull();
        // TODO 订单数据校验
    }
}
