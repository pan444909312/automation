package com.miller.market.open.index.getKingKongArea;

import com.miller.market.constants.ResponseConstant;
import com.miller.market.open.index.getKingKongArea.flow.MarketGetKingKongAreaWithoutLoginFlow;
import com.miller.market.open.index.getKingKongArea.request.MarketGetKingKongAreaRequestDTO;
import com.miller.market.open.index.getKingKongArea.response.MarketGetKingKongAreaResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


/**
 * 达达首页金刚区
 */
@Scenario(scenarioID = "01JA4ZPKGN7P6S2ZP8J8CTQ2KP",
        scenarioName = "正常流程_未登录_获取达达首页金刚区-不过滤烟-过滤烟",
        author = "zhangpei@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@EnvTag.Test
@DisplayName("PF_达达首页金刚区")
public class MarketDaDaGetKingKongAreaWithoutLoginScenarioTests {

    @MethodSource("staticGetGoodsListDataProvider")
    @ParameterizedTest
    @DisplayName("PF_正常流程_未登录_获取达达首页金刚区-不过滤烟-过滤烟")
    void getGoodsByFirstCategoryWithoutLoginSuccessfully(MarketGetKingKongAreaRequestDTO requestDTO) {
        //不过滤烟
        MarketGetKingKongAreaResponseDTO responseDTO= MarketGetKingKongAreaWithoutLoginFlow.getKingKongArea(requestDTO);

        Assertions.assertThat(responseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(responseDTO.getData()).isNotNull();

        //过滤烟
        requestDTO.setIsFilterTobaccoGoods(true);
        responseDTO= MarketGetKingKongAreaWithoutLoginFlow.getKingKongArea(requestDTO);

        Assertions.assertThat(responseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(responseDTO.getData()).isNotNull();


    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticGetGoodsListDataProvider() {
        MarketGetKingKongAreaRequestDTO requestDTO = new MarketGetKingKongAreaRequestDTO();
        requestDTO.setShopId(1314L);
        requestDTO.setIsFilterTobaccoGoods(false);
        return Stream.of(Arguments.of(requestDTO));
    }
}
