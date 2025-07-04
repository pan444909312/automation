package com.miller.userapp.module.order.shopping.settlement.feeItems;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.app.server.api.req.order.ProductCart;
import com.hungrypanda.app.server.common.enums.StatusEnum;
import com.hungrypanda.app.server.common.enums.order.CreateOrderTypeEnum;
import com.hungrypanda.app.server.common.enums.order.OrderReqTypeEnum;
import com.miller.data.center.merchant.TestCaseDataForMerchantConstant;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.userapp.module.order.shopping.settlement.request.SettlementRequestDTO;
import com.panda.common.enums.DeliveryTypeEnum;
import com.panda.common.enums.PayTypeEnum;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SettlementFeeDataProvider {
    static Stream<Arguments> shopSupportPandaDelAndPinckUp() {
        SettlementRequestDTO settlementRequestDTO = new SettlementRequestDTO();
        settlementRequestDTO.setOrderType(CreateOrderTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setTablewareCount(1);
        settlementRequestDTO.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setDeliveryType(DeliveryTypeEnum.third_party.getCode());
        settlementRequestDTO.setAddressId(TestCaseDataForUserConstant.addressId);
        settlementRequestDTO.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        settlementRequestDTO.setShopId(TestCaseDataForMerchantConstant.shopTestDeliveryWay);
        // 是否自动使用红包，不使用红包
        settlementRequestDTO.setAutoUseRedPacketStatus(StatusEnum.NO.getType());

        var productCartList = new ArrayList<ProductCart>();
        var productCart = new ProductCart();
        productCart.setSkuId(0L);
        productCart.setProductId(TestCaseDataForMerchantConstant.shopTestDeliveryWayProductId);
        productCartList.add(productCart);
        settlementRequestDTO.setProductCartList(JSON.toJSONString(productCartList));

        return Stream.of(Arguments.of(settlementRequestDTO));
    }

    //默认餐具数
    static Stream<Arguments> shopDefaultTableware() {
        SettlementRequestDTO settlementRequestDTO = new SettlementRequestDTO();
        settlementRequestDTO.setOrderType(CreateOrderTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setTablewareCount(TestCaseDataForMerchantConstant.defaultTablewareQuantity);
        settlementRequestDTO.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setDeliveryType(DeliveryTypeEnum.third_party.getCode());
        settlementRequestDTO.setAddressId(TestCaseDataForUserConstant.addressId);
        settlementRequestDTO.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        settlementRequestDTO.setShopId(TestCaseDataForMerchantConstant.shopTestDeliveryWay);
        // 是否自动使用红包，不使用红包
        settlementRequestDTO.setAutoUseRedPacketStatus(StatusEnum.NO.getType());

        var productCartList = new ArrayList<ProductCart>();
        var productCart = new ProductCart();
        productCart.setSkuId(0L);
        productCart.setProductId(82351748L);
        productCartList.add(productCart);
        settlementRequestDTO.setProductCartList(JSON.toJSONString(productCartList));

        return Stream.of(Arguments.of(settlementRequestDTO));
    }

    //不要餐具
    static Stream<Arguments> shopNoTableware() {
        SettlementRequestDTO settlementRequestDTO = new SettlementRequestDTO();
        settlementRequestDTO.setOrderType(CreateOrderTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setTablewareCount(TestCaseDataForMerchantConstant.defaultTablewareQuantity);
        settlementRequestDTO.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setDeliveryType(DeliveryTypeEnum.third_party.getCode());
        settlementRequestDTO.setAddressId(TestCaseDataForUserConstant.addressId);
        settlementRequestDTO.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        settlementRequestDTO.setShopId(TestCaseDataForMerchantConstant.shopTestDeliveryWay);
        // 是否自动使用红包，不使用红包
        settlementRequestDTO.setAutoUseRedPacketStatus(StatusEnum.NO.getType());

        var productCartList = new ArrayList<ProductCart>();
        var productCart = new ProductCart();
        productCart.setSkuId(0L);
        productCart.setProductId(82351748L);
        productCartList.add(productCart);
        settlementRequestDTO.setProductCartList(JSON.toJSONString(productCartList));

        return Stream.of(Arguments.of(settlementRequestDTO));
    }

    static Stream<Arguments> sameSkuAndTag() {
        SettlementRequestDTO settlementRequestDTO = new SettlementRequestDTO();
        settlementRequestDTO.setOrderType(CreateOrderTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setTablewareCount(1);
        settlementRequestDTO.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setDeliveryType(DeliveryTypeEnum.third_party.getCode());
        settlementRequestDTO.setAddressId(TestCaseDataForUserConstant.addressId);
        settlementRequestDTO.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        settlementRequestDTO.setShopId(TestCaseDataForMerchantConstant.shopTestDeliveryWay);
        // 是否自动使用红包，不使用红包
        settlementRequestDTO.setAutoUseRedPacketStatus(StatusEnum.NO.getType());

        var productCartList = new ArrayList<ProductCart>();

        for(int i=0;i<=2;i++) {
            var productCart = new ProductCart();
            List<String> tagId = new ArrayList<>();
            tagId.add(TestCaseDataForMerchantConstant.shopTestDeliveryWayProductTagId1+"-1");
            productCart.setSkuId(TestCaseDataForMerchantConstant.shopTestDeliveryWayProductSkuIdForPacking);
            productCart.setProductId(TestCaseDataForMerchantConstant.shopTestDeliveryWayProductIdForPacking);
            productCart.setTagId(tagId);
            productCartList.add(productCart);
            System.out.println(productCartList);
        }

        settlementRequestDTO.setProductCartList(JSON.toJSONString(productCartList));
        return Stream.of(Arguments.of(settlementRequestDTO));
    }

    static Stream<Arguments> sameSkuDifferentdTag() {
        SettlementRequestDTO settlementRequestDTO = new SettlementRequestDTO();
        settlementRequestDTO.setOrderType(CreateOrderTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setTablewareCount(1);
        settlementRequestDTO.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setDeliveryType(DeliveryTypeEnum.third_party.getCode());
        settlementRequestDTO.setAddressId(TestCaseDataForUserConstant.addressId);
        settlementRequestDTO.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        settlementRequestDTO.setShopId(TestCaseDataForMerchantConstant.shopTestDeliveryWay);
        // 是否自动使用红包，不使用红包
        settlementRequestDTO.setAutoUseRedPacketStatus(StatusEnum.NO.getType());

        var productCartList = new ArrayList<ProductCart>();

        for(int i=0;i<=2;i++) {
            var productCart = new ProductCart();
            List<String> tagId = new ArrayList<>();
            tagId.add(TestCaseDataForMerchantConstant.shopTestDeliveryWayProductTagId1+"-1");
            productCart.setSkuId(TestCaseDataForMerchantConstant.shopTestDeliveryWayProductSkuIdForPacking);
            productCart.setProductId(TestCaseDataForMerchantConstant.shopTestDeliveryWayProductIdForPacking);
            productCart.setTagId(tagId);
            productCartList.add(productCart);
            System.out.println(productCartList);
        }

        for(int i=0;i<=1;i++) {
            var productCart = new ProductCart();
            List<String> tagId = new ArrayList<>();
            tagId.add(TestCaseDataForMerchantConstant.shopTestDeliveryWayProductTagId2+"-1");
            productCart.setSkuId(TestCaseDataForMerchantConstant.shopTestDeliveryWayProductSkuIdForPacking);
            productCart.setProductId(TestCaseDataForMerchantConstant.shopTestDeliveryWayProductIdForPacking);
            productCart.setTagId(tagId);
            productCartList.add(productCart);
            System.out.println(productCartList);
        }

        settlementRequestDTO.setProductCartList(JSON.toJSONString(productCartList));
        return Stream.of(Arguments.of(settlementRequestDTO));
    }
}
