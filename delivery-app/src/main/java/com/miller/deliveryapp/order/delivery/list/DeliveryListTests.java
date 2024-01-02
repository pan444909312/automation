package com.miller.deliveryapp.order.delivery.list;

import com.miller.deliveryapp.constants.ResponseConstant;
import com.miller.deliveryapp.order.delivery.list.flow.DeliveryListFlow;
import com.miller.deliveryapp.order.delivery.list.request.DeliveryListRequestDTO;
import com.miller.deliveryapp.order.delivery.list.response.DeliveryListResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 测试用例_骑手-待配送列表
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/22 12:51:39
 */
@EnvTag.Test
@TestFramework
@DisplayName("骑手-待配送订单列表")
public class DeliveryListTests {

    @MethodSource("com.miller.deliveryapp.order.delivery.list.provider.DeliveryListDataProvider#deliveryListDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程-待配送列表")
    void shouldGetDeliveryListSuccessfully(DeliveryListRequestDTO deliveryListRequestDTO) {
        DeliveryListResponseDTO deliveryListResponseDTO = DeliveryListFlow.deliveryList(deliveryListRequestDTO);

        assertThat(deliveryListResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
    }

}
