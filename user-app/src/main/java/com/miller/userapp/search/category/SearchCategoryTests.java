package com.miller.userapp.search.category;


import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.search.category.flow.SearchCategoryFlow;
import com.miller.userapp.search.category.request.SearchCategoryRequestDTO;
import com.miller.userapp.search.category.response.SearchCategoryResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@DisplayName("搜索中间页-品类返回接口")
public class SearchCategoryTests {
    @MethodSource("com.miller.userapp.search.category.provider.SearchCategoryDataProvider#provideSearchCategoryData")
    @ParameterizedTest
    @DisplayName("搜索中间页-品类返回接口")
    public void shouldReturnSearchCategory(SearchCategoryRequestDTO SearchCategoryRequestDTO){
        SearchCategoryFlow SearchCategoryFlow=new SearchCategoryFlow();
        SearchCategoryResponseDTO SearchCategoryResponseDTO=SearchCategoryFlow.flowSearchCategory(SearchCategoryRequestDTO);
        System.out.println(SearchCategoryResponseDTO);
        assertThat(SearchCategoryResponseDTO.getResult().getSearchCategoryGroups().get(0).getSearchCategories().size()).isGreaterThan(0);
    }


}
