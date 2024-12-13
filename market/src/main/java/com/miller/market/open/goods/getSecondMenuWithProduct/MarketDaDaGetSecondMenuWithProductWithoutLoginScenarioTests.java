package com.miller.market.open.goods.getSecondMenuWithProduct;

import com.miller.market.constants.ResponseConstant;
import com.miller.market.open.goods.getFirstMenuList.flow.MarketGetFirstMenuListWithoutLoginFlow;
import com.miller.market.open.goods.getFirstMenuList.request.MarketGetFirstMenuListRequestDTO;
import com.miller.market.open.goods.getFirstMenuList.response.MarketGetFirstMenuListResponseDTO;
import com.miller.market.open.goods.getSecondMenuWithProduct.flow.MarketGetSecondMenuWithProductWithoutLoginFlow;
import com.miller.market.open.goods.getSecondMenuWithProduct.request.MarketGetSecondMenuWithProductRequestDTO;
import com.miller.market.open.goods.getSecondMenuWithProduct.response.MarketGetSecondMenuWithProductResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
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
@Scenario(scenarioID = "01JA4ZPKGN7P6S2ZP8J8CTQ2KQ",
        scenarioName = "正常流程_未登录_获取达达二级菜单及商品-不过滤烟-过滤烟",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@EnvTag.Test
@DisplayName("PF_达达二级菜单及商品")
public class MarketDaDaGetSecondMenuWithProductWithoutLoginScenarioTests {
    private static MarketGetFirstMenuListResponseDTO marketGetFirstMenuListResponseDTO = new MarketGetFirstMenuListResponseDTO();
    @BeforeAll
    static void beforeAll() {
        MarketGetFirstMenuListRequestDTO requestDTO = new MarketGetFirstMenuListRequestDTO();
        requestDTO.setShopId(1314L);
        requestDTO.setIsFilterTobaccoGoods(false);
        //调用达达一级分类接口接口
        marketGetFirstMenuListResponseDTO = MarketGetFirstMenuListWithoutLoginFlow.getFirstMenuList(requestDTO);

    }
    @MethodSource("staticGetGoodsListDataProvider")
    @ParameterizedTest
    @DisplayName("PF_正常流程_未登录_获取达达二级菜单及商品-不过滤烟-过滤烟")
    void getGoodsByFirstCategoryWithoutLoginSuccessfully(MarketGetSecondMenuWithProductRequestDTO requestDTO) {
        //不过滤烟
        MarketGetSecondMenuWithProductResponseDTO responseDTO= MarketGetSecondMenuWithProductWithoutLoginFlow.getSecondMenuWithProduct(requestDTO);

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
        //获取第一个分类id
        requestDTO.setMenuId(marketGetFirstMenuListResponseDTO.getData().get(0).getMenuId());
        return Stream.of(Arguments.of(requestDTO));
    }
}
