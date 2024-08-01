package com.miller.takeaway.order.master.delivery.merchant;

import com.miller.merchant.login.MerchantLoginTests;
import com.miller.merchant.order.delivery.MerchantConfirmUserReceivedOrderTests;
import com.miller.merchant.order.outing.OutingOrderTests;
import com.miller.merchant.order.waiting.receiving.ReceivingOrderTests;
import com.miller.service.framework.annotation.Scenario;
import com.miller.userapp.module.home.login.UserLoginTests;
import com.miller.userapp.module.order.confirm.ConfirmOrderStatusTests;
import com.miller.userapp.module.order.create.CreateOrderByMerchantDeliveryTests;
import com.miller.userapp.module.order.evaluate.EvaluateOrderTests;
import com.miller.userapp.module.pay.balance.PayByBalanceTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * 【主干场景】【商家配送】用户选择配送方式为配送，支付成功，商家配送，用户确认已送达，订单主流程结束
 * <p>
 * 流程：用户下单->配送->支付成功->待接单->等待商家接单->接单并备餐->备餐中->商家配送（26）->出餐（27）->商家点击用户已取餐->用户确认取餐（18）
 * ->确认送到（20）->订单主流程结束（22）
 * </p>
 * <p>
 *     <ul>覆盖路径
 *         <li>路径: 1， 2， 4， 5， 7， 9， 10， 12， 26， 27， 28， 20， 22</li>
 *     </ul>
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/28 18:05:28
 */
@SelectClasses({
        // 用户登录
        UserLoginTests.class,
        // 创建订单-商家配送
        CreateOrderByMerchantDeliveryTests.class,
        // 支付订单
        PayByBalanceTests.class,

        // 商家登录APP
        MerchantLoginTests.class,
        // 商家接单
        ReceivingOrderTests.class,
        // 商家-出餐
        OutingOrderTests.class,
        // 商家配送。商家-配送中列表-商家点击用户已取餐
        MerchantConfirmUserReceivedOrderTests.class,

        // 用户确认订单已送达。注意：需要订单被【骑手/商家】派送完成之后执行
        ConfirmOrderStatusTests.class,
        // 用户评价订单
        EvaluateOrderTests.class,

        // 商家回复评论
        com.miller.merchant.order.evaluate.EvaluateOrderTests.class,
        // 订单流程结束
})
@Suite
@SuiteDisplayName("【主干场景】【商家配送】用户选择配送方式为配送，支付成功，商家配送，用户确认已送达，订单主流程结束")
@Scenario(scenarioID = "01HJQY9HH260WG8SH4S19CQY4Q",
        scenarioName = "【主干场景】【商家配送】用户选择配送方式为配送，支付成功，商家配送，用户确认已送达，订单主流程结束",
        developmentTime = 12 * 60, maintenanceTime = 30, manualTestTime = 60)
public class OrderOfMerchantDeliveryScenarioTests {
}
