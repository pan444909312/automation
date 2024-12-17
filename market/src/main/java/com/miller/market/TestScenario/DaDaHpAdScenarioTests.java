package com.miller.market.TestScenario;

import com.miller.market.open.hpAd.getSceneGoodsList.MarketDaDaGetSceneGoodsListWithProductLoginTests;
import com.miller.market.open.hpAd.getShopAdList.MarketDaDaGetShopAdListLoginTests;
import com.miller.market.user.login.MarketLoginTests;
import com.miller.service.framework.annotation.Scenario;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;


@SelectClasses({
        // 用户登录
        MarketLoginTests.class,
        //达达场景广告列表
        MarketDaDaGetShopAdListLoginTests.class,
        //达达场景广告商品
        MarketDaDaGetSceneGoodsListWithProductLoginTests.class,


})

@Suite
@SuiteDisplayName("【主干场景】登录-达达获取场景广告列表-获取场景广告商品")
@Scenario(scenarioID = "01JDKRYYZ2E061J4C0Q29WQ74A",
        scenarioName = "【主干场景】登录-达达获取场景广告列表-获取场景广告商品",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
public class DaDaHpAdScenarioTests {
}
