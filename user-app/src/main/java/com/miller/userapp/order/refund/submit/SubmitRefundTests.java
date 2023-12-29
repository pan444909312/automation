package com.miller.userapp.order.refund.submit;

import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.order.refund.submit.flow.SubmitRefundFlow;
import com.miller.userapp.order.refund.submit.request.SubmitRefundRequestDTO;
import com.miller.userapp.order.refund.submit.response.SubmitRefundResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试用例-申请退款-提交
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/29 11:47:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("用户-申请退款-提交")
public class SubmitRefundTests {

    @MethodSource("com.miller.userapp.order.refund.submit.provider.SubmitRefundDataProvider#submitRefund")
    @ParameterizedTest
    @DisplayName("正常流程-申请退款-提交")
    void shouldSubmitRefundSuccessfully(SubmitRefundRequestDTO submitRefundRequestDTO) {
        SubmitRefundResponseDTO submitRefundResponseDTO = SubmitRefundFlow.applyRefund(submitRefundRequestDTO);
        assertThat(submitRefundResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(submitRefundResponseDTO.getSuccess()).isTrue();
    }

}
