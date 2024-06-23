package com.miller.userapp.module.home.search.category;


import com.miller.service.framework.annotation.EnvTag;
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
@DisplayName("搜索中间页-品类返回接口")
public class SearchCategoryTests {
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
