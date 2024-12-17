package com.miller.market.order.createOrder;

import com.miller.market.constants.BusinessConstant;
import com.miller.market.constants.ResponseConstant;
import com.miller.market.order.createOrder.flow.MarketCreateOrderFlow;
import com.miller.market.order.createOrder.request.MarketCreateOrderRequestDTO;
import com.miller.market.order.createOrder.response.MarketCreateOrderResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 中超创建订单接口
 */
@EnvTag.Test
@TestFramework
@DisplayName("PF_创建订单接口")
public class MarketCreateOrderTakeTimeTests {

    @MethodSource("com.miller.market.order.createOrder.provider.MarketCreateOrderDataProvider#marketTakeTimeDataProvider")
    @ParameterizedTest
    @DisplayName("PF_正常流程_订单创建成功-自取")
    void createOrderTakeTimeSuccessfully(MarketCreateOrderRequestDTO requestDTO) {
        MarketCreateOrderResponseDTO responseDTO = MarketCreateOrderFlow.createOrder(requestDTO);

        assertThat(responseDTO.getCode()).isEqualTo(ResponseConstant.code);
        assertThat(responseDTO.getData()).isNotNull();

        BusinessConstant.orderSn = responseDTO.getData().getOrderSn();
        BusinessConstant.orderId = responseDTO.getData().getOrderId();
    }

}
