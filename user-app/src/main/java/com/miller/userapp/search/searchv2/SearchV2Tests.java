package com.miller.userapp.search.searchv2;


import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.search.searchv2.flow.SearchV2Flow;
import com.miller.userapp.search.searchv2.request.SearchV2RequestDTO;
import com.miller.userapp.search.searchv2.response.SearchV2ResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.BOOLEAN;

@EnvTag.Test
@TestFramework
@DisplayName("APP-首页点击热搜词")
public class SearchV2Tests {

    @DisplayName("APP-进入用户首页-点击热搜词-结果需要返回热搜词商家")
    @MethodSource("com.miller.userapp.search.searchv2.provider.SearchV2DataProvider#provideSearchV2Data")
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
}
