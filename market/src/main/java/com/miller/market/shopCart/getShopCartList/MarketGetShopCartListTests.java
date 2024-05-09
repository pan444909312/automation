package com.miller.market.shopCart.getShopCartList;

import com.miller.market.constants.ResponseConstant;
import com.miller.market.login.flow.MarketLoginFlow;
import com.miller.market.login.request.MarketLoginRequestDTO;
import com.miller.market.login.response.MarketLoginResponseDTO;
import com.miller.market.shopCart.getShopCartList.flow.MarketGetShopCartListFlow;
import com.miller.market.shopCart.getShopCartList.request.MarketGetShopCartListRequestDTO;
import com.miller.market.shopCart.getShopCartList.response.MarketGetShopCartListResponseDTO;
import com.miller.market.util.RequestUtils;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 中超购物车列表
 */
@EnvTag.Test
@TestFramework
@DisplayName("购物车列表")
public class MarketGetShopCartListTests {

    @MethodSource("com.miller.market.shopCart.getShopCartList.provider.MarketGetShopCartListDataProvider#marketGetShopCartListDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_获取购物车列表")
    void getShopCartListSuccessfully(MarketGetShopCartListRequestDTO marketGetShopCartListRequestDTO) {
        MarketGetShopCartListResponseDTO marketGetShopCartListResponseDTO = MarketGetShopCartListFlow.getShopCartList(marketGetShopCartListRequestDTO);

        assertThat(marketGetShopCartListResponseDTO.getCode()).isEqualTo(ResponseConstant.code);
        assertThat(marketGetShopCartListResponseDTO.getData()).isNotNull();

    }

}
