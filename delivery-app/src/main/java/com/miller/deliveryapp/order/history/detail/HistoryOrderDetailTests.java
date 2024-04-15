package com.miller.deliveryapp.order.history.detail;

import com.miller.deliveryapp.constants.ResponseConstant;
import com.miller.deliveryapp.order.history.detail.flow.HistoryOrderDetailFlow;
import com.miller.deliveryapp.order.history.detail.request.HistoryOrderDetailRequestDTO;
import com.miller.deliveryapp.order.history.detail.response.HistoryOrderDetailResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 测试用例-骑手app历史订单详情页
 *
 * @author chenchunxia
 * @version 1.0
 * @since 2024/4/15 20:45:30
 */
@EnvTag.Test
@TestFramework
@DisplayName("骑手-历史订单详情页")
public class HistoryOrderDetailTests {

    @MethodSource("com.miller.deliveryapp.order.history.detail.provider.HistoryOrderDetailDataProvider#historyOrderDetailDataProvider")
    @ParameterizedTest
    @DisplayName("历史订单详情页")
    void shouldGetHistoryOrderDetailSuccessfully(HistoryOrderDetailRequestDTO historyOrderDetailRequestDTO) {
        HistoryOrderDetailResponseDTO historyOrderDetailResponseDTO = HistoryOrderDetailFlow.historyOrderDetail(historyOrderDetailRequestDTO);

        assertThat(historyOrderDetailResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
    }

}
