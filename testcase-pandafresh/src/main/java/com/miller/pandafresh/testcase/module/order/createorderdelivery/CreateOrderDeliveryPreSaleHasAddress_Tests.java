package com.miller.pandafresh.testcase.module.order.createorderdelivery;

import com.miller.pandafresh.testcase.module.address.getaddresses.GetHpfAddresses;
import com.miller.pandafresh.testcase.module.deliveryTime.getdeliverytime.GetDeliveryTime;
import com.miller.pandafresh.testcase.module.deliveryTime.getdeliverytimepresale.GetDeliveryTimePreSaleHasAddress;
import com.miller.pandafresh.testcase.module.order.getpreorderinfo.GetPreOrderInfoDelivery;
import com.miller.pandafresh.testcase.module.order.getpreorderinfo.GetPreOrderInfoDeliveryPreSaleHasAddressNoTime;
import com.miller.pandafresh.testcase.module.shopcart.addshopcart.AddShopCartOrdinaryGoods;
import com.miller.pandafresh.testcase.module.shopcart.addshopcart.AddShopCartPreSaleGoods;
import com.miller.pandafresh.testcase.module.shopcart.settleshopcart.SettleShopCartSingleInStock;
import com.miller.pandafresh.testcase.module.shopcart.settleshopcart.SettleShopCartSinglePreSale;
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
        GetDeliveryTimePreSaleHasAddress.class,
        GetPreOrderInfoDeliveryPreSaleHasAddressNoTime.class,
        CreateOrderDeliveryPreSaleHasAddress.class
})
@Scenario(
        scenarioID = "01K49XS5PYQVPH6C3C6XVCJMB5", // 自动生成，不要修改
        scenarioName = "创建配送单-有地址预售单：加购-结算-获取收货地址-获取配送时间段-获取预定单-创建订单",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 20, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("创建配送单-有地址预售单：加购-结算-获取收货地址-获取配送时间段-获取预定单-创建订单")
@Suite
public class CreateOrderDeliveryPreSaleHasAddress_Tests {


} 