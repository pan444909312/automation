package com.miller.merchant.summi.order.complete.statistics;

import com.miller.merchant.constants.ResponseConstant;
import com.miller.merchant.summi.order.complete.statistics.flow.OrderCompleteStatisticsFlow;
import com.miller.merchant.summi.order.complete.statistics.request.OrderCompleteStatisticsRequestDTO;
import com.miller.merchant.summi.order.complete.statistics.response.OrderCompleteStatisticsResponseDTO;
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
public class SummiOrderCompleteStatisticsTests {

    @MethodSource("com.miller.merchant.summi.order.complete.statistics.provider.OrderCompleteStatisticsDataProvider#orderListOfStatus")
    @ParameterizedTest
    @DisplayName("正常流程_已完成订单数据统计")
    void shouldGetOrderListSuccessfully(OrderCompleteStatisticsRequestDTO orderCompleteStatisticsRequestDTO) {
        OrderCompleteStatisticsResponseDTO orderCompleteStatisticsResponseDTO = OrderCompleteStatisticsFlow.orderCompleteStatistics (orderCompleteStatisticsRequestDTO);
        Assertions.assertThat(orderCompleteStatisticsResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        Assertions.assertThat(orderCompleteStatisticsResponseDTO.getSuccess()).isTrue();
        // TODO 订单数据校验
        // 2024-4-16 完成订单数量2个
        Assertions.assertThat(orderCompleteStatisticsResponseDTO.getResult().getOrderNum()).isEqualTo(2);
    }
}


