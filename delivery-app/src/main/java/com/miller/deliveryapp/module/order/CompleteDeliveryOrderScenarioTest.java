package com.miller.deliveryapp.module.order;

import com.miller.deliveryapp.module.login.DriverLoginTests;
import com.miller.deliveryapp.module.order.complete.*;
import com.miller.deliveryapp.module.order.create.CreateDeliveryOrderBySqlTests;
import com.miller.deliveryapp.module.order.grab.GrabDeliveryOrderTests;
import com.miller.deliveryapp.module.driver.online.DriverOnlineTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@SelectClasses({
        // 用户登录
        DriverLoginTests.class,
        // 骑手上线
        DriverOnlineTests.class,
        //创建订单
        CreateDeliveryOrderBySqlTests.class,
        //骑手抢单
        GrabDeliveryOrderTests.class,
        //正常流程_骑手-已到店
        OnShopTest.class,
        //骑手app->等待商家出餐
        NonOutMealTest.class,
        //骑手app->正在取餐
        TakeMealTest.class,
        //骑手app->已完成送餐并拍照送达
        DeliveryConsumerTest.class,

})
@Suite
@SuiteDisplayName("骑手完单流程：登录app-->上线-->抢单-->到店-->取餐-->派送-->签收订单")
public class CompleteDeliveryOrderScenarioTest {
}
