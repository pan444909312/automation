package com.miller.market;

import com.miller.market.address.MarketGetAddressTests;
import com.miller.market.login.MarketLoginTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * 当需要执行多个测试类时可以通过此程序进行调试
 */
@SelectClasses({
        // 用户登录
        MarketLoginTests.class,
        // 收货地址（登录-收货地址）
        MarketGetAddressTests.class,
//        //获取配送时间(登录-收货地址-配送时间)
//        MarketDeliveryTimeTests.class,
//        //获取自取时间（登录-自取时间）
//        MarketTakesTimeTests.class,
//        // 加购（登录-加购）
//        MarketAddShopCartTests.class,
//        // 获取购物车列表（登录-加购-购物车列表）
//        MarketGetShopCartListTests.class,
//        // 购物车结算（登录-加购-结算）
//        MarketSettleShopCartTests.class,
        //预订单-配送-有时间（登录-获取收货地址-获取配送时间-加购-预订单）
//        MarketGetPreOrderInfoDeliveryTimeTests.class,
//        //预订单-配送-无时间（登录-加购-预订单）
//        MarketGetPreOrderInfoDeliveryWithoutTimeTests.class,
//        //预订单-自取-无时间（登录-加购-预订单）
//        MarketGetPreOrderInfoTakeWithoutTimeTests.class,
        //预订单-自取-有时间（登录-获取自取时间-加购-预订单）
//        MarketGetPreOrderInfoTakeTimeTests.class,
//        // 删除购物车（登录-加购-删除购物车）
//        MarketDeleteShopCartTests.class,
        // 配送下单(登录-获取收货地址-获取配送时间-加购-结算-预订单（配送-有时间）-下单)
//        MarketCreateOrderDeliveryTimeTests.class,
        // 自取下单(登录-获取自取时间-加购-结算-预订单（自取-有时间）-下单)
//        MarketCreateOrderTakeTimeTests.class,
})
@SuiteDisplayName("调试多个测试用例")
@Suite
public class DebugTestCases {
}
