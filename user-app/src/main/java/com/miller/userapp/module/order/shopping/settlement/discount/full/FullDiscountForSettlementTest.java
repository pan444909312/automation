package com.miller.userapp.module.order.shopping.settlement.discount.full;

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

@Scenario(scenarioID = "01JF9QXJSJC06FW7Y92DV1P6ES",
        scenarioName = "正常流程_结算_优惠项-满减",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@EnvTag.Test
@DisplayName("满减优惠")
public class FullDiscountForSettlementTest {
    @BeforeAll
    static void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
    }

    @ParameterizedTest
    @MethodSource("settlementFullDiscount")
    @DisplayName("结算-满减-配置满10-2，当前商品无折扣，售价100")
    void settlementWithFullDiscount(SettlementRequestDTO settlementRequestDTO){

        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        List<OrderAmountVO> orderAmountItemList = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList();
        OrderAmountVO OrderAmountItemDiscount = orderAmountItemList.stream().filter(i ->i.getItemKey().equals("fullDiscount")).findFirst().get();
        assertThat(OrderAmountItemDiscount.getItemAmount()).isEqualTo(200);

    }

    //新人sku原价¥100，折扣6.5折
    static Stream<Arguments> settlementFullDiscount() {

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
        productCart.setProductId(82351756L);
        productCartList.add(productCart);
        settlementRequestDTO.setProductCartList(JSON.toJSONString(productCartList));

        return Stream.of(Arguments.of(settlementRequestDTO));
    }
}
