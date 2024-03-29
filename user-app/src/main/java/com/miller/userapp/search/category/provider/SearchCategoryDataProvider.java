package com.miller.userapp.search.category.provider;

import com.miller.userapp.search.category.request.SearchCategoryRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.stream.Stream;

public class SearchCategoryDataProvider {
    /**
     * @return 搜索中间页面品类入参数据
     */
    public static Stream<Arguments> provideSearchCategoryData(){
        SearchCategoryRequestDTO SearchCategoryRequestDTO=new SearchCategoryRequestDTO();
        ArrayList<Long> requestList=new ArrayList<>();
        SearchCategoryRequestDTO.setClickSearchCategoryIds(requestList);
        SearchCategoryRequestDTO.setCityName("杭州市");
        return Stream.of(Arguments.of(SearchCategoryRequestDTO));
    }
}
