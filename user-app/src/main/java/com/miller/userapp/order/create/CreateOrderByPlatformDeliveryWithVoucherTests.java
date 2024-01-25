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
 * 测试用例_创建订单-平台配送-代金劵合单
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/24 20:27:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("用户-创建订单-平台配送-代金券合单")
public class CreateOrderByPlatformDeliveryWithVoucherTests {

    @MethodSource("com.miller.userapp.order.create.provider.CreateOrderDataProvider#createOrderByPlatformDeliveryWithVoucher")
    @ParameterizedTest
    @DisplayName("正常流程_创建订单-平台配送-代金券合单")
    void shouldCreateOrderSuccessfully(CreateOrderRequestDTO createOrderRequestDTO) {
        CreateOrderResponseDTO createOrderResponseDTO = CreateOrderFlow.createOrder(createOrderRequestDTO);
        assertThat(createOrderResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(createOrderResponseDTO.getSuccess()).isTrue();
        assertThat(createOrderResponseDTO.getResult().getOrderSn()).isNotNull();
        // 代金券合单计算逻辑在结算测试用例中进行校验
    }
}
