package com.miller.market.open.hpAd.getSceneGoodsList;

import com.miller.market.constants.ResponseConstant;
import com.miller.market.open.hpAd.getSceneGoodsList.flow.MarketGetSceneGoodsListWithProductLoginFlow;
import com.miller.market.open.hpAd.getSceneGoodsList.flow.MarketGetSceneGoodsListWithProductWithoutLoginFlow;
import com.miller.market.open.hpAd.getSceneGoodsList.request.MarketGetSceneGoodsListWithProductRequestDTO;
import com.miller.market.open.hpAd.getSceneGoodsList.response.MarketGetSceneGoodsListWithProductResponseDTO;
import com.miller.market.open.hpAd.getShopAdList.flow.MarketGetShopAdListLoginFlow;
import com.miller.market.open.hpAd.getShopAdList.request.MarketGetShopAdListRequestDTO;
import com.miller.market.open.hpAd.getShopAdList.response.MarketGetShopAdListResponseDTO;
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
 * 达达获取店铺场景商品列表
 */
@EnvTag.Test
@TestFramework
@DisplayName("达达获取店铺场景商品列表")
public class MarketDaDaGetSceneGoodsListWithProductLoginTests {

    private static MarketGetShopAdListResponseDTO marketGetShopAdListResponseDTO = new MarketGetShopAdListResponseDTO();
    @BeforeAll
    static void beforeAll() {
        MarketGetShopAdListRequestDTO requestDTO = new MarketGetShopAdListRequestDTO();
        requestDTO.setShopId(1314L);
        requestDTO.setIsFilterTobaccoGoods(false);
        //调用达达场景广告列表接口
        marketGetShopAdListResponseDTO = MarketGetShopAdListLoginFlow.getShopAdList(requestDTO);

    }
    @MethodSource("staticGetGoodsListDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_已登录_获取达达二级菜单及商品-不过滤烟-过滤烟")
    void getSceneGoodsListLoginSuccessfully(MarketGetSceneGoodsListWithProductRequestDTO requestDTO) {
        //不过滤烟
        MarketGetSceneGoodsListWithProductResponseDTO responseDTO = MarketGetSceneGoodsListWithProductLoginFlow.getSceneGoodsList(requestDTO);

        Assertions.assertThat(responseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(responseDTO.getData()).isNotNull();

        //过滤烟
        requestDTO.setIsFilterTobaccoGoods(true);
        responseDTO= MarketGetSceneGoodsListWithProductWithoutLoginFlow.getSceneGoodsList(requestDTO);

        Assertions.assertThat(responseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(responseDTO.getData()).isNotNull();
    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticGetGoodsListDataProvider() {
        MarketGetSceneGoodsListWithProductRequestDTO requestDTO = new MarketGetSceneGoodsListWithProductRequestDTO();
        requestDTO.setShopId(1314L);
        requestDTO.setIsFilterTobaccoGoods(false);
        //获取第一个场景广告id
        requestDTO.setSceneId(marketGetShopAdListResponseDTO.getData().get(0).getAdList().get(0).getSceneId());
        return Stream.of(Arguments.of(requestDTO));
    }

}
