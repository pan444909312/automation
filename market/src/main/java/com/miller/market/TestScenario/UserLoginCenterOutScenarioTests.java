package com.miller.market.TestScenario;

import com.miller.market.address.addAddress.MarketAddAddressTests;
import com.miller.market.address.updateAddress.MarketUpdateAddressTests;
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
        //查看个人中心
        MarketCenterTests.class,
        //新增收货地址
        MarketAddAddressTests.class,
        //修改收货地址
        MarketUpdateAddressTests.class,
        // 登出
        MarketLoginOutTests.class

})

@Suite
@SuiteDisplayName("【主干场景】登录 - 查看个人中心 - 新增地址 - 修改地址 - 登出")
@Scenario(scenarioID = "01J5SGFNY03AZH1TY0GQ8Q7E79",
        scenarioName = "【主干场景】登录 - 查看个人中心 - 新增地址 - 修改地址 - 登出",
        developmentTime = 30, maintenanceTime = 30, manualTestTime = 15)
public class UserLoginCenterOutScenarioTests {
}
