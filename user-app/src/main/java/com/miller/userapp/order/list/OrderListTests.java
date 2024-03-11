package com.miller.userapp.order.list;

import com.hungrypanda.app.server.common.enums.order.OrderViewStatusEnum;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.order.list.flow.OrderListFlow;
import com.miller.userapp.order.list.request.OrderListRequestDTO;
import com.miller.userapp.order.list.response.OrderListResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试用例_订单列表
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/25 14:47:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("用户-订单列表")
public class OrderListTests {

    @MethodSource("com.miller.userapp.order.list.provider.OrderListDataProvider#getAllOrder")
    @ParameterizedTest
    @DisplayName("正常流程_获取所有订单")
    void shouldGetAllOrderSuccessfully(OrderListRequestDTO orderListRequestDTO) {
        OrderListResponseDTO orderListResponseDTO = OrderListFlow.getOrderList(orderListRequestDTO);
        assertThat(orderListResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(orderListResponseDTO.getSuccess()).isTrue();
    }

    @Test
    @DisplayName("正常流程_获取订单列表-根据订单状态")
    void shouldGetOrderByStatusSuccessfully() {
        OrderListResponseDTO orderListResponseDTO = OrderListFlow.getOrderListByStatus(OrderViewStatusEnum.PAYMENT_PENDING.getCode());
        assertThat(orderListResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(orderListResponseDTO.getSuccess()).isTrue();
    }

    @Test
    @DisplayName("正常流程_获取订单列表-所有订单")
    void shouldGetAllOrderStatusSuccessfully() {
        OrderListResponseDTO orderListResponseDTO = OrderListFlow.getAllOrder();
        assertThat(orderListResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(orderListResponseDTO.getSuccess()).isTrue();
    }
}
