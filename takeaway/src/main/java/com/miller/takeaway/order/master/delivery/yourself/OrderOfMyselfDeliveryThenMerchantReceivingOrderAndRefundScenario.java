package com.miller.takeaway.order.master.delivery.yourself;

import com.miller.merchant.order.waiting.lack.OrderLackProductTests;
import com.miller.merchant.order.waiting.receiving.ReceivingOrderTests;
import com.miller.service.framework.annotation.TestCase;
import com.miller.userapp.order.create.CreateOrderByMyselfDeliveryTests;
import com.miller.userapp.pay.balance.PayByBalanceTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * 【主干场景】【用户自取】用户选择配送方式为自取，支付成功后商家接单后选择退单，订单主流程结束
 * <p>
 * 流程：用户下单->自取->支付成功->待接单->等待商家接单（7）->接单并备餐（9）->备餐中（10）->商家退单（11）->缺菜-退菜->订单结束
 * </p>
 * <p>
 *     <ul>覆盖路径
 *         <li>路径: 1，3， 4， 5， 7，9，10，11</li>
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
        // 商家-待接单-缺菜-退菜-下架一小时
        OrderLackProductTests.class,

        // 订单结束

})
@Suite
@SuiteDisplayName("【主干场景】【用户自取】用户选择配送方式为自取，支付成功后商家接单后选择退单，订单主流程结束")
@TestCase(testCaseID = "01HJQY9HHVSJ7MHH6D1AHW2W99", name = "【主干场景】【用户自取】用户选择配送方式为自取，支付成功后商家接单后选择退单，订单主流程结束")
public class OrderOfMyselfDeliveryThenMerchantReceivingOrderAndRefundScenario {
}
