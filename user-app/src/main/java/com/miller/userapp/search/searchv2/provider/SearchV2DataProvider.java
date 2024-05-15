package com.miller.userapp.search.searchv2.provider;

import com.hungrypanda.app.server.api.req.shop.TagDTO;
import com.hungrypanda.app.server.entity.user.SearchHotEntity;
import com.miller.service.framework.cache.CacheUtils;
import com.miller.userapp.search.category.response.SearchCategoryResponseDTO;
import com.miller.userapp.search.modulelist.response.IndexOperateModuleListResponseDTO;
import com.miller.userapp.search.searchv2.request.SearchV2RequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * search/v2接口入参数据构造
 */
public class SearchV2DataProvider {
    public static Stream<Arguments> provideSearchV2Data(){
        SearchV2RequestDTO SearchV2RequestDTO =new SearchV2RequestDTO();
        SearchV2RequestDTO.setKeywords("全国维度热搜词");
        SearchV2RequestDTO.setFilters(new ArrayList<>());
        SearchV2RequestDTO.setFiltering(false);
        TagDTO SearchHotEntity = new TagDTO();
//        从缓存中读取全国维度热搜词对应的Tag信息
        SearchHotEntity.setTagId(CacheUtils.get("SEARCH_HOT_TAG_LIST_KEY", SearchHotEntity.class).getTagList().get(0).getTagId());
        SearchHotEntity.setType(CacheUtils.get("SEARCH_HOT_TAG_LIST_KEY", SearchHotEntity.class).getTagList().get(0).getType());
        SearchHotEntity.setName(CacheUtils.get("SEARCH_HOT_TAG_LIST_KEY", SearchHotEntity.class).getTagList().get(0).getName());
        List<TagDTO> SearchHotEntityList=new ArrayList<>();
        SearchHotEntityList.add(SearchHotEntity);
        SearchV2RequestDTO.setTagList(SearchHotEntityList);
        return Stream.of(Arguments.of(SearchV2RequestDTO));
    }
}
