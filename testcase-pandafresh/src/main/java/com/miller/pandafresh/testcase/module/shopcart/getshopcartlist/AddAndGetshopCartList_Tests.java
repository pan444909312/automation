package com.miller.pandafresh.testcase.module.shopcart.getshopcartlist;

import com.miller.service.framework.annotation.Scenario;
import com.miller.pandafresh.testcase.module.shopcart.addshopcart.AddShopCartOrdinaryGoods_Tests;
import org.junit.jupiter.api.DisplayName;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * getShopCartListHasGoods
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/07/15 16:31:09
 */
@SelectClasses({
        AddShopCartOrdinaryGoods_Tests.class,
        GetShopCartListHasGoods_Tests.class
})
@Scenario(
        scenarioID = "01K06KB29CV2B1GESK91ZJAMRU", // 自动生成，不要修改
        scenarioName = "购物车：加购成功&获取购物车列表",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 20, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("购物车：加购成功获取购物车列表")
@Suite
public class AddAndGetshopCartList_Tests {

}
