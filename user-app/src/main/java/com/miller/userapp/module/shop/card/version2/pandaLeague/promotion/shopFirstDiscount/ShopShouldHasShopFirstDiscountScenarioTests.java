package com.miller.userapp.module.shop.card.version2.pandaLeague.promotion.shopFirstDiscount;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.common.enums.ShopPromoteEnum;
import com.hungrypanda.app.server.common.utils.PriceUtil;
import com.hungrypanda.app.server.entity.search.ShopSearchMiddleEntity;
import com.hungrypanda.app.server.entity.shop.ShopFirstDiscountConfigEntity;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.hungrypanda.app.server.vo.index.ShopPromoteVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.search.ShopSearchMiddleMapper;
import com.miller.userapp.mapper.shop.ShopFirstDiscountMapper;
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

/**
 * @author panjuxiang
 * @since 2024/7/31 14:00
 */
@Scenario(scenarioID = "01JDRBYQ20JBWJKPVWG4MG5WT1",
        scenarioName = "商卡(中文)_普通店铺配送商卡-熊猫联盟频道_优惠标签_门店新客_熊猫联盟频道-商卡二期：门店新客24",
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 10)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasShopFirstDiscountScenarioTests {
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId"));
    private ShopSearchMiddleMapper shopSearchMiddleMapper;
    private ShopFirstDiscountMapper shopFirstDiscountMapper;

    //门店新客活动id
    private final Long id = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(),"user.app.for.test.shop.card.version2.shop.first.discount.id"));

    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
        SqlSession sqlSession = com.miller.userapp.util.DBUtils.getDBOfPandaTest();
        shopSearchMiddleMapper = sqlSession.getMapper(ShopSearchMiddleMapper.class);
        shopFirstDiscountMapper = sqlSession.getMapper(ShopFirstDiscountMapper.class);
    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡-熊猫联盟频道_优惠标签_门店新客_熊猫联盟频道-商卡二期：门店新客24")
    void shouldExistShopFirstDiscount(ShopListPandaLeagueRequestDTO shopListPandaLeagueRequestDTO) {

        ShopListResponseDTO shopList = ShopListPandaLeagueFlow.getShopList(shopListPandaLeagueRequestDTO);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();

        ShopPromoteVO shopPromoteVO = shopIndexVO.getShopPromoteList().stream().
                filter(item -> item.getType() == ShopPromoteEnum.INDEX_SHOP_FIRST_DISCOUNT.getType()).findFirst().get();
        ShopSearchMiddleEntity shopSearchMiddleEntity = shopSearchMiddleMapper.selectOne(new QueryWrapper<ShopSearchMiddleEntity>().eq("shop_id", shopId));
        ShopFirstDiscountConfigEntity shopFirstDiscountConfigEntity = shopFirstDiscountMapper.selectOne(new QueryWrapper<ShopFirstDiscountConfigEntity>().eq("shop_id", shopId));

        assert shopFirstDiscountConfigEntity.getId().equals(id);
        assert shopFirstDiscountConfigEntity.getShopFirstDiscount().equals(shopSearchMiddleEntity.getShopFirstDiscount());
        assert shopPromoteVO.getType() == ShopPromoteEnum.INDEX_SHOP_FIRST_DISCOUNT.getType();
        assert shopPromoteVO.getShowContent().equals("新客减" +  PriceUtil.getFixedPrice(shopSearchMiddleEntity.getShopFirstDiscount(), shopSearchMiddleEntity.getCountry()));
    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        return Stream.of(Arguments.of(PandaLeagueDataProvider.getCommonDataProvider()));
    }
}
