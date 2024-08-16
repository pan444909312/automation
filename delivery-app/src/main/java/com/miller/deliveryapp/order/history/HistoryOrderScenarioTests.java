package com.miller.deliveryapp.order.history;

import com.miller.deliveryapp.login.DeliveryLoginByDefaultAccountTests;
import com.miller.deliveryapp.order.history.detail.HistoryOrderDetailTests;
import com.miller.deliveryapp.order.history.list.HistoryOrderListTests;

import com.miller.service.framework.annotation.Scenario;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * 场景：骑手登录app，获取历史订单列表，并进入一个订单的详情页
 *
 * @author chenchunxia
 * @version 1.0
 * @since 2024/04/22 14:30:01
 */
@SelectClasses({
        // 用户登录
        DeliveryLoginByDefaultAccountTests.class,
        // 获取历史订单列表
        HistoryOrderListTests.class,
        // 获取订单详情页
        HistoryOrderDetailTests.class,

})
@Suite
@SuiteDisplayName("骑手登录app，获取历史订单列表，并进入一个订单的详情页")
//@Scenario(scenarioID = "xxxxxxxxx", name = "骑手登录app，获取历史订单列表，并进入一个订单的详情页")
public class HistoryOrderScenarioTests {
}
