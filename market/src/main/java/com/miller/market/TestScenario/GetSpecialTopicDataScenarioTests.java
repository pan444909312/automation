package com.miller.market.TestScenario;

import com.miller.market.topic.getSpecialTopicGoods.*;
import com.miller.market.user.login.MarketLoginTests;
import com.miller.service.framework.annotation.Scenario;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;


@SelectClasses({
        // 用户登录
        MarketLoginTests.class,
        //获取普通专题商品
        MarketGetSpecialTopicGoodsByGeneralTests.class,
//        //获取N选1专题商品
//        MarketGetSpecialTopicGoodsByChooseOneTests.class,
//        //获取组合促销商品
//        MarketGetSpecialTopicGoodsByCombineTests.class,
//        //获取随心配商品
//        MarketGetSpecialTopicGoodsByFollowWithTests.class,
//        //获取临期专题商品
//        MarketGetSpecialTopicGoodsByLotTests.class,
//        //获取新人专题商品
//        MarketGetSpecialTopicGoodsByNewPersonTests.class,
//        //获取限时秒杀专题商品
//        MarketGetSpecialTopicGoodsBySeckillTests.class,


})

@Suite
@SuiteDisplayName("【主干场景】登录 -查看普通专题/n选1/组合促销/随心配/临期/新人专题/秒杀专题商品")
@Scenario(scenarioID = "01JA4ZPKGN7P6S2ZP8J8CTQ2KP",
        scenarioName = "【主干场景】登录 -查看普通专题/n选1/组合促销/随心配/临期/新人专题/秒杀专题商品",
        author = "zhangpei@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
public class GetSpecialTopicDataScenarioTests {
}
