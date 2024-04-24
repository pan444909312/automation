package com.miller.deliveryapp.order.pendingorder.receiveorreject.provider;

import com.miller.deliveryapp.order.pendingorder.receiveorreject.request.ReceiveOrRejectOrderRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * 数据提供者_司管后台分单-骑手接受、骑手拒绝
 *
 * @author chenchunxia
 * @version 1.0
 * @since 2024/4/12 13:45:30
 */
@SuppressWarnings(value = "unused")
public class ReceiveOrRejectOrderDataProvider {
    /**
     * 司管后台分单-骑手接受
     */
    static Stream<Arguments> receiveOrderDataProvider() {
        ReceiveOrRejectOrderRequestDTO receiveOrRejectOrderRequestDTO = new ReceiveOrRejectOrderRequestDTO();
        receiveOrRejectOrderRequestDTO.setOrderPackageId(26614);
        receiveOrRejectOrderRequestDTO.setType((byte) 1);
        // 使用默认值，1页10条
        return Stream.of(
                arguments(receiveOrRejectOrderRequestDTO)
        );
    }
    /**
     * 司管后台分单-骑手拒绝
     */
    static Stream<Arguments> rejectOrderDataProvider() {
        ReceiveOrRejectOrderRequestDTO receiveOrRejectOrderRequestDTO = new ReceiveOrRejectOrderRequestDTO();
        receiveOrRejectOrderRequestDTO.setOrderPackageId(26616);
        receiveOrRejectOrderRequestDTO.setRejectReason("订单距离太远");
        receiveOrRejectOrderRequestDTO.setType((byte) 2);
        // 使用默认值，1页10条
        return Stream.of(
                arguments(receiveOrRejectOrderRequestDTO)
        );
    }

}
