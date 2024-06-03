package com.miller.market.shopCart.addShopCart;

import com.miller.market.constants.ResponseConstant;
import com.miller.market.shopCart.addShopCart.flow.MarketAddShopCartFlow;
import com.miller.market.shopCart.addShopCart.request.MarketAddShopCartRequestDTO;
import com.miller.market.shopCart.addShopCart.response.MarketAddShopCartResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 中超购物车列表
 */
@EnvTag.Test
@TestFramework
@DisplayName("购物车加购")
public class MarketAddShopCartTests {

    @MethodSource("com.miller.market.shopCart.addShopCart.provider.MarketAddShopCartDataProvider#marketAddShopCartDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_加购成功")
    void addShopCartSuccessfully(MarketAddShopCartRequestDTO marketAddShopCartRequestDTO) {
        MarketAddShopCartResponseDTO marketAddShopCartResponseDTO = MarketAddShopCartFlow.addShopCart(marketAddShopCartRequestDTO);

        assertThat(marketAddShopCartResponseDTO.getCode()).isEqualTo(ResponseConstant.code);
        assertThat(marketAddShopCartResponseDTO.getData().getResult()).isTrue();

    }

}
