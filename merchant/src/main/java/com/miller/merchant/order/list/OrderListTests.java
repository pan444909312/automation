package com.miller.merchant.order.list;

import com.miller.merchant.constants.ResponseConstant;
import com.miller.merchant.order.list.flow.OrderListFlow;
import com.miller.merchant.order.list.request.OrderListRequestDTO;
import com.miller.merchant.order.list.response.OrderListResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * 测试用例_订单列表
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/15 18:07:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("商家-订单列表")
public class OrderListTests {
    @MethodSource("com.miller.merchant.order.list.provider.OrderListDataProvider#orderListOfStatus")
    @ParameterizedTest
    @DisplayName("正常流程_订单列表")
    void shouldGetOrderListSuccessfully(OrderListRequestDTO orderListRequestDTO) {
        OrderListResponseDTO orderListResponseDTO = OrderListFlow.orderList(orderListRequestDTO);
        Assertions.assertThat(orderListResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        Assertions.assertThat(orderListResponseDTO.getSuccess()).isTrue();
        // TODO 订单数据校验
    }
}
