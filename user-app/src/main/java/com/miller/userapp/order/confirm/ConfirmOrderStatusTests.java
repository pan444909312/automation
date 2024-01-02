package com.miller.userapp.order.confirm;

import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.order.confirm.flow.ConfirmOrderStatusFlow;
import com.miller.userapp.order.confirm.request.ConfirmOrderStatusRequestDTO;
import com.miller.userapp.order.confirm.response.ConfirmOrderStatusResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

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

    @MethodSource("com.miller.userapp.order.confirm.provider.ConfirmOrderStatusDataProvider#confirmOrderStatusIsReceived")
    @ParameterizedTest
    @DisplayName("正常流程-用户确认订单已送达")
    void shouldConfirmOrderStatusSuccessfully(ConfirmOrderStatusRequestDTO confirmOrderStatusRequestDTO) {
        ConfirmOrderStatusResponseDTO confirmOrderStatusResponseDTO = ConfirmOrderStatusFlow.confirmOrderIsReceived(confirmOrderStatusRequestDTO);
        assertThat(confirmOrderStatusResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(confirmOrderStatusResponseDTO.getSuccess()).isTrue();
        // TODO 订单数据校验
    }
}
