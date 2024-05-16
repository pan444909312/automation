package com.miller.market.order.getPreOrderInfo;

import com.miller.market.constants.ResponseConstant;
import com.miller.market.order.getPreOrderInfo.flow.MarketGetPreOrderInfoFlow;
import com.miller.market.order.getPreOrderInfo.request.MarketGetPreOrderInfoRequestDTO;
import com.miller.market.order.getPreOrderInfo.response.MarketGetPreOrderInfoResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 中超预订单接口
 */
@EnvTag.Test
@TestFramework
@DisplayName("预订单接口")
public class MarketGetPreOrderInfoDeliveryWithoutTimeTests {

    @MethodSource("com.miller.market.order.getPreOrderInfo.provider.MarketGetPreOrderInfoDataProvider#marketDeliveryWithoutTimeDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_预订单创建成功-送货上门-无时间")
    void getPreOrderInfoDeliveryWithoutTimeSuccessfully(MarketGetPreOrderInfoRequestDTO marketGetPreOrderInfoRequestDTO) {
        MarketGetPreOrderInfoResponseDTO marketGetPreOrderInfoResponseDTO = MarketGetPreOrderInfoFlow.getPreOrderInfo(marketGetPreOrderInfoRequestDTO);

        assertThat(marketGetPreOrderInfoResponseDTO.getCode()).isEqualTo(ResponseConstant.code);

    }

}
