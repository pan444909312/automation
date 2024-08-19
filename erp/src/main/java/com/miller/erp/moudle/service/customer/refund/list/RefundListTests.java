package com.miller.erp.moudle.service.customer.refund.list;

import com.miller.data.center.user.CreateOrderResponseDTO;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.erp.constants.ResponseConstantOfERP;
import com.miller.erp.moudle.service.customer.refund.list.flow.RefundListFlow;
import com.miller.erp.moudle.service.customer.refund.list.request.RefundListRequestDTO;
import com.miller.erp.moudle.service.customer.refund.list.response.RefundListResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.cache.CacheUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * 测试用例_客户服务-退款审核-根据订单查询特殊单ID
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 11:11:39
 */
@EnvTag.Test
@TestFramework
@DisplayName("ERP-客户服务-退款审核")
public class RefundListTests {
    @MethodSource("refundList")
    @ParameterizedTest
    @DisplayName("正常流程_客户服务-退款审核-根据订单查询特殊单ID")
    void shouldQueryRefundListSuccessfully(RefundListRequestDTO refundListRequestDTO) {
        RefundListResponseDTO refundListResponseDTO = RefundListFlow.queryRefundList(refundListRequestDTO);
        Assertions.assertThat(refundListResponseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
    }
    static Stream<Arguments> refundList() {
        RefundListRequestDTO refundListRequestDTO = new RefundListRequestDTO();
        // 从缓存中获取订单ID
        String orderSn = CacheUtils.get(TestCaseDataForUserConstant.ORDER_ID_OBJECT_KEY, CreateOrderResponseDTO.class).getResult().getOrderSn();
        refundListRequestDTO.setOrderSn(orderSn);

        return Stream.of(
                arguments(refundListRequestDTO)
        );
    }
}
