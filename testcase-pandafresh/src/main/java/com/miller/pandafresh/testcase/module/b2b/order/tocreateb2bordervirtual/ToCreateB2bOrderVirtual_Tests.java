package com.miller.pandafresh.testcase.module.b2b.order.tocreateb2bordervirtual;

import com.miller.pandafresh.testcase.module.b2b.add.AddGoods;
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
 * toCreateB2BOrderVirtual
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/10/24 11:52:03
 */
@SelectClasses({
        AddGoods.class,
        ToCreateB2bOrderVirtual.class
})
@Scenario(
        scenarioID = "01K8A5GM02D7SSC8ZKS6AZ3T0B", // 自动生成，不要修改
        scenarioName = "b2b结算页：立即支付订单",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("b2b结算页：立即支付订单")
@Suite
public class ToCreateB2bOrderVirtual_Tests {

} 