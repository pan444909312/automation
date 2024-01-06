package com.miller.merchant.order.evaluate.provider;

import com.miller.data.center.user.CreateOrderResponseDTO;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.merchant.order.evaluate.request.EvaluateOrderRequestDTO;
import com.miller.service.framework.cache.CacheUtils;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * 数据提供者_订单评价
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/26 11:44:33
 */
@SuppressWarnings("unused")
public class EvaluateOrderDataProvider {

    static Stream<Arguments> evaluateOrder() {
        EvaluateOrderRequestDTO evaluateOrderRequestDTO = new EvaluateOrderRequestDTO();
        String orderSn = CacheUtils.get(TestCaseDataForUserConstant.ORDER_ID_OBJECT_KEY, CreateOrderResponseDTO.class).getResult().getOrderSn();
        evaluateOrderRequestDTO.setOrderSn(orderSn);
        evaluateOrderRequestDTO.setReplyContent("【自动化测试】商家回复评论");

        return Stream.of(Arguments.of(evaluateOrderRequestDTO));
    }
}
