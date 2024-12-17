package com.miller.market.shopCart.settleShopCart;

import com.miller.market.constants.ResponseConstant;
import com.miller.market.constants.TestCaseDataForMarketConstant;
import com.miller.market.shopCart.getShopCartList.flow.MarketGetShopCartListFlow;
import com.miller.market.shopCart.getShopCartList.response.MarketGetShopCartListResponseDTO;
import com.miller.market.shopCart.settleShopCart.flow.MarketSettleShopCartFlow;
import com.miller.market.shopCart.settleShopCart.request.MarketSettleShopCartRequestDTO;
import com.miller.market.shopCart.settleShopCart.response.MarketSettleShopCartResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 中超购物车结算列表
 */
@EnvTag.Test
@TestFramework
@DisplayName("PF_购物车结算")
public class MarketSettleShopCartTests {
    @BeforeAll
    static void beforeAll() {
        //购物车结算前，先获取购物车id
        MarketGetShopCartListResponseDTO marketGetShopCartListResponseDTO = MarketGetShopCartListFlow.getShopCartList();
        TestCaseDataForMarketConstant.shopCartId = marketGetShopCartListResponseDTO.getData().getNormalShopCartList().get(0).getShopCartList().get(0).getShopCartId();
    }
    @MethodSource("com.miller.market.shopCart.settleShopCart.provider.MarketSettleShopCartDataProvider#marketSettleShopCartDataProvider")
    @ParameterizedTest
    @DisplayName("PF_正常流程_加购结算成功")
    void addShopCartSuccessfully(MarketSettleShopCartRequestDTO marketSettleShopCartRequestDTO) {
        MarketSettleShopCartResponseDTO marketSettleShopCartResponseDTO = MarketSettleShopCartFlow.settleShopCart(marketSettleShopCartRequestDTO);

        assertThat(marketSettleShopCartResponseDTO.getCode()).isEqualTo(ResponseConstant.code);

    }

}
