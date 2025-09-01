package com.miller.pandafresh.testcase.module.order.createorderdelivery;

import com.miller.pandafresh.testcase.module.address.getaddresses.GetAddresses_Tests;
import com.miller.pandafresh.testcase.module.address.getaddresses.GetHpfAddresses;
import com.miller.pandafresh.testcase.module.address.getaddresses.GetHpfAddresses_Tests;
import com.miller.pandafresh.testcase.module.deliveryTime.getdeliverytime.GetDeliveryTime;
import com.miller.pandafresh.testcase.module.order.createorderdelivery.Createorderdelivery;
import com.miller.pandafresh.testcase.module.order.getpreorderinfo.GetPreOrderInfoDelivery;
import com.miller.pandafresh.testcase.module.shopcart.addshopcart.AddShopCartOrdinaryGoods;
import com.miller.pandafresh.testcase.module.shopcart.settleshopcart.SettleShopCartSingleInStock;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * createOrderDelivery
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/08/07 20:57:13
 */
@SelectClasses({
        AddShopCartOrdinaryGoods.class,
        SettleShopCartSingleInStock.class,
        GetHpfAddresses.class,
        GetDeliveryTime.class,
        GetPreOrderInfoDelivery.class,
        Createorderdelivery.class
})
@Scenario(
        scenarioID = "01K229PRN3TVCQAPPSAXSG072R", // 自动生成，不要修改
        scenarioName = "创建配送单：加购-结算-获取收货地址-获取配送时间段-获取预定单-创建订单",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 20, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("创建配送单：加购-结算-获取收货地址-获取配送时间段-获取预定单-创建订单")
@Suite
public class CreateOrderDelivery_Tests {

} 