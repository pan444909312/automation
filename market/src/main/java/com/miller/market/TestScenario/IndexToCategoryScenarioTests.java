package com.miller.market.TestScenario;

import com.miller.market.goods.getCategory.MarketGetCategoryLoginTests;

import com.miller.market.goods.getGoodsByFirstCategory.MarketGetGoodsByFirstCategoryLoginTests;
import com.miller.market.index.getGoodsList.MarketGetGoodsListLoginTests;
import com.miller.market.index.getIndex.MarketGetIndexLoginTests;

import com.miller.market.user.login.MarketLoginTests;

import com.miller.service.framework.annotation.Scenario;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;


@SelectClasses({
        // 用户登录
        MarketLoginTests.class,
        //获取首页
        MarketGetIndexLoginTests.class,
        //获取首页商品流
        MarketGetGoodsListLoginTests.class,
        // 获取分类页：分类信息
        MarketGetCategoryLoginTests.class,
        // 获取分类页：商品信息
        MarketGetGoodsByFirstCategoryLoginTests.class,

})

@Suite
@SuiteDisplayName("【主干场景】登录 - 获取首页-获取分类页：分类信息 - 获取分类页：商品信息")
@Scenario(scenarioID = "01J5SGFNY03AZH1TY0GQ8Q7E78",
        scenarioName = "【主干场景】登录 - 获取首页-首页商品流-获取分类页：分类信息 - 获取分类页：商品信息",
        author = "zhangpei@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 20, manualTestTime = 15)
public class IndexToCategoryScenarioTests {
}
