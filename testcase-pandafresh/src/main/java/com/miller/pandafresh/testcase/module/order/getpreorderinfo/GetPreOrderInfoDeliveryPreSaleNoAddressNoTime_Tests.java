package com.miller.pandafresh.testcase.module.order.getpreorderinfo;

import com.miller.pandafresh.testcase.module.shopcart.addshopcart.AddShopCartPreSaleGoods;
import com.miller.pandafresh.testcase.module.shopcart.settleshopcart.SettleShopCartSinglePreSale;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * getPreOrderInfoDeliveryPreSaleNoAddress
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/09/04 16:09:48
 */
@SelectClasses({
        AddShopCartPreSaleGoods.class,
        SettleShopCartSinglePreSale.class,
        GetPreOrderInfoDeliveryPreSaleNoAddressNoTime_Tests.class
})
@Scenario(
        scenarioID = "01K49WCKP0ZQ28GE642QZXB7P8", // 自动生成，不要修改
        scenarioName = "pf结算页-配送单-预售商品：加购-结算-进入配送结算页-地址待定未选时间",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 20, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("pf结算页-配送单-预售商品：加购-结算-进入配送结算页-地址待定未选时间")
@Suite
public class GetPreOrderInfoDeliveryPreSaleNoAddressNoTime_Tests {

} 