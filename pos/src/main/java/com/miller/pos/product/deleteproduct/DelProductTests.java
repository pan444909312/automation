package com.miller.pos.product.deleteproduct;


import com.miller.pos.product.deleteproduct.flow.DelProductFlow;
import com.miller.pos.product.deleteproduct.request.DelProductRequestDTO;
import com.miller.pos.product.deleteproduct.response.DelProductResponseDTO;
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
@DisplayName("pos--product--删除商品")
public class DelProductTests {


    @MethodSource("com.miller.pos.product.deleteproduct.provider.DelProductDataProvider#delproduct")
    @ParameterizedTest
    @DisplayName("正常流程_删除商品")
    void shouldProductListSuccessfully(DelProductRequestDTO delproductRequestDTO) {
        DelProductResponseDTO delProductResponseDTO = DelProductFlow.delProduct(delproductRequestDTO);
        assertThat(delProductResponseDTO.getCode()).isEqualTo(0);

        System.out.println("打印输出");
        System.out.println(delProductResponseDTO.getData());



    }
}
