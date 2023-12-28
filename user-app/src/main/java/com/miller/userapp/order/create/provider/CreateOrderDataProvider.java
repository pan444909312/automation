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
        CreateOrderRequestDTO createOrderRequestDTOByDelivery = new CreateOrderRequestDTO();
        createOrderRequestDTOByDelivery.setAddressId(1398663384L);
        // 0=商家配送；1=平台配送；2=自取
        createOrderRequestDTOByDelivery.setDeliveryType("1");
        createOrderRequestDTOByDelivery.setDeliveryTime("尽快送达");
        createOrderRequestDTOByDelivery.setFixedPrice(12000);
        createOrderRequestDTOByDelivery.setIsOnlinePay(true);
        // 为什么前端传的是1，服务器用的是  boolean
        createOrderRequestDTOByDelivery.setNeedNumberMasking(true);
        createOrderRequestDTOByDelivery.setPayType(16);
        createOrderRequestDTOByDelivery.setPlatform("2");
        // 这里为什么只能传字符串，不能传数组么。。。 服务端应该改成请求体为json
        createOrderRequestDTOByDelivery.setProductCartList("[{\"skuId\":0,\"purchaseTime\":1701350720797,\"productId\":81669204}]");
        createOrderRequestDTOByDelivery.setShopId(59750820L);
        createOrderRequestDTOByDelivery.setUseVoucherTemplate(0);
        createOrderRequestDTOByDelivery.setRemark("自动化测试创建订单");
        // 选择自取时需要传联系电话。但是我发现配送传这个字段也没关系
        createOrderRequestDTOByDelivery.setUserPhone("86 18711110002");

        return Stream.of(Arguments.of(createOrderRequestDTOByDelivery));
    }

    /**
     * 创建订单数据提供者-商家配送
     */
    static Stream<Arguments> createOrderByMerchantDelivery() {
        // 1. 选择配送方式数据
        CreateOrderRequestDTO createOrderRequestDTOByDelivery = new CreateOrderRequestDTO();
        createOrderRequestDTOByDelivery.setAddressId(1398663384L);
        // 0=商家配送；1=平台配送；2=自取
        createOrderRequestDTOByDelivery.setDeliveryType("0");
        createOrderRequestDTOByDelivery.setDeliveryTime("尽快送达");
        createOrderRequestDTOByDelivery.setFixedPrice(12000);
        createOrderRequestDTOByDelivery.setIsOnlinePay(true);

        createOrderRequestDTOByDelivery.setPayType(16);
        createOrderRequestDTOByDelivery.setPlatform("1");
        // 这里为什么只能传字符串，不能传数组么。。。 服务端应该改成请求体为json
        createOrderRequestDTOByDelivery.setProductCartList("[{\"skuId\":0,\"productId\":81669204}]");
        createOrderRequestDTOByDelivery.setShopId(59750820L);
        createOrderRequestDTOByDelivery.setUseVoucherTemplate(0);
        createOrderRequestDTOByDelivery.setRemark("自动化测试创建订单");
        // 选择自取/商家配送时需要传联系电话
        createOrderRequestDTOByDelivery.setUserPhone("86 18711110002");
        createOrderRequestDTOByDelivery.setTablewareCount(1);
        createOrderRequestDTOByDelivery.setOrderReqType(1);
        createOrderRequestDTOByDelivery.setVerify("0");
        return Stream.of(Arguments.of(createOrderRequestDTOByDelivery));
    }
}
