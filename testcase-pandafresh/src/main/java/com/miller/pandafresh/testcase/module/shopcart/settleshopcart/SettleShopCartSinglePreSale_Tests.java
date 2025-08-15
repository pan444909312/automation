package com.miller.pandafresh.testcase.module.shopcart.settleshopcart;

import com.miller.pandafresh.testcase.config.TestcaseConfig;
import com.miller.pandafresh.testcase.module.order.createordertakeself.CreateOrderTakeSelf;
import com.miller.pandafresh.testcase.module.order.getpreorderinfo.GetPreOrderInfoTakeSelf;
import com.miller.pandafresh.testcase.module.shopcart.addshopcart.AddShopCartOrdinaryGoods;
import com.miller.pandafresh.testcase.module.shopcart.addshopcart.AddShopCartPreSaleGoods;
import com.miller.pandafresh.testcase.module.takeTime.gettakestime.GetTakesTime_Tests;
import com.miller.pandafresh.testcase.utils.FreshTestDBHelpful;
import com.miller.pandafresh.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.JSONUtils;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import java.util.List;
import java.util.Map;

/**
 * settleShopCart
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/08/14 14:51:03
 */
@SelectClasses({
        AddShopCartPreSaleGoods.class,
        SettleShopCartSinglePreSale.class
})
@Scenario(
        scenarioID = "01K17SB8AMTC4R18C4G073SPVB", // 自动生成，不要修改
        scenarioName = "购物车-去结算-结算单个预售商品",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 15, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("购物车-去结算-结算单个预售商品")
@Suite
public class SettleShopCartSinglePreSale_Tests {

} 