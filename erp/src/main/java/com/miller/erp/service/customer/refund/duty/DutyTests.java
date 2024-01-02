package com.miller.erp.service.customer.refund.duty;

import com.miller.erp.constants.ResponseConstantOfERP;
import com.miller.erp.service.customer.refund.duty.flow.DutyFlow;
import com.miller.erp.service.customer.refund.duty.request.DutyRequestDTO;
import com.miller.erp.service.customer.refund.duty.response.DutyResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * 测试用例_客户服务-退款审核-定责
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 11:41:39
 */
@EnvTag.Test
@TestFramework
@DisplayName("ERP-客户服务-退款审核-定责")
public class DutyTests {
    @MethodSource("com.miller.erp.service.customer.refund.duty.provider.DutyDataProvider#duty")
    @ParameterizedTest
    @DisplayName("正常流程-退款核实责任方")
    void shouldConfirmDutySuccessfully(DutyRequestDTO dutyRequestDTO) {
        DutyResponseDTO dutyResponseDTO = DutyFlow.confirmDuty(dutyRequestDTO);
        Assertions.assertThat(dutyResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
        // 退款是异步的，这里接口仅仅只是返回来是否处理成功。用户是否收到退款需要轮训查询结果。
        // TODO
    }

}
