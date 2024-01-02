package com.miller.merchant;

import com.miller.merchant.login.LoginTests;
import com.miller.merchant.manage.product.ProductOnOrOffTests;
import com.miller.merchant.order.delivery.MerchantConfirmUserReceivedOrderTests;
import com.miller.merchant.order.details.OrderDetailsTests;
import com.miller.merchant.order.list.OrderListTests;
import com.miller.merchant.order.waiting.lack.OrderLackProductTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * 当需要执行多个测试类时可以通过此程序进行调试
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/06 18:57:00
 */
@SelectClasses({
        // 商家登录APP
        LoginTests.class,
        // 订单列表
//        OrderListTests.class,
        // 商家-订单详情
//        OrderDetailsTests.class,

        // 商家选择缺菜-换菜-下架一小时
        OrderLackProductTests.class,
        // 上架商品
//        ProductOnOrOffTests.class,
        // 接单
//        ReceivingOrderTests.class,
        // 出餐
//        OutingOrderTests.class,
        // 商家催骑手
//        ComplainOrderTests.class,

        // 配送中列表-商家点击"用户已取餐"
//        MerchantConfirmUserReceivedOrderTests.class,
        // 商家回复评论
//         EvaluateOrderTests.class,
})
@SuiteDisplayName("调试多个测试用例")
@Suite
public class DebugTestCases {
}
