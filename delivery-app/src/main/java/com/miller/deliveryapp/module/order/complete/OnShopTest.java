package com.miller.deliveryapp.module.order.complete;

import com.miller.deliveryapp.constants.ResponseConstant;
import com.miller.deliveryapp.module.order.complete.flow.CompleteOrderFlow;
import com.miller.deliveryapp.module.order.complete.request.CompleteOrderRequesDTO;
import com.miller.deliveryapp.module.order.complete.response.CompleteOrderRequesResponse;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.cache.CacheUtils;
import com.panda.delivery.app.server.common.enums.OrderDeliveryOptTypeEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@EnvTag.Test
@TestFramework
@DisplayName("骑手app-已到店")
public class OnShopTest {

    @MethodSource("modifyOrderStatusToDriverArrivedTheRestaurant")
    @ParameterizedTest
    @DisplayName("正常流程_骑手-已到店")
    void shouldModifyOrderStatusToDriverArrivedTheRestaurantSuccessfully(CompleteOrderRequesDTO completeOrderRequesDTO) {
        CompleteOrderRequesResponse completeOrderRequesResponse = CompleteOrderFlow.modifyOrderStatus(completeOrderRequesDTO);

        assertThat(completeOrderRequesResponse.getResultCode()).isEqualTo(ResponseConstant.resultCode);
    }

    /**
     * 修改订单状态为：骑手已到店
     */
    static Stream<Arguments> modifyOrderStatusToDriverArrivedTheRestaurant() {
        return Stream.of(
                arguments(modifyDeliveryStatus(OrderDeliveryOptTypeEnum.ON_SHOP.getValue()))
        );
    }

    private static CompleteOrderRequesDTO modifyDeliveryStatus(Byte operationType) {
        CompleteOrderRequesDTO completeOrderRequesDTO = new CompleteOrderRequesDTO();
        // 从缓存中获取订单ID
        String orderSn = CacheUtils.get("orderSnByDeliverySql").toString();
        completeOrderRequesDTO.setOrderSnList(List.of(orderSn));
        // 订单状态
        completeOrderRequesDTO.setOperationType(operationType);
        return completeOrderRequesDTO;
    }
}
