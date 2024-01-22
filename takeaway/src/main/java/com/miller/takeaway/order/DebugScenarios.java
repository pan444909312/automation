package com.miller.takeaway.order;

import com.miller.service.framework.listenner.TestExecuteListener;
import com.miller.takeaway.order.branch.refund.OrderOfPlatformDeliveryAutoRefundScenarioTests;
import com.miller.takeaway.order.branch.settlement.SettlementContainPackageDeliveryFeeScenarioTests;
import com.miller.takeaway.order.master.delivery.merchant.OrderOfMerchantDeliveryScenarioTests;
import com.miller.takeaway.order.master.delivery.merchant.OrderOfMerchantDeliveryThenRefundScenarioTests;
import com.miller.takeaway.order.master.delivery.platform.OrderOfPlatformDeliveryContainOutingOrderAndComplainOrderScenarioTests;
import com.miller.takeaway.order.master.delivery.platform.OrderOfPlatformDeliveryScenarioTests;
import com.miller.takeaway.order.master.delivery.yourself.*;
import com.miller.takeaway.order.master.foodcity.OrderOfFoodCityAllStallReceivingOrderThenMasterOrderStatusIsDoneScenarioTests;
import com.miller.takeaway.order.master.foodcity.OrderOfFoodCityMasterOrderRecivingOrderAllStallOrderStatusAutoChangeScenarioTests;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;
import static org.junit.platform.launcher.TagFilter.excludeTags;
import static org.junit.platform.launcher.TagFilter.includeTags;

/**
 * 调试场景集
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/28 16:21:53
 */
@SelectClasses({
        // 主干场景-商家配送
        OrderOfMerchantDeliveryScenarioTests.class,
        OrderOfMerchantDeliveryThenRefundScenarioTests.class,

        // 主干场景-平台配送
        OrderOfPlatformDeliveryScenarioTests.class,
        OrderOfPlatformDeliveryContainOutingOrderAndComplainOrderScenarioTests.class,

        // 主干场景-用户自取
        OrderOfMyselfDeliveryThenMerchantConfirmOrderAndUserConfirmOrderScenarioTests.class,
        OrderOfMyselfDeliveryThenMerchantOutOrderAndConfirmOrderScenarioTests.class,
        OrderOfMyselfDeliveryThenMerchantOutOrderAndUserConfirmOrderAndRefundScenarioTests.class,
        OrderOfMyselfDeliveryThenMerchantReceivingOrderAndRefundScenarioTests.class,
        OrderOfMyselfDeliveryThenMerchantRefundScenarioTests.class,
        OrderOfMyselfDeliveryThenRefundScenarioTests.class,
        OrderOfMyselfDeliveryThenUserConfirmOrderScenarioTests.class,

        // 分支场景-平台配送
        OrderOfPlatformDeliveryAutoRefundScenarioTests.class,
        // 分支场景-订单结算
        SettlementContainPackageDeliveryFeeScenarioTests.class,

        // 【主干场景】【美食城订单】
        OrderOfFoodCityMasterOrderRecivingOrderAllStallOrderStatusAutoChangeScenarioTests.class,
        OrderOfFoodCityAllStallReceivingOrderThenMasterOrderStatusIsDoneScenarioTests.class,
})
@Suite
@SuiteDisplayName("调试场景集")
public class DebugScenarios {

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
}
