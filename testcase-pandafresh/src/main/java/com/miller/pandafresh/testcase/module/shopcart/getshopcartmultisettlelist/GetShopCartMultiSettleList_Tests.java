package com.miller.pandafresh.testcase.module.shopcart.getshopcartmultisettlelist;

import com.miller.pandafresh.testcase.config.TestcaseConfig;
import com.miller.pandafresh.testcase.module.shopcart.addshopcart.AddShopCartOrdinaryGoods;
import com.miller.pandafresh.testcase.module.shopcart.addshopcart.AddShopCartPreSaleGoods;
import com.miller.pandafresh.testcase.module.shopcart.addshopcart.AddShopCartPreSaleGoodsWithOutDelete;
import com.miller.pandafresh.testcase.module.shopcart.settleshopcart.SettleShopCartSingleInStock;
import com.miller.service.framework.annotation.Scenario;
import com.miller.pandafresh.testcase.config.TestcaseConfig;
import com.miller.pandafresh.testcase.utils.PandaTestDBHelpful;
import com.miller.pandafresh.testcase.utils.TestCaseHelpful;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * getShopCartMultiSettleList
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/09/24 15:41:43
 */
@SelectClasses({
        AddShopCartOrdinaryGoods.class,
        AddShopCartPreSaleGoodsWithOutDelete.class,
        GetShopCartMultiSettleList.class
})
@Scenario(
        scenarioID = "01K5XAQJJWEH5P5PPPE3EF3WJ9", // 自动生成，不要修改
        scenarioName = "购物车-分开结算弹窗：有预售&普通商品",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("购物车-分开结算弹窗：有预售&普通商品")
@Suite
public class GetShopCartMultiSettleList_Tests {

} 