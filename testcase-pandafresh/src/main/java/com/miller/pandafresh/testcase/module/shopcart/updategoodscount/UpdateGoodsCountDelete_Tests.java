package com.miller.pandafresh.testcase.module.shopcart.updategoodscount;

import com.miller.pandafresh.testcase.module.shopcart.addshopcart.AddShopCartOrdinaryGoods;
import com.miller.pandafresh.testcase.module.shopcart.addshopcart.AddShopCartOrdinaryGoods_Tests;
import com.miller.pandafresh.testcase.module.shopcart.getshopcartlist.GetShopCartListHasGoods_Tests;
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
        //清空购物车加购一个商品
        AddShopCartOrdinaryGoods.class,
        //增加加购数量为2
        UpdateGoodsCount.class,
        //减少加购数量为1
        UpdateGoodsCountDelete.class
})
@Scenario(
        scenarioID = "01JY13JR7Q0VQE5AQ58F5HAEZ0", // 自动生成，不要修改
        scenarioName = "修改购物车商品数量-减少",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("修改购物车商品数量-减少")
@Suite
public class UpdateGoodsCountDelete_Tests {

} 