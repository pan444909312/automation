package com.miller.takeaway.order.master.delivery.platform;

import com.miller.deliveryapp.driver.online.DriverOnlineTests;
import com.miller.deliveryapp.login.DeliveryLoginTests;
import com.miller.deliveryapp.order.neworder.grab.GrabOrderTests;
import com.miller.deliveryapp.order.status.ModifyOrderStatusTests;
import com.miller.merchant.login.MerchantLoginTests;
import com.miller.merchant.order.waiting.receiving.ReceivingOrderTests;
import com.miller.service.framework.annotation.Scenario;
import com.miller.userapp.module.home.login.UserLoginTests;
import com.miller.userapp.module.order.confirm.ConfirmOrderStatusTests;
import com.miller.userapp.module.order.create.CreateOrderByPlatformDeliveryTests;
import com.miller.userapp.module.order.evaluate.EvaluateOrderTests;
import com.miller.userapp.module.pay.balance.PayByBalanceTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * 【主干场景】【平台配送】用户选择配送方式为配送，平台骑手接单后取餐完成送达，用户确认送达，订单主流程结束
 * <p>
 * 流程：用户下单->配送->支付成功->待接单->等待商家接单->接单并备餐->备餐中->平台判定配送方式->熊猫配送->
 * 骑手接单（32）->到店取餐->商家未出餐，骑手在店等商家出餐->商家已出餐->骑手配送->拍照完成配送->确认送达->订单主流程结束
 * </p>
 * <p>
 *     <ul>覆盖路径
 *         <li>覆盖: 1，2，4，5，7，9，10，12，29，32，33，34，35，36，37，38，20，22</li>
 *         <li>覆盖: 1，3，4，5，7，9，10，11，12，13，14，16，17，18，20，22，39，25</li>
 *         <li>覆盖: 1，3，4，5，7，9，10，11，12，13，14，16，17，18，20，22，39，40，25</li>
 *     </ul>
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/28 13:53:01
 */
@SelectClasses({
        // 用户登录
        UserLoginTests.class,
        // 创建订单-平台配送
        CreateOrderByPlatformDeliveryTests.class,
        // 支付订单
        PayByBalanceTests.class,

        // 商家登录APP
        MerchantLoginTests.class,
        // 商家接单
        ReceivingOrderTests.class,

        // 骑手登录
        DeliveryLoginTests.class,
        // 上线
        DriverOnlineTests.class,
        // 骑手抢单
        GrabOrderTests.class,
        // 骑手-已到店->等待商家出餐->正在取餐->已完成送餐并拍照送达
        ModifyOrderStatusTests.class,

        // 用户确认订单已送达。注意：需要订单被骑手派送完成之后执行
        ConfirmOrderStatusTests.class,
        // 用户评价订单
        EvaluateOrderTests.class,

        // 商家回复评论
        com.miller.merchant.order.evaluate.EvaluateOrderTests.class,
        // 订单流程结束
})
@Suite
@SuiteDisplayName("【主干场景】【平台配送】用户选择配送方式为配送，商家出餐、崔骑手，平台骑手接单后取餐完成送达，用户确认送达，订单主流程结束")
@Scenario(scenarioID = "01HJQH3Y82QYNFVEVCWQQQ93FG",
        scenarioName = "【主干场景】【平台配送】用户选择配送方式为配送，平台骑手接单后取餐完成送达，用户确认送达，订单主流程结束",
        author = "shandongdong@hungrypandagroup.com", developmentTime = 12 * 60, maintenanceTime = 60, manualTestTime = 2 * 60)
public class OrderOfPlatformDeliveryScenarioTests {
}
