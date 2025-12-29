package com.miller.userapp.moduleEnAu.shop.card.version3.category.feature.shopRank;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.entity.search.ShopSearchMiddleEntity;
import com.hungrypanda.app.server.vo.index.ShopFeatureVO;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.constants.ShopFeatureTypeConstant;
import com.miller.userapp.mapper.search.ShopSearchMiddleMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.moduleEnAu.shop.card.version3.category.flow.ShopListFlow;
import com.miller.userapp.moduleEnAu.shop.card.version3.category.request.ShopListRequestDTO;
import com.miller.userapp.moduleEnAu.shop.card.version3.category.response.ShopListResponseDTO;
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
 * @since 2024/8/24 16:33
 */
@Scenario(scenarioID = "01K4WCHW2Y9V31SS314K4ESETP",
        scenarioName = "普通店铺配送商卡-品类频道页-SKYX01_营销标_品类榜单_品类频道页-商卡二期：榜单",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 10, manualTestTime = 10)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasShopRankFeature {

    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.02.shopId"));
    private ShopSearchMiddleMapper shopSearchMiddleMapper;


    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
        SqlSession sqlSession = com.miller.userapp.util.DBUtils.getDBOfPandaTest();
        shopSearchMiddleMapper = sqlSession.getMapper(ShopSearchMiddleMapper.class);
    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡-品类频道页-SKYX01_营销标_品类榜单_品类频道页-商卡二期：榜单")
    void shouldExistShopRankFeature(ShopListRequestDTO shopListRequestDTO) {

        ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO, shopId);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();

        ShopFeatureVO shopFeatureVO = shopIndexVO.getShopFeatureList().stream().
                filter(item -> item.getType().equals(ShopFeatureTypeConstant.SHOP_RANK)).findFirst().get();

        ShopSearchMiddleEntity shopSearchMiddleEntity = shopSearchMiddleMapper.selectOne(new QueryWrapper<ShopSearchMiddleEntity>().eq("shop_id", shopId));

        assertThat(shopFeatureVO.getShowContent()).isEqualTo(shopSearchMiddleEntity.getShopRank());
    }


    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setFiltering(false);
        shopListRequestDTO.setMarketCategoryId(1);

        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}
