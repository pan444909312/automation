package com.miller.pandafresh.testcase.module.b2b.credit.listbyorder;

import com.miller.pandafresh.testcase.module.b2b.add.AddGoods;
import com.miller.pandafresh.testcase.module.b2b.order.tocreateb2bordervirtual.ToCreateB2bOrderVirtualImmediate;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * listByOrder
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/11/25 15:40:50
 */
@SelectClasses({
        AddGoods.class,
        ToCreateB2bOrderVirtualImmediate.class,
        ListByOrderUsable.class
})
@Scenario(
        scenarioID = "01KAWZAGDJH17AH0X0BFVVVVVE", // 自动生成，不要修改
        scenarioName = "B2B订单：credit列表-可用",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("B2B订单：credit列表-可用")
@Suite
public class ListByOrderUsable_Tests {

} 