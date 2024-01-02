package com.miller.userapp.order.create.provider;

import com.miller.userapp.order.create.request.CreateOrderRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * 数据提供者-创建订单
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/8 15:44:33
 */
@SuppressWarnings("unused")
public class CreateOrderDataProvider {
    /**
     * 创建订单数据提供者-平台配送
     */
    static Stream<Arguments> createOrderByPlatformDelivery() {
        // 1. 选择配送方式数据
        CreateOrderRequestDTO createOrderByPlatformDelivery = new CreateOrderRequestDTO();
        createOrderByPlatformDelivery.setAddressId(1398663384L);
        // 0=商家配送；1=平台配送；2=自取
        createOrderByPlatformDelivery.setDeliveryType("1");
        createOrderByPlatformDelivery.setDeliveryTime("尽快送达");
        createOrderByPlatformDelivery.setFixedPrice(12000);
        createOrderByPlatformDelivery.setIsOnlinePay(true);
        // 为什么前端传的是1，服务器用的是  boolean
        createOrderByPlatformDelivery.setNeedNumberMasking(true);
        createOrderByPlatformDelivery.setPayType(16);
        createOrderByPlatformDelivery.setPlatform("2");
        // 这里为什么只能传字符串，不能传数组么。。。 服务端应该改成请求体为json
        createOrderByPlatformDelivery.setProductCartList("[{\"skuId\":0,\"purchaseTime\":1701350720797,\"productId\":81669204}]");
        createOrderByPlatformDelivery.setShopId(59750820L);
        createOrderByPlatformDelivery.setUseVoucherTemplate(0);
        createOrderByPlatformDelivery.setRemark("自动化测试创建订单");
        // 选择自取时需要传联系电话。但是我发现配送传这个字段也没关系
        createOrderByPlatformDelivery.setUserPhone("86 18711110002");

        return Stream.of(Arguments.of(createOrderByPlatformDelivery));
    }

    /**
     * 创建订单数据提供者-商家配送
     */
    static Stream<Arguments> createOrderByMerchantDelivery() {
        // 1. 选择配送方式数据
        CreateOrderRequestDTO createOrderByMerchantDelivery = new CreateOrderRequestDTO();
        createOrderByMerchantDelivery.setAddressId(1398663384L);
        // 0=商家配送；1=平台配送；2=自取
        createOrderByMerchantDelivery.setDeliveryType("0");
        createOrderByMerchantDelivery.setDeliveryTime("尽快送达");
        createOrderByMerchantDelivery.setFixedPrice(12000);
        createOrderByMerchantDelivery.setIsOnlinePay(true);

        createOrderByMerchantDelivery.setPayType(16);
        createOrderByMerchantDelivery.setPlatform("1");
        // 这里为什么只能传字符串，不能传数组么。。。 服务端应该改成请求体为json
        createOrderByMerchantDelivery.setProductCartList("[{\"skuId\":0,\"productId\":81669204}]");
        createOrderByMerchantDelivery.setShopId(59750820L);
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
     * 创建订单数据提供者-用户自取
     */
    static Stream<Arguments> createOrderByMyselfDelivery() {
        // 1. 选择配送方式数据

        CreateOrderRequestDTO createOrderByMyselfDelivery = new CreateOrderRequestDTO();
        createOrderByMyselfDelivery.setDeliveryTime("尽快取餐");
        // 0=商家配送；1=平台配送；2=自取
        createOrderByMyselfDelivery.setDeliveryType("2");
        // 用户自取的价格是不一样的
        createOrderByMyselfDelivery.setFixedPrice(9900);
        // 选择自取/商家配送时需要传联系电话
        createOrderByMyselfDelivery.setUserPhone("86 18711110002");
        createOrderByMyselfDelivery.setTablewareCount(1);
        createOrderByMyselfDelivery.setOrderReqType(1);
        createOrderByMyselfDelivery.setRemark("【自动化测试】创建订单,用户自取");
        createOrderByMyselfDelivery.setPlatform("1");
        createOrderByMyselfDelivery.setAddressId(0L);
        // 这里为什么只能传字符串，不能传数组么。。。 服务端应该改成请求体为json
        createOrderByMyselfDelivery.setProductCartList("[{\"skuId\":0,\"productId\":81669204}]");
        createOrderByMyselfDelivery.setPayType(16);
        createOrderByMyselfDelivery.setVerify("0");
        createOrderByMyselfDelivery.setShopId(59750820L);
        createOrderByMyselfDelivery.setNeedNumberMasking(false);
        createOrderByMyselfDelivery.setIsOnlinePay(true);
        return Stream.of(Arguments.of(createOrderByMyselfDelivery));
    }
}
