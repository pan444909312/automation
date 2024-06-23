package com.miller.userapp.module.order.shopping.settlement;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.app.server.api.req.order.ProductCart;
import com.hungrypanda.app.server.common.enums.StatusEnum;
import com.hungrypanda.app.server.common.enums.order.CreateOrderTypeEnum;
import com.hungrypanda.app.server.common.enums.order.OrderReqTypeEnum;
import com.miller.data.center.merchant.TestCaseDataForMerchantConstant;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.module.order.shopping.settlement.flow.SettlementFlow;
import com.miller.userapp.module.order.shopping.settlement.request.SettlementRequestDTO;
import com.miller.userapp.module.order.shopping.settlement.response.SettlementResponseDTO;
import com.panda.common.enums.DeliveryTypeEnum;
import com.panda.common.enums.PayTypeEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@DisplayName("代金券合单")
public class SettlementWithCouponTests {

    @ParameterizedTest
    @MethodSource("settlementProductCoupon")
    @DisplayName("正常流程-平台配送-代金券合单并使用代金券结算")
    void shouldSettlementWithCouponSuccessfully(SettlementRequestDTO settlementRequestDTO){

        SettlementResponseDTO settlementResponseDTO= SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(settlementResponseDTO.getResult().getOrderOpt().getVoucher()).isNotNull();
        assertThat(settlementResponseDTO.getResult().getOrderOpt().getVoucher().getAvailableVouchers()).isNull();
    }

    /**
     * 结算-代金券合单
     */
    static Stream<Arguments> settlementProductCoupon() {
        SettlementRequestDTO settlementRequestDTO = new SettlementRequestDTO();
        settlementRequestDTO.setOrderType(CreateOrderTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setTablewareCount(1);
        settlementRequestDTO.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setDeliveryType(DeliveryTypeEnum.third_party.getCode());
        settlementRequestDTO.setAddressId(TestCaseDataForUserConstant.addressId);
        settlementRequestDTO.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        settlementRequestDTO.setShopId(TestCaseDataForMerchantConstant.shopId);
        settlementRequestDTO.setVoucherSn(TestCaseDataForUserConstant.voucherSn);
        settlementRequestDTO.setUseVoucherTemplate(1);
        // 是否自动使用红包，不使用红包
        settlementRequestDTO.setAutoUseRedPacketStatus(StatusEnum.NO.getType());

        var productCartList = new ArrayList<ProductCart>();
        var productCart = new ProductCart();
        productCart.setSkuId(TestCaseDataForMerchantConstant.skuId);
        productCart.setProductId(TestCaseDataForMerchantConstant.productId);
        productCartList.add(productCart);
        settlementRequestDTO.setProductCartList(JSON.toJSONString(productCartList));

        return Stream.of(Arguments.of(settlementRequestDTO));
    }
}
