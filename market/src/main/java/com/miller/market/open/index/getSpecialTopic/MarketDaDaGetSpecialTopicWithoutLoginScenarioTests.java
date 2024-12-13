package com.miller.market.open.index.getSpecialTopic;

import com.miller.market.constants.ResponseConstant;
import com.miller.market.open.index.getSpecialTopic.flow.MarketGetSpecialTopicWithoutLoginFlow;
import com.miller.market.open.index.getSpecialTopic.request.MarketGetSpecialTopicRequestDTO;
import com.miller.market.open.index.getSpecialTopic.response.MarketGetSpecialTopicResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


/**
 * 达达首页专题推荐
 */
@Scenario(scenarioID = "01JA4ZPKGN7P6S2ZP8J8CTQ2KN",
        scenarioName = "正常流程_未登录_获取达达首页专题推荐-不过滤烟-过滤烟",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@EnvTag.Test
@DisplayName("PF_达达首页专题推荐")
public class MarketDaDaGetSpecialTopicWithoutLoginScenarioTests {

    @MethodSource("staticGetGoodsListDataProvider")
    @ParameterizedTest
    @DisplayName("PF_正常流程_未登录_获取达达首页专题推荐-不过滤烟-过滤烟")
    void getGoodsByFirstCategoryWithoutLoginSuccessfully(MarketGetSpecialTopicRequestDTO requestDTO) {
        //不过滤烟
        MarketGetSpecialTopicResponseDTO responseDTO= MarketGetSpecialTopicWithoutLoginFlow.getSpecialTopic(requestDTO);

        Assertions.assertThat(responseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(responseDTO.getData()).isNotNull();

        //过滤烟
        requestDTO.setIsFilterTobaccoGoods(true);
        responseDTO= MarketGetSpecialTopicWithoutLoginFlow.getSpecialTopic(requestDTO);

        Assertions.assertThat(responseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(responseDTO.getData()).isNotNull();


    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticGetGoodsListDataProvider() {
        MarketGetSpecialTopicRequestDTO requestDTO = new MarketGetSpecialTopicRequestDTO();
        requestDTO.setShopId(1314L);
        requestDTO.setIsFilterTobaccoGoods(false);
        return Stream.of(Arguments.of(requestDTO));
    }
}
