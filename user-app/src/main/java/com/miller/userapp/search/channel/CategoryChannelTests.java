package com.miller.userapp.search.channel;

import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.search.channel.flow.CategoryChannelFlow;
import com.miller.userapp.search.channel.request.CategoryChannelRequestDTO;
import com.miller.userapp.search.channel.response.CategoryChannelResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@DisplayName("搜索中间页-进入品类频道")
public class CategoryChannelTests {
    @MethodSource("com.miller.userapp.search.channel.provider.CategoryChannelDataProvider#provideCategoryChannelData")
    @ParameterizedTest
    @DisplayName("搜索中间页-进入品类频道")
    public void shouldReturnCategoryChannelShops(CategoryChannelRequestDTO CategoryChannelRequestDTO){
        CategoryChannelFlow CategoryChannelFlow =new CategoryChannelFlow();
        CategoryChannelResponseDTO CategoryChannelResponseDTO= CategoryChannelFlow.flowCategoryChannel(CategoryChannelRequestDTO);
        System.out.println(CategoryChannelResponseDTO.getResult().getShopList().size());
        assertThat(CategoryChannelResponseDTO.getResult().getShopList().size()).isGreaterThan(10);
}}
