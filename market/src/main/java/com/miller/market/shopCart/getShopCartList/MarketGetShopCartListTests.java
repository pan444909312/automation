package com.miller.market.shopCart.getShopCartList;

import com.miller.market.constants.ResponseConstant;
import com.miller.market.constants.TestCaseDataForMarketConstant;
import com.miller.market.shopCart.getShopCartList.flow.MarketGetShopCartListFlow;
import com.miller.market.shopCart.getShopCartList.response.MarketGetShopCartListResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 中超购物车列表
 */
@EnvTag.Test
@TestFramework
@DisplayName("购物车列表")
public class MarketGetShopCartListTests {


    @Test
    @DisplayName("正常流程_获取购物车列表")
    void getShopCartListSuccessfully() {
        MarketGetShopCartListResponseDTO marketGetShopCartListResponseDTO = MarketGetShopCartListFlow.getShopCartList();

        assertThat(marketGetShopCartListResponseDTO.getCode()).isEqualTo(ResponseConstant.code);
        assertThat(marketGetShopCartListResponseDTO.getData()).isNotNull();

        TestCaseDataForMarketConstant.shopCartId = marketGetShopCartListResponseDTO.getData().getNormalShopCartList().get(0).getShopCartList().get(0).getShopCartId();
    }

}
