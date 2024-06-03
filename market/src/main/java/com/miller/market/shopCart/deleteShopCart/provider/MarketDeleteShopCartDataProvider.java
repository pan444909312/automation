package com.miller.market.shopCart.deleteShopCart.provider;

import com.miller.market.constants.TestCaseDataForMarketConstant;
import com.miller.market.shopCart.deleteShopCart.request.MarketDeleteShopCartRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * 数据提供者_购物车数据
 *
 */
@SuppressWarnings(value = "unused")
public class MarketDeleteShopCartDataProvider {

    static Stream<Arguments> marketDeleteShopCartDataProvider() {
        MarketDeleteShopCartRequestDTO delete1 = new MarketDeleteShopCartRequestDTO();
        delete1.setShopCartId(TestCaseDataForMarketConstant.shopCartId);
        // 使用默认值，1页10条
        return Stream.of(
                Arguments.arguments(delete1)
        );
    }
}
