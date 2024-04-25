package com.miller.userapp.order.create.provider;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.app.server.api.req.order.ProductCart;
import com.hungrypanda.app.server.common.enums.StatusEnum;
import com.hungrypanda.app.server.common.enums.member.MemberCombinedTypeEnum;
import com.hungrypanda.app.server.common.enums.order.CreateOrderTypeEnum;
import com.hungrypanda.app.server.common.enums.order.OrderReqTypeEnum;
import com.hungrypanda.common.enums.common.PlatformEnum;
import com.miller.data.center.merchant.TestCaseDataForMerchantConstant;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.userapp.order.create.request.CreateOrderRequestDTO;
import com.miller.userapp.order.shopping.settlement.flow.SettlementFlow;
import com.miller.userapp.order.shopping.settlement.request.SettlementRequestDTO;
import com.miller.userapp.order.shopping.settlement.response.SettlementResponseDTO;
import com.panda.common.enums.DeliveryTypeEnum;
import com.panda.common.enums.PayTypeEnum;
import com.panda.common.enums.VoucherStatusEnum;
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
        createOrderByPlatformDelivery.setDeliveryType(String.valueOf(DeliveryTypeEnum.third_party.getCode()));
        createOrderByPlatformDelivery.setDeliveryTime("尽快送达");
        // 商品价格。无需动态查询，初始化数据时就应当指定好的值
        createOrderByPlatformDelivery.setFixedPrice(12000);
        createOrderByPlatformDelivery.setIsOnlinePay(Boolean.TRUE);
        // 为什么前端传的是1，服务器用的是  boolean
        createOrderByPlatformDelivery.setNeedNumberMasking(Boolean.TRUE);
        createOrderByPlatformDelivery.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        createOrderByPlatformDelivery.setPlatform(String.valueOf(PlatformEnum.ANDROID.getCode()));

        // 这里为什么只能传字符串，不能传数组么。。。 服务端应该改成请求体为json
        // createOrderByMerchantDelivery.setProductCartList("[{\"skuId\":0,\"productId\":81669204}]");
        List<ProductCart> productCarts = new ArrayList<>();
        ProductCart productCart = new ProductCart();
        productCart.setSkuId(TestCaseDataForMerchantConstant.skuId);
        productCart.setProductId(TestCaseDataForMerchantConstant.productId);
        productCarts.add(productCart);
        createOrderByPlatformDelivery.setProductCartList(JSON.toJSONString(productCarts));
        createOrderByPlatformDelivery.setShopId(TestCaseDataForMerchantConstant.shopId);

        createOrderByPlatformDelivery.setUseVoucherTemplate(VoucherStatusEnum.WAIT_USE.getCode());
        createOrderByPlatformDelivery.setRemark("【自动化测试】创建订单");
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
        createOrderByMerchantDelivery.setDeliveryType(String.valueOf(DeliveryTypeEnum.shop.getCode()));
        createOrderByMerchantDelivery.setDeliveryTime("尽快送达");
        // 商品价格。无需动态查询，初始化数据时就应当指定好的值
        createOrderByMerchantDelivery.setFixedPrice(12000);
        createOrderByMerchantDelivery.setIsOnlinePay(Boolean.TRUE);

        createOrderByMerchantDelivery.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        createOrderByMerchantDelivery.setPlatform(String.valueOf(PlatformEnum.ANDROID.getCode()));

        // 这里为什么只能传字符串，不能传数组么。。。 服务端应该改成请求体为json
        // createOrderByMerchantDelivery.setProductCartList("[{\"skuId\":0,\"productId\":81669204}]");
        List<ProductCart> productCarts = new ArrayList<>();
        ProductCart productCart = new ProductCart();
        productCart.setSkuId(TestCaseDataForMerchantConstant.skuId);
        productCart.setProductId(TestCaseDataForMerchantConstant.productId);
        productCarts.add(productCart);
        createOrderByMerchantDelivery.setProductCartList(JSON.toJSONString(productCarts));
        createOrderByMerchantDelivery.setShopId(TestCaseDataForMerchantConstant.shopId);

        createOrderByMerchantDelivery.setUseVoucherTemplate(VoucherStatusEnum.WAIT_USE.getCode());
        createOrderByMerchantDelivery.setRemark("【自动化测试】创建订单");
        // 选择自取/商家配送时需要传联系电话
        createOrderByMerchantDelivery.setUserPhone("86 18711110002");
        createOrderByMerchantDelivery.setTablewareCount(1);
        createOrderByMerchantDelivery.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());
        createOrderByMerchantDelivery.setVerify(String.valueOf(StatusEnum.NO.getType()));
        return Stream.of(Arguments.of(createOrderByMerchantDelivery));
    }

    /**
     * 创建订单数据提供者_用户自取
     */
    static Stream<Arguments> createOrderByMyselfDelivery() {
        CreateOrderRequestDTO createOrderByMyselfDelivery = new CreateOrderRequestDTO();
        createOrderByMyselfDelivery.setDeliveryTime("尽快取餐");
        // 0=商家配送；1=平台配送；2=自取
        createOrderByMyselfDelivery.setDeliveryType(String.valueOf(DeliveryTypeEnum.user.getCode()));
        // 商品价格。用户自取的价格是不一样的。无需动态查询，初始化数据时就应当指定好的值
        createOrderByMyselfDelivery.setFixedPrice(9900);
        // 选择自取/商家配送时需要传联系电话
        createOrderByMyselfDelivery.setUserPhone("86 18711110002");
        createOrderByMyselfDelivery.setTablewareCount(1);
        createOrderByMyselfDelivery.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());
        createOrderByMyselfDelivery.setRemark("【自动化测试】创建订单,用户自取");
        createOrderByMyselfDelivery.setPlatform(String.valueOf(PlatformEnum.ANDROID.getCode()));

        // 这里为什么只能传字符串，不能传数组么。。。 服务端应该改成请求体为json
        //createOrderByMyselfDelivery.setProductCartList("[{\"skuId\":0,\"productId\":81669204}]");
        List<ProductCart> productCarts = new ArrayList<>();
        ProductCart productCart = new ProductCart();
        productCart.setSkuId(TestCaseDataForMerchantConstant.skuId);
        productCart.setProductId(TestCaseDataForMerchantConstant.productId);
        productCarts.add(productCart);
        createOrderByMyselfDelivery.setProductCartList(JSON.toJSONString(productCarts));
        createOrderByMyselfDelivery.setShopId(TestCaseDataForMerchantConstant.shopId);

        createOrderByMyselfDelivery.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        createOrderByMyselfDelivery.setVerify(String.valueOf(StatusEnum.NO.getType()));
        createOrderByMyselfDelivery.setNeedNumberMasking(Boolean.FALSE);
        createOrderByMyselfDelivery.setIsOnlinePay(Boolean.TRUE);
        return Stream.of(Arguments.of(createOrderByMyselfDelivery));
    }

    /**
     * 创建订单数据提供者_美食城多档口订单
     */
    static Stream<Arguments> createOrderByFoodCity() {
        CreateOrderRequestDTO createOrderByFoodCity = new CreateOrderRequestDTO();
        createOrderByFoodCity.setRemark("【自动化测试】创建美食城订单");
        createOrderByFoodCity.setDeliveryTime("尽快送达");
        createOrderByFoodCity.setTablewareCount(1);
        createOrderByFoodCity.setUserPhone("86 18711110002");
        createOrderByFoodCity.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());
        // 0=商家配送；1=平台配送；2=自取。美食城仅支持平台配送
        createOrderByFoodCity.setDeliveryType(String.valueOf(DeliveryTypeEnum.third_party.getCode()));
        // 商品价格。无需动态查询，初始化数据时就应当指定好的值。每件商品价格100元，共2件商品，覆盖2个档口。
        createOrderByFoodCity.setFixedPrice(23000);
        createOrderByFoodCity.setPlatform(String.valueOf(PlatformEnum.ANDROID.getCode()));
        createOrderByFoodCity.setAddressId(TestCaseDataForUserConstant.addressId);
        createOrderByFoodCity.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        createOrderByFoodCity.setShopId(TestCaseDataForMerchantConstant.shopIdOfFoodCity);
        createOrderByFoodCity.setIsOnlinePay(Boolean.TRUE);
        // 为什么前端传的是1，服务器用的是  boolean
        createOrderByFoodCity.setNeedNumberMasking(Boolean.TRUE);


        // 这里为什么只能传字符串，不能传数组么。。。 服务端应该改成请求体为json
        // createOrderByFoodCity.setProductCartList("[{"productId":81742258,"skuId":0,"tagId":[]},{"productId":81744208,"skuId":0,"tagId":[]}]");
        List<ProductCart> productCarts = new ArrayList<>();
        // 商品1
        ProductCart productCart1 = new ProductCart();
        productCart1.setProductId(TestCaseDataForMerchantConstant.productIdOfFoodCity1);
        productCart1.setSkuId(TestCaseDataForMerchantConstant.skuIdOfFoodCity1);
        // 商品2
        ProductCart productCart2 = new ProductCart();
        productCart2.setProductId(TestCaseDataForMerchantConstant.productIdOfFoodCity2);
        productCart2.setSkuId(TestCaseDataForMerchantConstant.skuIdOfFoodCity2);
        // 添加商品1、商品2
        productCarts.add(productCart1);
        productCarts.add(productCart2);
        createOrderByFoodCity.setProductCartList(JSON.toJSONString(productCarts));

        return Stream.of(Arguments.of(createOrderByFoodCity));
    }

    /**
     * 创建订单数据提供者_平台配送-会员合单。用户在下单时购买会员.
     */
    static Stream<Arguments> createOrderByPlatformDeliveryWithMember() {
        CreateOrderRequestDTO createOrderByPlatformDeliveryWithMember = new CreateOrderRequestDTO();
        createOrderByPlatformDeliveryWithMember.setAddressId(TestCaseDataForUserConstant.addressId);
        // 0=商家配送；1=平台配送；2=自取
        createOrderByPlatformDeliveryWithMember.setDeliveryType(String.valueOf(DeliveryTypeEnum.third_party.getCode()));
        createOrderByPlatformDeliveryWithMember.setDeliveryTime("尽快送达");
        createOrderByPlatformDeliveryWithMember.setIsOnlinePay(Boolean.TRUE);
        // 为什么前端传的是1，服务器用的是  boolean
        createOrderByPlatformDeliveryWithMember.setNeedNumberMasking(Boolean.TRUE);
        createOrderByPlatformDeliveryWithMember.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        createOrderByPlatformDeliveryWithMember.setPlatform(String.valueOf(PlatformEnum.ANDROID.getCode()));
        createOrderByPlatformDeliveryWithMember.setUseVoucherTemplate(VoucherStatusEnum.WAIT_USE.getCode());
        createOrderByPlatformDeliveryWithMember.setRemark("【自动化测试】创建订单");
        // 选择自取时需要传联系电话。但是我发现配送传这个字段也没关系
        createOrderByPlatformDeliveryWithMember.setUserPhone("86 18711110002");
        createOrderByPlatformDeliveryWithMember.setTablewareCount(1);
        createOrderByPlatformDeliveryWithMember.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());

        // 会员合单之后的商品价格。无需动态查询，初始化数据时就应当指定好会员的价值。
        createOrderByPlatformDeliveryWithMember.setFixedPrice(12500);
        // 会员合单相关参数
        createOrderByPlatformDeliveryWithMember.setMemberCityId(TestCaseDataForUserConstant.memberCityId);
        createOrderByPlatformDeliveryWithMember.setMemberBuyType(OrderReqTypeEnum.COMMON_ORDER.getType());
        createOrderByPlatformDeliveryWithMember.setMemberCombinedType(MemberCombinedTypeEnum.MEMBER_NO.getOpenBizType());

        // 这里为什么只能传字符串，不能传数组么。。。 服务端应该改成请求体为json
        // createOrderByMerchantDelivery.setProductCartList("[{\"skuId\":0,\"productId\":81669204}]");
        List<ProductCart> productCarts = new ArrayList<>();
        ProductCart productCart = new ProductCart();
        productCart.setSkuId(TestCaseDataForMerchantConstant.skuId);
        productCart.setProductId(TestCaseDataForMerchantConstant.productId);
        productCarts.add(productCart);
        createOrderByPlatformDeliveryWithMember.setProductCartList(JSON.toJSONString(productCarts));
        createOrderByPlatformDeliveryWithMember.setShopId(TestCaseDataForMerchantConstant.shopId);
        return Stream.of(Arguments.of(createOrderByPlatformDeliveryWithMember));
    }


    /**
     * 创建订单数据提供者_平台配送-代金券合单。用户在下单时购买代金券
     */
    static Stream<Arguments> createOrderByPlatformDeliveryWithVoucher() {
        CreateOrderRequestDTO createOrderByPlatformDeliveryWithVoucher = new CreateOrderRequestDTO();
        createOrderByPlatformDeliveryWithVoucher.setAddressId(TestCaseDataForUserConstant.addressId);
        // 0=商家配送；1=平台配送；2=自取
        createOrderByPlatformDeliveryWithVoucher.setDeliveryType(String.valueOf(DeliveryTypeEnum.third_party.getCode()));
        createOrderByPlatformDeliveryWithVoucher.setDeliveryTime("尽快送达");
        createOrderByPlatformDeliveryWithVoucher.setIsOnlinePay(Boolean.TRUE);
        // 为什么前端传的是1，服务器用的是  boolean
        createOrderByPlatformDeliveryWithVoucher.setNeedNumberMasking(Boolean.TRUE);
        createOrderByPlatformDeliveryWithVoucher.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        createOrderByPlatformDeliveryWithVoucher.setPlatform(String.valueOf(PlatformEnum.ANDROID.getCode()));
        createOrderByPlatformDeliveryWithVoucher.setRemark("【自动化测试】创建订单");
        // 选择自取时需要传联系电话。但是我发现配送传这个字段也没关系
        createOrderByPlatformDeliveryWithVoucher.setUserPhone("86 18711110002");
        createOrderByPlatformDeliveryWithVoucher.setTablewareCount(1);
        createOrderByPlatformDeliveryWithVoucher.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());
        createOrderByPlatformDeliveryWithVoucher.setShopId(TestCaseDataForMerchantConstant.shopId);

        // 代金劵合单之后的商品价格。无需动态查询，初始化数据时就应当指定好代金券的价格。
        createOrderByPlatformDeliveryWithVoucher.setFixedPrice(11500);
        createOrderByPlatformDeliveryWithVoucher.setMemberBuyType(OrderReqTypeEnum.COMMON_ORDER.getType());
        createOrderByPlatformDeliveryWithVoucher.setMemberCombinedType(MemberCombinedTypeEnum.MEMBER_NO.getOpenBizType());
        // 代金券单相关参数
        createOrderByPlatformDeliveryWithVoucher.setUseVoucherTemplate(VoucherStatusEnum.USED.getCode());
        createOrderByPlatformDeliveryWithVoucher.setVoucherSn(TestCaseDataForMerchantConstant.voucherSn);


        // 这里为什么只能传字符串，不能传数组么。。。 服务端应该改成请求体为json
        // createOrderByMerchantDelivery.setProductCartList("[{\"skuId\":0,\"productId\":81669204}]");
        List<ProductCart> productCarts = new ArrayList<>();
        ProductCart productCart = new ProductCart();
        productCart.setSkuId(TestCaseDataForMerchantConstant.skuId);
        productCart.setProductId(TestCaseDataForMerchantConstant.productId);
        productCarts.add(productCart);
        createOrderByPlatformDeliveryWithVoucher.setProductCartList(JSON.toJSONString(productCarts));
        createOrderByPlatformDeliveryWithVoucher.setShopId(TestCaseDataForMerchantConstant.shopId);
        return Stream.of(Arguments.of(createOrderByPlatformDeliveryWithVoucher));
    }

    /**
     * 优速达合单下单
     */
    static Stream<Arguments> createOrderByPlatformDeliveryFastDelivery() {
        CreateOrderRequestDTO createOrderByPlatformDeliveryWithFastDelivery = new CreateOrderRequestDTO();
        SettlementRequestDTO settlementRequestDTO=new SettlementRequestDTO();
        settlementRequestDTO.setOrderType(CreateOrderTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setTablewareCount(1);
        settlementRequestDTO.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setDeliveryType(DeliveryTypeEnum.third_party.getCode());
        settlementRequestDTO.setAddressId(TestCaseDataForUserConstant.addressId);
        settlementRequestDTO.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        settlementRequestDTO.setShopId(TestCaseDataForMerchantConstant.shopId);
        settlementRequestDTO.setChoseFastDelivery(1);
        // 是否自动使用红包，不使用红包
        settlementRequestDTO.setAutoUseRedPacketStatus(StatusEnum.NO.getType());

        var productCartList = new ArrayList<ProductCart>();
        var productCart = new ProductCart();
        productCart.setSkuId(TestCaseDataForMerchantConstant.skuId);
        productCart.setProductId(TestCaseDataForMerchantConstant.productId);
        productCartList.add(productCart);
        settlementRequestDTO.setProductCartList(JSON.toJSONString(productCartList));
        SettlementResponseDTO settlementResponseDTO = SettlementFlow.settlementProduct(settlementRequestDTO);
        // 优速达相关参数
        Integer fastDeliveryAmount = settlementResponseDTO.getResult().getOrderOpt().getOrderPaymentCombined().getAdditionalBusinessOrderVO().getFastDeliveryAdditionalVO().getFastDeliveryAmount();
        createOrderByPlatformDeliveryWithFastDelivery.setFastMinute(settlementResponseDTO.getResult().getOrderOpt().getOrderPaymentCombined().getAdditionalBusinessOrderVO().getFastDeliveryAdditionalVO().getFastMinute());
        createOrderByPlatformDeliveryWithFastDelivery.setFastDeliveryAmount(fastDeliveryAmount);
        createOrderByPlatformDeliveryWithFastDelivery.setChoseFastDelivery(1);

        createOrderByPlatformDeliveryWithFastDelivery.setAddressId(TestCaseDataForUserConstant.addressId);
        // 0=商家配送；1=平台配送；2=自取
        createOrderByPlatformDeliveryWithFastDelivery.setDeliveryType(String.valueOf(DeliveryTypeEnum.third_party.getCode()));
        createOrderByPlatformDeliveryWithFastDelivery.setDeliveryTime("尽快送达");
        createOrderByPlatformDeliveryWithFastDelivery.setIsOnlinePay(Boolean.TRUE);
        // 为什么前端传的是1，服务器用的是  boolean
        createOrderByPlatformDeliveryWithFastDelivery.setNeedNumberMasking(Boolean.TRUE);
        createOrderByPlatformDeliveryWithFastDelivery.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        createOrderByPlatformDeliveryWithFastDelivery.setPlatform(String.valueOf(PlatformEnum.ANDROID.getCode()));
        createOrderByPlatformDeliveryWithFastDelivery.setRemark("【自动化测试】创建订单");
        // 选择自取时需要传联系电话。但是我发现配送传这个字段也没关系
        createOrderByPlatformDeliveryWithFastDelivery.setUserPhone("86 18711110002");
        createOrderByPlatformDeliveryWithFastDelivery.setTablewareCount(1);
        createOrderByPlatformDeliveryWithFastDelivery.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());
        createOrderByPlatformDeliveryWithFastDelivery.setShopId(TestCaseDataForMerchantConstant.shopId);

        createOrderByPlatformDeliveryWithFastDelivery.setFixedPrice(settlementResponseDTO.getResult().getPriceInfo().getTotalAmount());

        productCart.setSkuId(TestCaseDataForMerchantConstant.skuId);
        productCart.setProductId(TestCaseDataForMerchantConstant.productId);
        productCartList.add(productCart);
        createOrderByPlatformDeliveryWithFastDelivery.setProductCartList(JSON.toJSONString(productCartList));
        createOrderByPlatformDeliveryWithFastDelivery.setShopId(TestCaseDataForMerchantConstant.shopId);
        return Stream.of(Arguments.of(createOrderByPlatformDeliveryWithFastDelivery));
    }
}
