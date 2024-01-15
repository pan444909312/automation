package com.miller.userapp.order.create.provider;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.app.server.api.req.order.ProductCart;
import com.miller.data.center.merchant.TestCaseDataForMerchantConstant;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.userapp.order.create.request.CreateOrderRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * 数据提供者_创建订单
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/8 15:44:33
 */
@SuppressWarnings("unused")
public class CreateOrderDataProvider {
    /**
     * 创建订单数据提供者_平台配送
     */
    static Stream<Arguments> createOrderByPlatformDelivery() {
        // 选择配送方式数据
        CreateOrderRequestDTO createOrderByPlatformDelivery = new CreateOrderRequestDTO();
        createOrderByPlatformDelivery.setAddressId(TestCaseDataForUserConstant.addressId);
        // 0=商家配送；1=平台配送；2=自取
        createOrderByPlatformDelivery.setDeliveryType("1");
        createOrderByPlatformDelivery.setDeliveryTime("尽快送达");
        // 商品价格。无需动态查询，初始化数据时就应当指定好的值
        createOrderByPlatformDelivery.setFixedPrice(12000);
        createOrderByPlatformDelivery.setIsOnlinePay(true);
        // 为什么前端传的是1，服务器用的是  boolean
        createOrderByPlatformDelivery.setNeedNumberMasking(true);
        createOrderByPlatformDelivery.setPayType(16);
        createOrderByPlatformDelivery.setPlatform("2");

        // 这里为什么只能传字符串，不能传数组么。。。 服务端应该改成请求体为json
        // createOrderByMerchantDelivery.setProductCartList("[{\"skuId\":0,\"productId\":81669204}]");
        List<ProductCart> productCarts = new ArrayList<>();
        ProductCart productCart = new ProductCart();
        productCart.setSkuId(TestCaseDataForMerchantConstant.skuId);
        productCart.setProductId(TestCaseDataForMerchantConstant.productId);
        productCarts.add(productCart);
        createOrderByPlatformDelivery.setProductCartList(JSON.toJSONString(productCarts));
        createOrderByPlatformDelivery.setShopId(TestCaseDataForMerchantConstant.shopId);


        createOrderByPlatformDelivery.setUseVoucherTemplate(0);
        createOrderByPlatformDelivery.setRemark("自动化测试创建订单");
        // 选择自取时需要传联系电话。但是我发现配送传这个字段也没关系
        createOrderByPlatformDelivery.setUserPhone("86 18711110002");

        return Stream.of(Arguments.of(createOrderByPlatformDelivery));
    }

    /**
     * 创建订单数据提供者_商家配送
     */
    static Stream<Arguments> createOrderByMerchantDelivery() {
        CreateOrderRequestDTO createOrderByMerchantDelivery = new CreateOrderRequestDTO();
        createOrderByMerchantDelivery.setAddressId(TestCaseDataForUserConstant.addressId);
        // 0=商家配送；1=平台配送；2=自取
        createOrderByMerchantDelivery.setDeliveryType("0");
        createOrderByMerchantDelivery.setDeliveryTime("尽快送达");
        // 商品价格。无需动态查询，初始化数据时就应当指定好的值
        createOrderByMerchantDelivery.setFixedPrice(12000);
        createOrderByMerchantDelivery.setIsOnlinePay(true);

        createOrderByMerchantDelivery.setPayType(16);
        createOrderByMerchantDelivery.setPlatform("1");

        // 这里为什么只能传字符串，不能传数组么。。。 服务端应该改成请求体为json
        // createOrderByMerchantDelivery.setProductCartList("[{\"skuId\":0,\"productId\":81669204}]");
        List<ProductCart> productCarts = new ArrayList<>();
        ProductCart productCart = new ProductCart();
        productCart.setSkuId(TestCaseDataForMerchantConstant.skuId);
        productCart.setProductId(TestCaseDataForMerchantConstant.productId);
        productCarts.add(productCart);
        createOrderByMerchantDelivery.setProductCartList(JSON.toJSONString(productCarts));
        createOrderByMerchantDelivery.setShopId(TestCaseDataForMerchantConstant.shopId);

        createOrderByMerchantDelivery.setUseVoucherTemplate(0);
        createOrderByMerchantDelivery.setRemark("自动化测试创建订单");
        // 选择自取/商家配送时需要传联系电话
        createOrderByMerchantDelivery.setUserPhone("86 18711110002");
        createOrderByMerchantDelivery.setTablewareCount(1);
        createOrderByMerchantDelivery.setOrderReqType(1);
        createOrderByMerchantDelivery.setVerify("0");
        return Stream.of(Arguments.of(createOrderByMerchantDelivery));
    }

    /**
     * 创建订单数据提供者_用户自取
     */
    static Stream<Arguments> createOrderByMyselfDelivery() {
        CreateOrderRequestDTO createOrderByMyselfDelivery = new CreateOrderRequestDTO();
        createOrderByMyselfDelivery.setDeliveryTime("尽快取餐");
        // 0=商家配送；1=平台配送；2=自取
        createOrderByMyselfDelivery.setDeliveryType("2");
        // 商品价格。用户自取的价格是不一样的。无需动态查询，初始化数据时就应当指定好的值
        createOrderByMyselfDelivery.setFixedPrice(9900);
        // 选择自取/商家配送时需要传联系电话
        createOrderByMyselfDelivery.setUserPhone("86 18711110002");
        createOrderByMyselfDelivery.setTablewareCount(1);
        createOrderByMyselfDelivery.setOrderReqType(1);
        createOrderByMyselfDelivery.setRemark("【自动化测试】创建订单,用户自取");
        createOrderByMyselfDelivery.setPlatform("1");
        createOrderByMyselfDelivery.setAddressId(0L);

        // 这里为什么只能传字符串，不能传数组么。。。 服务端应该改成请求体为json
        //createOrderByMyselfDelivery.setProductCartList("[{\"skuId\":0,\"productId\":81669204}]");
        List<ProductCart> productCarts = new ArrayList<>();
        ProductCart productCart = new ProductCart();
        productCart.setSkuId(TestCaseDataForMerchantConstant.skuId);
        productCart.setProductId(TestCaseDataForMerchantConstant.productId);
        productCarts.add(productCart);
        createOrderByMyselfDelivery.setProductCartList(JSON.toJSONString(productCarts));
        createOrderByMyselfDelivery.setShopId(TestCaseDataForMerchantConstant.shopId);

        createOrderByMyselfDelivery.setPayType(16);
        createOrderByMyselfDelivery.setVerify("0");
        createOrderByMyselfDelivery.setNeedNumberMasking(false);
        createOrderByMyselfDelivery.setIsOnlinePay(true);
        return Stream.of(Arguments.of(createOrderByMyselfDelivery));
    }

    /**
     * 创建订单数据提供者_美食城多档口订单
     */
    static Stream<Arguments> createOrderByBigCity() {
        CreateOrderRequestDTO createOrderByBigCity = new CreateOrderRequestDTO();
        createOrderByBigCity.setDeliveryTime("尽快送达");
        createOrderByBigCity.setTablewareCount(1);
        createOrderByBigCity.setUserPhone("86 18711110002");
        createOrderByBigCity.setOrderReqType(1);
        // 0=商家配送；1=平台配送；2=自取。美食城仅支持平台配送
        createOrderByBigCity.setDeliveryType("1");
        // 商品价格。无需动态查询，初始化数据时就应当指定好的值。每件商品价格100元，共2件商品，覆盖2个档口。
        createOrderByBigCity.setFixedPrice(23000);
        createOrderByBigCity.setPlatform("1");
        createOrderByBigCity.setAddressId(TestCaseDataForUserConstant.addressId);
        createOrderByBigCity.setPayType(16);
        createOrderByBigCity.setShopId(TestCaseDataForMerchantConstant.shopIdOfBigCity);
        createOrderByBigCity.setIsOnlinePay(true);
        // 为什么前端传的是1，服务器用的是  boolean
        createOrderByBigCity.setNeedNumberMasking(true);


        // 这里为什么只能传字符串，不能传数组么。。。 服务端应该改成请求体为json
        // createOrderByBigCity.setProductCartList("[{"productId":81742258,"skuId":0,"tagId":[]},{"productId":81744208,"skuId":0,"tagId":[]}]");
        List<ProductCart> productCarts = new ArrayList<>();
        // 商品1
        ProductCart productCart1 = new ProductCart();
        productCart1.setProductId(TestCaseDataForMerchantConstant.productIdOfBigCity1);
        productCart1.setSkuId(TestCaseDataForMerchantConstant.skuIdOfBigCity1);
        // 商品2
        ProductCart productCart2 = new ProductCart();
        productCart2.setProductId(TestCaseDataForMerchantConstant.productIdOfBigCity2);
        productCart2.setSkuId(TestCaseDataForMerchantConstant.skuIdOfBigCity2);
        // 添加商品1、商品2
        productCarts.add(productCart1);
        productCarts.add(productCart2);
        createOrderByBigCity.setProductCartList(JSON.toJSONString(productCarts));

        return Stream.of(Arguments.of(createOrderByBigCity));
    }
}
