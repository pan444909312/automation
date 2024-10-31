package com.miller.market.goods.getGoods;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.miller.market.constants.BusinessConstant;
import com.miller.market.constants.ResponseConstant;
import com.miller.market.goods.getGoods.flow.MarketGetGoodsWithoutLoginFlow;
import com.miller.market.goods.getGoods.request.MarketGetGoodsRequestDTO;
import com.miller.market.goods.getGoods.response.MarketGetGoodsByFirstCategoryResponseDTO;
import com.miller.market.mapper.frontGroups.FrontGroupsMapper;
import com.miller.market.mapper.goods.GoodsMapper;
import com.miller.market.util.DBUtils;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.panda.market.common.enums.GoodsTypeEnum;
import com.panda.market.dal.entity.FrontGroups;
import com.panda.market.dal.entity.Goods;
import org.apache.ibatis.session.SqlSession;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


/**
 * 商品详情
 */
@Scenario(scenarioID = "01JA4ZPKGN7P6S2ZP8J8CTQ2KR",
        scenarioName = "正常流程_未登录_获取商品详情",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 0)
@EnvTag.Test
@DisplayName("商品详情")
public class MarketGetGoodsWithoutLoginScenarioTests {
    private static GoodsMapper goodsMapper;
    @BeforeAll
    static void beforeAll() {
        SqlSession sqlSession = DBUtils.getDBOfFreshTest();
        goodsMapper = sqlSession.getMapper(GoodsMapper.class);

    }

    @MethodSource("staticCategoryDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_未登录_获取分类页商品")
    void getGoodsByFirstCategoryWithoutLoginSuccessfully(MarketGetGoodsRequestDTO requestDTO) {
        MarketGetGoodsByFirstCategoryResponseDTO marketGetGoodsByFirstCategoryResponseDTO = MarketGetGoodsWithoutLoginFlow.getGoods(requestDTO);

        Assertions.assertThat(marketGetGoodsByFirstCategoryResponseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(marketGetGoodsByFirstCategoryResponseDTO.getData()).isNotNull();

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticCategoryDataProvider() {
        MarketGetGoodsRequestDTO requestDTO = new MarketGetGoodsRequestDTO();
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("portal_id", BusinessConstant.portalId);
        queryWrapper.eq("is_del", "0");
        //商品类型区分
        queryWrapper.eq("type", GoodsTypeEnum.GOODS_ORDINARY.getCode());
        queryWrapper.last("limit 1");
        Goods goods = goodsMapper.selectOne(queryWrapper);
        requestDTO.setGoodsId(goods.getGoodsId());

        return Stream.of(Arguments.of(requestDTO));
    }
}
