package com.miller.userapp.module.order.shopping.settlement.discount.redpacket;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.app.server.api.req.order.ProductCart;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
@Scenario(scenarioID = "01JKJ8KJHV1DZ44QX4K7N839A4",
        scenarioName = "正常流程_结算_优惠项-红包运费减免",
        author = "luwei@hungrypandagroup.com", developmentTime = 240, maintenanceTime = 30, manualTestTime = 60)
@EnvTag.Test
@DisplayName("运费减免-达到上限")

public class RedPacketDeliveryDiscountForSettlementTest {
    @BeforeAll
    static void beforeAll() {
        UserLoginFlow.loginByDefaultUser();

    }
    @ParameterizedTest
    @MethodSource("settlementRedPacketDeliveryDiscountReach")
    @DisplayName("结算-优惠-运费减免-达到上限-减免7")
    void settlementWithRedPacketDeliveryDiscount(SettlementRequestDTO settlementRequestDTO){
        //先领取红包
        fetchShopRedPacketTest(888893964L);
        //结算
        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        List<OrderAmountVO> orderAmountItemList = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList();
        OrderAmountVO orderAmountItemDiscount = orderAmountItemList.stream().filter(i ->i.getItemKey().equals("redPacketDiscount")).findFirst().get();
        assertThat(orderAmountItemDiscount.getItemAmount()).isEqualTo((700));

    }
    @ParameterizedTest
    @MethodSource("settlementRedPacketDeliveryDiscount")
    @DisplayName("结算-优惠-运费减免-未达到上限，配送费价格")
    void settlementWithRedPacketDeliveryDiscountReach(SettlementRequestDTO settlementRequestDTO){
        //先领取红包
        fetchShopRedPacketTest(888893976L);
        //结算
        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        List<OrderAmountVO> orderAmountItemList = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList();
        OrderAmountVO orderAmountItemDiscount = orderAmountItemList.stream().filter(i ->i.getItemKey().equals("redPacketDiscount")).findFirst().get();
        OrderAmountVO orderAmountDeliveryDiscount = orderAmountItemList.stream().filter(i ->i.getItemKey().equals("discountDelivery")).findFirst().get();
        assertThat(orderAmountItemDiscount.getItemAmount()).isEqualTo((orderAmountDeliveryDiscount.getItemAmount()));

    }
    static Stream<Arguments> settlementRedPacketDeliveryDiscountReach() {

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
        settlementRequestDTO.setRedUseSn("fcfd7593-aa33-45c7-9fa9-0584a4f2bfaa");
        var productCartList = new ArrayList<ProductCart>();
        var productCart = new ProductCart();
        productCart.setSkuId(0L);
        productCart.setProductId(82351748L);
        productCartList.add(productCart);
        settlementRequestDTO.setProductCartList(JSON.toJSONString(productCartList));

        return Stream.of(Arguments.of(settlementRequestDTO));
    }
    static Stream<Arguments> settlementRedPacketDeliveryDiscount() {

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
        settlementRequestDTO.setRedUseSn("d4064ca8-573c-4a43-9768-910c64986e1d");
        var productCartList = new ArrayList<ProductCart>();
        var productCart = new ProductCart();
        productCart.setSkuId(0L);
        productCart.setProductId(82351748L);
        productCartList.add(productCart);
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
