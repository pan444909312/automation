package com.miller.market.goods.getGoodsByFirstCategory;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.miller.market.constants.BusinessConstant;
import com.miller.market.constants.ResponseConstant;
import com.miller.market.goods.getGoodsByFirstCategory.flow.MarketGetGoodsByFirstCategoryWithoutLoginFlow;
import com.miller.market.goods.getGoodsByFirstCategory.request.MarketGetGoodsByFirstCategoryRequestDTO;
import com.miller.market.goods.getGoodsByFirstCategory.response.MarketGetGoodsByFirstCategoryResponseDTO;
import com.miller.market.mapper.frontGroups.FrontGroupsMapper;
import com.miller.market.util.DBUtils;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.panda.market.dal.entity.FrontGroups;
import com.panda.market.dal.entity.User;
import org.apache.ibatis.session.SqlSession;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


/**
 * 分类页
 */
@EnvTag.Test
@TestFramework
@DisplayName("分类页商品")
public class MarketGetGoodsByFirstCategoryWithoutLoginScenarioTests {
    private static FrontGroupsMapper frontGroupsMapper;
    @BeforeAll
    static void beforeAll() {
        SqlSession sqlSession = DBUtils.getDBOfFreshTest();
        frontGroupsMapper = sqlSession.getMapper(FrontGroupsMapper.class);

    }

    @MethodSource("staticCategoryDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_未登录_获取分类页")
    void getGoodsByFirstCategoryWithoutLoginSuccessfully(MarketGetGoodsByFirstCategoryRequestDTO marketGetGoodsByFirstCategoryRequestDTO) {
        MarketGetGoodsByFirstCategoryResponseDTO marketGetGoodsByFirstCategoryResponseDTO = MarketGetGoodsByFirstCategoryWithoutLoginFlow.getCategoryGoods(marketGetGoodsByFirstCategoryRequestDTO);

        Assertions.assertThat(marketGetGoodsByFirstCategoryResponseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(marketGetGoodsByFirstCategoryResponseDTO.getData()).isNotNull();
        Assertions.assertThat(marketGetGoodsByFirstCategoryResponseDTO.getData().getCategoryList()).isNotNull();

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticCategoryDataProvider() {
        MarketGetGoodsByFirstCategoryRequestDTO requestDTO = new MarketGetGoodsByFirstCategoryRequestDTO();
        QueryWrapper<FrontGroups> groupsLambdaQueryWrapper = new QueryWrapper<>();
        groupsLambdaQueryWrapper.eq("portal_id", BusinessConstant.portalId);
        groupsLambdaQueryWrapper.eq("parent_id", "0");
        groupsLambdaQueryWrapper.last("limit 1");
        FrontGroups groups = frontGroupsMapper.selectOne(groupsLambdaQueryWrapper);
        requestDTO.setCategoryId(groups.getGroupsId());

        return Stream.of(Arguments.of(requestDTO));
    }
}
