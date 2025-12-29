package com.miller.userapp.moduleEnAu.shop.card.version3.search.feature.returnedVisitor;

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
import com.miller.userapp.moduleEnAu.shop.card.version3.search.flow.ShopListFlow;
import com.miller.userapp.moduleEnAu.shop.card.version3.search.request.ShopListRequestDTO;
import com.miller.userapp.moduleEnAu.shop.card.version3.search.response.ShopListResponseDTO;
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
@Scenario(scenarioID = "01K9RWTX9S9QKGYT6AKTVP5944",
        scenarioName = "搜索列表商卡-SKYX01_营销标_回头客_搜索列表-商卡二期：回头客，不满足最低限制，不展示",
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
    @DisplayName("搜索列表商卡-SKYX01_营销标_回头客_搜索列表-商卡二期：回头客，不满足最低限制，不展示")
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
        // 可以不用传参数
        shopListRequestDTO.setFiltering(false);
        shopListRequestDTO.setKeywords("商卡自动化");

        return Stream.of(Arguments.of(shopListRequestDTO));
    }

}
