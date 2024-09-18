package com.miller.userapp.module.home.search.channel;

import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.cache.CacheUtils;
import com.miller.userapp.module.home.search.category.response.SearchCategoryResponseDTO;
import com.miller.userapp.module.home.search.channel.flow.CategoryChannelFlow;
import com.miller.userapp.module.home.search.channel.request.CategoryChannelRequestDTO;
import com.miller.userapp.module.home.search.channel.response.CategoryChannelResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@Scenario(scenarioID = "01J821AKXRZ9E55WEVWG44TQJE", scenarioName = "用户-搜索-搜索中间页-品类频道接口"
        , developmentTime = 20, maintenanceTime = 0, manualTestTime = 0)
@DisplayName("搜索中间页-进入品类频道")
public class CategoryChannelScenarioTests {
    @MethodSource("provideCategoryChannelData")
    @ParameterizedTest
    @DisplayName("搜索中间页-进入品类频道")
    public void shouldReturnCategoryChannelShops(CategoryChannelRequestDTO CategoryChannelRequestDTO) {
        CategoryChannelFlow CategoryChannelFlow = new CategoryChannelFlow();
        CategoryChannelResponseDTO CategoryChannelResponseDTO = CategoryChannelFlow.flowCategoryChannel(CategoryChannelRequestDTO);
        System.out.println(CategoryChannelResponseDTO.getResult().getShopList().size());
        assertThat(CategoryChannelResponseDTO.getResult().getShopList().size()).isGreaterThan(10);
    }

    static Stream<Arguments> provideCategoryChannelData(){
        CategoryChannelRequestDTO CategoryChannelRequestDTO = new CategoryChannelRequestDTO();
        CategoryChannelRequestDTO.setFiltering(Boolean.FALSE);
        CategoryChannelRequestDTO.setIsNeedMarketCategory(1);
//    读取缓存中SearchCategoryId
        Long SearchCategoryId =CacheUtils.get("SEARCH_CATEGORY_OBJECT_KEY", SearchCategoryResponseDTO.class).getResult().getSearchCategoryGroups().get(0).getSearchCategories().get(0).getSearchCategoryId();
        CategoryChannelRequestDTO.setSearchCategoryId(Math.toIntExact(SearchCategoryId));
        return Stream.of(Arguments.of(CategoryChannelRequestDTO));
    }
}
