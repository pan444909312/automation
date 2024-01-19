package com.miller.merchant.order.waiting.receiving;

import com.miller.data.center.user.CreateOrderResponseDTO;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.merchant.constants.ResponseConstant;
import com.miller.merchant.login.flow.MerchantLoginFlow;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试用例_商家-接单并备餐-美食城订单-美食城下的档口账号接单
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/19 17:07:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("商家-接单并备餐-美食城订单-美食城档口账号接单")
public class ReceivingOrderOfFoodCityUseStallAccountTests {

    @MethodSource("com.miller.merchant.order.waiting.receiving.provider.ReceivingOrderDataProvider#receivingOrder")
    @ParameterizedTest
    @DisplayName("正常流程_接单并备餐-美食城订单-美食城档口账号接单")
    void shouldReceivingOrderSuccessfully(ReceivingOrderRequestDTO receivingOrderRequestDTO) {
        // 美食城下的档口1账号接单
        MerchantLoginFlow.switchUser("18722220003", "HmCGrF8c9");
        // 账号A: 接单之前订单状态为1
        assertThat(getOrderDetailsResponseDTO().getResult().getOrderInfoData().getMerchantOrderStatus()).isEqualTo(1);
        ReceivingOrderResponseDTO receivingOrderResponseDTO1 = ReceivingOrderFlow.receivingOrder(receivingOrderRequestDTO);
        assertThat(receivingOrderResponseDTO1.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(receivingOrderResponseDTO1.getSuccess()).isTrue();
        // 账号A:  接单之后订单状态为2
        assertThat(getOrderDetailsResponseDTO().getResult().getOrderInfoData().getMerchantOrderStatus()).isEqualTo(2);

        // 美食城下的档口2账号接单
        MerchantLoginFlow.switchUser("18722220004", "Lfm9og6Jj");
        // 账号B: 接单之前订单状态为1
        assertThat(getOrderDetailsResponseDTO().getResult().getOrderInfoData().getMerchantOrderStatus()).isEqualTo(1);
        ReceivingOrderResponseDTO receivingOrderResponseDTO2 = ReceivingOrderFlow.receivingOrder(receivingOrderRequestDTO);
        assertThat(receivingOrderResponseDTO2.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(receivingOrderResponseDTO2.getSuccess()).isTrue();
        // 账号B:  接单之后订单状态为2
        assertThat(getOrderDetailsResponseDTO().getResult().getOrderInfoData().getMerchantOrderStatus()).isEqualTo(2);
    }

    /*
    最好切换到美食城账号，否则需要保证所有档口都需要操作出餐
     */
    @AfterEach
    void afterEach() {
        // 档口1 & 2 接单之后校验美食城账号下的订单状态自动更新为2(备餐中)
        MerchantLoginFlow.switchUser("18722220002", "7R55iFbbd");
        // 校验状态自动更新为2
        assertThat(getOrderDetailsResponseDTO().getResult().getOrderInfoData().getMerchantOrderStatus()).isEqualTo(2);
    }

    /*
    获取订单详情
     */
    private static OrderDetailsResponseDTO getOrderDetailsResponseDTO() {
        var orderSn = CacheUtils.get(TestCaseDataForUserConstant.ORDER_ID_OBJECT_KEY, CreateOrderResponseDTO.class).getResult().getOrderSn();
        var orderDetailsRequestDTO = new OrderDetailsRequestDTO();
        orderDetailsRequestDTO.setOrderSn(orderSn);
        return OrderDetailsFlow.getOrderDetails(orderDetailsRequestDTO);
    }

}
