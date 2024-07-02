package com.miller.deliveryapp.order.neworder.grab;

import com.miller.data.center.user.CreateOrderResponseDTO;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.deliveryapp.constants.ResponseConstant;
import com.miller.deliveryapp.order.neworder.grab.flow.GrabOrderFlow;
import com.miller.deliveryapp.order.neworder.grab.request.GrabOrderRequestDTO;
import com.miller.deliveryapp.order.neworder.grab.response.GrabOrderResponseDTO;
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


/**
 * 测试用例_骑手-新订单-抢单
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/22 13:51:39
 */
@EnvTag.Test
@TestFramework
@DisplayName("骑手-新订单-抢单")
public class GrabOrderTests {

    @MethodSource("grabOrderDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_骑手抢单")
    void shouldGrabOrderSuccessfully(GrabOrderRequestDTO grabOrderRequestDTO) {
        GrabOrderResponseDTO grabOrderResponseDTO = GrabOrderFlow.grabOrder(grabOrderRequestDTO);

        assertThat(grabOrderResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
    }

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
