package com.miller.market.TestScenario;

import com.miller.market.address.MarketGetAddressTests;
import com.miller.market.deliveryTime.MarketDeliveryTimeTests;
import com.miller.market.login.MarketLoginTests;
import com.miller.market.order.createOrder.MarketCreateOrderDeliveryTimeTests;
import com.miller.market.order.getPreOrderInfo.MarketGetPreOrderInfoDeliveryTimeTests;
import com.miller.market.shopCart.addShopCart.MarketAddShopCartTests;
import com.miller.market.shopCart.getShopCartList.MarketGetShopCartListTests;
import com.miller.market.shopCart.settleShopCart.MarketSettleShopCartTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;


@SelectClasses({
        // 用户登录
        MarketLoginTests.class,
        // 收货地址（登录-收货地址）
        MarketGetAddressTests.class,
        //获取配送时间(登录-收货地址-配送时间)
        MarketDeliveryTimeTests.class,
        // 加购（登录-加购）
        MarketAddShopCartTests.class,
        // 获取购物车列表（登录-加购-购物车列表）
        MarketGetShopCartListTests.class,
        // 购物车结算（登录-加购-结算）
        MarketSettleShopCartTests.class,
        //预订单-配送-有时间（登录-获取收货地址-获取配送时间-加购-预订单）
        MarketGetPreOrderInfoDeliveryTimeTests.class,
        // 配送下单(登录-获取收货地址-获取配送时间-加购-结算-预订单（配送-有时间）-下单)
        MarketCreateOrderDeliveryTimeTests.class,

})

@Suite
@SuiteDisplayName("【主干场景】登录-获取收货地址-获取配送时间-加购-结算-预订单（配送-有时间）-下单")
public class OrderOfDeliveryScenarioTests {
}
