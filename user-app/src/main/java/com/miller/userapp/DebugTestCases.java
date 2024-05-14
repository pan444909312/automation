package com.miller.userapp;

import com.miller.userapp.login.UserLoginTests;
import com.miller.userapp.newuserpopup.NewUserTests;
import com.miller.userapp.order.create.CreateOrderByPlatformDeliveryTests;
import com.miller.userapp.order.shopping.car.ShoppingCarTests;
import com.miller.userapp.pay.balance.PayByBalanceWithVoucherTests;
import com.miller.userapp.pay.card.AddCardRecordTest;
import com.miller.userapp.pay.card.CreatePaymentMethodTest;
import com.miller.userapp.pay.card.GetPaymentMethodsTest;
import com.miller.userapp.pay.nopassword.NoPasswordListTest;
import com.miller.userapp.pay.nopassword.NoPasswordStatusTest;
import com.miller.userapp.pay.payment.StripePaymentOnlyTest;
import com.miller.userapp.search.category.SearchCategoryTests;
import com.miller.userapp.search.channel.CategoryChannelTests;
import com.miller.userapp.search.modulelist.IndexOperateModuleListTests;
import com.miller.userapp.search.searchv2.SearchV2Tests;
import com.miller.userapp.userpack.UserPackTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * 当需要执行多个测试类时可以通过此程序进行调试
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/06 16:57:00
 */
@SelectClasses({
        // 用户登录
        UserLoginTests.class,
        // 添加商品到购物车
//        ShoppingCarTests.class,
        // 创建订单-结算
//        SettlementCarTests.class,
        // 创建订单-结算-会员结算
//        SettlementWithMemberTests.class,
        // 创建订单-平台配送
//        CreateOrderByPlatformDeliveryTests.class,
        // 创建订单-商家配送
//        CreateOrderByMerchantDeliveryTests.class,
        // 创建订单-用户自取
//        CreateOrderByMyselfDeliveryTests.class,
        // 创建订单-美食城订单
//        CreateOrderByFoodCityTests.class,
        // 创建订单-平台配送-代金券合单
//        CreateOrderByPlatformDeliveryWithVoucherTests.class,
        // 支付订单-余额支付-代金券合单
//        PayByBalanceWithVoucherTests.class,
//        // 支付订单
//        PayByBalanceTests.class,

        // 用户确认订单已送达。注意：需要订单被骑手派送完成之后执行
//        ConfirmOrderStatusTests.class,
        // 用户评价订单
//        EvaluateOrderTests.class,
        // 用户预申请退款。这个是一个流程提供者，场景中用不上，直接使用 SubmitRefundTests 即可，已经包含了申请退款流程
        // ApplyRefundTests.class,
        // 用户申请退款-提交
//        SubmitRefundTests.class,

        // 用户-订单列表
//        OrderListTests.class,
        //用户-添加卡
//        AddCardRecordTest.class

//        //用户-绑定stripe 卡
//        CreatePaymentMethodTest.class,
//        //用户-获取stripe 卡
//        GetPaymentMethodsTest.class,
        //用户-Stripe Payment【需要先下单】
//        StripePaymentOnlyTest.class

//        新人权益弹窗（未登录）
//        NewUserTests.class
//        自取频道商家列表
//        UserPackTests.class
//        搜索中间页面分类入口，点击进入品类页面
//        SearchCategoryTests.class,
//        CategoryChannelTests.class
//        首页热搜词返回，点击热搜词搜索结果校验
//        IndexOperateModuleListTests.class,
//        SearchV2Tests.class
//        获取免密支付方式列表
//        NoPasswordListTest.class
//        支付方式是否开启免密，0关闭1开启
//        NoPasswordStatusTest.class


})
@SuiteDisplayName("调试多个测试用例")
@Suite
public class DebugTestCases {
}
