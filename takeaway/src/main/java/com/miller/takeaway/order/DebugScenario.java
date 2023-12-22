package com.miller.takeaway.order;

import com.miller.deliveryapp.driver.online.DriverOnlineTests;
import com.miller.deliveryapp.order.delivery.list.DeliveryListTests;
import com.miller.deliveryapp.order.neworder.list.NewOrderListTests;
import com.miller.deliveryapp.order.pickup.list.PickUpListTests;
import com.miller.merchant.order.complain.ComplainOrderTests;
import com.miller.merchant.order.list.OrderListTests;
import com.miller.merchant.order.outing.OutingOrderTests;
import com.miller.merchant.order.receiving.ReceivingOrderTests;
import com.miller.userapp.login.LoginTests;
import com.miller.userapp.order.create.CreateOrderTests;
import com.miller.userapp.pay.balance.PayByBalanceTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * 场景调试
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/21 16:21:53
 */
@SelectClasses({
        // 用户登录
        LoginTests.class,
        // 创建订单
        CreateOrderTests.class,
        // 支付订单
        PayByBalanceTests.class,
        // 商家登录APP
        com.miller.merchant.login.LoginTests.class,
        // 订单列表
        OrderListTests.class,
        // 接单
        ReceivingOrderTests.class,
        // 出餐
        OutingOrderTests.class,
        // 商家催骑手
        ComplainOrderTests.class,
        // 骑手登录
        com.miller.deliveryapp.login.LoginTests.class,
        // 上线
        DriverOnlineTests.class,
        // 新订单列表
        NewOrderListTests.class,
        // 待取餐列表
        PickUpListTests.class,
        // 待配送列表
        DeliveryListTests.class
})
@Suite
@SuiteDisplayName("测试订单场景")
public class DebugScenario {
}
