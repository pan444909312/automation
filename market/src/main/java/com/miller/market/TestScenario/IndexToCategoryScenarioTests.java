package com.miller.market.TestScenario;

import com.miller.market.goods.getCategory.MarketGetCategoryLoginTests;

import com.miller.market.goods.getGoodsByFirstCategory.MarketGetGoodsByFirstCategoryLoginTests;
import com.miller.market.index.getIndex.MarketGetIndexLoginTests;

import com.miller.market.login.MarketLoginTests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;


@SelectClasses({
        // 用户登录
        MarketLoginTests.class,
        //获取首页
        MarketGetIndexLoginTests.class,
        // 获取分类页：分类信息
        MarketGetCategoryLoginTests.class,
        // 获取分类页：商品信息
        MarketGetGoodsByFirstCategoryLoginTests.class,

})

@Suite
@SuiteDisplayName("【主干场景】登录 - 获取首页-获取分类页：分类信息 - 获取分类页：商品信息")
public class IndexToCategoryScenarioTests {
}
