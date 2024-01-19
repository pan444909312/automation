package com.miller.takeaway.order;

import com.miller.takeaway.order.branch.refund.OrderOfPlatformDeliveryAutoRefundScenario;
import com.miller.takeaway.order.branch.settlement.SettlementContainPackageDeliveryFeeScenario;
import com.miller.takeaway.order.master.delivery.merchant.OrderOfMerchantDeliveryScenario;
import com.miller.takeaway.order.master.delivery.merchant.OrderOfMerchantDeliveryThenRefundScenario;
import com.miller.takeaway.order.master.delivery.platform.OrderOfPlatformDeliveryContainOutingOrderAndComplainOrderScenario;
import com.miller.takeaway.order.master.delivery.platform.OrderOfPlatformDeliveryScenario;
import com.miller.takeaway.order.master.delivery.yourself.*;
import com.miller.takeaway.order.master.foodcity.OrderOfFoodCityMasterOrderRecivingOrderAllStallOrderStatusAutoChangeScenario;
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
        OrderOfMerchantDeliveryScenario.class,
        OrderOfMerchantDeliveryThenRefundScenario.class,

        // 主干场景-平台配送
        OrderOfPlatformDeliveryScenario.class,
        OrderOfPlatformDeliveryContainOutingOrderAndComplainOrderScenario.class,

        // 主干场景-用户自取
        OrderOfMyselfDeliveryThenMerchantConfirmOrderAndUserConfirmOrderScenario.class,
        OrderOfMyselfDeliveryThenMerchantOutOrderAndConfirmOrderScenario.class,
        OrderOfMyselfDeliveryThenMerchantOutOrderAndUserConfirmOrderAndRefundScenario.class,
        OrderOfMyselfDeliveryThenMerchantReceivingOrderAndRefundScenario.class,
        OrderOfMyselfDeliveryThenMerchantRefundScenario.class,
        OrderOfMyselfDeliveryThenRefundScenario.class,
        OrderOfMyselfDeliveryThenUserConfirmOrderScenario.class,

        // 分支场景-平台配送
        OrderOfPlatformDeliveryAutoRefundScenario.class,
        // 分支场景-订单结算
        SettlementContainPackageDeliveryFeeScenario.class,

        // 【主干场景】【美食城订单】用户在美食城下单,美食城账号接单
        OrderOfFoodCityMasterOrderRecivingOrderAllStallOrderStatusAutoChangeScenario.class,
})
@Suite
@SuiteDisplayName("调试场景集")
public class DebugScenarios {
}
