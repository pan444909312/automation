package com.miller.userapp.search.channel.provider;

import com.miller.data.center.user.CreateOrderResponseDTO;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.service.framework.cache.CacheUtils;
import com.miller.userapp.search.category.response.SearchCategoryResponseDTO;
import com.miller.userapp.search.channel.request.CategoryChannelRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.stream.Stream;

public class CategoryChannelDataProvider {
    /**
     * @return 搜索分类频道页入参组装
     */
public static Stream<Arguments> provideCategoryChannelData(){
    CategoryChannelRequestDTO CategoryChannelRequestDTO = new CategoryChannelRequestDTO();
    CategoryChannelRequestDTO.setFiltering(Boolean.FALSE);
    CategoryChannelRequestDTO.setIsNeedMarketCategory(1);
    String orderSn = CacheUtils.get(TestCaseDataForUserConstant.ORDER_ID_OBJECT_KEY, CreateOrderResponseDTO.class).getResult().getOrderSn();
//    读取缓存中SearchCategoryId
    Long SearchCategoryId =CacheUtils.get("SEARCH_CATEGORY_OBJECT_KEY", SearchCategoryResponseDTO.class).getResult().getSearchCategoryGroups().get(0).getSearchCategories().get(0).getSearchCategoryId();
    CategoryChannelRequestDTO.setSearchCategoryId(Math.toIntExact(SearchCategoryId));
    return Stream.of(Arguments.of(CategoryChannelRequestDTO));
}

}
