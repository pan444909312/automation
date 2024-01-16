package com.miller.deliveryapp.order.status;

import com.miller.deliveryapp.constants.ResponseConstant;
import com.miller.deliveryapp.order.status.flow.ModifyOrderStatusFlow;
import com.miller.deliveryapp.order.status.request.ModifyOrderStatusRequestDTO;
import com.miller.deliveryapp.order.status.response.ModifyOrderStatusResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.depend.DependsOnMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 测试用例_骑手-新订单-抢单
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/22 13:51:39
 */
@EnvTag.Test
@TestFramework
@DisplayName("骑手-已到店->等待商家出餐->正在取餐->已完成送餐并拍照送达")
public class ModifyOrderStatusTests {

    @MethodSource("com.miller.deliveryapp.order.status.provider.ModifyOrderStatusDataProvider#modifyOrderStatusToDriverArrivedTheRestaurant")
    @ParameterizedTest
    @DisplayName("正常流程_骑手-已到店")
    void shouldModifyOrderStatusToDriverArrivedTheRestaurantSuccessfully(ModifyOrderStatusRequestDTO modifyOrderStatusRequestDTO) {
        ModifyOrderStatusResponseDTO modifyOrderStatusResponseDTO = ModifyOrderStatusFlow.modifyOrderStatus(modifyOrderStatusRequestDTO);

        assertThat(modifyOrderStatusResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
    }

    @DependsOnMethod("shouldModifyOrderStatusToDriverArrivedTheRestaurantSuccessfully")
    @MethodSource("com.miller.deliveryapp.order.status.provider.ModifyOrderStatusDataProvider#modifyOrderStatusToDriverArrivedTheRestaurantButWaitingOrder")
    @ParameterizedTest
    @DisplayName("正常流程_骑手-等待商家出餐")
    void shouldModifyOrderStatusToDriverArrivedTheRestaurantButWaitingOrderSuccessfully(ModifyOrderStatusRequestDTO modifyOrderStatusRequestDTO) {
        ModifyOrderStatusResponseDTO modifyOrderStatusResponseDTO = ModifyOrderStatusFlow.modifyOrderStatus(modifyOrderStatusRequestDTO);

        assertThat(modifyOrderStatusResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
    }

    @DependsOnMethod("shouldModifyOrderStatusToDriverArrivedTheRestaurantButWaitingOrderSuccessfully")
    @MethodSource("com.miller.deliveryapp.order.status.provider.ModifyOrderStatusDataProvider#modifyOrderStatusToDriverArrivedTheRestaurantAndTakingOrder")
    @ParameterizedTest
    @DisplayName("正常流程_骑手-正在取餐")
    void shouldModifyOrderStatusToDriverArrivedTheRestaurantAndTakingOrderSuccessfully(ModifyOrderStatusRequestDTO modifyOrderStatusRequestDTO) {
        ModifyOrderStatusResponseDTO modifyOrderStatusResponseDTO = ModifyOrderStatusFlow.modifyOrderStatus(modifyOrderStatusRequestDTO);

        assertThat(modifyOrderStatusResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
    }

    @DependsOnMethod("shouldModifyOrderStatusToDriverArrivedTheRestaurantAndTakingOrderSuccessfully")
    @MethodSource("com.miller.deliveryapp.order.status.provider.ModifyOrderStatusDataProvider#modifyOrderStatusToDriverFinishedOrder")
    @ParameterizedTest
    @DisplayName("正常流程_骑手-已完成送餐并拍照送达")
    void shouldModifyOrderStatusToDriverFinishedOrderSuccessfully(ModifyOrderStatusRequestDTO modifyOrderStatusRequestDTO) {
        ModifyOrderStatusResponseDTO modifyOrderStatusResponseDTO = ModifyOrderStatusFlow.modifyOrderStatus(modifyOrderStatusRequestDTO);

        assertThat(modifyOrderStatusResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
    }

}
