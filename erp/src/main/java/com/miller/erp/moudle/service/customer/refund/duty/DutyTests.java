package com.miller.erp.moudle.service.customer.refund.duty;

import com.hungrypanda.common.enums.refund.CompensateTypeEnum;
import com.miller.data.center.user.CreateOrderResponseDTO;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.erp.constants.ResponseConstantOfERP;
import com.miller.erp.moudle.service.customer.refund.duty.flow.DutyFlow;
import com.miller.erp.moudle.service.customer.refund.duty.request.DutyRequestDTO;
import com.miller.erp.moudle.service.customer.refund.duty.response.DutyResponseDTO;
import com.miller.erp.moudle.service.customer.refund.list.flow.RefundListFlow;
import com.miller.erp.moudle.service.customer.refund.list.request.RefundListRequestDTO;
import com.miller.erp.moudle.service.customer.refund.list.response.RefundListResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.cache.CacheUtils;
import com.panda.erp.server.common.dto.refund.resp.RefundRecordVo;
import com.panda.order.server.api.enums.refund.RefundDutyShowEnum;
import com.panda.order.server.api.enums.refund.RefundProblemEnum;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

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
    @MethodSource("dutyDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_退款核实责任方")
    void shouldConfirmDutyDataProviderSuccessfully(DutyRequestDTO dutyRequestDTO) {
        DutyResponseDTO dutyResponseDTO = DutyFlow.confirmDuty(dutyRequestDTO);
        Assertions.assertThat(dutyResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
        // 退款是异步的，这里接口仅仅只是返回来是否处理成功。用户是否收到退款需要轮训查询结果。
    }
    static Stream<Arguments> dutyDataProvider() {
        // 根据订单ID查询特殊单ID
        RefundListRequestDTO refundListRequestDTO = new RefundListRequestDTO();
        String orderSn = CacheUtils.get(TestCaseDataForUserConstant.ORDER_ID_OBJECT_KEY, CreateOrderResponseDTO.class).getResult().getOrderSn();
        refundListRequestDTO.setOrderSn(orderSn);
        RefundListResponseDTO refundListResponseDTO = RefundListFlow.queryRefundList(refundListRequestDTO);

        // 构造定责数据
        DutyRequestDTO dutyRequestDTO = new DutyRequestDTO();
        dutyRequestDTO.setDuty(RefundDutyShowEnum.PLATFORM.getCode());
        dutyRequestDTO.setProblem(RefundProblemEnum.PROBLEM_2010.getCode());
        dutyRequestDTO.setRemark("【自动化测试】ERP后台人工审核定责备注字段。");
        dutyRequestDTO.setCompensateType(CompensateTypeEnum.DIRECT_COMPENSATE_DUTY.getValue());
        // 特殊单 ID
        Long id = null;
        List<RefundRecordVo> refundList = refundListResponseDTO.getData();
        for(RefundRecordVo refundRecordVo : refundList){
            if(refundRecordVo.getRefundStatus() == 0){
                id = refundRecordVo.getId();
                // 只会存在一个待审核的特殊单，所以直接跳出循环
                break;
            }
        }

        // 特殊单id 是从退款列表中查询的
        dutyRequestDTO.setId(id);

        return Stream.of(
                arguments(dutyRequestDTO)
        );
    }
}
