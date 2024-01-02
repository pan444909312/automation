package com.miller.merchant.manage.product.provider;

import com.miller.data.center.user.TestCaseDataConstant;
import com.miller.merchant.manage.product.request.ProductOnOrOffRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Stream;

/**
 * 数据提供者-商品上架、下架
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 19:44:33
 */
@SuppressWarnings("unused")
public class ProductOnOrOffDataProvider {
    static Stream<Arguments> productOnOrOff() {
        ProductOnOrOffRequestDTO productOnOrOffRequestDTO = new ProductOnOrOffRequestDTO();
        productOnOrOffRequestDTO.setProductIds(List.of(TestCaseDataConstant.productId));
        productOnOrOffRequestDTO.setStatus(0);

        return Stream.of(Arguments.of(productOnOrOffRequestDTO));
    }
}
