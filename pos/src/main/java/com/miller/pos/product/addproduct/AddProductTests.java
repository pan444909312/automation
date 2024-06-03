package com.miller.pos.product.addproduct;


import com.miller.pos.product.addproduct.flow.AddProductFlow;
import com.miller.pos.product.addproduct.request.AddProductRequestDTO;
import com.miller.pos.product.addproduct.response.AddProductResponseDTO;
import com.miller.pos.util.RequestUtils;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.AfterAll;
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
public class AddProductTests {

    public static Long id;
//    public static HashMap<String,AddProductResponseDTO> addProductResponse;

    public static HashMap<String,AddProductResponseDTO> addProductResponse = new HashMap<>();
    //    @AfterAll
//    static void afterAll() {
//        // 获取token
//        var headers = new HashMap<String, Object>();
//        headers.put("Content-Type", "application/json");
//        headers.put("Authorization", token);
//
//        // 更新全局请求头参数。设置测试用例的默认用户。
//        RequestUtils.setHeaders(headers);
//        assertThat(RequestUtils.getHeaders().get("Authorization")).isNotNull();
//    }
    @MethodSource("com.miller.pos.product.addproduct.provider.AddProductDataProvider#addproduct")
    @ParameterizedTest
    @DisplayName("正常流程_新增商品")
    void shouldProductListSuccessfully(AddProductRequestDTO addproductRequestDTO) {
        AddProductResponseDTO addProductResponseDTO = AddProductFlow.addProduct(addproductRequestDTO);
        assertThat(addProductResponseDTO.getCode()).isEqualTo(0);
        id = addProductResponseDTO.getData().getProductId();
        addProductResponse.put("productId",addProductResponseDTO);
        System.out.println("打印输出");
        System.out.println(addProductResponseDTO.getData().getProductId());



    }
}
