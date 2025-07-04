package com.miller.takeaway.order.master.delivery.yourself;

import com.miller.merchant.login.MerchantLoginTests;
import com.miller.merchant.order.delivery.MerchantConfirmUserReceivedOrderTests;
import com.miller.merchant.order.outing.OutingOrderTests;
import com.miller.merchant.order.waiting.receiving.ReceivingOrderTests;
import com.miller.service.framework.annotation.Scenario;
import com.miller.userapp.module.home.login.UserLoginTests;
import com.miller.userapp.module.order.confirm.ConfirmOrderStatusTests;
import com.miller.userapp.module.order.create.CreateOrderByMyselfDeliveryTests;
import com.miller.userapp.module.pay.balance.PayByBalanceTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * 【主干场景】【用户自取】用户选择配送方式为自取，支付成功，商家出餐，商家确认用户已取餐，用户确认取餐，订单主流程结束
 * <p>
 * 流程：用户下单->自取->支付成功->待接单->等待商家接单（7）->接单并备餐（9）->备餐中（10）->
 * 出餐(16)->商家确认用户已取餐（19）->用户确认取餐（18）->确认送到（20）
 * ->订单主流程结束（22）
 * </p>
 * <p>
 *     <ul>覆盖路径
 *         <li>路径: 1，3，4，5，7，9，10，11，12，13，14，16，17，19，22</li>
 *     </ul>
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/3 14:55:28
 */
@SelectClasses({
        // 用户登录
        UserLoginTests.class,
        // 创建订单-用户自取
        CreateOrderByMyselfDeliveryTests.class,
        // 支付订单
        PayByBalanceTests.class,

        // 商家登录APP
        MerchantLoginTests.class,
        // 接单并备餐
        ReceivingOrderTests.class,
        // 出餐
        OutingOrderTests.class,
        // 商家-配送中列表-商家点击用户已取餐
        MerchantConfirmUserReceivedOrderTests.class,
        // 用户确认订单已送达。注意：需要订单被【骑手/商家】派送完成之后执行，用户自取除外。
        ConfirmOrderStatusTests.class,

        // 订单主流程结束
})
@Suite
@SuiteDisplayName("【主干场景】【用户自取】用户选择配送方式为自取，支付成功，商家出餐，商家确认用户已取餐，用户确认取餐，订单主流程结束")
@Scenario(scenarioID = "01HJQY9HJ1H4TNSDDMEYJ8DBB5",
        scenarioName = "【主干场景】【用户自取】用户选择配送方式为自取，支付成功，商家出餐，商家确认用户已取餐，用户确认取餐，订单主流程结束",
        author = "shandongdong@hungrypandagroup.com", developmentTime = 2 * 60, maintenanceTime = 0, manualTestTime = 60)
public class OrderOfMyselfDeliveryThenMerchantOutOrderAndConfirmOrderScenarioTests {
}
