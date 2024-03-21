package com.miller.merchant.product.addproduct.provider;

import com.miller.merchant.constants.ZlBusinessConstant;
import com.miller.merchant.product.addproduct.request.AddProductRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.math.BigDecimal;
import java.util.stream.Stream;

/**
 * 数据提供者_商品上架、下架
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 19:44:33
 */
@SuppressWarnings("unused")
public class AddProductDataProvider {
    static Stream<Arguments> addproduct() {
        AddProductRequestDTO AddProductRequestDTO = new AddProductRequestDTO();
        AddProductRequestDTO.setProductName(ZlBusinessConstant.productname);
        AddProductRequestDTO.setMenuId(ZlBusinessConstant.menuid);
        AddProductRequestDTO.setProductPrice(BigDecimal.valueOf(ZlBusinessConstant.productprice));
        AddProductRequestDTO.setLimit(ZlBusinessConstant.limit);
        AddProductRequestDTO.setProductLabel(ZlBusinessConstant.productlabel);
        AddProductRequestDTO.setTaxRate(BigDecimal.valueOf(ZlBusinessConstant.taxRate));
        AddProductRequestDTO.setProductBuyLimitMin(ZlBusinessConstant.productBuyLimitMin);
        AddProductRequestDTO.setShopId(Long.valueOf(ZlBusinessConstant.localshopid));
        AddProductRequestDTO.setStatus(ZlBusinessConstant.status);






        return Stream.of(Arguments.of(AddProductRequestDTO));
    }
}
