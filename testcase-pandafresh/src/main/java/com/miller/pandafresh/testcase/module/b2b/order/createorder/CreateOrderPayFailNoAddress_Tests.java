package com.miller.pandafresh.testcase.module.b2b.order.createorder;

import com.miller.pandafresh.testcase.module.b2b.add.AddGoods;
import com.miller.pandafresh.testcase.module.b2b.order.getb2bcustomaddress.GetB2bCustomAddress_Tests;
import com.miller.pandafresh.testcase.module.b2b.order.getb2bdeliverytime.GetB2bDeliveryTime_Tests;
import com.miller.pandafresh.testcase.module.b2b.order.tocreateb2bordervirtual.ToCreateB2bOrderVirtualImmediate;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * createOrder
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/10/27 17:34:03
 */
@SelectClasses({
        AddGoods.class,
        ToCreateB2bOrderVirtualImmediate.class,
//        GetB2bCustomAddress_Tests.class,
        GetB2bDeliveryTime_Tests.class,
        CreateOrderPayFailNoAddress.class
})
@Scenario(
        scenarioID = "01K8A4FN5XZTKBFWJQG3RMT6MP", // 自动生成，不要修改
        scenarioName = "b2b创建订单失败：缺失收货地址",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("b2b创建订单失败：缺失收货地址")
@Suite
public class CreateOrderPayFailNoAddress_Tests {

} 