package com.miller.merchant.product.queryproduct.provider;

import com.miller.merchant.product.queryproduct.request.ProductListRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * 数据提供者_商品上架、下架
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 19:44:33
 */
@SuppressWarnings("unused")
public class ProductListDataProvider {
    static Stream<Arguments> productList() {
        ProductListRequestDTO productListRequestDTO = new ProductListRequestDTO();
        productListRequestDTO.setProductName("佩奇");
        productListRequestDTO.setPageSize(20);
        return Stream.of(Arguments.of(productListRequestDTO));
    }
}
