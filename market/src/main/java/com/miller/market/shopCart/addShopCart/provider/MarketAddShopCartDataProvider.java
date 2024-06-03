package com.miller.market.shopCart.addShopCart.provider;

import com.miller.market.constants.TestCaseDataForMarketConstant;
import com.miller.market.shopCart.addShopCart.request.MarketAddShopCartRequestDTO;
import org.junit.jupiter.params.provider.Arguments;


import java.util.stream.Stream;

/**
 * 数据提供者_加购数据
 *
 */
@SuppressWarnings(value = "unused")
public class MarketAddShopCartDataProvider {

    static Stream<Arguments> marketAddShopCartDataProvider() {
        MarketAddShopCartRequestDTO add1 = new MarketAddShopCartRequestDTO();
        add1.setIsAdd(1);
        add1.setGoodsId(TestCaseDataForMarketConstant.goodsId);
        add1.setGoodsSkuId(TestCaseDataForMarketConstant.skuId);
        add1.setGoodsCount(1);
        // 使用默认值，1页10条
        return Stream.of(
                Arguments.arguments(add1)
        );
    }
}
