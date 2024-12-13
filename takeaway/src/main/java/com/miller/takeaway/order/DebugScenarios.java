package com.miller.takeaway.order;

import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.Scenario;
import com.miller.takeaway.order.branch.order.OrderOfPlatformDeliveryWithMemberScenarioTests;
import com.miller.takeaway.order.branch.order.OrderOfPlatformDeliveryWithVoucherScenarioTests;
import com.miller.takeaway.order.branch.refund.OrderOfPlatformDeliveryAutoRefundScenarioTests;
import com.miller.takeaway.order.branch.settlement.SettlementContainMemberScenarioTests;
import com.miller.takeaway.order.branch.settlement.SettlementContainPackageDeliveryFeeScenarioTests;
import com.miller.takeaway.order.master.delivery.merchant.OrderOfMerchantDeliveryScenarioTests;
import com.miller.takeaway.order.master.delivery.merchant.OrderOfMerchantDeliveryThenRefundScenarioTests;
import com.miller.takeaway.order.master.delivery.platform.OrderOfPlatformDeliveryContainOutingOrderAndComplainOrderScenarioTests;
import com.miller.takeaway.order.master.delivery.platform.OrderOfPlatformDeliveryScenarioTests;
import com.miller.takeaway.order.master.delivery.yourself.*;
import com.miller.takeaway.order.master.foodcity.OrderOfFoodCityAllStallReceivingOrderThenMasterOrderStatusIsDoneScenarioTests;
import com.miller.takeaway.order.master.foodcity.OrderOfFoodCityMasterOrderRecivingOrderAllStallOrderStatusAutoChangeScenarioTests;
import org.junit.jupiter.api.Disabled;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * 调试场景集
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/28 16:21:53
 */
@SelectClasses({
        // 【主干场景】【商家配送】用户选择配送方式为配送，支付成功，商家配送，用户确认已送达，订单主流程结束
        OrderOfMerchantDeliveryScenarioTests.class,
        // 【主干场景】【商家配送】用户选择配送方式为配送，支付成功，商家配送，用户确认已送达，申请售后，订单结束
        OrderOfMerchantDeliveryThenRefundScenarioTests.class,

        // 【主干场景】【平台配送】用户选择配送方式为配送，商家出餐、崔骑手，平台骑手接单后取餐完成送达，用户确认送达，订单主流程结束
        OrderOfPlatformDeliveryScenarioTests.class,
        // 【主干场景】【平台配送】用户选择配送方式为配送，商家出餐、崔骑手，平台骑手接单后取餐完成送达，用户确认送达，订单主流程结束
        OrderOfPlatformDeliveryContainOutingOrderAndComplainOrderScenarioTests.class,

        // 【主干场景】【用户自取】
        OrderOfMyselfDeliveryThenMerchantConfirmOrderAndUserConfirmOrderScenarioTests.class, OrderOfMyselfDeliveryThenMerchantOutOrderAndConfirmOrderScenarioTests.class, OrderOfMyselfDeliveryThenMerchantOutOrderAndUserConfirmOrderAndRefundScenarioTests.class, OrderOfMyselfDeliveryThenMerchantReceivingOrderAndRefundScenarioTests.class, OrderOfMyselfDeliveryThenMerchantRefundScenarioTests.class, OrderOfMyselfDeliveryThenRefundScenarioTests.class, OrderOfMyselfDeliveryThenUserConfirmOrderScenarioTests.class,

        // 【主干场景】【美食城订单】美食城账号接单之后，下面所有档口的订单状态自动更新
        OrderOfFoodCityMasterOrderRecivingOrderAllStallOrderStatusAutoChangeScenarioTests.class,
        // 【主干场景】【美食城订单】美食城下的所有档口账号接单之后，美食城订单状态更新为完成
        OrderOfFoodCityAllStallReceivingOrderThenMasterOrderStatusIsDoneScenarioTests.class,

        // 【分支场景】【申请售后】待接单阶段，申请售后，系统自动审核通过，自动退款
        OrderOfPlatformDeliveryAutoRefundScenarioTests.class,
        // 【分支场景】【平台配送】【会员合单】用户下单时开通会员
        OrderOfPlatformDeliveryWithMemberScenarioTests.class,
        // 【分支场景】【平台配送】【代金券合单】用户下单时购买代金券
        OrderOfPlatformDeliveryWithVoucherScenarioTests.class,
        // 【分支场景】【订单结算】订单金额 = 商品小计 + 打包费 + 配送费
        SettlementContainPackageDeliveryFeeScenarioTests.class,
        // 【分支场景】【订单结算】订单金额 = 商品小计 + 打包费 + 原配送费 + 开通会员价格 - 配送费VIP优惠金额
        SettlementContainMemberScenarioTests.class,

})
@Disabled
//@Scenario(scenarioID = "01HK72EK786CKN8N2QYVXRJ1AD", scenarioName = "【调试场景集】", developmentTime = 1, maintenanceTime = 0, manualTestTime = 15)
@Suite
@SuiteDisplayName("调试场景集")
public class DebugScenarios {
/*
    public static void main(String[] args) {
        for(int i = 0; i < 30; i++){
            runTest();
        }
    }

    static void runTest() {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(
                        selectClass(DebugScenarios.class)
                ).build();
        executeRequest(request);
    }

    private static void executeRequest(LauncherDiscoveryRequest request) {
        Launcher launcher = LauncherFactory.create();

        // 概要结果监听器
        SummaryGeneratingListener summaryGeneratingListener = new SummaryGeneratingListener();
        // 自定义的结果监听器
        TestExecutionListener listener = new TestExecuteListener();
        // 注册监听器
        launcher.registerTestExecutionListeners(listener, summaryGeneratingListener);
        // 执行启动器
        launcher.execute(request);
        // 获取执行结果
        summaryGeneratingListener.getSummary().printTo(new PrintWriter(System.out));
    }
    */
}
