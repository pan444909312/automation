package com.miller.pos.shop.queryshop;

import com.miller.pos.constants.ResponseConstant;
import com.miller.pos.shop.queryshop.flow.QueryShopFlow;
import com.miller.pos.shop.queryshop.request.QueryShopRequestDTO;
import com.miller.pos.shop.queryshop.response.QueryShopResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static com.miller.pos.constants.BusinessConstant.shop_id;
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
@DisplayName("pos--shop--查询店铺")
public class QueryShopTests {

    @MethodSource("com.miller.pos.shop.queryshop.provider.QueryShopDataProvider#queryshop")
    @ParameterizedTest
    @DisplayName("正常流程_查询店铺")
    void shouldProductListSuccessfully(QueryShopRequestDTO queryShopRequestDTO) {
        QueryShopResponseDTO queryShopResponseDTO = QueryShopFlow.queryShop(queryShopRequestDTO);
        assertThat(queryShopResponseDTO.getCode()).isEqualTo(0);
        System.out.println(queryShopResponseDTO.getData().getStatus());



    }
}
