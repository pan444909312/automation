package com.miller.pandafresh.testcase.module.order.getpreorderinfo;

import com.miller.pandafresh.testcase.module.shopcart.addshopcart.AddShopCartOrdinaryGoods;
import com.miller.pandafresh.testcase.module.shopcart.settleshopcart.SettleShopCartSingleInStock;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * getShopCartListHasGoods
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/07/15 16:31:09
 */
@SelectClasses({
        AddShopCartOrdinaryGoods.class,
        SettleShopCartSingleInStock.class,
        GetPreOrderInfoTakeSelf.class
})
@Scenario(
        scenarioID = "01K06KB29CV2B1GESK91ZJAMXZ", // 自动生成，不要修改
        scenarioName = "pf结算页-自提单：加购-结算-进入自提结算页",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 20, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("pf结算页-自提单")
@Suite
public class GetPreOrderInfoTakeSelf_Tests {

}
