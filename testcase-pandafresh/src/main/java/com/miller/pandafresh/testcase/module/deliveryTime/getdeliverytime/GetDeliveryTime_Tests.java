package com.miller.pandafresh.testcase.module.deliveryTime.getdeliverytime;

import com.miller.pandafresh.testcase.config.TestcaseConfig;
import com.miller.pandafresh.testcase.module.address.getaddresses.GetHpfAddresses_Tests;
import com.miller.pandafresh.testcase.module.order.getpreorderinfo.GetPreOrderInfoDelivery;
import com.miller.pandafresh.testcase.module.shopcart.addshopcart.AddShopCartOrdinaryGoods;
import com.miller.pandafresh.testcase.module.shopcart.settleshopcart.SettleShopCartSingleInStock;
import com.miller.pandafresh.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.JSONUtils;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * getDeliveryTime
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/08/07 20:11:36
 */

@SelectClasses({
        GetHpfAddresses_Tests.class,
        GetDeliveryTime.class
})
@Scenario(
        scenarioID = "01JY317E0C3WXMCYKN6SPHGJH4", // 自动生成，不要修改
        scenarioName = "获取配送时间段:获取地址-查看地址对应的配送时间段",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("获取配送时间段:获取地址-查看地址对应的配送时间段")
@Suite
public class GetDeliveryTime_Tests {



} 