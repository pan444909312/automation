package com.miller.market.TestScenario;

import com.miller.market.address.addAddress.MarketAddAddressTests;
import com.miller.market.address.deleteAddress.MarketDeleteAddressTests;
import com.miller.market.address.getAddress.MarketGetAddressTests;
import com.miller.market.address.location.MarketLocationAddressTests;
import com.miller.market.address.search.MarketSearchAddressTests;
import com.miller.market.address.updateAddress.MarketUpdateAddressTests;
import com.miller.market.user.login.MarketLoginTests;
import com.miller.service.framework.annotation.Scenario;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;


@SelectClasses({
        // 用户登录
        MarketLoginTests.class,
        //搜索地址
        MarketSearchAddressTests.class,
        //获取定位
        MarketLocationAddressTests.class,
        //新增收货地址
        MarketAddAddressTests.class,
        //获取收货地址列表
        MarketGetAddressTests.class,
        //修改收货地址
        MarketUpdateAddressTests.class,
        //删除收货地址
        MarketDeleteAddressTests.class,



})

@Suite
@SuiteDisplayName("【主干场景】登录-搜索地址-地址定位-增删改查收货地址")
@Scenario(scenarioID = "01JA4ZPKGN7P6S2ZP8J8CTQ2KS",
        scenarioName = "【主干场景】登录-搜索地址-地址定位-增删改查收货地址",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 0)
public class AddressScenarioTests {
}
