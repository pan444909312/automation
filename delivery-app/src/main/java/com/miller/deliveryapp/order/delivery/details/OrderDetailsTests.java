package com.miller.deliveryapp.order.delivery.details;

import com.miller.deliveryapp.constants.ResponseConstant;
import com.miller.deliveryapp.order.delivery.details.flow.OrderDetailsFlow;
import com.miller.deliveryapp.order.delivery.details.request.OrderDetailsRequestDTO;
import com.miller.deliveryapp.order.delivery.details.response.OrderDetailsResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试用例_订单详情
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/16 14:43:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("骑手-订单详情")
public class OrderDetailsTests {

    @MethodSource("com.miller.deliveryapp.order.delivery.details.provider.OrderDetailsDataProvider#getOrderDetails")
    @ParameterizedTest
    @DisplayName("正常流程_获取订单详情")
    void shouldGetOrderDetailsSuccessfully(OrderDetailsRequestDTO orderDetailsRequestDTO) {
        OrderDetailsResponseDTO orderDetailsResponseDTO = OrderDetailsFlow.getOrderDetails(orderDetailsRequestDTO);
        assertThat(orderDetailsResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(orderDetailsResponseDTO.getSuccess()).isTrue();
    }
}
