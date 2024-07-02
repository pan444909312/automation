package com.miller.merchant.order.outing;

import com.miller.data.center.user.CreateOrderResponseDTO;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.merchant.constants.ResponseConstant;
import com.miller.merchant.order.outing.flow.OutingOrderFlow;
import com.miller.merchant.order.outing.request.OutingOrderRequestDTO;
import com.miller.merchant.order.outing.response.OutingOrderResponseDTO;
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
 * 测试用例_商家出餐
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/21 20:07:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("商家-出餐")
public class OutingOrderTests {

    @MethodSource("outingOrder")
    @ParameterizedTest
    @DisplayName("正常流程_商家出餐")
    void shouldOutingOrderSuccessfully(OutingOrderRequestDTO outingOrderRequestDTO) {
        OutingOrderResponseDTO outingOrderResponseDTO = OutingOrderFlow.outingOrder(outingOrderRequestDTO);
        assertThat(outingOrderResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(outingOrderResponseDTO.getSuccess()).isTrue();
    }
    static Stream<Arguments> outingOrder() {
        OutingOrderRequestDTO outingOrderRequestDTO = new OutingOrderRequestDTO();

        // 从缓存中获取订单ID
        String orderSn = CacheUtils.get(TestCaseDataForUserConstant.ORDER_ID_OBJECT_KEY, CreateOrderResponseDTO.class).getResult().getOrderSn();
        outingOrderRequestDTO.setOrderSn(orderSn);
        /*
         * 是否是否需要校验异常，默认服务端是 false。
         * true：当订单超时时客户端会弹出浮层提示用户是否确认，用户点击确认之后参数变为false
         * false: 直接出餐不校验异常信息
         */
        outingOrderRequestDTO.setIsNeedVerifyException(Boolean.FALSE);
        return Stream.of(Arguments.of(outingOrderRequestDTO)
        );
    }
}
