package com.miller.market.shopCart.getShopCartList.provider;

import com.miller.market.shopCart.getShopCartList.request.MarketGetShopCartListRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * 数据提供者_购物车列表
 *
 */
@SuppressWarnings(value = "unused")
public class MarketGetShopCartListDataProvider {

    static Stream<Arguments> marketGetShopCartListDataProvider() {
        MarketGetShopCartListRequestDTO marketGetShopCartListRequestDTO = new MarketGetShopCartListRequestDTO();
        // 使用默认值，1页10条
        return Stream.of(
                Arguments.arguments(marketGetShopCartListRequestDTO)
        );
    }
}
