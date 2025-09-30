package com.miller.pandafresh.testcase.module.shopcart.deleteshopcart;

import com.miller.pandafresh.testcase.module.shopcart.addshopcart.AddShopCartOrdinaryGoods;
import com.miller.pandafresh.testcase.module.shopcart.settleshopcart.SettleShopCartSingleInStock;
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
 * deleteShopCart
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/06/19 10:24:27
 */
@SelectClasses({
        AddShopCartOrdinaryGoods.class,
        DeleteShopCart.class
})
@Scenario(
        scenarioID = "01JY300XGN31ZBVRVW6JKPWAQ8", // 自动生成，不要修改
        scenarioName = "购物车删除商品",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("购物车删除商品")
@Suite
public class DeleteShopCart_Tests {

} 