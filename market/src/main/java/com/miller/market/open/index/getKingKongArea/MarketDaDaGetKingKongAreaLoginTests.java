package com.miller.market.open.index.getKingKongArea;

import com.miller.market.constants.BusinessConstant;
import com.miller.market.constants.ResponseConstant;
import com.miller.market.open.index.getKingKongArea.flow.MarketGetKingKongAreaLoginFlow;
import com.miller.market.open.index.getKingKongArea.flow.MarketGetKingKongAreaWithoutLoginFlow;
import com.miller.market.open.index.getKingKongArea.request.MarketGetKingKongAreaRequestDTO;
import com.miller.market.open.index.getKingKongArea.response.MarketGetKingKongAreaResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


/**
 * 达达首页金刚区
 */
@EnvTag.Test
@TestFramework
@DisplayName("PF_达达首页金刚区")
public class MarketDaDaGetKingKongAreaLoginTests {
    @MethodSource("staticGetGoodsListDataProvider")
    @ParameterizedTest
    @DisplayName("PF_正常流程_已登录_获取达达首页金刚区-不过滤烟-过滤烟")
    void getGoodsByFirstCategoryLoginSuccessfully(MarketGetKingKongAreaRequestDTO requestDTO) {
        //不过滤烟
        MarketGetKingKongAreaResponseDTO responseDTO = MarketGetKingKongAreaLoginFlow.getKingKongArea(requestDTO);

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
        requestDTO.setUserId(BusinessConstant.userId);
        return Stream.of(Arguments.of(requestDTO));
    }

}
