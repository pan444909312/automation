package com.miller.market.TestScenario;

import com.miller.market.specialOrder.getDetail.MarketSpecialOrderDetailTests;
import com.miller.market.specialOrder.getPageList.MarketSpecialOrderListlTests;
import com.miller.market.user.login.MarketLoginTests;
import com.miller.service.framework.annotation.Scenario;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;


@SelectClasses({
        // 用户登录
        MarketLoginTests.class,
        //获取特殊单列表
        MarketSpecialOrderListlTests.class,
        //特殊单详情
        MarketSpecialOrderDetailTests.class,


})

@Suite
@SuiteDisplayName("【主干场景】登录 -查看特殊单列表 - 特殊单详情")
@Scenario(scenarioID = "01JA4ZPKGN7P6S2ZP8J8CTQ2KQ",
        scenarioName = "【主干场景】登录 -查看特殊单列表 - 特殊单详情",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
public class GetSpecialOrderDataScenarioTests {
}
