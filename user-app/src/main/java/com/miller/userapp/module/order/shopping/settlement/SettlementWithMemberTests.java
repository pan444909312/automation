package com.miller.userapp.module.order.shopping.settlement;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.app.server.api.req.order.ProductCart;
import com.hungrypanda.app.server.common.enums.StatusEnum;
import com.hungrypanda.app.server.common.enums.member.MemberBuyTypeEnum;
import com.hungrypanda.app.server.common.enums.member.MemberCombinedTypeEnum;
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

/**
 * 测试用例_结算-会员结算，不使用红包
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/22 14:57:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("用户-结算-会员结算-不使用红包")
public class SettlementWithMemberTests {

    @MethodSource("settlementProductWithMember")
    @ParameterizedTest
    @DisplayName("正常流程_会员结算-不使用红包")
    void shouldSettlementProductSuccessfully(SettlementRequestDTO settlementRequestDTO) {
        SettlementResponseDTO settlementResponseDTO = SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(settlementResponseDTO.getSuccess()).isTrue();
        // 商品小记费用
        Integer product = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream()
                .filter(value -> value.getItemKey().equalsIgnoreCase("product"))
                .findFirst().get().getItemAmount();
        // 打包费
        Integer packaging = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream()
                .filter(value -> value.getItemKey().equalsIgnoreCase("packaging"))
                .findFirst().get().getItemAmount();

        // 配送费折扣价 = 开通会员价格（10） -VIP配送费优惠（5）
        Integer discountDelivery = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream()
                .filter(value -> value.getItemKey().equalsIgnoreCase("discountDelivery"))
                .findFirst().get().getItemAmount();
        // 配送费原价
        Integer deliveryItemAmount = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream()
                .filter(value -> value.getItemKey().equalsIgnoreCase("discountDelivery"))
                .findFirst().get().getMergeList().stream()
                .filter(value -> value.getItemKey().equalsIgnoreCase("delivery")).findFirst().get().getItemAmount();
        // 配送费 VIP 优惠金额
        Integer memberDeliveryDiscount = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream()
                .filter(value -> value.getItemKey().equalsIgnoreCase("discountDelivery"))
                .findFirst().get().getMergeList().stream()
                .filter(value -> value.getItemKey().equalsIgnoreCase("memberDeliveryDiscount")).findFirst().get().getItemAmount();
        // 校验配送费折扣价
        assertThat(discountDelivery).isEqualTo(deliveryItemAmount - memberDeliveryDiscount);

        // 开通会员费用
        Integer buyMember = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream()
                .filter(value -> value.getItemKey().equalsIgnoreCase("buyMember"))
                .findFirst().get().getItemAmount();

        // 订单金额 = 商品小计 + 打包费 + 原配送费 + 开通会员价格 - 配送费VIP优惠金额。100+10+10+10-5-6
        assertThat(12500).isEqualTo(product + packaging + deliveryItemAmount + buyMember - memberDeliveryDiscount);

    }

    /**
     * 结算-使用会员
     */
    static Stream<Arguments> settlementProductWithMember() {
        SettlementRequestDTO settlementRequestDTO = new SettlementRequestDTO();
        settlementRequestDTO.setOrderType(CreateOrderTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setTablewareCount(1);
        settlementRequestDTO.setOrderReqType(OrderReqTypeEnum.COMMON_ORDER.getType());
        settlementRequestDTO.setDeliveryType(DeliveryTypeEnum.third_party.getCode());
        settlementRequestDTO.setAddressId(TestCaseDataForUserConstant.addressId);
        settlementRequestDTO.setMemberBuyType(MemberBuyTypeEnum.COMMON_BUY.getBuyType());
        settlementRequestDTO.setMemberCombinedType(MemberCombinedTypeEnum.MEMBER_NO.getOpenBizType());
        settlementRequestDTO.setPayType(PayTypeEnum.PAY_WAY_BALANCE.getCode());
        settlementRequestDTO.setShopId(TestCaseDataForMerchantConstant.shopId);

        // 会员城市ID
        settlementRequestDTO.setMemberCityId(TestCaseDataForUserConstant.memberCityId);
        // 是否自动使用红包，不使用红包
        settlementRequestDTO.setAutoUseRedPacketStatus(StatusEnum.NO.getType());


        // 这里为什么只能传字符串，不能传数组么。。。 服务端应该改成请求体为json
        //createOrderByMyselfDelivery.setProductCartList("[{\"productId\":81669204,\"skuId\":0,\"tagId\":[]}]");
        var productCartList = new ArrayList<ProductCart>();
        var productCart = new ProductCart();
        productCart.setSkuId(TestCaseDataForMerchantConstant.skuId);
        productCart.setProductId(TestCaseDataForMerchantConstant.productId);
        productCartList.add(productCart);
        settlementRequestDTO.setProductCartList(JSON.toJSONString(productCartList));

        return Stream.of(Arguments.of(settlementRequestDTO));
    }

}
