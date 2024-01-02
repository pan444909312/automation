package com.miller.takeaway.order;

import com.miller.takeaway.order.master.delivery.merchant.OrderOfMerchantDeliveryScenario;
import com.miller.takeaway.order.master.delivery.merchant.OrderOfMerchantDeliveryThenRefundScenario;
import com.miller.takeaway.order.master.delivery.platform.OrderOfPlatformDeliveryContainOutingOrderAndComplainOrderScenario;
import com.miller.takeaway.order.master.delivery.platform.OrderOfPlatformDeliveryScenario;
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
        OrderOfPlatformDeliveryScenario.class,
        OrderOfPlatformDeliveryContainOutingOrderAndComplainOrderScenario.class,
        OrderOfMerchantDeliveryScenario.class,
        OrderOfMerchantDeliveryThenRefundScenario.class,
})
@Suite
@SuiteDisplayName("调试场景集")
public class DebugScenarios {
}
