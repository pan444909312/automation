package com.miller.takeaway.order;

import com.miller.takeaway.order.branch.refund.OrderOfPlatformDeliveryAutoRefundScenario;
import com.miller.takeaway.order.branch.settlement.SettlementContainPackageDeliveryFee;
import com.miller.takeaway.order.master.delivery.merchant.OrderOfMerchantDeliveryScenario;
import com.miller.takeaway.order.master.delivery.merchant.OrderOfMerchantDeliveryThenRefundScenario;
import com.miller.takeaway.order.master.delivery.platform.OrderOfPlatformDeliveryContainOutingOrderAndComplainOrderScenario;
import com.miller.takeaway.order.master.delivery.platform.OrderOfPlatformDeliveryScenario;
import com.miller.takeaway.order.master.delivery.yourself.*;
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
        SettlementContainPackageDeliveryFee.class,
})
@Suite
@SuiteDisplayName("调试场景集")
public class DebugScenarios {
}
