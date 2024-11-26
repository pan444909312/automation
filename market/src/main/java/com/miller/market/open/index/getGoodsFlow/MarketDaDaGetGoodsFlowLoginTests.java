package com.miller.market.open.index.getGoodsFlow;

import com.miller.market.constants.BusinessConstant;
import com.miller.market.constants.ResponseConstant;
import com.miller.market.open.index.getGoodsFlow.flow.MarketGetGoodsFlowLoginFlow;
import com.miller.market.open.index.getGoodsFlow.flow.MarketGetGoodsFlowWithoutLoginFlow;
import com.miller.market.open.index.getGoodsFlow.request.MarketGetGoodsFlowRequestDTO;
import com.miller.market.open.index.getGoodsFlow.response.MarketGetGoodsFlowResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


/**
 * 达达首页商品流
 */
@EnvTag.Test
@TestFramework
@DisplayName("达达首页商品流")
public class MarketDaDaGetGoodsFlowLoginTests {
    @MethodSource("staticGetGoodsListDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_已登录_获取达达首页商品流-不过滤烟-过滤烟")
    void getGoodsByFirstCategoryLoginSuccessfully(MarketGetGoodsFlowRequestDTO requestDTO) {
        //不过滤烟
        MarketGetGoodsFlowResponseDTO responseDTO = MarketGetGoodsFlowLoginFlow.getGoodsList(requestDTO);

        Assertions.assertThat(responseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(responseDTO.getData()).isNotNull();

        //过滤烟
        requestDTO.setIsFilterTobaccoGoods(true);
        responseDTO= MarketGetGoodsFlowWithoutLoginFlow.getGoodsList(requestDTO);

        Assertions.assertThat(responseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(responseDTO.getData()).isNotNull();
    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticGetGoodsListDataProvider() {
        MarketGetGoodsFlowRequestDTO requestDTO = new MarketGetGoodsFlowRequestDTO();
        requestDTO.setShopId(1314L);
        requestDTO.setIsFilterTobaccoGoods(false);
        requestDTO.setUserId(BusinessConstant.userId);
        return Stream.of(Arguments.of(requestDTO));
    }

}
