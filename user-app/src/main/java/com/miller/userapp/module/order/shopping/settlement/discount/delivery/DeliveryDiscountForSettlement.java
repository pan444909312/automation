package com.miller.userapp.module.order.shopping.settlement.discount.delivery;

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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
@Scenario(scenarioID = "01JJ0W6E74E06CKN24ZCVEWT7H",
        scenarioName = "正常流程_结算_优惠项-运费减免",
        author = "luwei@hungrypandagroup.com", developmentTime = 180, maintenanceTime = 30, manualTestTime = 30)
@EnvTag.Test
@DisplayName("运费减免")
public class DeliveryDiscountForSettlement {
    @BeforeAll
    static void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
    }

    @ParameterizedTest
    @MethodSource("settlementDeliveryDiscount")
    @DisplayName("结算-优惠-配送减免-满4减2，满8减3")
    void settlementWithDeliveryDiscount(SettlementRequestDTO settlementRequestDTO){

        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        List<OrderAmountVO> orderAmountItemList = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList();
        OrderAmountVO orderAmountItemDiscount = orderAmountItemList.stream().filter(i ->i.getItemKey().equals("discountDelivery")).findFirst().get();
        List<OrderAmountVO> mergetList =  orderAmountItemDiscount.getMergeList();
        int delivery = 0;
        int deliveryDiscount = 0;
        if(!mergetList.isEmpty()){
            for(OrderAmountVO orderAmountVO : mergetList){
                if (orderAmountVO.getItemKey().equals("deliveryDiscount")){
                    deliveryDiscount = orderAmountVO.getItemAmount();
                }else {
                    delivery += orderAmountVO.getItemAmount();
                }
            }
            int  actualDelivery = Math.max(delivery - deliveryDiscount,0);
            assertThat(deliveryDiscount).isEqualTo((300));
            assertThat(orderAmountItemDiscount.getItemAmount()).isEqualTo(actualDelivery);
        }else
            assertThat(orderAmountItemDiscount.getItemAmount()).isEqualTo((300));


    }


    static Stream<Arguments> settlementDeliveryDiscount() {

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
        productCart.setProductId(82351760L);
        productCartList.add(productCart);
        settlementRequestDTO.setProductCartList(JSON.toJSONString(productCartList));

        return Stream.of(Arguments.of(settlementRequestDTO));
    }


}
