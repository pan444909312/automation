package com.miller.userapp.order.confirm.provider;

import com.miller.data.center.user.TestCaseDataConstant;
import com.miller.service.framework.cache.CacheUtils;
import com.miller.userapp.order.confirm.request.ConfirmOrderStatusRequestDTO;
import com.miller.userapp.order.create.response.CreateOrderResponseDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * 数据提供者-用户确认订单已送到
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/25 11:14:33
 */
@SuppressWarnings("unused")
public class ConfirmOrderStatusDataProvider {

    static Stream<Arguments> confirmOrderStatusIsReceived() {
        ConfirmOrderStatusRequestDTO confirmOrderStatusRequestDTO = new ConfirmOrderStatusRequestDTO();
        String orderSn = CacheUtils.get(TestCaseDataConstant.ORDER_ID_OBJECT_KEY, CreateOrderResponseDTO.class).getResult().getOrderSn();
        confirmOrderStatusRequestDTO.setOrderSn(orderSn);

        return Stream.of(Arguments.of(confirmOrderStatusRequestDTO));
    }
}
