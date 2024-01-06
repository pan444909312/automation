package com.miller.erp.service.customer.refund.duty.provider;

import com.miller.data.center.user.CreateOrderResponseDTO;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.erp.service.customer.refund.duty.request.DutyRequestDTO;
import com.miller.erp.service.customer.refund.list.flow.RefundListFlow;
import com.miller.erp.service.customer.refund.list.request.RefundListRequestDTO;
import com.miller.erp.service.customer.refund.list.response.RefundListResponseDTO;
import com.miller.service.framework.cache.CacheUtils;
import com.panda.erp.server.common.dto.refund.resp.RefundRecordVo;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * 数据提供者_客户服务-退款审核-定责
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 11:20:12
 */
@SuppressWarnings(value = "unused")
public class DutyDataProvider {
    static Stream<Arguments> duty() {
        // 根据订单ID查询特殊单ID
        RefundListRequestDTO refundListRequestDTO = new RefundListRequestDTO();
        String orderSn = CacheUtils.get(TestCaseDataForUserConstant.ORDER_ID_OBJECT_KEY, CreateOrderResponseDTO.class).getResult().getOrderSn();
        refundListRequestDTO.setOrderSn(orderSn);
        RefundListResponseDTO refundListResponseDTO = RefundListFlow.queryRefundList(refundListRequestDTO);

        // 构造定责数据
        DutyRequestDTO dutyRequestDTO = new DutyRequestDTO();
        dutyRequestDTO.setDuty(2);
        dutyRequestDTO.setProblem(2010);
        dutyRequestDTO.setRemark("【自动化测试】ERP后台人工审核定责备注字段。");
        dutyRequestDTO.setCompensateType(2);
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
