package com.miller.takeaway.order.branch.settlement;

import com.miller.service.framework.annotation.TestCase;
import com.miller.userapp.module.home.login.UserLoginTests;
import com.miller.userapp.module.order.shopping.car.ShoppingCarTests;
import com.miller.userapp.module.order.shopping.settlement.SettlementWithMemberTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * 【分支场景】【订单结算】订单金额 = 商品小计 + 打包费 + 原配送费 + 开通会员价格 - 配送费VIP优惠金额
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/22 17:26:47
 */
@SelectClasses({
        // 用户登录
        UserLoginTests.class,
        // 添加商品到购物车
        ShoppingCarTests.class,
        // 用户-结算-会员结算
        SettlementWithMemberTests.class,
})
@TestCase(testCaseID = "01HK72EK786CKN8N2QYVXRJ1AD", name = "【分支场景】【订单结算】订单金额 = 商品小计 + 打包费 + 原配送费 + 开通会员价格 - 配送费VIP优惠金额")
@SuiteDisplayName("【分支场景】【订单结算】订单金额 = 商品小计 + 打包费 + 原配送费 + 开通会员价格 - 配送费VIP优惠金额")
@Suite
public class SettlementContainMemberScenarioTests {
}
