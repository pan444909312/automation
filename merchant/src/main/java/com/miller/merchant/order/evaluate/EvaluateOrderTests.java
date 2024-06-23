package com.miller.merchant.order.evaluate;

import com.miller.data.center.user.CreateOrderResponseDTO;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.merchant.constants.ResponseConstant;
import com.miller.merchant.order.evaluate.flow.EvaluateOrderFlow;
import com.miller.merchant.order.evaluate.request.EvaluateOrderRequestDTO;
import com.miller.merchant.order.evaluate.response.EvaluateOrderResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.cache.CacheUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试用例_商家评价订单
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/26 15:47:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("商家-回复评论")
public class EvaluateOrderTests {

    @MethodSource("evaluateOrder")
    @ParameterizedTest
    @DisplayName("正常流程_商家回复评论")
    void shouldEvaluateOrderSuccessfully(EvaluateOrderRequestDTO evaluateOrderRequestDTO) {
        EvaluateOrderResponseDTO evaluateOrderResponseDTO = EvaluateOrderFlow.evaluateOrder(evaluateOrderRequestDTO);
        assertThat(evaluateOrderResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(evaluateOrderResponseDTO.getSuccess()).isTrue();
        // TODO 订单数据校验
    }
    static Stream<Arguments> evaluateOrder() {
        EvaluateOrderRequestDTO evaluateOrderRequestDTO = new EvaluateOrderRequestDTO();
        String orderSn = CacheUtils.get(TestCaseDataForUserConstant.ORDER_ID_OBJECT_KEY, CreateOrderResponseDTO.class).getResult().getOrderSn();
        evaluateOrderRequestDTO.setOrderSn(orderSn);
        evaluateOrderRequestDTO.setReplyContent("【自动化测试】商家回复评论");

        return Stream.of(Arguments.of(evaluateOrderRequestDTO));
    }
}
