package com.miller.merchant.order.waiting.receiving;

import com.miller.data.center.user.CreateOrderResponseDTO;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.merchant.constants.ResponseConstant;
import com.miller.merchant.order.waiting.receiving.flow.ReceivingOrderFlow;
import com.miller.merchant.order.waiting.receiving.request.ReceivingOrderRequestDTO;
import com.miller.merchant.order.waiting.receiving.response.ReceivingOrderResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.cache.CacheUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试用例_商家接单并备餐
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/15 20:07:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("商家-接单并备餐")
public class ReceivingOrderTests {

    @MethodSource("receivingOrder")
    @ParameterizedTest
    @DisplayName("正常流程_商家接单并备餐")
    void shouldReceivingOrderSuccessfully(ReceivingOrderRequestDTO receivingOrderRequestDTO) {
        ReceivingOrderResponseDTO receivingOrderResponseDTO = ReceivingOrderFlow.receivingOrder(receivingOrderRequestDTO);
        assertThat(receivingOrderResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(receivingOrderResponseDTO.getSuccess()).isTrue();
    }
    /**
     * 接单并备餐数据提供者
     *
     * @return Stream<Arguments>
     */
    static Stream<Arguments> receivingOrder() {
        ReceivingOrderRequestDTO receivingOrderRequestDTO = new ReceivingOrderRequestDTO();

        // 从缓存中获取订单ID
        String orderSn = CacheUtils.get(TestCaseDataForUserConstant.ORDER_ID_OBJECT_KEY, CreateOrderResponseDTO.class).getResult().getOrderSn();
        receivingOrderRequestDTO.setOrderSn(orderSn);
        /*
         * 是否是否需要校验异常，默认服务端是 false。
         * true：当订单超时时客户端会弹出浮层提示用户是否确认，用户点击确认之后参数变为false
         * false: 直接出餐不校验异常信息
         */
        receivingOrderRequestDTO.setIsNeedVerifyException(Boolean.FALSE);
        return Stream.of(Arguments.of(receivingOrderRequestDTO));
    }
}
