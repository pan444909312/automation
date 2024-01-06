package com.miller.erp.service.customer.refund.list.provider;

import com.miller.data.center.user.CreateOrderResponseDTO;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.erp.service.customer.refund.list.request.RefundListRequestDTO;
import com.miller.service.framework.cache.CacheUtils;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * 数据提供者_客户服务-退款审核-根据订单查询特殊单ID
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 11:10:12
 */
@SuppressWarnings(value = "unused")
public class RefundListDataProvider {
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
