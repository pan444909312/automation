package com.miller.merchant.order.delivery.provider;

import com.miller.data.center.user.CreateOrderResponseDTO;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.merchant.order.delivery.request.MerchantConfirmUserReceivedOrderRequestDTO;
import com.miller.service.framework.cache.CacheUtils;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * 数据提供者_配送中列表-商家点击"用户已取餐"
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/15 15:44:33
 */
@SuppressWarnings("unused")
public class MerchantConfirmUserReceivedOrderDataProvider {
    /**
     * 配送中列表-商家点击"用户已取餐"
     */
    static Stream<Arguments> merchantConfirmUserReceivedOrder() {
        MerchantConfirmUserReceivedOrderRequestDTO merchantConfirmUserReceivedOrderRequestDTO = new MerchantConfirmUserReceivedOrderRequestDTO();

        // 从缓存中获取订单ID
        String orderSn = CacheUtils.get(TestCaseDataForUserConstant.ORDER_ID_OBJECT_KEY, CreateOrderResponseDTO.class).getResult().getOrderSn();
        merchantConfirmUserReceivedOrderRequestDTO.setOrderSn(orderSn);
        /*
         * 是否是否需要校验异常，默认服务端是 false。
         * true：当订单超时时客户端会弹出浮层提示用户是否确认，用户点击确认之后参数变为false
         * false: 直接出餐不校验异常信息
         */
        merchantConfirmUserReceivedOrderRequestDTO.setIsNeedVerifyException(Boolean.FALSE);
        return Stream.of(Arguments.of(merchantConfirmUserReceivedOrderRequestDTO));
    }
}
