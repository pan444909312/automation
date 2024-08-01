package com.miller.takeaway.order.master.delivery.yourself;

import com.miller.erp.login.ERPLoginTests;
import com.miller.erp.service.customer.refund.duty.DutyTests;
import com.miller.service.framework.annotation.Scenario;
import com.miller.userapp.module.home.login.UserLoginTests;
import com.miller.userapp.module.order.create.CreateOrderByMyselfDeliveryTests;
import com.miller.userapp.module.order.refund.submit.SubmitRefundTests;
import com.miller.userapp.module.pay.balance.PayByBalanceTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * 【主干场景】【用户自取】用户选择配送方式为自取，支付成功后申请退款，订单主流程结束
 * <p>
 * 流程：用户下单->自取->支付成功->待接单->申请售后（6）->订单结束
 * </p>
 * <p>
 *     <ul>覆盖路径
 *         <li>路径: 1，3，4，5，6</li>
 *     </ul>
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/3 14:05:28
 */
@SelectClasses({
        // 用户登录
        UserLoginTests.class,
        // 创建订单-用户自取
        CreateOrderByMyselfDeliveryTests.class,
        // 支付订单
        PayByBalanceTests.class,

        // 用户-申请售后->申请退款-提交
        SubmitRefundTests.class,
        // ERP-登录
        ERPLoginTests.class,
        // ERP-客户服务-退款审核-定责
        DutyTests.class,
        // 订单结束

})
@Suite
@SuiteDisplayName("【主干场景】【用户自取】用户选择配送方式为自取，支付成功后申请退款，订单主流程结束")
@Scenario(scenarioID = "01HJQY9HHMVR22YM50MK94NV0F",
        scenarioName = "【主干场景】【用户自取】用户选择配送方式为自取，支付成功后申请退款，订单主流程结束",
        developmentTime = 60, maintenanceTime = 0, manualTestTime = 30)
public class OrderOfMyselfDeliveryThenRefundScenarioTests {
}
