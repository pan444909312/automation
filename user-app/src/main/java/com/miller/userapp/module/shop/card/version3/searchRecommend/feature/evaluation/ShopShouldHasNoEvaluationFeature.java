package com.miller.userapp.module.shop.card.version3.searchRecommend.feature.evaluation;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.entity.search.ShopSearchMiddleEntity;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.constants.ShopFeatureTypeConstant;
import com.miller.userapp.mapper.search.ShopSearchMiddleMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version3.searchRecommend.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version3.searchRecommend.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version3.searchRecommend.response.ShopListResponseDTO;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.ArrayList;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author panjuxiang
 * @since 2024/8/24 16:34
 */
@Scenario(scenarioID = "01KJ6V2PCXY737WX6NNT2JKS6D",
        scenarioName = "搜索推荐列表商卡-SKYX01_营销标_人工营销标签_搜索列表-商卡二期：营销文案 - 无数据",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 5, manualTestTime = 10)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasNoEvaluationFeature {


    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.blank.compare.shopId"));
    private ShopSearchMiddleMapper shopSearchMiddleMapper;

    @BeforeAll
    void beforeAll(){
        UserLoginFlow.loginByDefaultUser();
        SqlSession sqlSession = com.miller.userapp.util.DBUtils.getDBOfPandaTest();
        shopSearchMiddleMapper = sqlSession.getMapper(ShopSearchMiddleMapper.class);
    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("搜索推荐列表商卡-SKYX01_营销标_人工营销标签_搜索列表-商卡二期：营销文案 - 无数据")
    void shouldNotExistEvaluationFeature(ShopListRequestDTO shopListRequestDTO) {

        ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO,shopId);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();
        ShopSearchMiddleEntity shopSearchMiddleEntity = shopSearchMiddleMapper.selectOne(new QueryWrapper<ShopSearchMiddleEntity>().eq("shop_id", shopId));

        boolean flag = shopIndexVO.getShopFeatureList().stream().noneMatch(item -> item.getType().equals(ShopFeatureTypeConstant.EVALUATION));

        assertThat(flag).isTrue();
        assertThat(shopIndexVO.getEvaluation()).isEqualTo("");
        assertThat(shopSearchMiddleEntity.getEvaluation()).isEqualTo("");

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setKeywords("推荐");// 开发代码Bug，没有对 null 进行判断，应该默认给false的
        ArrayList<Long> shopIdList = new ArrayList<>();
        shopIdList.add(45367036L);
        shopIdList.add(930937488L);
        shopListRequestDTO.setShopIdList(shopIdList);
        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}
