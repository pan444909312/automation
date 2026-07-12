package com.miller.market.shopCart.settleShopCart.provider;

import com.miller.market.constants.TestCaseDataForMarketConstant;
import com.miller.market.shopCart.settleShopCart.request.MarketSettleShopCartRequestDTO;
import com.panda.market.dal.entity.ShopCart;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * 数据提供者_加购结算数据
 *
 */
@SuppressWarnings(value = "unused")
public class MarketSettleShopCartDataProvider {

    static Stream<Arguments> marketSettleShopCartDataProvider() {
        MarketSettleShopCartRequestDTO settle1 = new MarketSettleShopCartRequestDTO();

        ShopCart shopCart1 = new ShopCart();
        shopCart1.setShopCartId(TestCaseDataForMarketConstant.shopCartId);
        shopCart1.setGoodsCount(1);
        shopCart1.setGoodsId(TestCaseDataForMarketConstant.goodsId);
        shopCart1.setGoodsSkuId(TestCaseDataForMarketConstant.skuId);
        // 预售实际状态 1=开始 0=未开始
        shopCart1.setPreSellActualStatus(0);

        List<ShopCart> shopCarts = new ArrayList<>();
        shopCarts.add(shopCart1);

        settle1.setShopCartList(shopCarts);
        // 使用默认值，1页10条
        return Stream.of(
                Arguments.arguments(settle1)
        );
    }
}
