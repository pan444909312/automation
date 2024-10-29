package com.miller.deliveryapp.module.order;

import com.miller.deliveryapp.module.login.DriverLoginTests;
import com.miller.deliveryapp.module.order.complete.*;
import com.miller.deliveryapp.module.order.create.CreateDeliveryOrderBySqlTests;
import com.miller.deliveryapp.module.order.grab.GrabDeliveryOrderTests;
import com.miller.deliveryapp.module.driver.online.DriverOnlineTests;
import com.miller.service.framework.annotation.Scenario;
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
@Scenario(scenarioID = "01JBBNREVYZG830J5CZ2XZPZPS",
        scenarioName = "【主干场景】骑手完单流程：造D侧派送订单->骑手登录app->上线->抢单->到店->未出餐->取餐->送达",
        developmentTime = 16 * 60, maintenanceTime = 0, manualTestTime = 60)
@Suite
@SuiteDisplayName("【主干场景】骑手完单流程：造D侧派送订单->骑手登录app->上线->抢单->到店->未出餐->取餐->送达")
public class CompleteDeliveryOrderScenarioTest {
}
