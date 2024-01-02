package com.miller.userapp.order.refund.apply;

import com.miller.erp.constants.ResponseConstantOfERP;
import com.miller.erp.login.flow.LoginFlow;
import com.miller.erp.manage.merchant.edit.businessinfo.flow.BusinessInfoEditFlow;
import com.miller.erp.manage.merchant.edit.businessinfo.response.BusinessInfoEditResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.order.refund.apply.flow.ApplyRefundFlow;
import com.miller.userapp.order.refund.apply.request.ApplyRefundRequestDTO;
import com.miller.userapp.order.refund.apply.response.ApplyRefundResponseDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试用例_申请退款
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/29 11:27:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("用户-申请退款")
public class ApplyRefundTests {

    @MethodSource("com.miller.userapp.order.refund.apply.provider.ApplyRefundDataProvider#applyRefund")
    @ParameterizedTest
    @DisplayName("正常流程-申请退款")
    void shouldApplyRefundSuccessfully(ApplyRefundRequestDTO applyRefundRequestDTO) {
        ApplyRefundResponseDTO applyRefundResponseDTO = ApplyRefundFlow.applyRefund(applyRefundRequestDTO);
        assertThat(applyRefundResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(applyRefundResponseDTO.getSuccess()).isTrue();
        assertThat(applyRefundResponseDTO.getResult().getOrderSn()).isNotNull();
        // TODO 订单数据校验
    }

}
