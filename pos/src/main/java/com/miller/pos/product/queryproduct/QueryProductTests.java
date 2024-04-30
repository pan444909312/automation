package com.miller.pos.product.queryproduct;


import com.miller.pos.product.queryproduct.flow.QueryProductFlow;
import com.miller.pos.product.queryproduct.request.QueryProductRequestDTO;
import com.miller.pos.product.queryproduct.response.QueryProductResponseDTO;
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
@DisplayName("pos--product--查询商品")
public class QueryProductTests {


    @MethodSource("com.miller.pos.product.queryproduct.provider.QueryProductDataProvider#queryproduct")
    @ParameterizedTest
    @DisplayName("正常流程_查询商品")
    void shouldProductListSuccessfully(QueryProductRequestDTO queryproductRequestDTO) {
        QueryProductResponseDTO queryProductResponseDTO = QueryProductFlow.queryProduct(queryproductRequestDTO);
        assertThat(queryProductResponseDTO.getCode()).isEqualTo(0);

        System.out.println("打印输出");
        System.out.println(queryProductResponseDTO.getData());



    }
}
