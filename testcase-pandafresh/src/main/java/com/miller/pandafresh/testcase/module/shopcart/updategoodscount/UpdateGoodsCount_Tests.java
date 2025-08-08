package com.miller.pandafresh.testcase.module.shopcart.updategoodscount;

import com.miller.pandafresh.testcase.module.shopcart.addshopcart.AddShopCartOrdinaryGoods;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.JSONUtils;
import com.miller.pandafresh.testcase.config.TestcaseConfig;
import com.miller.pandafresh.testcase.utils.FreshTestDBHelpful;
import com.miller.pandafresh.testcase.utils.TestCaseHelpful;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import java.util.List;
import java.util.Map;

/**
 * updateGoodsCount
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/06/18 16:48:08
 */
@SelectClasses({
        AddShopCartOrdinaryGoods.class,
        UpdateGoodsCount.class
})
@Scenario(
        scenarioID = "01JY13JR7Q0VQE5AQ58F5HAEZM", // 自动生成，不要修改
        scenarioName = "修改购物车商品数量-增加",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("修改购物车商品数量-增加")
@Suite
public class UpdateGoodsCount_Tests {

} 