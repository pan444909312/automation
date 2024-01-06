package com.miller.userapp.pay.balance.provider;

import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.service.framework.cache.CacheUtils;
import com.miller.userapp.order.create.response.CreateOrderResponseDTO;
import com.miller.userapp.pay.balance.request.PayByBalanceRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * 数据提供者_余额支付
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/13 16:27:27
 */
@SuppressWarnings("unused")
public class PayByBalanceDataProvider {

    /**
     * 余额支付数据提供者
     *
     * @return Stream<Arguments>
     */
    static Stream<Arguments> payByBalanceDataProviderFromDB() {
        // 订单支付数据
        PayByBalanceRequestDTO payOfOrder = new PayByBalanceRequestDTO();
        // String orderSn = CreateOrderFlow.createOrderResponseDTO.getResult().getOrderSn();
        // 从缓存中获取订单ID
        String orderSn = CacheUtils.get(TestCaseDataForUserConstant.ORDER_ID_OBJECT_KEY, CreateOrderResponseDTO.class).getResult().getOrderSn();
        payOfOrder.setOrderSn(orderSn);   // 订单 ID
        payOfOrder.setPassword("123456"); // 支付密码
        payOfOrder.setPaymentType("2");   // 类型：2:订单；1:会员支付；3:余额充值
        return Stream.of(
                Arguments.of(
                        payOfOrder
                ));
    }
}