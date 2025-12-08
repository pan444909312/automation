package com.miller.pandafresh.testcase.module.b2b.order.payment;

import com.miller.pandafresh.testcase.module.b2b.order.getpaymentmethods.GetPaymentMethods_Tests;
import com.miller.pandafresh.testcase.module.b2b.order.paymentpattern.PaymentPattern_Tests;
import com.miller.service.framework.annotation.Scenario;
import org.junit.jupiter.api.DisplayName;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * createOrder
 *
 * @author zhangpei
 * @version 2.0
 * @since 2025/10/24 11:34:03
 */
@SelectClasses({
        GetPaymentMethods_Tests.class,
        PaymentPattern_Tests.class,
        Payment.class,
        PaymentIntentsConfirm.class
})
@Scenario(
        scenarioID = "01KBYC5Y6N0Z0Z401A5JVC4DMF", // 自动生成，不要修改
        scenarioName = "B2B订单-支付成功",
        author = "zhangpei@hungrypandagroup.com", // 配置本机 Git email 后可自动生成
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 3)
@DisplayName("B2B订单-支付成功")
@Suite
public class PaymentImmediate_Tests {

} 