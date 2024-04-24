package com.miller.deliveryapp.order.pendingorder.list;

import com.miller.deliveryapp.constants.ResponseConstant;
import com.miller.deliveryapp.order.pendingorder.list.flow.PendingOrderListFlow;
import com.miller.deliveryapp.order.pendingorder.list.request.PendingOrderListRequestDTO;
import com.miller.deliveryapp.order.pendingorder.list.response.PendingOrderListResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 测试用例_骑手待接单列表
 *
 * @author chenchunxia
 * @version 1.0
 * @since 2024/4/12 13:45:30
 */
@EnvTag.Test
@TestFramework
@DisplayName("骑手-待接单列表")
public class PendingOrderListTests {

    @MethodSource("com.miller.deliveryapp.order.pendingorder.list.provider.PendingOrderListDataProvider#pendingOrderListDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_待接单列表")
    void shouldGetPendingOrderListSuccessfully(PendingOrderListRequestDTO pendingOrderListRequestDTO) {
        PendingOrderListResponseDTO pendingOrderListResponseDTO = PendingOrderListFlow.pendingOrderList(pendingOrderListRequestDTO);

        assertThat(pendingOrderListResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
    }

}
