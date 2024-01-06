package com.miller.merchant.order.outing;

import com.miller.merchant.constants.ResponseConstant;
import com.miller.merchant.order.outing.flow.OutingOrderFlow;
import com.miller.merchant.order.outing.request.OutingOrderRequestDTO;
import com.miller.merchant.order.outing.response.OutingOrderResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试用例_商家出餐
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/21 20:07:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("商家-出餐")
public class OutingOrderTests {

    @MethodSource("com.miller.merchant.order.outing.provider.OutingOrderDataProvider#outingOrder")
    @ParameterizedTest
    @DisplayName("正常流程_商家出餐")
    void shouldOutingOrderSuccessfully(OutingOrderRequestDTO outingOrderRequestDTO) {
        OutingOrderResponseDTO outingOrderResponseDTO = OutingOrderFlow.outingOrder(outingOrderRequestDTO);
        assertThat(outingOrderResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(outingOrderResponseDTO.getSuccess()).isTrue();
        // TODO 订单数据校验
    }
}
