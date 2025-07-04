package com.miller.userapp.module.order.shopping.settlement.discount.pickup;

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
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
@Scenario(scenarioID = "01JFSBNCPDS55N3D32TXZN2HFG",
        scenarioName = "正常流程_结算_优惠项-自取折扣",
        author = "luwei@hungrypandagroup.com", developmentTime = 60, maintenanceTime = 30, manualTestTime = 10)
@EnvTag.Test
@DisplayName("自取折扣")
public class PickupDiscountForSettlementTest {
    @BeforeAll
    static void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
    }

    @ParameterizedTest
    @MethodSource("settlementPickupDiscount")
    @DisplayName("结算-优惠-自取折扣-自取折扣9折，折扣上限20，自取折扣未达上限")
    void settlementWithPickupDiscount(SettlementRequestDTO settlementRequestDTO){

        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        List<OrderAmountVO> orderAmountItemList = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList();
        final int[] baseAmount = {0,0};
        orderAmountItemList.forEach(amount ->{
            if(amount.getItemKey().contains("takeDiscount")) return;
            if(amount.getItemKey().contains("Discount") ){
                baseAmount[0] += amount.getItemAmount();
            }else {
                baseAmount[1] += amount.getItemAmount();
            }
        });
        int actualAmount = new BigDecimal(baseAmount[1]-baseAmount[0]).multiply(new BigDecimal(0.1)).setScale(0, RoundingMode.HALF_UP).intValue();
        OrderAmountVO OrderAmountItemDiscount = orderAmountItemList.stream().filter(i ->i.getItemKey().equals("takeDiscount")).findFirst().get();
        assertThat(OrderAmountItemDiscount.getItemAmount()).isEqualTo((actualAmount));


    }
    @ParameterizedTest
    @MethodSource("settlementPickupDiscountReach")
    @DisplayName("结算-优惠-自取折扣-自取折扣9折，折扣上限20，自取折扣达到上限")
    void settlementWithPickupDiscountReach(SettlementRequestDTO settlementRequestDTO){

        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        List<OrderAmountVO> orderAmountItemList = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList();
        OrderAmountVO OrderAmountItemDiscount = orderAmountItemList.stream().filter(i ->i.getItemKey().equals("takeDiscount")).findFirst().get();
        assertThat(OrderAmountItemDiscount.getItemAmount()).isEqualTo(2000);

    }
    //自取折扣-自取折扣9折，折扣上限20，自取折扣未达上限
    static Stream<Arguments> settlementPickupDiscount() {

        SettlementRequestDTO settlementRequestDTO = new SettlementRequestDTO();
        settlementRequestDTO.setOrderType(CreateOrderTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setTablewareCount(1);
        settlementRequestDTO.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setDeliveryType(DeliveryTypeEnum.user.getCode());
        settlementRequestDTO.setAddressId(0L);
        settlementRequestDTO.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        settlementRequestDTO.setShopId(ShopsEnum.EXSHOPDiscount.getShopId());
        // 是否自动使用红包，不使用红包
        settlementRequestDTO.setAutoUseRedPacketStatus(StatusEnum.NO.getType());
        var productCartList = new ArrayList<ProductCart>();
        var productCart = new ProductCart();
        productCart.setSkuId(0L);
        productCart.setProductId(82351792L);
        productCartList.add(productCart);
        settlementRequestDTO.setProductCartList(JSON.toJSONString(productCartList));

        return Stream.of(Arguments.of(settlementRequestDTO));
    }

    //自取折扣-自取折扣9折，折扣上限20，自取折扣达到上限
    static Stream<Arguments> settlementPickupDiscountReach() {

        SettlementRequestDTO settlementRequestDTO = new SettlementRequestDTO();
        settlementRequestDTO.setOrderType(CreateOrderTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setTablewareCount(1);
        settlementRequestDTO.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setDeliveryType(DeliveryTypeEnum.user.getCode());
        settlementRequestDTO.setAddressId(0L);
        settlementRequestDTO.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        settlementRequestDTO.setShopId(ShopsEnum.EXSHOPDiscount.getShopId());
        // 是否自动使用红包，不使用红包
        settlementRequestDTO.setAutoUseRedPacketStatus(StatusEnum.NO.getType());
        var productCartList = new ArrayList<ProductCart>();
        var productCart = new ProductCart();
        productCart.setSkuId(0L);
        productCart.setProductId(82351792L);
        productCartList.add(productCart);
        var productCart1 = new ProductCart();
        productCart1.setSkuId(0L);
        productCart1.setProductId(82351792L);
        productCartList.add(productCart1);
        var productCart2 = new ProductCart();
        productCart2.setSkuId(0L);
        productCart2.setProductId(82351792L);
        productCartList.add(productCart2);
        settlementRequestDTO.setProductCartList(JSON.toJSONString(productCartList));

        return Stream.of(Arguments.of(settlementRequestDTO));
    }
}
