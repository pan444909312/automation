package com.miller.merchant.order.receiving;

import com.miller.merchant.constants.ResponseConstant;
import com.miller.merchant.order.receiving.flow.ReceivingOrderFlow;
import com.miller.merchant.order.receiving.request.ReceivingOrderRequestDTO;
import com.miller.merchant.order.receiving.response.ReceivingOrderResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 商家接单接口测试用例
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/15 20:07:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("商家接单测试用例")
public class ReceivingOrderTests {

    @MethodSource("com.miller.merchant.order.receiving.provider.ReceivingOrderDataProvider#receivingOrder")
    @ParameterizedTest
    @DisplayName("商家接单-正常流程")
    void shouldReceivingOrderSuccessfully(ReceivingOrderRequestDTO receivingOrderRequestDTO) {
        ReceivingOrderResponseDTO receivingOrderResponseDTO = ReceivingOrderFlow.receivingOrder(receivingOrderRequestDTO);
        assertThat(receivingOrderResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(receivingOrderResponseDTO.getSuccess()).isTrue();
        // TODO 订单数据校验
    }
}
