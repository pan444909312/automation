package com.miller.market.TestScenario;

import com.miller.market.order.cancelOrder.MarketCancelNoPayOrderTests;
import com.miller.market.order.orderDetail.MarketOrderDetailTests;
import com.miller.market.user.login.MarketLoginTests;
import com.miller.market.order.createOrder.MarketCreateOrderTakeTimeTests;
import com.miller.market.order.getPreOrderInfo.MarketGetPreOrderInfoTakeTimeTests;
import com.miller.market.shopCart.addShopCart.MarketAddShopCartTests;
import com.miller.market.shopCart.getShopCartList.MarketGetShopCartListTests;
import com.miller.market.shopCart.settleShopCart.MarketSettleShopCartTests;
import com.miller.market.takesTime.MarketTakesTimeTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;


@SelectClasses({
        // 用户登录
        MarketLoginTests.class,
        //获取自取时间（登录-自取时间）
        MarketTakesTimeTests.class,
        // 加购（登录-加购）
        MarketAddShopCartTests.class,
        // 获取购物车列表（登录-加购-购物车列表）
        MarketGetShopCartListTests.class,
        // 购物车结算（登录-加购-结算）
        MarketSettleShopCartTests.class,

        // 预订单-自取-有时间（登录-获取自取时间-加购-预订单）
        MarketGetPreOrderInfoTakeTimeTests.class,

        // 自取下单(登录-获取自取时间-加购-结算-预订单（自取-有时间）-下单)
        MarketCreateOrderTakeTimeTests.class,
        //查看订单详情
        MarketOrderDetailTests.class,
        //取消订单
        MarketCancelNoPayOrderTests.class,
})

@Suite
@SuiteDisplayName("【主干场景】登录-获取自取时间-加购-结算-预订单（自取-有时间）-下单 -查看订单详情 - 取消订单")
public class OrderOfTakeTimeScenarioTests {
}
