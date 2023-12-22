package com.miller.deliveryapp;

import com.miller.deliveryapp.driver.online.DriverOnlineTests;
import com.miller.deliveryapp.login.LoginTests;
import com.miller.deliveryapp.order.delivery.list.DeliveryListTests;
import com.miller.deliveryapp.order.neworder.grab.GrabOrderTests;
import com.miller.deliveryapp.order.neworder.list.NewOrderListTests;
import com.miller.deliveryapp.order.pickup.list.PickUpListTests;
import com.miller.deliveryapp.order.status.ModifyOrderStatusTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * 当需要执行多个测试类时可以通过此程序进行调试
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/06 18:57:00
 */
@SelectClasses({
        // 骑手登录
        LoginTests.class,
        // 上线
        DriverOnlineTests.class,
        // 新订单列表
        NewOrderListTests.class,
        // 骑手抢单
        GrabOrderTests.class,
        // 待取餐列表
        PickUpListTests.class,
        // 待配送列表
        DeliveryListTests.class,
        // 骑手-已到店->等待商家出餐->正在取餐->已完成送餐并拍照送达
        ModifyOrderStatusTests.class,
})
//@SelectPackages("com.miller.deliveryapp")
@SuiteDisplayName("调试多个测试用例")
@Suite
public class DebugTestCases {
}
