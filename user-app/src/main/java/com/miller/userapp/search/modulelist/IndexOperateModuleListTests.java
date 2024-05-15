package com.miller.userapp.search.modulelist;

import com.hungrypanda.app.server.entity.user.SearchHotEntity;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.cache.CacheUtils;
import com.miller.userapp.search.channel.request.CategoryChannelRequestDTO;
import com.miller.userapp.search.modulelist.flow.IndexOperateModuleListFlow;
import com.miller.userapp.search.modulelist.request.IndexOperateModuleListRequestDTO;
import com.miller.userapp.search.modulelist.response.IndexOperateModuleListResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@DisplayName("APP-进入用户首页")
public class IndexOperateModuleListTests {
    @Test
    @DisplayName("APP-进入用户首页-需要返回必吃榜全国维度热搜词")
    public void shouldReturnIndexOperateList(){
        IndexOperateModuleListResponseDTO IndexOperateModuleListResponseDTO= IndexOperateModuleListFlow.flowIndexOperateModuleList();
        List<SearchHotEntity> searchHotEntityList=IndexOperateModuleListResponseDTO.getResult().getHotSearch();
//      取出热搜词列表,为空时校验失败
        assertThat(searchHotEntityList.isEmpty()).isFalse();
        List<String> keywords=new ArrayList<>();
        for (SearchHotEntity searchHotEntity:searchHotEntityList){
            keywords.add(searchHotEntity.getKeywords());
            if (searchHotEntity.getKeywords().equals("全国维度热搜词")) {
//              判断热搜词是否关联taglist
                assertThat(searchHotEntity.getTagList().size()).isGreaterThan(0);
//              存储入缓存：全国维度热搜词关联标签
                CacheUtils.set("SEARCH_HOT_TAG_LIST_KEY",searchHotEntity);


            }
        }
        System.out.println(keywords);
//       判断热搜词中是否有全国维度热搜词
         assertThat(keywords.contains("全国维度热搜词")).isTrue();
    }
}
