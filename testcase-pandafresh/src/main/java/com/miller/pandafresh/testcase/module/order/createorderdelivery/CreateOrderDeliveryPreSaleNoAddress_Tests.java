package com.miller.pandafresh.testcase.module.order.createorderdelivery;

import com.miller.pandafresh.testcase.module.address.getaddresses.GetHpfAddresses;
import com.miller.pandafresh.testcase.module.deliveryTime.getdeliverytimepresale.GetDeliveryTimePreSaleHasAddress;
import com.miller.pandafresh.testcase.module.deliveryTime.getdeliverytimepresale.GetDeliveryTimePreSaleNoAddress_Tests;
import com.miller.pandafresh.testcase.module.order.getpreorderinfo.GetPreOrderInfoDeliveryPreSaleHasAddressNoTime;
import com.miller.pandafresh.testcase.module.order.getpreorderinfo.GetPreOrderInfoDeliveryPreSaleNoAddressNoTime;
import com.miller.pandafresh.testcase.module.shopcart.addshopcart.AddShopCartPreSaleGoods;
import com.miller.pandafresh.testcase.module.shopcart.settleshopcart.SettleShopCartSinglePreSale;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * createOrderDeliveryPreSaleHasAddress
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/09/04 16:34:08
 */
@SelectClasses({
        AddShopCartPreSaleGoods.class,
        SettleShopCartSinglePreSale.class,
        GetHpfAddresses.class,
        GetDeliveryTimePreSaleNoAddress_Tests.class,
        GetPreOrderInfoDeliveryPreSaleNoAddressNoTime.class,
        CreateOrderDeliveryPreSaleNoAddress.class
})
@Scenario(
        scenarioID = "01K49XS5PYQVPH6C3C6XVCJMB6", // 自动生成，不要修改
        scenarioName = "创建配送单-无地址预售单：加购-结算-获取收货地址-获取配送时间段-获取预定单-创建订单",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 20, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("创建配送单-无地址预售单：加购-结算-获取收货地址-获取配送时间段-获取预定单-创建订单")
@Suite
public class CreateOrderDeliveryPreSaleNoAddress_Tests {


} 