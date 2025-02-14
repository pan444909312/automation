package com.miller.userapp.module.shop.card.version2.pandaLeague.feature.fastFood;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.entity.search.ShopSearchMiddleEntity;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.constants.ShopFeatureTypeConstant;
import com.miller.userapp.mapper.search.ShopSearchMiddleMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version2.pandaLeague.dataProvider.PandaLeagueDataProvider;
import com.miller.userapp.module.shop.card.version2.pandaLeague.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version2.pandaLeague.flow.ShopListPandaLeagueFlow;
import com.miller.userapp.module.shop.card.version2.pandaLeague.request.ShopListPandaLeagueRequestDTO;
import com.miller.userapp.module.shop.card.version2.pandaLeague.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version2.pandaLeague.response.ShopListResponseDTO;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author panjuxiang
 * @since 2024/8/24 17:06
 */
@Scenario(scenarioID = "01JD3T8HMXC84YWKWQA793EK9D",
        scenarioName = "商卡(中文)_普通店铺配送商卡-熊猫联盟频道_营销标_标签7_快速出餐_熊猫联盟频道-商卡二期：快速出餐 - 不展示",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 10, maintenanceTime = 0, manualTestTime = 10)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasNoFastFoodFeature {

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
    @DisplayName("普通店铺配送商卡-熊猫联盟频道_营销标_标签7_快速出餐_熊猫联盟频道-商卡二期：快速出餐 - 不展示")
    void shouldNotExistFastFoodFeature(ShopListPandaLeagueRequestDTO shopListPandaLeagueRequestDTO) {

        ShopListResponseDTO shopList = ShopListPandaLeagueFlow.getShopList(shopListPandaLeagueRequestDTO);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();
        ShopSearchMiddleEntity shopSearchMiddleEntity = shopSearchMiddleMapper.selectOne(new QueryWrapper<ShopSearchMiddleEntity>().eq("shop_id", shopId));

        boolean flag = shopIndexVO.getShopFeatureList().stream().noneMatch(item -> item.getType().equals(ShopFeatureTypeConstant.FAST_FOOD));

        assertThat(flag).isTrue();
        assertThat(shopIndexVO.getIsFastFood()).isNull();
        assertThat(shopSearchMiddleEntity.getIsFastFood()).isEqualTo(0);

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        return Stream.of(Arguments.of(PandaLeagueDataProvider.getCommonDataProvider()));
    }
}
