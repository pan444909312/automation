package com.miller.takeaway.order;

import com.miller.takeaway.order.branch.refund.OrderOfPlatformDeliveryAutoRefundScenarioTests;
import com.miller.takeaway.order.branch.settlement.SettlementContainPackageDeliveryFeeScenarioTests;
import com.miller.takeaway.order.master.delivery.merchant.OrderOfMerchantDeliveryScenarioTests;
import com.miller.takeaway.order.master.delivery.merchant.OrderOfMerchantDeliveryThenRefundScenarioTests;
import com.miller.takeaway.order.master.delivery.platform.OrderOfPlatformDeliveryContainOutingOrderAndComplainOrderScenarioTests;
import com.miller.takeaway.order.master.delivery.platform.OrderOfPlatformDeliveryScenarioTests;
import com.miller.takeaway.order.master.delivery.yourself.*;
import com.miller.takeaway.order.master.foodcity.OrderOfFoodCityAllStallReceivingOrderThenMasterOrderStatusIsDoneScenarioTests;
import com.miller.takeaway.order.master.foodcity.OrderOfFoodCityMasterOrderRecivingOrderAllStallOrderStatusAutoChangeScenarioTests;
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
}
