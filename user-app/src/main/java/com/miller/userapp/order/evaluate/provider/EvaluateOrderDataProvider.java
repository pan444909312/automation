package com.miller.userapp.order.evaluate.provider;

import com.miller.data.center.user.TestCaseDataConstant;
import com.miller.service.framework.cache.CacheUtils;
import com.miller.userapp.order.create.response.CreateOrderResponseDTO;
import com.miller.userapp.order.evaluate.request.EvaluateOrderRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static com.hungrypanda.common.enums.order.EvaluateTagEnum.*;

/**
 * 数据提供者-订单评价
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/25 11:44:33
 */
@SuppressWarnings("unused")
public class EvaluateOrderDataProvider {

    static Stream<Arguments> evaluateOrder() {
        EvaluateOrderRequestDTO evaluateOrderRequestDTOByDelivery = new EvaluateOrderRequestDTO();

        String orderSn = CacheUtils.get(TestCaseDataConstant.ORDER_ID_OBJECT_KEY, CreateOrderResponseDTO.class).getResult().getOrderSn();
        evaluateOrderRequestDTOByDelivery.setOrderSn(orderSn);

        // 0/true：非匿名评价；1/false:匿名评价
        evaluateOrderRequestDTOByDelivery.setAnonymousFlag(true);
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
