package com.miller.pos.shop.queryshoplist;

import com.miller.pos.shop.queryshoplist.flow.QueryShopListFlow;
import com.miller.pos.shop.queryshoplist.request.QueryShopListRequestDTO;
import com.miller.pos.shop.queryshoplist.response.QueryShopListResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 测试用例_店铺查询
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 20:37:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("pos--shop--查询店铺列表")
public class QueryShopListTests {

    @MethodSource("com.miller.pos.shop.queryshoplist.provider.QueryShopListDataProvider#queryshoplist")
    @ParameterizedTest
    @DisplayName("正常流程_查询店铺列表")
    void shouldProductListSuccessfully(QueryShopListRequestDTO queryShopListRequestDTO) {
        QueryShopListResponseDTO queryShopListResponseDTO = QueryShopListFlow.queryshoplist(queryShopListRequestDTO);
        assertThat(queryShopListResponseDTO.getCode()).isEqualTo(0);
        System.out.println(queryShopListResponseDTO.getData());





    }
}
