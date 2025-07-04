package com.miller.market.open.hpAd.getShopAdList;

import com.miller.market.constants.ResponseConstant;
import com.miller.market.open.hpAd.getShopAdList.flow.MarketGetShopAdListWithoutLoginFlow;
import com.miller.market.open.hpAd.getShopAdList.request.MarketGetShopAdListRequestDTO;
import com.miller.market.open.hpAd.getShopAdList.response.MarketGetShopAdListResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


/**
 * 达达获取店铺广告列表
 */
@Scenario(scenarioID = "01JDKRYYZ2E061J4C0Q29WQ74B",
        scenarioName = "正常流程_未登录_获取店铺广告列表-不过滤烟-过滤烟",
        author = "zhangpei@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@EnvTag.Test
@DisplayName("PF_达达获取店铺广告列表")
public class MarketDaDaGetShopAdListWithoutLoginScenarioTests {

    @MethodSource("staticGetGoodsListDataProvider")
    @ParameterizedTest
    @DisplayName("PF_正常流程_未登录_获取店铺广告列表-不过滤烟-过滤烟")
    void getShopAdListWithoutLoginSuccessfully(MarketGetShopAdListRequestDTO requestDTO) {
        //不过滤烟
        MarketGetShopAdListResponseDTO responseDTO= MarketGetShopAdListWithoutLoginFlow.getShopAdList(requestDTO);

        Assertions.assertThat(responseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(responseDTO.getData()).isNotNull();

        //过滤烟
        requestDTO.setIsFilterTobaccoGoods(true);
        responseDTO= MarketGetShopAdListWithoutLoginFlow.getShopAdList(requestDTO);

        Assertions.assertThat(responseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(responseDTO.getData()).isNotNull();


    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticGetGoodsListDataProvider() {
        MarketGetShopAdListRequestDTO requestDTO = new MarketGetShopAdListRequestDTO();
        requestDTO.setShopId(1314L);
        requestDTO.setIsFilterTobaccoGoods(false);
        return Stream.of(Arguments.of(requestDTO));
    }
}
