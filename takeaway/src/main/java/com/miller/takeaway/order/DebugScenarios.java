package com.miller.takeaway.order;

import com.miller.takeaway.order.master.delivery.platform.OrderOfPlatformDeliveryContainOutingOrderAndComplainOrderScenario;
import com.miller.takeaway.order.master.delivery.platform.OrderOfPlatformDeliveryScenario;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * 场景调试
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/21 16:21:53
 */
@SelectClasses({
        OrderOfPlatformDeliveryScenario.class,
        OrderOfPlatformDeliveryContainOutingOrderAndComplainOrderScenario.class,
})
@Suite
@SuiteDisplayName("调试场景集")
public class DebugScenarios {
}
