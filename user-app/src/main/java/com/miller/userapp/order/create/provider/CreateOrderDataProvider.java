package com.miller.userapp.order.create.provider;

import com.hungrypanda.app.server.api.res.order.OrderReturnVO;
import com.miller.userapp.order.create.request.CreateOrderRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * 创建订单数据提供者
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/8 15:44:33
 */
@SuppressWarnings("unused")
public class CreateOrderDataProvider {
    /**
     * 创建订单数据提供者
     *
     * @return Stream<Arguments>
     */
    static Stream<Arguments> createOrderDataProviderFromDB() {
        // TODO 从数据库中获取数据
        // 1. 选择配送方式数据
        CreateOrderRequestDTO createOrderRequestDTOByDelivery = new CreateOrderRequestDTO();
        createOrderRequestDTOByDelivery.setAddressId(1398663384L);
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
}
