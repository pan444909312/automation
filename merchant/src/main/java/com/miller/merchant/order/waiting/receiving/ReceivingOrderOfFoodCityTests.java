package com.miller.merchant.order.waiting.receiving;

import com.miller.data.center.user.CreateOrderResponseDTO;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.merchant.constants.ResponseConstant;
import com.miller.merchant.login.flow.LoginFlow;
import com.miller.merchant.order.details.flow.OrderDetailsFlow;
import com.miller.merchant.order.details.request.OrderDetailsRequestDTO;
import com.miller.merchant.order.details.response.OrderDetailsResponseDTO;
import com.miller.merchant.order.waiting.receiving.flow.ReceivingOrderFlow;
import com.miller.merchant.order.waiting.receiving.request.ReceivingOrderRequestDTO;
import com.miller.merchant.order.waiting.receiving.response.ReceivingOrderResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.cache.CacheUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试用例_商家-接单并备餐-美食城订单-美食城账号接单
 * <p>
 * 美食城订单接单与普遍订单接单不同。如果是美食城账号接单，那么下面所有商家档口的订单状态会自动同步订单状态。所以测试用例校验时需要使用到多账号切换登录校验。
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/15 17:07:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("商家-接单并备餐-美食城订单-美食城账号接单")
public class ReceivingOrderOfFoodCityTests {
    @BeforeEach
    void beforeEach() {
        // 美食城账号登录
        LoginFlow.switchUser("18722220002", "7R55iFbbd");
    }

    @MethodSource("com.miller.merchant.order.waiting.receiving.provider.ReceivingOrderDataProvider#receivingOrder")
    @ParameterizedTest
    @DisplayName("正常流程_接单并备餐-美食城订单-美食城账号接单")
    void shouldReceivingOrderSuccessfully(ReceivingOrderRequestDTO receivingOrderRequestDTO) {
        ReceivingOrderResponseDTO receivingOrderResponseDTO = ReceivingOrderFlow.receivingOrder(receivingOrderRequestDTO);
        assertThat(receivingOrderResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(receivingOrderResponseDTO.getSuccess()).isTrue();
    }

    @AfterEach
    void afterEach() {
        // 校验美食城账号接单之后，所有档口查询出来的订单状态也被更新了
        // 从缓存中获取订单ID
        var orderSn = CacheUtils.get(TestCaseDataForUserConstant.ORDER_ID_OBJECT_KEY, CreateOrderResponseDTO.class).getResult().getOrderSn();
        var orderDetailsRequestDTO = new OrderDetailsRequestDTO();
        orderDetailsRequestDTO.setOrderSn(orderSn);

        // 美食城下的档口1账号登录, 并校验订单状态为2
        LoginFlow.switchUser("18722220003", "HmCGrF8c9");
        OrderDetailsResponseDTO orderDetails1 = OrderDetailsFlow.getOrderDetails(orderDetailsRequestDTO);
        assertThat(orderDetails1.getResult().getOrderInfoData().getMerchantOrderStatus()).isEqualTo(2);
        // 美食城下的档口2账号登录, 并校验订单状态为2
        LoginFlow.switchUser("18722220004", "Lfm9og6Jj");
        OrderDetailsResponseDTO orderDetails2 = OrderDetailsFlow.getOrderDetails(orderDetailsRequestDTO);
        assertThat(orderDetails2.getResult().getOrderInfoData().getMerchantOrderStatus()).isEqualTo(2);
    }

}
