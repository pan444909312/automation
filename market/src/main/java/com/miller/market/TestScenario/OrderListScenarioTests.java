package com.miller.market.TestScenario;

import com.miller.market.order.orderList.MarketOrderListTests;
import com.miller.market.user.login.MarketLoginTests;
import com.miller.service.framework.annotation.Scenario;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;


@SelectClasses({
        // 用户登录
        MarketLoginTests.class,

        //订单列表
        MarketOrderListTests.class,

})

@Suite
@SuiteDisplayName("【主干场景】登录_获取PF订单列表：全部-待支付-进行中-已完成")
@Scenario(scenarioID = "01JDKRYYZ2E061J4C0Q29WQ74D",
        scenarioName = "【主干场景】登录_获取PF订单列表：全部-待支付-进行中-已完成",
        author = "zhangpei@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
public class OrderListScenarioTests {
}
