package com.miller.pos.product.editproduct;


import com.miller.pos.product.editproduct.flow.EditProductFlow;
import com.miller.pos.product.editproduct.request.EditProductRequestDTO;
import com.miller.pos.product.editproduct.response.EditProductResponseDTO;
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
@DisplayName("pos--product--编辑商品")
public class EditProductTests {

    public static Long id;

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
    @MethodSource("com.miller.pos.product.editproduct.provider.EditProductDataProvider#editproduct")
    @ParameterizedTest
    @DisplayName("正常流程_编辑商品")
    void shouldProductListSuccessfully(EditProductRequestDTO editproductRequestDTO) {
        EditProductResponseDTO editProductResponseDTO = EditProductFlow.editProduct(editproductRequestDTO);
        assertThat(editProductResponseDTO.getCode()).isEqualTo(0);

        System.out.println("打印输出");
        System.out.println(editProductResponseDTO.getData());



    }
}
