package com.miller.merchant.summi.order.complete.list;

import com.miller.merchant.constants.ResponseConstant;
import com.miller.merchant.summi.order.complete.list.flow.OrderCompleteListFlow;
import com.miller.merchant.summi.order.complete.list.request.OrderCompleteListRequestDTO;
import com.miller.merchant.summi.order.complete.list.response.OrderCompleteListResponseDTO;
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
@DisplayName("商家-已完成订单列表")
public class SummiOrderCompleteListTests {

    @MethodSource("com.miller.merchant.summi.order.complete.list.provider.OrderCompleteListDataProvider#orderListOfStatus")
    @ParameterizedTest
    @DisplayName("正常流程_已完成订单列表")
    void shouldGetOrderListSuccessfully(OrderCompleteListRequestDTO orderCompleteListRequestDTO) {
        OrderCompleteListResponseDTO orderCompleteListResponseDTO = OrderCompleteListFlow.orderCompleteList (orderCompleteListRequestDTO);
        Assertions.assertThat(orderCompleteListResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        Assertions.assertThat(orderCompleteListResponseDTO.getSuccess()).isTrue();
        // TODO 订单数据校验

    }
}


