package com.miller.userapp.module.order.shopping.settlement.discount.redpacket;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.app.server.api.req.order.ProductCart;
import com.hungrypanda.app.server.api.res.order.OrderAmountItemMergeDTO;
import com.hungrypanda.app.server.api.res.order.OrderAmountVO;
import com.hungrypanda.app.server.common.enums.StatusEnum;
import com.hungrypanda.app.server.common.enums.order.CreateOrderTypeEnum;
import com.hungrypanda.app.server.common.enums.order.OrderReqTypeEnum;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.order.shopping.settlement.discount.ShopsEnum;
import com.miller.userapp.module.order.shopping.settlement.flow.SettlementFlow;
import com.miller.userapp.module.order.shopping.settlement.request.SettlementRequestDTO;
import com.miller.userapp.module.order.shopping.settlement.response.SettlementResponseDTO;
import com.miller.userapp.module.shop.card.version2.redPacket.base.flow.FetchShopRedPacketFlow;
import com.miller.userapp.module.shop.card.version2.redPacket.base.request.FetchShopRedPacketRequestDTO;
import com.panda.common.enums.DeliveryTypeEnum;
import com.panda.common.enums.PayTypeEnum;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
@Scenario(scenarioID = "01JJ461JE3X761GWRNPWW835NV",
        scenarioName = "正常流程_结算_优惠项-红包减免",
        author = "luwei@hungrypandagroup.com", developmentTime = 480, maintenanceTime = 30, manualTestTime = 120)
@EnvTag.Test
@DisplayName("红包减免")
public class RedPacketDiscountForSettlementTest {
    @BeforeAll
    static void beforeAll() {
        UserLoginFlow.loginByDefaultUser();

    }

    @ParameterizedTest
    @MethodSource("settlementRedPacketFullReduce")
    @DisplayName("结算-优惠-满减红包-减免10")
    void settlementWithRedPacketFullReduce(SettlementRequestDTO settlementRequestDTO){
        //先领取红包
        fetchShopRedPacketTest(888893600L);
        //结算
        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        List<OrderAmountVO> orderAmountItemList = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList();
        OrderAmountVO orderAmountItemDiscount = orderAmountItemList.stream().filter(i ->i.getItemKey().equals("redPacketDiscount")).findFirst().get();
        assertThat(orderAmountItemDiscount.getItemAmount()).isEqualTo((1000));

    }
    @ParameterizedTest
    @MethodSource("settlementRedPacketFullReduceAdding")
    @DisplayName("结算-优惠-满减红包-减免8,店铺日常加码2，会员日加码3")
    void settlementWithRedPacketFullReduceAdding(SettlementRequestDTO settlementRequestDTO){
        //先领取红包
        fetchShopRedPacketTest(888893612L);
        //结算
        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        List<OrderAmountVO> orderAmountItemList = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList();
        OrderAmountVO orderAmountItemDiscount = orderAmountItemList.stream().filter(i ->i.getItemKey().equals("redPacketDiscount")).findFirst().get();
        assertThat(orderAmountItemDiscount.getItemAmount()).isEqualTo((1000));

    }
    @ParameterizedTest
    @MethodSource("settlementRedPacketFullDiscount")
    @DisplayName("结算-优惠-满折红包-折扣5折，最高抵扣90,未达抵扣上限")
    void settlementWithRedPacketFullDiscount(SettlementRequestDTO settlementRequestDTO){
        //先领取红包
        fetchShopRedPacketTest(888893618L);
        //结算
        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        List<OrderAmountVO> orderAmountItemList = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList();
        int baseAmount = 0;
        int redPacketDiscountAmount = 0;
        for (OrderAmountVO orderAmountVO : orderAmountItemList){
            if (orderAmountVO.getItemKey().equals("tableware")){
                baseAmount += orderAmountVO.getItemAmount();
            }
            if (orderAmountVO.getItemKey().equals("packaging")){
                baseAmount += orderAmountVO.getItemAmount();
            }
            if (orderAmountVO.getItemKey().equals("product")){
                baseAmount += orderAmountVO.getItemAmount();
            }
            if (orderAmountVO.getItemKey().equals("redPacketDiscount")){
                redPacketDiscountAmount += orderAmountVO.getItemAmount();
            }
        }
        int discount = new BigDecimal(baseAmount).divide(new BigDecimal(2)).intValue();
        assertThat(discount).isEqualTo(redPacketDiscountAmount);

    }
    @ParameterizedTest
    @MethodSource("settlementRedPacketFullDiscountReach")
    @DisplayName("结算-优惠-满折红包-折扣5折，最高抵扣90,达到抵扣上限")
    void settlementWithRedPacketFullDiscountReach(SettlementRequestDTO settlementRequestDTO){
        //先领取红包
        fetchShopRedPacketTest(888893618L);
        //结算
        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        List<OrderAmountVO> orderAmountItemList = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList();
        int baseAmount = 0;
        int redPacketDiscountAmount = 0;
        for (OrderAmountVO orderAmountVO : orderAmountItemList){
            if (orderAmountVO.getItemKey().equals("tableware")){
                baseAmount += orderAmountVO.getItemAmount();
            }
            if (orderAmountVO.getItemKey().equals("packaging")){
                baseAmount += orderAmountVO.getItemAmount();
            }
            if (orderAmountVO.getItemKey().equals("product")){
                baseAmount += orderAmountVO.getItemAmount();
            }
            if (orderAmountVO.getItemKey().equals("redPacketDiscount")){
                redPacketDiscountAmount += orderAmountVO.getItemAmount();
            }
        }
        int discount = new BigDecimal(baseAmount).divide(new BigDecimal(2)).intValue();
        assertThat(discount).isGreaterThan(redPacketDiscountAmount);
        assertThat(redPacketDiscountAmount).isEqualTo(9000);

    }


    static Stream<Arguments> settlementRedPacketFullReduce() {

        SettlementRequestDTO settlementRequestDTO = new SettlementRequestDTO();
        settlementRequestDTO.setOrderType(CreateOrderTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setTablewareCount(1);
        settlementRequestDTO.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setDeliveryType(DeliveryTypeEnum.third_party.getCode());
        settlementRequestDTO.setAddressId(TestCaseDataForUserConstant.addressId);
        settlementRequestDTO.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        settlementRequestDTO.setShopId(ShopsEnum.SKUDiscount.getShopId());
        // 是否自动使用红包，使用红包
        settlementRequestDTO.setAutoUseRedPacketStatus(StatusEnum.YES.getType());
        settlementRequestDTO.setRedUseSn("932ffe91-f12d-457a-af62-a8078b2ed3b5");
        var productCartList = new ArrayList<ProductCart>();
        var productCart = new ProductCart();
        productCart.setSkuId(0L);
        productCart.setProductId(82351748L);
        productCartList.add(productCart);
        settlementRequestDTO.setProductCartList(JSON.toJSONString(productCartList));

        return Stream.of(Arguments.of(settlementRequestDTO));
    }
    static Stream<Arguments> settlementRedPacketFullReduceAdding() {

        SettlementRequestDTO settlementRequestDTO = new SettlementRequestDTO();
        settlementRequestDTO.setOrderType(CreateOrderTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setTablewareCount(1);
        settlementRequestDTO.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setDeliveryType(DeliveryTypeEnum.third_party.getCode());
        settlementRequestDTO.setAddressId(TestCaseDataForUserConstant.addressId);
        settlementRequestDTO.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        settlementRequestDTO.setShopId(ShopsEnum.SKUDiscount.getShopId());
        // 是否自动使用红包，使用红包
        settlementRequestDTO.setAutoUseRedPacketStatus(StatusEnum.YES.getType());
        settlementRequestDTO.setRedUseSn("6cef6841-025c-4671-ae31-e8d7b79428f6");
        var productCartList = new ArrayList<ProductCart>();
        var productCart = new ProductCart();
        productCart.setSkuId(0L);
        productCart.setProductId(82351748L);
        productCartList.add(productCart);
        settlementRequestDTO.setProductCartList(JSON.toJSONString(productCartList));

        return Stream.of(Arguments.of(settlementRequestDTO));
    }
    static Stream<Arguments> settlementRedPacketFullDiscount() {

        SettlementRequestDTO settlementRequestDTO = new SettlementRequestDTO();
        settlementRequestDTO.setOrderType(CreateOrderTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setTablewareCount(1);
        settlementRequestDTO.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setDeliveryType(DeliveryTypeEnum.third_party.getCode());
        settlementRequestDTO.setAddressId(TestCaseDataForUserConstant.addressId);
        settlementRequestDTO.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        settlementRequestDTO.setShopId(ShopsEnum.SKUDiscount.getShopId());
        // 是否自动使用红包，使用红包
        settlementRequestDTO.setAutoUseRedPacketStatus(StatusEnum.YES.getType());
        settlementRequestDTO.setRedUseSn("0c20f148-c26f-482a-98e4-e4eeb3fe87c5");
        var productCartList = new ArrayList<ProductCart>();
        var productCart = new ProductCart();
        productCart.setSkuId(0L);
        productCart.setProductId(82351748L);
        productCartList.add(productCart);
        settlementRequestDTO.setProductCartList(JSON.toJSONString(productCartList));

        return Stream.of(Arguments.of(settlementRequestDTO));
    }
    static Stream<Arguments> settlementRedPacketFullDiscountReach() {

        SettlementRequestDTO settlementRequestDTO = new SettlementRequestDTO();
        settlementRequestDTO.setOrderType(CreateOrderTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setTablewareCount(1);
        settlementRequestDTO.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setDeliveryType(DeliveryTypeEnum.third_party.getCode());
        settlementRequestDTO.setAddressId(TestCaseDataForUserConstant.addressId);
        settlementRequestDTO.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        settlementRequestDTO.setShopId(ShopsEnum.SKUDiscount.getShopId());
        // 是否自动使用红包，使用红包
        settlementRequestDTO.setAutoUseRedPacketStatus(StatusEnum.YES.getType());
        settlementRequestDTO.setRedUseSn("0c20f148-c26f-482a-98e4-e4eeb3fe87c5");
        var productCartList = new ArrayList<ProductCart>();
        var productCart = new ProductCart();
        productCart.setSkuId(0L);
        productCart.setProductId(82351748L);
        var productCart1 = new ProductCart();
        productCart1.setSkuId(0L);
        productCart1.setProductId(82351748L);
        productCartList.add(productCart);
        productCartList.add(productCart1);
        settlementRequestDTO.setProductCartList(JSON.toJSONString(productCartList));

        return Stream.of(Arguments.of(settlementRequestDTO));
    }
    private static void fetchShopRedPacketTest(Long redPacketId){
        FetchShopRedPacketRequestDTO requestDTO = new FetchShopRedPacketRequestDTO();
        requestDTO.setShopId(ShopsEnum.SKUDiscount.getShopId());
        requestDTO.setPacketBenefitType(1);
        requestDTO.setRedPacketId(redPacketId);
        FetchShopRedPacketFlow.fetchShopRedPacketFlow(requestDTO);
    }
}
