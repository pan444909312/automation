package com.miller.userapp.order.shopping.settlement;

import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.order.shopping.settlement.flow.SettlementFlow;
import com.miller.userapp.order.shopping.settlement.request.SettlementRequestDTO;
import com.miller.userapp.order.shopping.settlement.response.SettlementResponseDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

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

    @MethodSource("com.miller.userapp.order.shopping.settlement.provider.SettlementDataProvider#settlementProductWithMember")
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
}
