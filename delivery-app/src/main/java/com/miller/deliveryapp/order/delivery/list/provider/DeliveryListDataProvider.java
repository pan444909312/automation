package com.miller.deliveryapp.order.delivery.list.provider;

import com.miller.deliveryapp.order.delivery.list.request.DeliveryListRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * 数据提供者_待配送列表
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/22 12:10:12
 */
@SuppressWarnings(value = "unused")
public class DeliveryListDataProvider {

    static Stream<Arguments> deliveryListDataProvider() {
        DeliveryListRequestDTO deliveryListRequestDTO = new DeliveryListRequestDTO();
        // 使用默认值，1页10条
        return Stream.of(
                arguments(deliveryListRequestDTO)
        );
    }
}
