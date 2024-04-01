package com.miller.merchant.product.addproduct;

import com.miller.merchant.constants.ResponseConstant;
import com.miller.merchant.product.addproduct.flow.AddProductFlow;
import com.miller.merchant.product.addproduct.request.AddProductRequestDTO;
import com.miller.merchant.product.addproduct.response.AddProductResponseDTO;
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
@DisplayName("商家-商品管理-新增商品")
public class AddProductTests {

    @MethodSource("com.miller.merchant.product.addproduct.provider.AddProductDataProvider#addproduct")
    @ParameterizedTest
    @DisplayName("正常流程_新增商品")
    void shouldProductListSuccessfully(AddProductRequestDTO AddProductRequestDTO) {
        AddProductResponseDTO productListResponseDTO = AddProductFlow.addProduct(AddProductRequestDTO);
        assertThat(productListResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(productListResponseDTO.getSuccess()).isTrue();
    }
}
