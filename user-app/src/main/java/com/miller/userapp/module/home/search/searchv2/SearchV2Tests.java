package com.miller.userapp.module.home.search.searchv2;


import com.hungrypanda.app.server.api.req.shop.TagDTO;
import com.hungrypanda.app.server.entity.user.SearchHotEntity;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.cache.CacheUtils;
import com.miller.userapp.module.home.search.searchv2.flow.SearchV2Flow;
import com.miller.userapp.module.home.search.searchv2.request.SearchV2RequestDTO;
import com.miller.userapp.module.home.search.searchv2.response.SearchV2ResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@DisplayName("APP-首页点击热搜词")
public class SearchV2Tests {

    @DisplayName("APP-进入用户首页-点击热搜词-结果需要返回热搜词商家")
    @MethodSource("provideSearchV2Data")
    @ParameterizedTest
    public void shouldReturnSearchTagShopTests(SearchV2RequestDTO SearchV2RequestDTO){
        SearchV2ResponseDTO SearchV2ResponseDTO= SearchV2Flow.searchV2Flow(SearchV2RequestDTO);
        List<ShopIndexVO> ShopList=SearchV2ResponseDTO.getResult().getShopList();
        boolean flag=false;
        for (ShopIndexVO ShopIndexVO :ShopList){
            if(ShopIndexVO.getShopId().toString().equals("59750820")){
                flag=true;
            }
        }
//        校验热搜词搜索到的商家包含标签商家59750820
        assertThat(flag).isTrue();
    }
    static Stream<Arguments> provideSearchV2Data(){
        SearchV2RequestDTO SearchV2RequestDTO =new SearchV2RequestDTO();
        SearchV2RequestDTO.setKeywords("全国维度热搜词");
        SearchV2RequestDTO.setFilters(new ArrayList<>());
        SearchV2RequestDTO.setFiltering(false);
        TagDTO SearchHotEntity = new TagDTO();
//        从缓存中读取全国维度热搜词对应的Tag信息
        SearchHotEntity.setTagId(CacheUtils.get("SEARCH_HOT_TAG_LIST_KEY", com.hungrypanda.app.server.entity.user.SearchHotEntity.class).getTagList().get(0).getTagId());
        SearchHotEntity.setType(CacheUtils.get("SEARCH_HOT_TAG_LIST_KEY", SearchHotEntity.class).getTagList().get(0).getType());
        SearchHotEntity.setName(CacheUtils.get("SEARCH_HOT_TAG_LIST_KEY", SearchHotEntity.class).getTagList().get(0).getName());
        List<TagDTO> SearchHotEntityList=new ArrayList<>();
        SearchHotEntityList.add(SearchHotEntity);
        SearchV2RequestDTO.setTagList(SearchHotEntityList);
        return Stream.of(Arguments.of(SearchV2RequestDTO));
    }
}
