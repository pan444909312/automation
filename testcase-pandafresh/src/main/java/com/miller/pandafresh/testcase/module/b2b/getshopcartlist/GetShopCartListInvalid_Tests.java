package com.miller.pandafresh.testcase.module.b2b.getshopcartlist;

import com.miller.pandafresh.testcase.config.TestcaseConfig;
import com.miller.pandafresh.testcase.module.b2b.add.AddGoods;
import com.miller.pandafresh.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * getShopCartList
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/10/23 18:28:28
 */
@SelectClasses({
        AddGoods.class,
        GetShopCartListInvalid.class
})
@Scenario(
        scenarioID = "01JXYEB2K7QW0M7GBBAT7SH3MD", // 自动生成，不要修改
        scenarioName = "b2b购物车列表：失效列表",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("b2b购物车列表：失效列表")
@Suite
public class GetShopCartListInvalid_Tests {

} 