package com.miller.userapp.module.order.shopping.settlement.discount.item;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.app.server.api.req.order.ProductCart;
import com.hungrypanda.app.server.api.res.order.OrderAmountVO;
import com.hungrypanda.app.server.common.enums.StatusEnum;
import com.hungrypanda.app.server.common.enums.order.CreateOrderTypeEnum;
import com.hungrypanda.app.server.common.enums.order.OrderReqTypeEnum;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.service.framework.annotation.Scenario;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.order.shopping.settlement.discount.ShopsEnum;
import com.miller.userapp.module.order.shopping.settlement.flow.SettlementFlow;
import com.miller.userapp.module.order.shopping.settlement.request.SettlementRequestDTO;
import com.miller.userapp.module.order.shopping.settlement.response.SettlementResponseDTO;
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

@Scenario(scenarioID = "01JET8DV4XRPF8V5NGVNK3STBB",
        scenarioName = "正常流程_结算_优惠项-独享折扣",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
public class ProductExclusiveDiscountForSettlement {
    @BeforeAll
    static void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
    }
    @ParameterizedTest
    @MethodSource("settlementProductItemsExclusiveDiscount")
    @DisplayName("结算-使用独享折扣-折扣7.2折+无抵扣上限+不限购")
    void settlementWithProductExclusiveDiscount(SettlementRequestDTO settlementRequestDTO){

        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        List<OrderAmountVO> orderAmountItemList = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList();
        OrderAmountVO OrderAmountItemDiscount = orderAmountItemList.stream().filter(i ->i.getItemKey().equals("exclusiveDiscount")).findFirst().get();
        assertThat(OrderAmountItemDiscount.getItemAmount()).isEqualTo(2800); //原价10000

    }
    @ParameterizedTest
    @MethodSource("settlementProductItemsFullExclusiveDiscount")
    @DisplayName("结算-使用折扣6折+抵扣上限20+不限购，未达到抵扣上限")
    void settlementWithProductFullExclusiveDiscount(SettlementRequestDTO settlementRequestDTO){

        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        List<OrderAmountVO> orderAmountItemList = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList();
        OrderAmountVO OrderAmountItemDiscount = orderAmountItemList.stream().filter(i ->i.getItemKey().equals("exclusiveDiscount")).findFirst().get();
        assertThat(OrderAmountItemDiscount.getItemAmount()).isEqualTo(800); //20*(1-0.6)

    }
    @ParameterizedTest
    @MethodSource("settlementProductItemsFullExclusiveDiscountReach")
    @DisplayName("结算-使用折扣6折+抵扣上限20+不限购，达到抵扣上限")
    void settlementWithProductFullExclusiveDiscountReach(SettlementRequestDTO settlementRequestDTO){

        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        List<OrderAmountVO> orderAmountItemList = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList();
        OrderAmountVO OrderAmountItemDiscount = orderAmountItemList.stream().filter(i ->i.getItemKey().equals("exclusiveDiscount")).findFirst().get();
        assertThat(OrderAmountItemDiscount.getItemAmount()).isEqualTo(2000); //20*(1-0.6)*3>20

    }
    //独享折扣-折扣7.2折+无抵扣上限+不限购
    static Stream<Arguments> settlementProductItemsExclusiveDiscount() {

        SettlementRequestDTO settlementRequestDTO = new SettlementRequestDTO();
        settlementRequestDTO.setOrderType(CreateOrderTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setTablewareCount(1);
        settlementRequestDTO.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setDeliveryType(DeliveryTypeEnum.third_party.getCode());
        settlementRequestDTO.setAddressId(TestCaseDataForUserConstant.addressId);
        settlementRequestDTO.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        settlementRequestDTO.setShopId(ShopsEnum.SKUDiscount.getShopId());
        // 是否自动使用红包，不使用红包
        settlementRequestDTO.setAutoUseRedPacketStatus(StatusEnum.NO.getType());
        var productCartList = new ArrayList<ProductCart>();
        var productCart = new ProductCart();
        productCart.setSkuId(0L);
        productCart.setProductId(82351752L);
        productCartList.add(productCart);
        settlementRequestDTO.setProductCartList(JSON.toJSONString(productCartList));

        return Stream.of(Arguments.of(settlementRequestDTO));
    }

    //折扣6折+抵扣上限20+不限购，未达到抵扣上限
    static Stream<Arguments> settlementProductItemsFullExclusiveDiscount() {

        SettlementRequestDTO settlementRequestDTO = new SettlementRequestDTO();
        settlementRequestDTO.setOrderType(CreateOrderTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setTablewareCount(1);
        settlementRequestDTO.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setDeliveryType(DeliveryTypeEnum.third_party.getCode());
        settlementRequestDTO.setAddressId(TestCaseDataForUserConstant.addressId);
        settlementRequestDTO.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        settlementRequestDTO.setShopId(ShopsEnum.EXSHOPDiscount.getShopId());
        // 是否自动使用红包，不使用红包
        settlementRequestDTO.setAutoUseRedPacketStatus(StatusEnum.NO.getType());
        var productCartList = new ArrayList<ProductCart>();
        var productCart = new ProductCart();
        productCart.setSkuId(40296190L);
        productCart.setProductId(82351806L);
        productCartList.add(productCart);
        settlementRequestDTO.setProductCartList(JSON.toJSONString(productCartList));

        return Stream.of(Arguments.of(settlementRequestDTO));
    }
    //折扣6折+抵扣上限20+不限购，达到抵扣上限
    static Stream<Arguments> settlementProductItemsFullExclusiveDiscountReach() {

        SettlementRequestDTO settlementRequestDTO = new SettlementRequestDTO();
        settlementRequestDTO.setOrderType(CreateOrderTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setTablewareCount(1);
        settlementRequestDTO.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setDeliveryType(DeliveryTypeEnum.third_party.getCode());
        settlementRequestDTO.setAddressId(TestCaseDataForUserConstant.addressId);
        settlementRequestDTO.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        settlementRequestDTO.setShopId(ShopsEnum.EXSHOPDiscount.getShopId());
        // 是否自动使用红包，不使用红包
        settlementRequestDTO.setAutoUseRedPacketStatus(StatusEnum.NO.getType());
        var productCartList = new ArrayList<ProductCart>();
        var productCart1 = new ProductCart();
        productCart1.setSkuId(40296190L);
        productCart1.setProductId(82351806L);
        productCartList.add(productCart1);
        var productCart2 = new ProductCart();
        productCart2.setSkuId(40296190L);
        productCart2.setProductId(82351806L);
        productCartList.add(productCart2);
        var productCart3 = new ProductCart();
        productCart3.setSkuId(40296190L);
        productCart3.setProductId(82351806L);
        productCartList.add(productCart3);
        settlementRequestDTO.setProductCartList(JSON.toJSONString(productCartList));

        return Stream.of(Arguments.of(settlementRequestDTO));
    }

}
