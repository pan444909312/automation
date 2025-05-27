package com.miller.market.search.getUserOfSearchGoods;

import com.miller.market.constants.ResponseConstant;
import com.miller.market.search.getUserOfSearchGoods.flow.MarketGetUserOfSearchGoodsWithoutLoginFlow;
import com.miller.market.search.getUserOfSearchGoods.request.MarketGetUserOfSearchGoodsRequestDTO;
import com.miller.market.search.getUserOfSearchGoods.response.MarketGetUserOfSearchGoodsResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


/**
 * 客户端搜索
 */
@Scenario(scenarioID = "01JVY46FBN411N5ZFJCS2SA564",
        scenarioName = "正常流程_未登录_搜索页面常买和热销榜单商品列表",
        author = "zhangpei@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@EnvTag.Test
@DisplayName("PF_客户端搜索")
public class MarketGetUserOfSearchGoodsScenarioTests {
    @MethodSource("staticSearchKeyWordProvider")
    @ParameterizedTest
    @DisplayName("PF_正常流程-搜索页面常买和热销榜单商品列表")
    void searchSuccessfully(MarketGetUserOfSearchGoodsRequestDTO marketGetUserOfSearchGoodsRequestDTO) {
        MarketGetUserOfSearchGoodsResponseDTO marketGetUserOfSearchGoodsResponseDTO = MarketGetUserOfSearchGoodsWithoutLoginFlow.search(marketGetUserOfSearchGoodsRequestDTO);

        Assertions.assertThat(marketGetUserOfSearchGoodsResponseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(marketGetUserOfSearchGoodsResponseDTO.getData()).isNotNull();
    }



    static Stream<Arguments> staticSearchKeyWordProvider() {
        MarketGetUserOfSearchGoodsRequestDTO requestDTO = new MarketGetUserOfSearchGoodsRequestDTO();
        return Stream.of(Arguments.of(requestDTO));
    }



}
