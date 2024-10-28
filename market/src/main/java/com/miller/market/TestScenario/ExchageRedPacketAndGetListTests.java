package com.miller.market.TestScenario;

import com.miller.market.address.addAddress.MarketAddAddressTests;
import com.miller.market.address.updateAddress.MarketUpdateAddressTests;
import com.miller.market.redPacket.exchangeRedPacket.MarketExchangeRedPacketTests;
import com.miller.market.redPacket.getRedPacketListByOrder.MarketGetRedPacketListByOrderTests;
import com.miller.market.user.center.MarketCenterTests;
import com.miller.market.user.login.MarketLoginTests;
import com.miller.market.user.loginOut.MarketLoginOutTests;
import com.miller.service.framework.annotation.Scenario;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;


@SelectClasses({
        // 用户登录
        MarketLoginTests.class,
        //兑换红包
        MarketExchangeRedPacketTests.class,
        //查看订单 pf红包&优惠券列表
        MarketGetRedPacketListByOrderTests.class,

})

@Suite
@SuiteDisplayName("【主干场景】登录 - 兑换红包 - 查看订单详情pf红包&优惠券列表")
@Scenario(scenarioID = "01JA4ZPKGMEJSHP04GZWZZPQE2",
        scenarioName = "【主干场景】登录 - 兑换红包 - 查看订单详情pf红包&优惠券列表",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 15)
public class ExchageRedPacketAndGetListTests {
}
