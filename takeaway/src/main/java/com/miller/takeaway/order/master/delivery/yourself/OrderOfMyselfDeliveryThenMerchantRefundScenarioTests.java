package com.miller.takeaway.order.master.delivery.yourself;

import com.miller.merchant.login.MerchantLoginTests;
import com.miller.merchant.order.waiting.lack.OrderLackProductTests;
import com.miller.service.framework.annotation.TestCase;
import com.miller.userapp.module.home.login.UserLoginTests;
import com.miller.userapp.order.create.CreateOrderByMyselfDeliveryTests;
import com.miller.userapp.module.pay.balance.PayByBalanceTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * 【主干场景】【用户自取】用户选择配送方式为自取，支付成功后商家未接单选择退单(退菜)，订单主流程结束
 * <p>
 * 流程：用户下单->自取->支付成功->待接单->等待商家接单（7）->商家退单（8）->缺菜->退菜->订单结束
 * </p>
 * <p>
 *     <ul>覆盖路径
 *         <li>路径: 1，3，4，5，7，8</li>
 *         <li>路径: RO1，RO2，RO3，RO8，RO9，RO10</li>
 *     </ul>
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/3 14:15:28
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
        // 商家-待接单-缺菜-退菜-下架一小时
        OrderLackProductTests.class,

        // 订单结束
})
@Suite
@SuiteDisplayName("【主干场景】【用户自取】用户选择配送方式为自取，支付成功后商家未接单选择退单(退菜)，订单主流程结束")
@TestCase(testCaseID = "01HJQY9HHRAHX76Y8QWWJDCNZ5", name = "【主干场景】【用户自取】用户选择配送方式为自取，支付成功后商家未接单选择退单(退菜)，订单主流程结束")
public class OrderOfMyselfDeliveryThenMerchantRefundScenarioTests {
}
