package com.miller.pandafresh.testcase.module.deliveryTime.getdeliverytimepresale;

import com.miller.pandafresh.testcase.config.TestcaseConfig;
import com.miller.pandafresh.testcase.module.address.getaddresses.GetHpfAddresses_Tests;
import com.miller.pandafresh.testcase.module.deliveryTime.getdeliverytime.GetDeliveryTime;
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

/**
 * getDeliveryTimePreSaleNoAddress
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/08/15 16:16:33
 */
@SelectClasses({
        GetHpfAddresses_Tests.class,
        GetDeliveryTimePreSaleHasAddress.class
})
@Scenario(
        scenarioID = "01K2PCTKC75KK5K7DE35ZWW05Y", // 自动生成，不要修改
        scenarioName = "获取配送时间段:预售单-有地址",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 15, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("获取配送时间段:预售单-有地址")
@Suite
public class GetDeliveryTimePreSaleHasAddress_Tests {

} 