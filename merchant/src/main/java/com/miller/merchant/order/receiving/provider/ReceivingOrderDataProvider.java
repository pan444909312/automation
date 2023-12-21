package com.miller.merchant.order.receiving.provider;

import com.miller.data.center.user.CreateOrderResponseDTO;
import com.miller.data.center.user.TestCaseDataConstant;
import com.miller.merchant.order.receiving.request.ReceivingOrderRequestDTO;
import com.miller.service.framework.cache.CacheUtils;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * 接单并备餐数据提供者
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/15 15:44:33
 */
@SuppressWarnings("unused")
public class ReceivingOrderDataProvider {
    /**
     * 接单并备餐数据提供者
     *
     * @return Stream<Arguments>
     */
    static Stream<Arguments> receivingOrder() {
        ReceivingOrderRequestDTO receivingOrderRequestDTO = new ReceivingOrderRequestDTO();

        // 从缓存中获取订单ID
        String orderSn = CacheUtils.get(TestCaseDataConstant.ORDER_ID_OBJECT_KEY, CreateOrderResponseDTO.class).getResult().getOrderSn();
        receivingOrderRequestDTO.setOrderSn(orderSn);
        /*
         * 是否是否需要校验异常，默认服务端是 false。
         * true：当订单超时时客户端会弹出浮层提示用户是否确认，用户点击确认之后参数变为false
         * false: 直接出餐不校验异常信息
         */
        receivingOrderRequestDTO.setIsNeedVerifyException(false);
        return Stream.of(Arguments.of(receivingOrderRequestDTO));
    }
}
