package com.miller.market.TestScenario;

import com.miller.market.personalize.getDetailOneOfIn.MarketGetDetailOneOfInByCustomizeTests;
import com.miller.market.personalize.getDetailOneOfIn.MarketGetDetailOneOfInByHotSaleTests;
import com.miller.market.user.login.MarketLoginTests;
import com.miller.service.framework.annotation.Scenario;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;


@SelectClasses({
        // 用户登录
        MarketLoginTests.class,
        //获取自定义详情
        MarketGetDetailOneOfInByCustomizeTests.class,
        //获取热销榜单详情
        MarketGetDetailOneOfInByHotSaleTests.class,


})

@Suite
@SuiteDisplayName("【主干场景】登录 -查看自定义详情 - 查看热销榜单详情")
@Scenario(scenarioID = "01JA4ZPKGN7P6S2ZP8J8CTQ2KN",
        scenarioName = "【主干场景】登录 -查看自定义详情 - 查看热销榜单详情",
        author = "zhangpei@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
public class GetPersonalizeDetailScenarioTests {
}
