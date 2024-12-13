package com.miller.market.TestScenario;

import com.miller.market.open.goods.getFirstMenuList.MarketDaDaGetFirstMenuListLoginTests;
import com.miller.market.open.goods.getSecondMenuWithProduct.MarketDaDaGetSecondMenuWithProductLoginTests;
import com.miller.market.user.login.MarketLoginTests;
import com.miller.service.framework.annotation.Scenario;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;


@SelectClasses({
        // 用户登录
        MarketLoginTests.class,
        //达达一级分类
        MarketDaDaGetFirstMenuListLoginTests.class,
        //达达二级分类及商品
        MarketDaDaGetSecondMenuWithProductLoginTests.class,


})

@Suite
@SuiteDisplayName("【主干场景】登录-达达分类页-一级分类-二级分类-商品")
@Scenario(scenarioID = "01JA4ZPKGN7P6S2ZP8J8CTQ2KS",
        scenarioName = "【主干场景】登录-达达分类页-一级分类-二级分类-商品",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
public class DaDaCategoryGoodsScenarioTests {
}
