package com.miller.market.takesTime;

import com.miller.market.constants.ResponseConstant;
import com.miller.market.constants.TestCaseDataForMarketConstant;
import com.miller.market.takesTime.flow.MarketGetTakesTimeFlow;
import com.miller.market.takesTime.request.MarketGetTakesTimeRequestDTO;
import com.miller.market.takesTime.response.MarketGetTakesTimeResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 中超获取自取时间接口
 */
@EnvTag.Test
@TestFramework
@DisplayName("自取时间接口")
public class MarketTakesTimeTests {

    @MethodSource("com.miller.market.takesTime.provider.MarketGetTakesTimeDataProvider#marketTakesTimeDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_获取自取时间")
    void getDeliveryTimeSuccessfully(MarketGetTakesTimeRequestDTO marketGetTakesTimeRequestDTO) {
        MarketGetTakesTimeResponseDTO marketGetTakesTimeResponseDTO = MarketGetTakesTimeFlow.getTakesTime(marketGetTakesTimeRequestDTO);

        assertThat(marketGetTakesTimeResponseDTO.getCode()).isEqualTo(ResponseConstant.code);
        //获取到自取时间不为空
        assertThat(marketGetTakesTimeResponseDTO.getData()).isNotNull();

        //第一个自取时间赋值给全局变量，方便后续接口使用
        TestCaseDataForMarketConstant.takesTime = marketGetTakesTimeResponseDTO.getData().getTakesTimeList().get(0);

    }

}
