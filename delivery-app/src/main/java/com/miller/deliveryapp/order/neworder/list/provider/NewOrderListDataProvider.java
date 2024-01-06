package com.miller.deliveryapp.order.neworder.list.provider;

import com.miller.deliveryapp.order.neworder.list.request.NewOrderListRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * 数据提供者_新订单列表
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/22 11:10:12
 */
@SuppressWarnings(value = "unused")
public class NewOrderListDataProvider {

    static Stream<Arguments> newOrderListDataProvider() {
        NewOrderListRequestDTO newOrderListRequestDTO = new NewOrderListRequestDTO();
        // 使用默认值，1页10条
        return Stream.of(
                arguments(newOrderListRequestDTO)
        );
    }
}
