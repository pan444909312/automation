package com.miller.market.index.getGoodsList;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miller.market.constants.BusinessConstant;
import com.miller.market.constants.ResponseConstant;
import com.miller.market.index.getGoodsList.flow.MarketGetGoodsListWithoutLoginFlow;
import com.miller.market.index.getGoodsList.request.MarketGetGoodsListRequestDTO;
import com.miller.market.index.getGoodsList.response.MarketGetGoodsListResponseDTO;
import com.miller.market.index.getIndex.flow.MarketGetIndexWithoutLoginFlow;
import com.miller.market.index.getIndex.response.MarketGetIndexResponseDTO;
import com.miller.market.mapper.frontGroups.FrontGroupsMapper;
import com.miller.market.util.DBUtils;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.panda.market.dal.dto.IndexItemDTO;
import com.panda.market.dal.dto.IndexListDTO;
import com.panda.market.dal.entity.FrontGroups;
import org.apache.ibatis.session.SqlSession;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * 首页商品流
 */
@Scenario(scenarioID = "01J5SGFNY03AZH1TY0GQ8Q7E77",
        scenarioName = "正常流程_未登录_获取首页商品流",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@EnvTag.Test
@DisplayName("首页商品流")
public class MarketGetGoodsListWithoutLoginScenarioTests {
    private static MarketGetIndexResponseDTO marketGetIndexResponseDTO = new MarketGetIndexResponseDTO();
    private static List<IndexListDTO>  indexList = new ArrayList<>();
    @BeforeAll
    static void beforeAll() {
        //调用首页接口
        marketGetIndexResponseDTO = MarketGetIndexWithoutLoginFlow.getIndex();

    }

    @MethodSource("staticGetGoodsListDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_未登录_获取首页第一个商品流_获取第二个商品流")
    void getGoodsByFirstCategoryWithoutLoginSuccessfully(MarketGetGoodsListRequestDTO marketGetGoodsListRequestDTO) {
        //第一个商品流
        MarketGetGoodsListResponseDTO marketGetGoodsListResponseDTO= MarketGetGoodsListWithoutLoginFlow.getGoodsList(marketGetGoodsListRequestDTO);

        Assertions.assertThat(marketGetGoodsListResponseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(marketGetGoodsListResponseDTO.getData()).isNotNull();

        //切换成第二个商品流
        marketGetGoodsListRequestDTO.setType(Long.valueOf(indexList.get(0).getItems().get(1).getType()));
        MarketGetGoodsListResponseDTO marketGetGoodsListResponseDTO1 = MarketGetGoodsListWithoutLoginFlow.getGoodsList(marketGetGoodsListRequestDTO);

        Assertions.assertThat(marketGetGoodsListResponseDTO1.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(marketGetGoodsListResponseDTO1.getData()).isNotNull();

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticGetGoodsListDataProvider() {
        MarketGetGoodsListRequestDTO requestDTO = new MarketGetGoodsListRequestDTO();
        //获取首页接口返回的商品瀑布里模块信息
        indexList = marketGetIndexResponseDTO.getData().getIndexList().stream().filter(IndexListDTO -> Objects.equals(IndexListDTO.getType(),"goods_flow")).collect(Collectors.toList());
        //获取第一个商品流的id
        requestDTO.setType(Long.valueOf(indexList.get(0).getItems().get(0).getType()));
        Page page = new Page();
        page.setCurrent(1L);
        page.setSize(20L);
        requestDTO.setPage(page);

        return Stream.of(Arguments.of(requestDTO));
    }
}
