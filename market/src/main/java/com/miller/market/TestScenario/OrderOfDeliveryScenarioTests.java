package com.miller.market.TestScenario;

import com.miller.market.address.getAddress.MarketGetAddressTests;
import com.miller.market.deliveryTime.MarketDeliveryTimeTests;
import com.miller.market.order.cancelOrder.MarketCancelNoPayOrderTests;
import com.miller.market.order.orderDetail.MarketOrderDetailTests;
import com.miller.market.pay.paymentPattern.MarketPaymentPatternTests;
import com.miller.market.user.login.MarketLoginTests;
import com.miller.market.order.createOrder.MarketCreateOrderDeliveryTimeTests;
import com.miller.market.order.getPreOrderInfo.MarketGetPreOrderInfoDeliveryTimeTests;
import com.miller.market.shopCart.addShopCart.MarketAddShopCartTests;
import com.miller.market.shopCart.getShopCartList.MarketGetShopCartListTests;
import com.miller.market.shopCart.settleShopCart.MarketSettleShopCartTests;
import com.miller.service.framework.annotation.Scenario;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;


@SelectClasses({
        // 用户登录
        MarketLoginTests.class,
        // 收货地址
        MarketGetAddressTests.class,
        //获取配送时间
        MarketDeliveryTimeTests.class,
        // 加购
        MarketAddShopCartTests.class,
        // 获取购物车列表
        MarketGetShopCartListTests.class,
        // 购物车结算
        MarketSettleShopCartTests.class,
        //预订单-配送-有时间
        MarketGetPreOrderInfoDeliveryTimeTests.class,
        // 配送下单
        MarketCreateOrderDeliveryTimeTests.class,
        MarketPaymentPatternTests.class,
        //查看订单详情
        MarketOrderDetailTests.class,
        //取消订单
        MarketCancelNoPayOrderTests.class,

})

@Suite
@SuiteDisplayName("【主干场景】登录-获取收货地址-获取配送时间-加购-结算-预订单（配送-有时间）-下单 -查看订单详情 - 取消订单")
@Scenario(scenarioID = "01JQT4S9A8K21M67YD4JRSMMC4",
        scenarioName = "【主干场景】登录-获取收货地址-获取配送时间-加购-结算-预订单（配送-有时间）-下单 -查看订单详情 - 取消订单",
        author = "zhangpei@hungrypandagroup.com", developmentTime = 60, maintenanceTime = 0, manualTestTime = 20)
public class OrderOfDeliveryScenarioTests {
}
