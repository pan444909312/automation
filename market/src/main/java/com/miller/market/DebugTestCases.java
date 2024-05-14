package com.miller.market;

import com.miller.market.login.MarketLoginTests;
import com.miller.market.shopCart.addShopCart.MarketAddShopCartTests;
import com.miller.market.shopCart.deleteShopCart.MarketDeleteShopCartTests;
import com.miller.market.shopCart.getShopCartList.MarketGetShopCartListTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * 当需要执行多个测试类时可以通过此程序进行调试
 */
@SelectClasses({
        // 用户登录
        MarketLoginTests.class,
        // 加购
        MarketAddShopCartTests.class,
        // 获取购物车列表
        MarketGetShopCartListTests.class,
        // 删除购物车
        MarketDeleteShopCartTests.class,
})
@SuiteDisplayName("调试多个测试用例")
@Suite
public class DebugTestCases {
}
