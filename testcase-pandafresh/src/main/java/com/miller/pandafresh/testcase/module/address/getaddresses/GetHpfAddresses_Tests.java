package com.miller.pandafresh.testcase.module.address.getaddresses;

import com.miller.pandafresh.testcase.module.shopcart.addshopcart.AddShopCartOrdinaryGoods;
import com.miller.pandafresh.testcase.module.shopcart.settleshopcart.SettleShopCartSingleInStock;
import com.miller.service.framework.annotation.Scenario;
import com.miller.pandafresh.testcase.config.TestcaseConfig;
import com.miller.pandafresh.testcase.utils.TestCaseHelpful;
import com.miller.service.framework.util.JSONUtils;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * getAddresses
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/06/19 10:45:29
 */
@SelectClasses({
        AddShopCartOrdinaryGoods.class,
        GetHpfAddresses.class
})
@Scenario(
        scenarioID = "01JY317E0C3WXMCYKN6SPHGJH3", // 自动生成，不要修改
        scenarioName = "融合订单结算页获取收货地址:pf配送区域内",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 10, maintenanceTime = 5, manualTestTime = 3)
@DisplayName("融合订单结算页获取收货地址：pf配送区域内")
@Suite
public class GetHpfAddresses_Tests {

} 