package com.miller.market.open.goods.getFirstMenuList;

import com.miller.market.constants.ResponseConstant;
import com.miller.market.open.goods.getFirstMenuList.flow.MarketGetFirstMenuListLoginFlow;
import com.miller.market.open.goods.getFirstMenuList.flow.MarketGetFirstMenuListWithoutLoginFlow;
import com.miller.market.open.goods.getFirstMenuList.request.MarketGetFirstMenuListRequestDTO;
import com.miller.market.open.goods.getFirstMenuList.response.MarketGetFirstMenuListResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


/**
 * 达达首页商品流
 */
@EnvTag.Test
@TestFramework
@DisplayName("PF_达达分类页-一级分类")
public class MarketDaDaGetFirstMenuListLoginTests {
    @MethodSource("staticGetGoodsListDataProvider")
    @ParameterizedTest
    @DisplayName("PF_正常流程_已登录_获取达达分类页一级分类-不过滤烟-过滤烟")
    void getGoodsByFirstCategoryLoginSuccessfully(MarketGetFirstMenuListRequestDTO requestDTO) {
        //不过滤烟
        MarketGetFirstMenuListResponseDTO responseDTO = MarketGetFirstMenuListLoginFlow.getFirstMenuList(requestDTO);

        Assertions.assertThat(responseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(responseDTO.getData()).isNotNull();

        //过滤烟
        requestDTO.setIsFilterTobaccoGoods(true);
        responseDTO= MarketGetFirstMenuListWithoutLoginFlow.getFirstMenuList(requestDTO);

        Assertions.assertThat(responseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(responseDTO.getData()).isNotNull();
    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticGetGoodsListDataProvider() {
        MarketGetFirstMenuListRequestDTO requestDTO = new MarketGetFirstMenuListRequestDTO();
        requestDTO.setShopId(1314L);
        requestDTO.setIsFilterTobaccoGoods(false);
        return Stream.of(Arguments.of(requestDTO));
    }

}
