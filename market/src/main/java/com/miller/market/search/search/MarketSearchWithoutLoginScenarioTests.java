package com.miller.market.search.search;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miller.market.constants.ResponseConstant;
import com.miller.market.search.search.flow.MarketSearchWithoutLoginFlow;
import com.miller.market.search.search.request.MarketSearchRequestDTO;
import com.miller.market.search.search.response.MarketSearchResponseDTO;
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
@Scenario(scenarioID = "01JA4ZPKGMEJSHP04GZWZZPQE1",
        scenarioName = "正常流程_未登录_搜索商品",
        author = "zhangpei@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@EnvTag.Test
@DisplayName("PF_客户端搜索")
public class MarketSearchWithoutLoginScenarioTests {
    @MethodSource("staticSearchKeyWordProvider")
    @ParameterizedTest
    @DisplayName("PF_正常流程-搜索商品")
    void searchSuccessfully(MarketSearchRequestDTO marketSearchRequestDTO) {
        MarketSearchResponseDTO marketSearchResponseDTO = MarketSearchWithoutLoginFlow.search(marketSearchRequestDTO);

        Assertions.assertThat(marketSearchResponseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(marketSearchResponseDTO.getData().getRecords().get(0)).isNotNull();
        Assertions.assertThat(marketSearchResponseDTO.getData().getRecords().get(0).getGoodsName()).contains(marketSearchRequestDTO.getKeyword());

    }



    static Stream<Arguments> staticSearchKeyWordProvider() {
        MarketSearchRequestDTO requestDTO = new MarketSearchRequestDTO();
        requestDTO.setKeyword("自动化");
        requestDTO.setSortType(1);
        Page page = new Page();
        page.setSize(10);
        page.setCurrent(1);
        requestDTO.setPage(page);
        return Stream.of(Arguments.of(requestDTO));
    }



}
