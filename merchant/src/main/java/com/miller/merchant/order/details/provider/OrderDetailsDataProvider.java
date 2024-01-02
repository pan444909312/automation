package com.miller.merchant.order.details.provider;

import com.miller.data.center.user.CreateOrderResponseDTO;
import com.miller.data.center.user.TestCaseDataConstant;
import com.miller.merchant.order.details.request.OrderDetailsRequestDTO;
import com.miller.service.framework.cache.CacheUtils;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * 数据提供者-订单详情
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 17:44:33
 */
@SuppressWarnings("unused")
public class OrderDetailsDataProvider {
    static Stream<Arguments> getOrderDetails() {
        OrderDetailsRequestDTO orderDetailsRequestDTO = new OrderDetailsRequestDTO();
        // 从缓存中获取订单ID
        String orderSn = CacheUtils.get(TestCaseDataConstant.ORDER_ID_OBJECT_KEY, CreateOrderResponseDTO.class).getResult().getOrderSn();
        orderDetailsRequestDTO.setOrderSn(orderSn);

        return Stream.of(Arguments.of(orderDetailsRequestDTO));
    }
}
