package com.miller.takeaway.order;

import com.miller.deliveryapp.driver.online.DriverOnlineTests;
import com.miller.deliveryapp.order.delivery.list.DeliveryListTests;
import com.miller.deliveryapp.order.neworder.grab.GrabOrderTests;
import com.miller.deliveryapp.order.neworder.list.NewOrderListTests;
import com.miller.deliveryapp.order.pickup.list.PickUpListTests;
import com.miller.deliveryapp.order.status.ModifyOrderStatusTests;
import com.miller.erp.service.customer.refund.duty.DutyTests;
import com.miller.merchant.order.complain.ComplainOrderTests;
import com.miller.merchant.order.list.OrderListTests;
import com.miller.merchant.order.outing.OutingOrderTests;
import com.miller.merchant.order.waiting.receiving.ReceivingOrderTests;
import com.miller.userapp.order.confirm.ConfirmOrderStatusTests;
import com.miller.userapp.order.create.CreateOrderByPlatformDeliveryTests;
import com.miller.userapp.order.refund.submit.SubmitRefundTests;
import com.miller.userapp.pay.balance.PayByBalanceTests;
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
        // 用户登录
        com.miller.userapp.login.LoginTests.class,
        // 创建订单-平台配送
        CreateOrderByPlatformDeliveryTests.class,
        // 支付订单
        PayByBalanceTests.class,

        // 商家登录APP
        com.miller.merchant.login.LoginTests.class,
        // 订单列表
        OrderListTests.class,
        // 商家-待接单-缺菜-退菜-下架一小时
//         OrderLackProductTests.class,
        // 接单并备餐
        ReceivingOrderTests.class,
        // 出餐
        OutingOrderTests.class,
        // 商家催骑手
        ComplainOrderTests.class,

        // 商家配送/用户自取。商家-配送中列表-商家点击用户已取餐
        // MerchantConfirmUserReceivedOrderTests.class,

        // 骑手登录
        com.miller.deliveryapp.login.LoginTests.class,
        // 上线
        DriverOnlineTests.class,
        // 新订单列表
        NewOrderListTests.class,
        // 骑手抢单
        GrabOrderTests.class,
        // 待取餐列表
        PickUpListTests.class,
        // 待配送列表
        DeliveryListTests.class,
        // 骑手-已到店->等待商家出餐->正在取餐->已完成送餐并拍照送达
        ModifyOrderStatusTests.class,

        // 用户确认订单已送达。注意：需要订单被【骑手/商家】派送完成之后执行，用户自取除外。
        ConfirmOrderStatusTests.class,
        // 用户评价订单
        com.miller.userapp.order.evaluate.EvaluateOrderTests.class,

        // 商家回复评论
        com.miller.merchant.order.evaluate.EvaluateOrderTests.class,

        // 用户-申请售后->申请退款-提交
        SubmitRefundTests.class,
        // ERP-登录
        com.miller.erp.login.LoginTests.class,
        // ERP-客户服务-退款审核-定责
        DutyTests.class,

})
@Suite
@SuiteDisplayName("测试订单场景")
public class DebugScenario {
}
