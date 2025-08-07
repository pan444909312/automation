package com.miller.pandafresh.testcase.module.order.createordertakeself;

import com.miller.pandafresh.testcase.module.order.getpreorderinfo.GetPreOrderInfoDelivery;
import com.miller.pandafresh.testcase.module.order.getpreorderinfo.GetPreOrderInfoTakeSelf;
import com.miller.pandafresh.testcase.module.shopcart.addshopcart.AddShopCartOrdinaryGoods;
import com.miller.pandafresh.testcase.module.shopcart.settleshopcart.SettleShopCartSingleInStock;
import com.miller.pandafresh.testcase.module.takeTime.gettakestime.GetTakesTime_Tests;
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
 * createOrderTakeSelf
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/08/07 20:39:34
 */
@SelectClasses({
        AddShopCartOrdinaryGoods.class,
        GetTakesTime_Tests.class,
        SettleShopCartSingleInStock.class,
        GetPreOrderInfoTakeSelf.class,
        CreateOrderTakeSelf.class
})
@Scenario(
        scenarioID = "01K228PETSRJ8CTPJ6TRNYN0AR", // 自动生成，不要修改
        scenarioName = "创建自提单：加购-获取自提时间段-结算-获取预定单-创建订单",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 20, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("创建自提单：加购-获取自提时间段-结算-获取预定单-创建订单")
@Suite
public class CreateOrderTakeSelf_Tests {


} 