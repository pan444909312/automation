package com.miller.userapp.order.shopping.settlement;

import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.order.shopping.settlement.flow.SettlementFlow;
import com.miller.userapp.order.shopping.settlement.request.SettlementRequestDTO;
import com.miller.userapp.order.shopping.settlement.response.SettlementResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试用例_结算
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/1/10 15:27:44
 */
@EnvTag.Test
@TestFramework
@DisplayName("用户-结算")
public class SettlementCarTests {

    @MethodSource("com.miller.userapp.order.shopping.settlement.provider.SettlementDataProvider#settlementProduct")
    @ParameterizedTest
    @DisplayName("正常流程_单个商品结算")
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
        // 配送费
        Integer delivery = settlementResponseDTO.getResult().getPriceInfo().getOrderAmountItemList().stream()
                .filter(value -> value.getItemKey().equalsIgnoreCase("delivery"))
                .findFirst().get().getItemAmount();

        // 订单总价格应该为 120元
        assertThat(12000).isEqualTo( product + packaging + delivery);
    }
}
