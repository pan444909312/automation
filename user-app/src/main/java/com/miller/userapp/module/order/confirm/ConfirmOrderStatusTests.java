package com.miller.userapp.module.order.confirm;

import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.cache.CacheUtils;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.module.order.confirm.flow.ConfirmOrderStatusFlow;
import com.miller.userapp.module.order.confirm.request.ConfirmOrderStatusRequestDTO;
import com.miller.userapp.module.order.confirm.response.ConfirmOrderStatusResponseDTO;
import com.miller.userapp.module.order.create.response.CreateOrderResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试用例_用户确认订单已送达
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/25 11:17:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("用户-确认订单已送达")
public class ConfirmOrderStatusTests {

    @MethodSource("confirmOrderStatusIsReceived")
    @ParameterizedTest
    @DisplayName("正常流程_用户确认订单已送达")
    void shouldConfirmOrderStatusSuccessfully(ConfirmOrderStatusRequestDTO confirmOrderStatusRequestDTO) {
        ConfirmOrderStatusResponseDTO confirmOrderStatusResponseDTO = ConfirmOrderStatusFlow.confirmOrderIsReceived(confirmOrderStatusRequestDTO);
        assertThat(confirmOrderStatusResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(confirmOrderStatusResponseDTO.getSuccess()).isTrue();
    }
    /**
     * 用户确认订单已送到
     *
     * @return {@link ConfirmOrderStatusRequestDTO}
     */
    static Stream<Arguments> confirmOrderStatusIsReceived() {
        ConfirmOrderStatusRequestDTO confirmOrderStatusRequestDTO = new ConfirmOrderStatusRequestDTO();
        String orderSn = CacheUtils.get(TestCaseDataForUserConstant.ORDER_ID_OBJECT_KEY, CreateOrderResponseDTO.class).getResult().getOrderSn();
        confirmOrderStatusRequestDTO.setOrderSn(orderSn);

        return Stream.of(Arguments.of(confirmOrderStatusRequestDTO));
    }
}
