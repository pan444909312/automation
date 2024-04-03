package com.miller.pos.shop.queryshop.provider;

import com.miller.pos.constants.BusinessConstant;
import com.miller.pos.shop.queryshop.request.QueryShopRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static com.miller.pos.constants.BusinessConstant.shop_id;

/**
 * 数据提供者_商品上架、下架
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 19:44:33
 */
@SuppressWarnings("unused")
public class QueryShopDataProvider {
    static Stream<Arguments> queryshop() {
        QueryShopRequestDTO queryShopRequestDTO = new QueryShopRequestDTO();

        queryShopRequestDTO.setShop_id(shop_id);

        return Stream.of(Arguments.of(queryShopRequestDTO));
    }
}
