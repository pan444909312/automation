package com.miller.pos.shop.statusshop.provider;

import com.miller.pos.shop.statusshop.request.StatusShopRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

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
public class StatusShopDataProvider {
    static Stream<Arguments> statusshop() {
        StatusShopRequestDTO statusShopRequestDTO = new StatusShopRequestDTO();

        statusShopRequestDTO.setShop_id(shop_id);

        return Stream.of(Arguments.of(statusShopRequestDTO));
    }
}
