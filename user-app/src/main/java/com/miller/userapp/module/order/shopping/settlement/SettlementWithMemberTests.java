package com.miller.userapp.module.order.shopping.settlement;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.app.server.api.req.order.ProductCart;
import com.hungrypanda.app.server.api.res.order.OrderAmountVO;
import com.hungrypanda.app.server.common.enums.StatusEnum;
import com.hungrypanda.app.server.common.enums.member.MemberBuyTypeEnum;
import com.hungrypanda.app.server.common.enums.member.MemberCombinedTypeEnum;
import com.hungrypanda.app.server.common.enums.order.CreateOrderTypeEnum;
import com.hungrypanda.app.server.common.enums.order.OrderReqTypeEnum;
import com.miller.data.center.merchant.TestCaseDataForMerchantConstant;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.erp.manage.member.delete.MemberDeleteFlow;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.module.order.shopping.settlement.flow.SettlementFlow;
import com.miller.userapp.module.order.shopping.settlement.request.SettlementRequestDTO;
import com.miller.userapp.module.order.shopping.settlement.response.SettlementResponseDTO;
import com.panda.common.enums.DeliveryTypeEnum;
import com.panda.common.enums.PayTypeEnum;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void beforeEach() {
        MemberDeleteFlow.deleteMemberByUserId(
                PropertiesUtils.getProperty("user.app.account.of.user002.account.id"));

    }

    @MethodSource("settlementProductWithMember")
    @ParameterizedTest
    @DisplayName("正常流程_会员结算-不使用红包")
    void shouldSettlementProductSuccessfully(SettlementRequestDTO settlementRequestDTO) {
        SettlementResponseDTO settlementResponseDTO = SettlementFlow.settlementProduct(settlementRequestDTO);
        assertThat(settlementResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(settlementResponseDTO.getSuccess()).isTrue();
        // 商品小记费
        Integer product = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream()
                .filter(value -> value.getItemKey().equalsIgnoreCase("product"))
                .findFirst().map(OrderAmountVO::getItemAmount).orElse(0);
        // 打包费
        Integer packaging = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream()
                .filter(value -> value.getItemKey().equalsIgnoreCase("packaging"))
                .findFirst().map(OrderAmountVO::getItemAmount).orElse(0);

        // 配送费折扣价 = 配送费（delivery）-VIP配送优惠金额（memberDeliveryDiscount）
        Integer discountDelivery = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream()
                .filter(value -> value.getItemKey().equalsIgnoreCase("discountDelivery"))
                .findFirst().map(OrderAmountVO::getItemAmount).orElse(0);
        // 配送费
        Integer delivery = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream()
                .filter(value -> value.getItemKey().equalsIgnoreCase("discountDelivery"))
                .findFirst().get()
                .getMergeList().stream().filter(value -> value.getItemKey().equalsIgnoreCase("delivery"))
                .findFirst()
                .map(OrderAmountVO::getItemAmount).orElse(0);
        // VIP配送优惠金额
        Integer memberDeliveryDiscount = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream()
                .filter(value -> value.getItemKey().equalsIgnoreCase("discountDelivery"))
                .findFirst().get()
                .getMergeList().stream().filter(value -> value.getItemKey().equalsIgnoreCase("memberDeliveryDiscount"))
                .findFirst()
                .map(OrderAmountVO::getItemAmount).orElse(0);
        // 校验配送费折扣价
        assertThat(discountDelivery).isEqualTo(delivery - memberDeliveryDiscount);

        // 新增收费项cn
        Integer deliveryAddFee622 = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream()
                .filter(value -> value.getItemKey().equalsIgnoreCase("DELIVERY_ADD_FEE622"))
                .findFirst().map(OrderAmountVO::getItemAmount).orElse(0);

        // 红包优惠
        Integer redPacketDiscount = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream()
                .filter(value -> value.getItemKey().equalsIgnoreCase("redPacketDiscount"))
                .findFirst().map(OrderAmountVO::getItemAmount).orElse(0);

        // 开通会员费用
        Integer buyMember = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream()
                .filter(value -> value.getItemKey().equalsIgnoreCase("buyMember"))
                .findFirst().map(OrderAmountVO::getItemAmount).orElse(0);


        // 订单金额 = 商品小计 + 打包费 + 配送费折扣价(配送费-VIP配送优惠金额）+ 开通会员价格 + 新增收费项cn - 红包优惠
        Integer totalAmount = settlementResponseDTO.getResult().getPriceInfo().getTotalAmount();
        assertThat(totalAmount).isEqualTo(product + packaging + discountDelivery + deliveryAddFee622
                + buyMember - redPacketDiscount);

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
