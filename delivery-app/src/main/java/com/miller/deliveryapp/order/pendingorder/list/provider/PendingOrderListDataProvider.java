package com.miller.deliveryapp.order.pendingorder.list.provider;

import com.miller.deliveryapp.order.pendingorder.list.request.PendingOrderListRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * 数据提供者_待接单列表
 *
 * @author chenchunxia
 * @version 1.0
 * @since 2024/4/12 13:45:30
 */
@SuppressWarnings(value = "unused")
public class PendingOrderListDataProvider {

    static Stream<Arguments> pendingOrderListDataProvider() {
        PendingOrderListRequestDTO pendingOrderListRequestDTO = new PendingOrderListRequestDTO();
        // 使用默认值，1页10条
        return Stream.of(
                arguments(pendingOrderListRequestDTO)
        );
    }
}
