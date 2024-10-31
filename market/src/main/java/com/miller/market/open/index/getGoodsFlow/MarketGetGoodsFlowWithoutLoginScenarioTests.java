package com.miller.market.open.index.getGoodsFlow;

import com.miller.market.constants.ResponseConstant;
import com.miller.market.index.getIndex.flow.MarketGetIndexWithoutLoginFlow;
import com.miller.market.index.getIndex.response.MarketGetIndexResponseDTO;
import com.miller.market.open.index.getGoodsFlow.flow.MarketGetGoodsFlowWithoutLoginFlow;
import com.miller.market.open.index.getGoodsFlow.request.MarketGetGoodsFlowRequestDTO;
import com.miller.market.open.index.getGoodsFlow.response.MarketGetGoodsFlowResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.panda.market.dal.dto.IndexListDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


/**
 * 达达首页商品流
 */
@Scenario(scenarioID = "01JASKMRY6YABS9MDEX8FXSX5H",
        scenarioName = "正常流程_未登录_获取达达首页店铺流-不过滤烟-过滤烟",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@EnvTag.Test
@DisplayName("达达首页商品流")
public class MarketGetGoodsFlowWithoutLoginScenarioTests {
    private static MarketGetIndexResponseDTO marketGetIndexResponseDTO = new MarketGetIndexResponseDTO();
    private static List<IndexListDTO>  indexList = new ArrayList<>();
    @BeforeAll
    static void beforeAll() {
        //调用首页接口
        marketGetIndexResponseDTO = MarketGetIndexWithoutLoginFlow.getIndex();

    }

    @MethodSource("staticGetGoodsListDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_未登录_获取达达首页店铺流-不过滤烟-过滤烟")
    void getGoodsByFirstCategoryWithoutLoginSuccessfully(MarketGetGoodsFlowRequestDTO requestDTO) {
        //不过滤烟
        MarketGetGoodsFlowResponseDTO responseDTO= MarketGetGoodsFlowWithoutLoginFlow.getGoodsList(requestDTO);

        Assertions.assertThat(responseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(responseDTO.getData()).isNotNull();

        //过滤烟
        requestDTO.setIsFilterTobaccoGoods(true);
        Stream.of(Arguments.of(requestDTO));
        responseDTO= MarketGetGoodsFlowWithoutLoginFlow.getGoodsList(requestDTO);

        Assertions.assertThat(responseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(responseDTO.getData()).isNotNull();


    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticGetGoodsListDataProvider() {
        MarketGetGoodsFlowRequestDTO requestDTO = new MarketGetGoodsFlowRequestDTO();
        requestDTO.setShopId(1314L);
        requestDTO.setIsFilterTobaccoGoods(false);
        return Stream.of(Arguments.of(requestDTO));
    }
}
