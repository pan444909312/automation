package com.miller.merchant.product.queryproduct;

import com.miller.merchant.constants.ResponseConstant;
import com.miller.merchant.product.queryproduct.flow.ProductListFlow;
import com.miller.merchant.product.queryproduct.request.ProductListRequestDTO;
import com.miller.merchant.product.queryproduct.response.ProductListResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试用例_商品查询
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 20:37:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("商家-商品管理-查询商品")
public class ProductListTests {

    @MethodSource("com.miller.merchant.product.queryproduct.provider.ProductListDataProvider#productList")
    @ParameterizedTest
    @DisplayName("正常流程_查询商品")
    void shouldProductListSuccessfully(ProductListRequestDTO productListRequestDTO) {
        ProductListResponseDTO productListResponseDTO = ProductListFlow.productList(productListRequestDTO);
        assertThat(productListResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(productListResponseDTO.getSuccess()).isTrue();
    }
}
