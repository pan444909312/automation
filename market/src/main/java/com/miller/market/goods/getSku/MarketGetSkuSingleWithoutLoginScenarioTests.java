package com.miller.market.goods.getSku;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.miller.market.constants.BusinessConstant;
import com.miller.market.constants.ResponseConstant;
import com.miller.market.goods.getSku.flow.MarketGetSkuWithoutLoginFlow;
import com.miller.market.goods.getSku.request.MarketGetSkuRequestDTO;
import com.miller.market.goods.getSku.response.MarketGetSkuResponseDTO;
import com.miller.market.mapper.goods.GoodsMapper;
import com.miller.market.util.DBUtils;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.panda.market.common.enums.GoodsTypeEnum;
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
 * 商品sku
 */
@Scenario(scenarioID = "01JVY5PFWDPM2K9TGZWYAGJNFS",
        scenarioName = "正常流程_未登录_获取单规格商品sku",
        author = "zhangpei@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@EnvTag.Test
@DisplayName("PF_商品sku")
public class MarketGetSkuSingleWithoutLoginScenarioTests {
    private static GoodsMapper goodsMapper;
    @BeforeAll
    static void beforeAll() {
        SqlSession sqlSession = DBUtils.getDBOfFreshTest();
        goodsMapper = sqlSession.getMapper(GoodsMapper.class);

    }

    @MethodSource("staticCategoryDataProvider")
    @ParameterizedTest
    @DisplayName("PF_正常流程_未登录_获取单规格商品sku")
    void getSkuWithoutLoginSuccessfully(MarketGetSkuRequestDTO requestDTO) {
        MarketGetSkuResponseDTO marketGetSkuResponseDTO = MarketGetSkuWithoutLoginFlow.getGoods(requestDTO);

        Assertions.assertThat(marketGetSkuResponseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(marketGetSkuResponseDTO.getData()).isNotNull();

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticCategoryDataProvider() {
        MarketGetSkuRequestDTO requestDTO = new MarketGetSkuRequestDTO();
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("portal_id", BusinessConstant.portalId);
        queryWrapper.eq("is_del", "0");
        //商品类型区分
        queryWrapper.eq("type", GoodsTypeEnum.GOODS_ORDINARY.getCode());
        //查询多规格商品
        queryWrapper.eq("has_specs",0);
        queryWrapper.last("limit 1");
        Goods goods = goodsMapper.selectOne(queryWrapper);
        requestDTO.setGoodsId(goods.getGoodsId());

        return Stream.of(Arguments.of(requestDTO));
    }
}
