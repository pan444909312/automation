package com.miller.market.shopCart.deleteShopCart;

import com.miller.market.constants.ResponseConstant;
import com.miller.market.constants.TestCaseDataForMarketConstant;
import com.miller.market.shopCart.deleteShopCart.flow.MarketDeleteShopCartFlow;
import com.miller.market.shopCart.deleteShopCart.request.MarketDeleteShopCartRequestDTO;
import com.miller.market.shopCart.deleteShopCart.response.MarketDeleteShopCartResponseDTO;
import com.miller.market.shopCart.getShopCartList.flow.MarketGetShopCartListFlow;
import com.miller.market.shopCart.getShopCartList.response.MarketGetShopCartListResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 中超购物车删除接口
 */
@EnvTag.Test
@TestFramework
@DisplayName("购物车删除接口")
public class MarketDeleteShopCartTests {
    @BeforeAll
    static void beforeAll() {
        //删除购物车前，先获取购物车id
        MarketGetShopCartListResponseDTO marketGetShopCartListResponseDTO = MarketGetShopCartListFlow.getShopCartList();
        TestCaseDataForMarketConstant.shopCartId = marketGetShopCartListResponseDTO.getData().getNormalShopCartList().get(0).getShopCartList().get(0).getShopCartId();
    }
    @MethodSource("com.miller.market.shopCart.deleteShopCart.provider.MarketDeleteShopCartDataProvider#marketDeleteShopCartDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_购物车删除成功")
    void deleteShopCartSuccessfully(MarketDeleteShopCartRequestDTO marketDeleteShopCartRequestDTO) {
        MarketDeleteShopCartResponseDTO marketDeleteShopCartResponseDTO = MarketDeleteShopCartFlow.deleteShopCart(marketDeleteShopCartRequestDTO);

        assertThat(marketDeleteShopCartResponseDTO.getCode()).isEqualTo(ResponseConstant.code);
        assertThat(marketDeleteShopCartResponseDTO.getData().getResult()).isTrue();

    }

}
