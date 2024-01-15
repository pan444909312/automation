package com.miller.takeaway.order.branch.settlement;

import com.miller.service.framework.annotation.TestCase;
import com.miller.userapp.order.shopping.car.ShoppingCarTests;
import com.miller.userapp.order.shopping.settlement.SettlementCarTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 *
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/15 14:16:47
 */
@SelectClasses({
        // 用户登录
        com.miller.userapp.login.LoginTests.class,
        // 添加商品到购物车
        ShoppingCarTests.class,
        // 创建订单-结算
        SettlementCarTests.class,
})
@TestCase(testCaseID = "01HK72EK733AS48DCMJ8MCRGCM", name = "【分支场景】【订单结算】订单金额=商品小记+打包费+配送费")
@SuiteDisplayName("【分支场景】【订单结算】订单金额=商品小计+打包费+配送费")
@Suite
public class SettlementContainPackageDeliveryFee {
}
