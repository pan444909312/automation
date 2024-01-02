package com.miller.erp.service.customer.refund;

import com.miller.erp.constants.ResponseConstantOfERP;
import com.miller.erp.service.customer.refund.flow.RefundListFlow;
import com.miller.erp.service.customer.refund.request.RefundListRequestDTO;
import com.miller.erp.service.customer.refund.response.RefundListResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 测试用例-客户服务-退款审核-根据订单查询特殊单ID
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 11:11:39
 */
@EnvTag.Test
@TestFramework
@DisplayName("ERP-客户服务-退款审核")
public class RefundListTests {
    @MethodSource("com.miller.erp.service.customer.refund.provider.RefundListDataProvider#refundList")
    @ParameterizedTest
    @DisplayName("正常流程-客户服务-退款审核-根据订单查询特殊单ID")
    void shouldQueryRefundListSuccessfully(RefundListRequestDTO refundListRequestDTO) {
        RefundListResponseDTO refundListResponseDTO = RefundListFlow.queryRefundList(refundListRequestDTO);
        assertThat(refundListResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
    }

}
