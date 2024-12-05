package com.miller.market.open.goods.getFirstMenuList;

import com.miller.market.constants.ResponseConstant;
import com.miller.market.open.goods.getFirstMenuList.flow.MarketGetFirstMenuListWithoutLoginFlow;
import com.miller.market.open.goods.getFirstMenuList.request.MarketGetFirstMenuListRequestDTO;
import com.miller.market.open.goods.getFirstMenuList.response.MarketGetFirstMenuListResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


/**
 * 达达首页分类页
 */
@Scenario(scenarioID = "01JA4ZPKGN7P6S2ZP8J8CTQ2KQ",
        scenarioName = "正常流程_未登录_获取达达分类页一级分类-不过滤烟-过滤烟",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@EnvTag.Test
@DisplayName("达达分类页-一级分类")
public class MarketDaDaGetFirstMenuListWithoutLoginScenarioTests {

    @MethodSource("staticGetGoodsListDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_未登录_获取达达分类页一级分类-不过滤烟-过滤烟")
    void getGoodsByFirstCategoryWithoutLoginSuccessfully(MarketGetFirstMenuListRequestDTO requestDTO) {
        //不过滤烟
        MarketGetFirstMenuListResponseDTO responseDTO= MarketGetFirstMenuListWithoutLoginFlow.getFirstMenuList(requestDTO);

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
