package com.miller.deliveryapp.order.history.list;

import com.miller.deliveryapp.constants.ResponseConstant;
import com.miller.deliveryapp.order.history.list.flow.HistoryOrderListFlow;
import com.miller.deliveryapp.order.history.list.request.HistoryOrderListRequestDTO;
import com.miller.deliveryapp.order.history.list.response.HistoryOrderListResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 测试用例-骑手app历史订单列表
 *
 * @author chenchunxia
 * @version 1.0
 * @since 2024/4/15 20:45:30
 */
@EnvTag.Test
@TestFramework
@DisplayName("骑手-历史订单列表")
public class HistoryOrderListTests {

    @MethodSource("com.miller.deliveryapp.order.history.list.provider.HistoryOrderListDataProvider#historyOrderListDataProvider")
    @ParameterizedTest
    @DisplayName("历史订单列表")
    void shouldGetHistoryOrderListSuccessfully(HistoryOrderListRequestDTO historyOrderListRequestDTO) {
        HistoryOrderListResponseDTO historyOrderListResponseDTO = HistoryOrderListFlow.historyOrderList(historyOrderListRequestDTO);

        assertThat(historyOrderListResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
    }

}
