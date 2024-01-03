package com.miller.takeaway.order.master.delivery.yourself;

import com.miller.merchant.order.waiting.lack.OrderChangeMenuTests;
import com.miller.merchant.order.waiting.receiving.ReceivingOrderTests;
import com.miller.service.framework.annotation.TestCase;
import com.miller.userapp.order.confirm.ConfirmOrderStatusTests;
import com.miller.userapp.order.create.CreateOrderByMyselfDeliveryTests;
import com.miller.userapp.pay.balance.PayByBalanceTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * 【主干场景】【用户自取】用户选择配送方式为自取，支付成功后商家接单后选择退单(换菜)，订单主流程结束
 * <p>
 * 流程：用户下单->自取->支付成功->待接单->等待商家接单（7）->接单并备餐（9）->备餐中（10）->
 * 商家退单（11）->缺菜->换菜->退回订单部分金额->自动退款
 * ->订单结束
 * </p>

 * <p>
 *     <ul>覆盖路径
 *         <li>路径: 1，3， 4， 5， 7，9，10，11</li>
 *         <li>路径: RO1， RO2， RO3， RO4， RO5， RO6， RO7</li>
 *     </ul>
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/3 14:25:28
 */
@SelectClasses({
        // 用户登录
        com.miller.userapp.login.LoginTests.class,
        // 创建订单-用户自取
        CreateOrderByMyselfDeliveryTests.class,
        // 支付订单
        PayByBalanceTests.class,

        // 商家登录APP
        com.miller.merchant.login.LoginTests.class,
        // 接单并备餐
        ReceivingOrderTests.class,
        // 商家-待接单-缺菜-换菜-下架一小时并退款x金额
        OrderChangeMenuTests.class,

        // 用户确认订单已送达。注意：需要订单被【骑手/商家】派送完成之后执行，用户自取除外。
        ConfirmOrderStatusTests.class,
        // 订单结束
})
@Suite
@SuiteDisplayName("【主干场景】【用户自取】用户选择配送方式为自取，支付成功后商家接单后选择退单(换菜)，订单主流程结束")
@TestCase(testCaseID = "01HJQY9HHVSJ7MHH6D1AHW2W99", name = "【主干场景】【用户自取】用户选择配送方式为自取，支付成功后商家接单后选择退单(换菜)，订单主流程结束")
public class OrderOfMyselfDeliveryThenMerchantReceivingOrderAndRefundScenario {
}
