package com.miller.deliveryapp.module.order.grab;

import com.miller.deliveryapp.constants.ResponseConstant;
import com.miller.deliveryapp.module.order.grab.flow.DeliveryGrabOrderFlow;
import com.miller.deliveryapp.module.order.grab.request.DeliveryGrabOrderRequestDTO;
import com.miller.deliveryapp.module.order.grab.response.DeliveryGrabOrderResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.cache.CacheUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;


@EnvTag.Test
@TestFramework
@DisplayName("骑手app-新订单-抢单")
public class GrabDeliveryOrderTests {

    @MethodSource("deliveryGrabOrderDataProvider")
    @ParameterizedTest
    @DisplayName("骑手抢单")
    void shouldGrabOrderSuccessfully(DeliveryGrabOrderRequestDTO deliveryGrabOrderRequestDTO) {
        DeliveryGrabOrderResponseDTO deliveryGrabOrderResponseDTO = DeliveryGrabOrderFlow.grabOrder(deliveryGrabOrderRequestDTO);

        assertThat(deliveryGrabOrderResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
    }

    static Stream<Arguments> deliveryGrabOrderDataProvider() {
        DeliveryGrabOrderRequestDTO deliveryGrabOrderRequestDTO = new DeliveryGrabOrderRequestDTO();
        // 从缓存中获取订单ID
        String orderSn = CacheUtils.get("orderSnByDeliverySql").toString();
        deliveryGrabOrderRequestDTO.setOrderSn(orderSn);

        return Stream.of(
                arguments(deliveryGrabOrderRequestDTO)
        );
    }

}
