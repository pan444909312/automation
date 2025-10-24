package com.miller.pandafresh.testcase.module.b2b.order.createorder;

import com.miller.pandafresh.testcase.module.b2b.add.AddGoods;
import com.miller.pandafresh.testcase.module.b2b.getshopcartlist.GetShopCartList;
import com.miller.pandafresh.testcase.module.b2b.order.getb2bcustomaddress.GetB2bCustomAddress_Tests;
import com.miller.pandafresh.testcase.module.b2b.order.getb2bdeliverytime.GetB2bDeliveryTime_Tests;
import com.miller.pandafresh.testcase.module.b2b.order.tocreateb2bordervirtual.ToCreateB2bOrderVirtual;
import com.miller.service.framework.annotation.Scenario;
import com.miller.pandafresh.testcase.config.TestcaseConfig;
import com.miller.pandafresh.testcase.utils.PandaTestDBHelpful;
import com.miller.pandafresh.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.util.JSONUtils;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * createOrder
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/10/24 11:34:03
 */
@SelectClasses({
        AddGoods.class,
        ToCreateB2bOrderVirtual.class,
        GetB2bCustomAddress_Tests.class,
        GetB2bDeliveryTime_Tests.class,
        CreateOrderPayImmediate.class
})
@Scenario(
        scenarioID = "01K8A4FN5XZTKBFWJQG3RMT6MM", // 自动生成，不要修改
        scenarioName = "b2b创建订单：立即支付订单",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("b2b创建订单：立即支付订单")
@Suite
public class CreateOrderPayImmediate_Tests {

} 