package com.miller.userapp.module.order.evaluate;

import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.cache.CacheUtils;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.module.order.create.response.CreateOrderResponseDTO;
import com.miller.userapp.module.order.evaluate.flow.EvaluateOrderFlow;
import com.miller.userapp.module.order.evaluate.request.EvaluateOrderRequestDTO;
import com.miller.userapp.module.order.evaluate.response.EvaluateOrderResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.hungrypanda.common.enums.order.EvaluateTagEnum.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试用例_用户评价订单
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/25 11:47:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("用户-评价订单")
public class EvaluateOrderTests {

    @MethodSource("evaluateOrder")
    @ParameterizedTest
    @DisplayName("正常流程_用户评价订单")
    void shouldEvaluateOrderSuccessfully(EvaluateOrderRequestDTO evaluateOrderRequestDTO) {
        EvaluateOrderResponseDTO evaluateOrderResponseDTO = EvaluateOrderFlow.evaluateOrder(evaluateOrderRequestDTO);
        assertThat(evaluateOrderResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(evaluateOrderResponseDTO.getSuccess()).isTrue();
    }

    static Stream<Arguments> evaluateOrder() {
        EvaluateOrderRequestDTO evaluateOrderRequestDTOByDelivery = new EvaluateOrderRequestDTO();

        String orderSn = CacheUtils.get(TestCaseDataForUserConstant.ORDER_ID_OBJECT_KEY, CreateOrderResponseDTO.class).getResult().getOrderSn();
        evaluateOrderRequestDTOByDelivery.setOrderSn(orderSn);

        // 是否匿名评价。0/true：匿名评价；1/false:非匿名评价
        evaluateOrderRequestDTOByDelivery.setAnonymousFlag(Boolean.FALSE);
        evaluateOrderRequestDTOByDelivery.setComposite(100);
        evaluateOrderRequestDTOByDelivery.setDriverEvaluate(1);
        evaluateOrderRequestDTOByDelivery.setDeliveryRemark("【自动化测试】骑手评价语");
        evaluateOrderRequestDTOByDelivery.setDriverTagIdList(new String[]{
                SATISFY_HANDOVER_PERFECT.name(),
                SATISFY_FAST_EFFICIENT.name(),
                SATISFY_SERVICE_FRIENDLY.name(),
                SATISFY_WEAR_WORK_CLOTHES.name(),
                SATISFY_OTHER.name()
        });
        evaluateOrderRequestDTOByDelivery.setManner(100);
        evaluateOrderRequestDTOByDelivery.setRemark("【自动化测试】对订单的评价，五星好评，非匿名提交");
        evaluateOrderRequestDTOByDelivery.setTaste(100);

        return Stream.of(Arguments.of(evaluateOrderRequestDTOByDelivery));
    }
}
