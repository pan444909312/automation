package com.miller.pandafresh.testcase.module.shopcart.settleshopcart;

import com.miller.pandafresh.testcase.module.shopcart.addshopcart.AddShopCartOrdinaryGoods;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * settleShopCart
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/10/28 16:51:03
 */

@SelectClasses({
        AddShopCartOrdinaryGoods.class,
        SettleShopCartSingleOffShelf.class
})
@Scenario(
        scenarioID = "01K17SB8AMTC4R18C4G073SPVC", // 自动生成，不要修改
        scenarioName = "购物车-去结算-结算单个下架商品",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 15, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("购物车-去结算-结算单个下架商品")
@Suite
public class SettleShopCartSingleOffShelf_Tests {


} 