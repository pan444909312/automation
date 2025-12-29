package com.miller.userapp.moduleEnAu.shop.card.version3.userPack.feature.returnedVisitor;

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
import com.miller.userapp.moduleEnAu.shop.card.version3.userPack.flow.ShopListFlow;
import com.miller.userapp.moduleEnAu.shop.card.version3.userPack.request.ShopListRequestDTO;
import com.miller.userapp.moduleEnAu.shop.card.version3.userPack.response.ShopListResponseDTO;
import com.miller.userapp.util.PandaTestDBHelpful;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author panjuxiang
 * @since 2024/8/19 21:10
 */
@Scenario(scenarioID = "01K7JWZ6K5KT6A6ZF9DMT7D6Z2",
        scenarioName = "普通店铺配送商卡-自取频道-SKYX01_营销标_回头客_自取频道：回头客，不满足最低限制，不展示",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 10, manualTestTime = 10)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasNoReturnedVisitorFeatureCauseLimit {

    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId"));
    private ShopSearchMiddleMapper shopSearchMiddleMapper;


    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
        SqlSession sqlSession = com.miller.userapp.util.DBUtils.getDBOfPandaTest();
        shopSearchMiddleMapper = sqlSession.getMapper(ShopSearchMiddleMapper.class);
    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡-自取频道-SKYX01_营销标_回头客_自取频道：回头客，不满足最低限制，不展示")
    void shouldExistReturnedVisitorFeature(ShopListRequestDTO shopListRequestDTO) {

        ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO, shopId);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().orElse(null);

        ShopFeatureVO shopFeatureVO = shopIndexVO.getShopFeatureList().stream().
                filter(item -> item.getType().equals(ShopFeatureTypeConstant.RETURNED_VISITOR)).findFirst().orElse(null);

        ShopSearchMiddleEntity shopSearchMiddleEntity = shopSearchMiddleMapper.selectOne(new QueryWrapper<ShopSearchMiddleEntity>().eq("shop_id", shopId));

        List<Map<String, Object>> maps = PandaTestDBHelpful.executeSelectListSql("SELECT returned_vis2 FROM hp_data_shop_home_recommend_label where shop_id = " + shopId);
        Map<String, Object> map = maps.get(0);
        assertThat((Integer) map.get("returned_vis2") > 0).isTrue();
        assertThat(shopFeatureVO).isNull();
        assertThat(shopSearchMiddleEntity.getRepeatCustomer()).isEqualTo(0);
    }


    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 自取频道店铺流必须传经纬度
        shopListRequestDTO.setFiltering(false); // 开发代码Bug，没有对 null 进行判断，应该默认给false的
        shopListRequestDTO.setLongitude("115.954100");
        shopListRequestDTO.setLatitude("29.660580");
        shopListRequestDTO.setIsNeedMarketCategory(1);
        shopListRequestDTO.setMarketCategoryId(0);

        return Stream.of(Arguments.of(shopListRequestDTO));
    }

}
