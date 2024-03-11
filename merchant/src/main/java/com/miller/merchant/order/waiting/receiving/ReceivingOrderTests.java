package com.miller.merchant.order.waiting.receiving;

import com.miller.merchant.constants.ResponseConstant;
import com.miller.merchant.order.waiting.receiving.flow.ReceivingOrderFlow;
import com.miller.merchant.order.waiting.receiving.request.ReceivingOrderRequestDTO;
import com.miller.merchant.order.waiting.receiving.response.ReceivingOrderResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试用例_商家接单并备餐
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/15 20:07:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("商家-接单并备餐")
public class ReceivingOrderTests {

    @MethodSource("com.miller.merchant.order.waiting.receiving.provider.ReceivingOrderDataProvider#receivingOrder")
    @ParameterizedTest
    @DisplayName("正常流程_商家接单并备餐")
    void shouldReceivingOrderSuccessfully(ReceivingOrderRequestDTO receivingOrderRequestDTO) {
        ReceivingOrderResponseDTO receivingOrderResponseDTO = ReceivingOrderFlow.receivingOrder(receivingOrderRequestDTO);
        assertThat(receivingOrderResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(receivingOrderResponseDTO.getSuccess()).isTrue();
    }
}
