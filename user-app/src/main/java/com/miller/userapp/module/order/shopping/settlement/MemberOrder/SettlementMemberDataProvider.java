package com.miller.userapp.module.order.shopping.settlement.MemberOrder;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.app.server.api.req.order.ProductCart;
import com.hungrypanda.app.server.common.enums.StatusEnum;
import com.hungrypanda.app.server.common.enums.order.CreateOrderTypeEnum;
import com.hungrypanda.app.server.common.enums.order.OrderReqTypeEnum;
import com.miller.data.center.merchant.TestCaseDataForMerchantConstant;
import com.miller.userapp.module.order.shopping.settlement.request.SettlementRequestDTO;
import com.panda.common.enums.DeliveryTypeEnum;
import com.panda.common.enums.PayTypeEnum;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * @author heyuan
 * @version 1.0
 * @since 2024/12/4 11:47
 */
public class SettlementMemberDataProvider {
    static Stream<Arguments> memberDeliveryFeeData() {
        SettlementRequestDTO settlementRequestDTO = new SettlementRequestDTO();
        settlementRequestDTO.setOrderType(CreateOrderTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setTablewareCount(1);
        settlementRequestDTO.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setDeliveryType(DeliveryTypeEnum.third_party.getCode());
        settlementRequestDTO.setAddressId(TestCaseDataForMerchantConstant.memberAddressId);
        settlementRequestDTO.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        settlementRequestDTO.setShopId(TestCaseDataForMerchantConstant.shopIDFormemberOrder);
        // 是否自动使用红包，不使用红包
        settlementRequestDTO.setAutoUseRedPacketStatus(StatusEnum.NO.getType());

        var productCartList = new ArrayList<ProductCart>();
        var productCart = new ProductCart();
        productCart.setSkuId(0L);
        productCart.setProductId(TestCaseDataForMerchantConstant.productIDFormemberOrder);
        productCartList.add(productCart);
        settlementRequestDTO.setProductCartList(JSON.toJSONString(productCartList));

        return Stream.of(Arguments.of(settlementRequestDTO));
    }

}
  