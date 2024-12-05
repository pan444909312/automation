package com.miller.market.TestScenario;

import com.miller.market.open.index.getGoodsFlow.MarketDaDaGetGoodsFlowLoginTests;
import com.miller.market.open.index.getKingKongArea.MarketDaDaGetKingKongAreaLoginTests;
import com.miller.market.open.index.getSpecialTopic.MarketDaDaGetSpecialTopicLoginTests;
import com.miller.market.user.login.MarketLoginTests;
import com.miller.service.framework.annotation.Scenario;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;


@SelectClasses({
        // 用户登录
        MarketLoginTests.class,
        //达达首页金刚区
        MarketDaDaGetKingKongAreaLoginTests.class,
        //达达首页专题推荐
        MarketDaDaGetSpecialTopicLoginTests.class,
        //达达首页商品流
        MarketDaDaGetGoodsFlowLoginTests.class,


})

@Suite
@SuiteDisplayName("【主干场景】登录-达达金刚区-专题推荐-商品流")
@Scenario(scenarioID = "01JA4ZPKGN7P6S2ZP8J8CTQ2KR",
        scenarioName = "【主干场景】登录-达达金刚区-专题推荐-商品流",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 0)
public class DaDaIndexScenarioTests {
}
