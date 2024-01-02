package com.miller.merchant.manage.product;

import com.miller.merchant.constants.ResponseConstant;
import com.miller.merchant.manage.product.flow.ProductOnOrOffFlow;
import com.miller.merchant.manage.product.request.ProductOnOrOffRequestDTO;
import com.miller.merchant.manage.product.response.ProductOnOrOffResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试用例_商品上架、下架
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 20:37:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("商家-商品管理-上/下架商品")
public class ProductOnOrOffTests {

    @MethodSource("com.miller.merchant.manage.product.provider.ProductOnOrOffDataProvider#productOnOrOff")
    @ParameterizedTest
    @DisplayName("正常流程-上架商品")
    void shouldProductOnOrOfSuccessfully(ProductOnOrOffRequestDTO productOnOrOffRequestDTO) {
        ProductOnOrOffResponseDTO productOnOrOffResponseDTO = ProductOnOrOffFlow.productOnOrOff(productOnOrOffRequestDTO);
        assertThat(productOnOrOffResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(productOnOrOffResponseDTO.getSuccess()).isTrue();
    }
}
