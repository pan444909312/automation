package com.miller.market.topic.getSpecialTopicGoods;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.miller.market.constants.BusinessConstant;
import com.miller.market.constants.ResponseConstant;
import com.miller.market.mapper.specialTopic.SpecialTopicMapper;
import com.miller.market.topic.getSpecialTopicGoods.flow.MarketGetSpecialTopicGoodsFlow;
import com.miller.market.topic.getSpecialTopicGoods.request.MarketGetSpecialTopicGoodsRequestDTO;
import com.miller.market.topic.getSpecialTopicGoods.response.MarketGetSpecialTopicGoodsResponseDTO;
import com.miller.market.util.DBUtils;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.panda.market.common.enums.SpecialTopicTypeEnum;
import com.panda.market.dal.entity.SpecialTopic;
import org.apache.ibatis.session.SqlSession;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


/**
 * 专题商品接口
 */
@EnvTag.Test
@TestFramework
@DisplayName("PF_临期专题商品")
public class MarketGetSpecialTopicGoodsByLotTests {
    private static SpecialTopicMapper specialTopicMapper;
    @BeforeAll
    static void beforeAll() {
        SqlSession sqlSession = DBUtils.getDBOfFreshTest();
        specialTopicMapper = sqlSession.getMapper(SpecialTopicMapper.class);

    }
    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("PF_正常流程_获取临期专题商品")
    void getDetailOneOfInByCustomizeSuccessfully(MarketGetSpecialTopicGoodsRequestDTO requestDTO) {
        MarketGetSpecialTopicGoodsResponseDTO responseDTO = MarketGetSpecialTopicGoodsFlow.getSpecialTopicGoods(requestDTO);

        Assertions.assertThat(responseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(responseDTO.getData()).isNotNull();

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        MarketGetSpecialTopicGoodsRequestDTO requestDTO = new MarketGetSpecialTopicGoodsRequestDTO();
        //获取临期专题(启用生效中未删除的专题)
        QueryWrapper<SpecialTopic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("portal_id", BusinessConstant.portalId);
        queryWrapper.eq("is_del", 0);
        queryWrapper.eq("effect_status",1);
        queryWrapper.eq("special_topic_status",1);
        queryWrapper.eq("type", SpecialTopicTypeEnum.LOT.getCode());
        queryWrapper.orderByDesc("special_topic_id");
        queryWrapper.last("limit 1");
        SpecialTopic specialTopic = specialTopicMapper.selectOne(queryWrapper);
        requestDTO.setSpecialTopicId(specialTopic.getSpecialTopicId());

        Page page = new Page();
        page.setCurrent(1L);
        page.setSize(20L);
        requestDTO.setPage(page);

        return Stream.of(Arguments.of(requestDTO));
    }

}
