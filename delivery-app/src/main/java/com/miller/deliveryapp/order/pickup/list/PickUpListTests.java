package com.miller.deliveryapp.order.pickup.list;

import com.miller.deliveryapp.constants.ResponseConstant;
import com.miller.deliveryapp.order.pickup.list.flow.PickUpListFlow;
import com.miller.deliveryapp.order.pickup.list.request.PickUpListRequestDTO;
import com.miller.deliveryapp.order.pickup.list.response.PickUpListResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 测试用例_骑手-待取餐列表
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/22 11:51:39
 */
@EnvTag.Test
@TestFramework
@DisplayName("骑手-待取餐订单列表")
public class PickUpListTests {

    @MethodSource("com.miller.deliveryapp.order.pickup.list.provider.PickUpListDataProvider#pickUpListDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_待取餐列表")
    void shouldGetPickUpListSuccessfully(PickUpListRequestDTO pickUpListRequestDTO) {
        PickUpListResponseDTO pickUpListResponseDTO = PickUpListFlow.pickUpList(pickUpListRequestDTO);

        assertThat(pickUpListResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
    }

}
