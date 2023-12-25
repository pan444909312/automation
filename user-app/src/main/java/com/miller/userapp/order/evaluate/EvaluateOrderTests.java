package com.miller.userapp.order.evaluate;

import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.order.evaluate.flow.EvaluateOrderFlow;
import com.miller.userapp.order.evaluate.request.EvaluateOrderRequestDTO;
import com.miller.userapp.order.evaluate.response.EvaluateOrderResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试用例-用户评价订单
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/25 11:47:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("用户-评价订单")
public class EvaluateOrderTests {

    @MethodSource("com.miller.userapp.order.evaluate.provider.EvaluateOrderDataProvider#evaluateOrder")
    @ParameterizedTest
    @DisplayName("正常流程-用户评价订单")
    void shouldEvaluateOrderSuccessfully(EvaluateOrderRequestDTO evaluateOrderRequestDTO) {
        EvaluateOrderResponseDTO evaluateOrderResponseDTO = EvaluateOrderFlow.evaluateOrder(evaluateOrderRequestDTO);
        assertThat(evaluateOrderResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(evaluateOrderResponseDTO.getSuccess()).isTrue();
        // TODO 订单数据校验
    }
}
