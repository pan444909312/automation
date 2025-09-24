package com.miller.pandafresh.testcase.module.shopcart.getshopcartmultisettlelist;

import com.miller.pandafresh.testcase.module.shopcart.addshopcart.AddShopCartOrdinaryGoods;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
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
        GetShopCartMultiSettleListOneGoods.class
})
@Scenario(
        scenarioID = "01K5XAQJJWEH5P5PPPE3EF3WJ1", // 自动生成，不要修改
        scenarioName = "购物车-分开结算弹窗:仅一个普通商品",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("购物车-分开结算弹窗:仅一个普通商品")
@Suite
public class GetShopCartMultiSettleListOnlyOneOrdinaryGoods_Tests {

} 