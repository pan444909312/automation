package com.miller.deliveryapp.order.neworder.list;

import com.miller.deliveryapp.constants.ResponseConstant;
import com.miller.deliveryapp.order.neworder.list.flow.NewOrderListFlow;
import com.miller.deliveryapp.order.neworder.list.request.NewOrderListRequestDTO;
import com.miller.deliveryapp.order.neworder.list.response.NewOrderListResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 测试用例_骑手上线
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/22 10:51:39
 */
@EnvTag.Test
@TestFramework
@DisplayName("骑手-新订单列表")
public class NewOrderListTests {

    @MethodSource("com.miller.deliveryapp.order.neworder.list.provider.NewOrderListDataProvider#newOrderListDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程-新订单列表")
    void shouldGetNewOrderListSuccessfully(NewOrderListRequestDTO newOrderListRequestDTO) {
        NewOrderListResponseDTO newOrderListResponseDTO = NewOrderListFlow.newOrderList(newOrderListRequestDTO);

        assertThat(newOrderListResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
    }

}
