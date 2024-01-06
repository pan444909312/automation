package com.miller.merchant.order.complain.provider;

import com.miller.data.center.user.CreateOrderResponseDTO;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.merchant.order.complain.request.ComplainOrderRequestDTO;
import com.miller.service.framework.cache.CacheUtils;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * 商家催骑手数据提供者
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/21 16:44:33
 */
@SuppressWarnings("unused")
public class ComplainOrderDataProvider {
    static Stream<Arguments> complainOrder() {
        ComplainOrderRequestDTO complainOrderRequestDTO = new ComplainOrderRequestDTO();

        // 从缓存中获取订单ID
        String orderSn = CacheUtils.get(TestCaseDataForUserConstant.ORDER_ID_OBJECT_KEY, CreateOrderResponseDTO.class).getResult().getOrderSn();
        complainOrderRequestDTO.setOrderSn(orderSn);
        complainOrderRequestDTO.setComplainType(1);
        return Stream.of(
                Arguments.of(complainOrderRequestDTO)
        );
    }
}
