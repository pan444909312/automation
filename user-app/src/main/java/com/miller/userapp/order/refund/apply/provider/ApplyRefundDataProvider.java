package com.miller.userapp.order.refund.apply.provider;

import com.hungrypanda.app.server.common.enums.order.CreateOrderTypeEnum;
import com.miller.data.center.user.CreateOrderResponseDTO;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.service.framework.cache.CacheUtils;
import com.miller.userapp.order.refund.apply.request.ApplyRefundRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * 数据提供者_申请退款
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/29 11:24:33
 */
@SuppressWarnings("unused")
public class ApplyRefundDataProvider {

    static Stream<Arguments> applyRefund() {
        ApplyRefundRequestDTO applyRefundRequestDTOByDelivery = new ApplyRefundRequestDTO();
        applyRefundRequestDTOByDelivery.setOrderType(CreateOrderTypeEnum.COMMON_ORDER.getType());
        // 从缓存中获取订单ID
        String orderSn = CacheUtils.get(TestCaseDataForUserConstant.ORDER_ID_OBJECT_KEY, CreateOrderResponseDTO.class).getResult().getOrderSn();
        applyRefundRequestDTOByDelivery.setOrderSn(orderSn);

        return Stream.of(Arguments.of(applyRefundRequestDTOByDelivery));
    }
}
