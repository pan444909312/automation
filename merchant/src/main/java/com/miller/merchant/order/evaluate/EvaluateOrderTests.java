package com.miller.merchant.order.evaluate;

import com.miller.merchant.constants.ResponseConstant;
import com.miller.merchant.order.evaluate.flow.EvaluateOrderFlow;
import com.miller.merchant.order.evaluate.request.EvaluateOrderRequestDTO;
import com.miller.merchant.order.evaluate.response.EvaluateOrderResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试用例-商家评价订单
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/26 15:47:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("商家-回复评论")
public class EvaluateOrderTests {

    @MethodSource("com.miller.merchant.order.evaluate.provider.EvaluateOrderDataProvider#evaluateOrder")
    @ParameterizedTest
    @DisplayName("正常流程-商家回复评论")
    void shouldEvaluateOrderSuccessfully(EvaluateOrderRequestDTO evaluateOrderRequestDTO) {
        EvaluateOrderResponseDTO evaluateOrderResponseDTO = EvaluateOrderFlow.evaluateOrder(evaluateOrderRequestDTO);
        assertThat(evaluateOrderResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(evaluateOrderResponseDTO.getSuccess()).isTrue();
        // TODO 订单数据校验
    }
}
