package com.miller.market.search.searchKeywords;

import com.miller.market.constants.ResponseConstant;
import com.miller.market.search.searchKeywords.flow.MarketSearchKeywordsWithoutLoginFlow;
import com.miller.market.search.searchKeywords.request.MarketSearchKeywordsRequestDTO;
import com.miller.market.search.searchKeywords.response.MarketSearchKeywordsResponseDTO;
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
@Scenario(scenarioID = "01JVY46FBN411N5ZFJCS2SA563",
        scenarioName = "正常流程_未登录_搜索联系词",
        author = "zhangpei@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@EnvTag.Test
@DisplayName("PF_客户端搜索")
public class MarketSearchKeywordsScenarioTests {
    @MethodSource("staticSearchKeyWordProvider")
    @ParameterizedTest
    @DisplayName("PF_正常流程-搜索联想词")
    void searchSuccessfully(MarketSearchKeywordsRequestDTO marketSearchKeywordsRequestDTO) {
        MarketSearchKeywordsResponseDTO marketSearchKeywordsResponseDTO = MarketSearchKeywordsWithoutLoginFlow.search(marketSearchKeywordsRequestDTO);

        Assertions.assertThat(marketSearchKeywordsResponseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(marketSearchKeywordsResponseDTO.getData().getList()).isNotNull();
    }



    static Stream<Arguments> staticSearchKeyWordProvider() {
        MarketSearchKeywordsRequestDTO requestDTO = new MarketSearchKeywordsRequestDTO();
        requestDTO.setKeyword("青菜");
        return Stream.of(Arguments.of(requestDTO));
    }



}
