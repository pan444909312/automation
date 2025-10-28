package com.miller.pandafresh.testcase.module.shopcart.getshopcartlist;

import com.miller.pandafresh.testcase.config.TestcaseConfig;
import com.miller.pandafresh.testcase.module.shopcart.addshopcart.AddShopCartOrdinaryGoods;
import com.miller.pandafresh.testcase.module.shopcart.addshopcart.AddShopCartOrdinaryGoods_Tests;
import com.miller.pandafresh.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;


@SelectClasses(
        {
                AddShopCartOrdinaryGoods.class,
                GetShopCartListGoodsOffShelf.class
        }
)
@Scenario(
        scenarioID = "01K06KB29CV2B1GESK91ZJAMRV", // 自动生成，不要修改
        scenarioName = "购物车列表：有下架商品",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 20, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("购物车列表：有下架商品")
@Suite
public class GetShopCartListGoodsOffShelf_Tests {

}
