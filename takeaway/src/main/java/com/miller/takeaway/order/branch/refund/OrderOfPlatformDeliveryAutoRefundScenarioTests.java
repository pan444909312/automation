package com.miller.takeaway.order.branch.refund;

import com.miller.service.framework.annotation.Scenario;
import com.miller.userapp.module.home.login.UserLoginTests;
import com.miller.userapp.module.order.create.CreateOrderByPlatformDeliveryTests;
import com.miller.userapp.module.order.refund.submit.SubmitRefundTests;
import com.miller.userapp.module.pay.balance.PayByBalanceTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * 【分支场景】【申请售后】待接单阶段，申请售后，系统自动审核通过，自动退款
 * <p>
 * 流程：用户下单->配送->支付成功->用户申请售后（6）->系统自动退款成功->订单结束
 * </p>
 * <p>
 *     <ul>覆盖路径
 *         <li>覆盖: 1， 2， 4， 5，6</li>
 *     </ul>
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/2 14:53:01
 */
@SelectClasses({
        // 用户登录
        UserLoginTests.class,
        // 创建订单-平台配送
        CreateOrderByPlatformDeliveryTests.class,
        // 支付订单
        PayByBalanceTests.class,
        // 用户-申请售后->申请退款-提交
        SubmitRefundTests.class,
        // 未接单提交退款，系统自动审核通过，自动退款
})
@Suite
@SuiteDisplayName("【分支场景】【申请售后】待接单阶段，申请售后，系统自动审核通过，自动退款")
@Scenario(scenarioID = "01HJQH3Y82QYNFVEVCWQQQ93FH", scenarioName = "【分支场景】【申请售后】待接单阶段，申请售后，系统自动审核通过，自动退款"
        , developmentTime = 3 * 60, maintenanceTime = 60, manualTestTime = 60)
public class OrderOfPlatformDeliveryAutoRefundScenarioTests {
}
