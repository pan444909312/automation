package com.miller.deliveryapp.order.pendingorder.receiveorreject;

import com.miller.deliveryapp.constants.ResponseConstant;
import com.miller.deliveryapp.order.pendingorder.receiveorreject.flow.ReceiveOrRejectOrderFlow;
import com.miller.deliveryapp.order.pendingorder.receiveorreject.request.ReceiveOrRejectOrderRequestDTO;
import com.miller.deliveryapp.order.pendingorder.receiveorreject.response.ReceiveOrRejectResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 测试用例_司管后台分单-骑手接受、骑手拒绝
 *
 * @author chenchunxia
 * @version 1.0
 * @since 2024/4/12 13:45:30
 */
@EnvTag.Test
@TestFramework
@DisplayName("骑手-司管后台分单-骑手接受和拒绝")
public class ReceiveOrRejectOrderTests {

    @MethodSource("com.miller.deliveryapp.order.pendingorder.receiveorreject.provider.ReceiveOrRejectOrderDataProvider#receiveOrderDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_司管后台分单-骑手接受")
    void shouldReceiveOrderSuccessfully(ReceiveOrRejectOrderRequestDTO receiveOrderRequestDTO) {
        ReceiveOrRejectResponseDTO receiveOrderResponseDTO = ReceiveOrRejectOrderFlow.receiveOrRejectOrder(receiveOrderRequestDTO);

        assertThat(receiveOrderResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
    }
    @MethodSource("com.miller.deliveryapp.order.pendingorder.receiveorreject.provider.ReceiveOrRejectOrderDataProvider#rejectOrderDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_司管后台分单-骑手拒绝")
    void shouldRejectOrderListSuccessfully(ReceiveOrRejectOrderRequestDTO receiveOrderRequestDTO) {
        ReceiveOrRejectResponseDTO receiveOrderResponseDTO = ReceiveOrRejectOrderFlow.receiveOrRejectOrder(receiveOrderRequestDTO);

        assertThat(receiveOrderResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
    }

}
