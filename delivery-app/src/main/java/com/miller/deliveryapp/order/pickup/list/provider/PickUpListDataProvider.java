package com.miller.deliveryapp.order.pickup.list.provider;

import com.miller.deliveryapp.order.pickup.list.request.PickUpListRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * 数据提供者-待取餐列表
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/22 11:10:12
 */
@SuppressWarnings(value = "unused")
public class PickUpListDataProvider {

    static Stream<Arguments> pickUpListDataProvider() {
        PickUpListRequestDTO pickUpListRequestDTO = new PickUpListRequestDTO();
        // 使用默认值，1页10条
        return Stream.of(
                arguments(pickUpListRequestDTO)
        );
    }
}
