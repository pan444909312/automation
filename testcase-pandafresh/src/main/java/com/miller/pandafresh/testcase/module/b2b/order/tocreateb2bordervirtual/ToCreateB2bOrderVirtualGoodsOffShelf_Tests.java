package com.miller.pandafresh.testcase.module.b2b.order.tocreateb2bordervirtual;

import com.miller.pandafresh.testcase.module.b2b.add.AddGoods;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * toCreateB2BOrderVirtual
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/10/27 17:52:03
 */
@SelectClasses({
        AddGoods.class,
        ToCreateB2bOrderVirtualGoodsOffShelf.class
})
@Scenario(
        scenarioID = "01K8A5GM02D7SSC8ZKS6AZ3T0D", // 自动生成，不要修改
        scenarioName = "b2b结算页：商品下架",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("b2b结算页：商品下架")
@Suite
public class ToCreateB2bOrderVirtualGoodsOffShelf_Tests {

} 