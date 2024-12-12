package com.miller.market.deliveryTime;

import com.miller.market.constants.ResponseConstant;
import com.miller.market.constants.TestCaseDataForMarketConstant;
import com.miller.market.deliveryTime.flow.MarketGetDeliveryTimeFlow;
import com.miller.market.deliveryTime.request.MarketGetDeliveryTimeRequestDTO;
import com.miller.market.deliveryTime.response.MarketGetDeliveryTimeResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 中超获取配送时间接口
 */
@EnvTag.Test
@TestFramework
@DisplayName("PF_配送时间接口")
public class MarketDeliveryTimeTests {

    @MethodSource("com.miller.market.deliveryTime.provider.MarketGetDeliveryTimeDataProvider#marketDeliveryTimeDataProvider")
    @ParameterizedTest
    @DisplayName("PF_正常流程_获取配送时间")
    void getDeliveryTimeSuccessfully(MarketGetDeliveryTimeRequestDTO marketGetDeliveryTimeRequestDTO) {
        MarketGetDeliveryTimeResponseDTO marketGetDeliveryTimeResponseDTO = MarketGetDeliveryTimeFlow.getDeliveryTime(marketGetDeliveryTimeRequestDTO);

        assertThat(marketGetDeliveryTimeResponseDTO.getCode()).isEqualTo(ResponseConstant.code);
        //获取到配送时间不为空
        assertThat(marketGetDeliveryTimeResponseDTO.getData().getDeliveryTimeList()).isNotNull();

        //第一个配送时间赋值给全局变量，方便后续接口使用
        TestCaseDataForMarketConstant.deliveryTime = marketGetDeliveryTimeResponseDTO.getData().getDeliveryTimeList().get(0);
    }

}
