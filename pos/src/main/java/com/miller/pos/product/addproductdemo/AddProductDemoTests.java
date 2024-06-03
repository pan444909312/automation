package com.miller.pos.product.addproductdemo;


import com.miller.pos.product.addproductdemo.flow.AddProductFlow;
import com.miller.pos.product.addproductdemo.request.AddProductRequestDTO;
import com.miller.pos.product.addproductdemo.response.AddProductResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;

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
@DisplayName("pos--product--新增商品")
public class AddProductDemoTests {




    public static HashMap<String, AddProductResponseDTO> addProductResponse = new HashMap<>();


    @MethodSource("com.miller.pos.product.addproductdemo.provider.AddProductDataProviderJsonData#addproduct")
    @ParameterizedTest
    @DisplayName("正常流程_新增商品")
    void shouldProductListSuccessfully(AddProductRequestDTO addproductRequestDTO) {
        AddProductResponseDTO addProductResponseDTO = AddProductFlow.addProduct(addproductRequestDTO);
        assertThat(addProductResponseDTO.getCode()).isEqualTo(0);
        addProductResponse.put("productId",addProductResponseDTO);
        System.out.println("打印输出");
        System.out.println(addProductResponseDTO.getData().getProductId());



    }
}
