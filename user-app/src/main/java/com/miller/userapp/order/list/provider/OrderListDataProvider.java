package com.miller.userapp.order.list.provider;

import com.hungrypanda.app.server.common.enums.order.OrderViewStatusEnum;
import com.miller.userapp.order.list.request.OrderListRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * 数据提供者_订单列表
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/25 14:44:33
 */
@SuppressWarnings("unused")
public class OrderListDataProvider {

    /**
     * 获取所有订单
     */
    static Stream<Arguments> getAllOrder() {
        OrderListRequestDTO orderListRequestDTO = new OrderListRequestDTO();
        orderListRequestDTO.setPageNo(1);
        // 待付款的订单，就是所有订单
        orderListRequestDTO.setOrderStatus(OrderViewStatusEnum.PAYMENT_PENDING.getCode());

        return Stream.of(Arguments.of(orderListRequestDTO));
    }
}
