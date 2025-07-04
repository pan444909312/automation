package com.miller.market.open.goods.getSecondMenuWithProduct;

import com.miller.market.constants.ResponseConstant;
import com.miller.market.open.goods.getFirstMenuList.flow.MarketGetFirstMenuListLoginFlow;
import com.miller.market.open.goods.getFirstMenuList.request.MarketGetFirstMenuListRequestDTO;
import com.miller.market.open.goods.getFirstMenuList.response.MarketGetFirstMenuListResponseDTO;
import com.miller.market.open.goods.getSecondMenuWithProduct.flow.MarketGetSecondMenuWithProductLoginFlow;
import com.miller.market.open.goods.getSecondMenuWithProduct.flow.MarketGetSecondMenuWithProductWithoutLoginFlow;
import com.miller.market.open.goods.getSecondMenuWithProduct.request.MarketGetSecondMenuWithProductRequestDTO;
import com.miller.market.open.goods.getSecondMenuWithProduct.response.MarketGetSecondMenuWithProductResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


/**
 * 达达二级菜单及商品
 */
@EnvTag.Test
@TestFramework
@DisplayName("PF_达达二级菜单及商品")
public class MarketDaDaGetSecondMenuWithProductLoginTests {

    private static MarketGetFirstMenuListResponseDTO marketGetFirstMenuListResponseDTO = new MarketGetFirstMenuListResponseDTO();
    @BeforeAll
    static void beforeAll() {
        MarketGetFirstMenuListRequestDTO requestDTO = new MarketGetFirstMenuListRequestDTO();
        requestDTO.setShopId(1314L);
        requestDTO.setIsFilterTobaccoGoods(false);
        //调用达达一级分类接口接口
        marketGetFirstMenuListResponseDTO = MarketGetFirstMenuListLoginFlow.getFirstMenuList(requestDTO);

    }
    @MethodSource("staticGetGoodsListDataProvider")
    @ParameterizedTest
    @DisplayName("PF_正常流程_已登录_获取达达二级菜单及商品-不过滤烟-过滤烟")
    void getGoodsByFirstCategoryLoginSuccessfully(MarketGetSecondMenuWithProductRequestDTO requestDTO) {
        //不过滤烟
        MarketGetSecondMenuWithProductResponseDTO responseDTO = MarketGetSecondMenuWithProductLoginFlow.getSecondMenuWithProduct(requestDTO);

        Assertions.assertThat(responseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(responseDTO.getData()).isNotNull();

        //过滤烟
        requestDTO.setIsFilterTobaccoGoods(true);
        responseDTO= MarketGetSecondMenuWithProductWithoutLoginFlow.getSecondMenuWithProduct(requestDTO);

        Assertions.assertThat(responseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(responseDTO.getData()).isNotNull();
    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticGetGoodsListDataProvider() {
        MarketGetSecondMenuWithProductRequestDTO requestDTO = new MarketGetSecondMenuWithProductRequestDTO();
        requestDTO.setShopId(1314L);
        requestDTO.setIsFilterTobaccoGoods(false);
        requestDTO.setMenuId(marketGetFirstMenuListResponseDTO.getData().get(0).getMenuId());
        return Stream.of(Arguments.of(requestDTO));
    }

}
