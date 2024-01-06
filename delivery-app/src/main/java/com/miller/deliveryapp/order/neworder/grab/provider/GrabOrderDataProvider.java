package com.miller.deliveryapp.order.neworder.grab.provider;

import com.miller.data.center.user.CreateOrderResponseDTO;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.deliveryapp.order.neworder.grab.request.GrabOrderRequestDTO;
import com.miller.service.framework.cache.CacheUtils;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * 数据提供者_骑手抢新订单
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/22 13:10:12
 */
@SuppressWarnings(value = "unused")
public class GrabOrderDataProvider {

    static Stream<Arguments> grabOrderDataProvider() {
        GrabOrderRequestDTO grabOrderRequestDTO = new GrabOrderRequestDTO();
        // 从缓存中获取订单ID
        String orderSn = CacheUtils.get(TestCaseDataForUserConstant.ORDER_ID_OBJECT_KEY, CreateOrderResponseDTO.class).getResult().getOrderSn();
        grabOrderRequestDTO.setOrderSn(orderSn);

        return Stream.of(
                arguments(grabOrderRequestDTO)
        );
    }
}
