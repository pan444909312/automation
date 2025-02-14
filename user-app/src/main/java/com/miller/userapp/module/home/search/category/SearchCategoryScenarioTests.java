package com.miller.userapp.module.home.search.category;


import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.module.home.search.category.flow.SearchCategoryFlow;
import com.miller.userapp.module.home.search.category.request.SearchCategoryRequestDTO;
import com.miller.userapp.module.home.search.category.response.SearchCategoryResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@Scenario(scenarioID = "01J821AKXRZ9E55WEVWG44TQJD", scenarioName = "用户-搜索-搜索中间页-品类返回接口"
        , author = "yancancan@hungrypandagroup.com", developmentTime = 20, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("搜索中间页-品类返回接口")
public class SearchCategoryScenarioTests {
    @MethodSource("provideSearchCategoryData")
    @ParameterizedTest
    @DisplayName("搜索中间页-品类返回接口")
    public void shouldReturnSearchCategory(SearchCategoryRequestDTO SearchCategoryRequestDTO){
        SearchCategoryFlow SearchCategoryFlow=new SearchCategoryFlow();
        SearchCategoryResponseDTO SearchCategoryResponseDTO=SearchCategoryFlow.flowSearchCategory(SearchCategoryRequestDTO);
        System.out.println(SearchCategoryResponseDTO);
        assertThat(SearchCategoryResponseDTO.getResult().getSearchCategoryGroups().get(0).getSearchCategories().size()).isGreaterThan(0);
    }

    static Stream<Arguments> provideSearchCategoryData(){
        SearchCategoryRequestDTO SearchCategoryRequestDTO=new SearchCategoryRequestDTO();
        ArrayList<Long> requestList=new ArrayList<>();
        SearchCategoryRequestDTO.setClickSearchCategoryIds(requestList);
        SearchCategoryRequestDTO.setCityName("杭州市");
        return Stream.of(Arguments.of(SearchCategoryRequestDTO));
    }


}
