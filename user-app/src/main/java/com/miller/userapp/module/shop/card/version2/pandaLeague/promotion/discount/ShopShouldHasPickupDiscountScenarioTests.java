package com.miller.userapp.module.shop.card.version2.pandaLeague.promotion.discount;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.common.enums.ShopPromoteEnum;
import com.hungrypanda.app.server.entity.search.ShopSearchMiddleEntity;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.hungrypanda.app.server.vo.index.ShopPromoteVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.db.DBUtils;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.search.ShopSearchMiddleMapper;
import com.miller.userapp.mapper.shop.ProductDiscountMapper;
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

import java.util.Map;
import java.util.stream.Stream;

/**
 * @author panjuxiang
 * @since 2024/7/25 15:03
 */
@Scenario(scenarioID = "01JDRD88GFJRBECSZR2GR0XC1X",
        scenarioName = "商卡(中文)_普通店铺配送商卡-熊猫联盟频道_优惠标签_商品折扣_熊猫联盟频道-商卡二期:商品折扣28-自取可用",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@EnvTag.Test
@TestFramework
@DisplayName("商卡(中文)")
public class ShopShouldHasPickupDiscountScenarioTests {
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId"));
    private ShopSearchMiddleMapper shopSearchMiddleMapper;
    private ProductDiscountMapper productDiscountMapper;
    private DBUtils dbUtils;
    //单品折扣使用渠道为自取的活动sn
    private String discountSn = new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.pickup.discountSn");


    @BeforeAll
    void beforeAll() {
        dbUtils = new DBUtils(new PropertiesUtils().getProperty(this.getClass(), "spring.datasource.url"),
                new PropertiesUtils().getProperty(this.getClass(), "spring.datasource.username"),
                new PropertiesUtils().getProperty(this.getClass(), "spring.datasource.password"));
        UserLoginFlow.loginByDefaultUser();
        SqlSession sqlSession = com.miller.userapp.util.DBUtils.getDBOfPandaTest();
        shopSearchMiddleMapper = sqlSession.getMapper(ShopSearchMiddleMapper.class);
        productDiscountMapper = sqlSession.getMapper(ProductDiscountMapper.class);
    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡-熊猫联盟频道_优惠标签_商品折扣_自取可用_熊猫联盟频道-商卡二期:商品折扣28-自取可用")
    void shouldExistPickupDiscount(ShopListPandaLeagueRequestDTO shopListPandaLeagueRequestDTO) {

        ShopListResponseDTO shopList = ShopListPandaLeagueFlow.getShopList(shopListPandaLeagueRequestDTO);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();

        ShopPromoteVO pickup = shopIndexVO.getShopPromoteList().stream().
                filter(item -> item.getShowContent().contains("自取")).findFirst().get();
//        目前ProductDiscountEntity类的字段和数据库对不上，调用Mapper的方法会报错，改使用jdbcTemplate
//        ProductDiscountEntity productDiscountEntity = productDiscountMapper.selectOne(new QueryWrapper<ProductDiscountEntity>().eq("shop_id", shopId).eq("discount_sn",discountSn));
        ShopSearchMiddleEntity shopSearchMiddleEntity = shopSearchMiddleMapper.selectOne(new QueryWrapper<ShopSearchMiddleEntity>().eq("shop_id", shopId));

//        List<ProductDiscountEntity> prouctDiscountDiscountValue = productDiscountMapper.getProductDiscountDiscountValue(shopId.toString());


        String sql = "SELECT * FROM hp_product_discount where shop_id = ? and discount_sn = ?";
        Map<String, Object> stringObjectMap = dbUtils.queryOneObjectReturnMap(sql,shopId,discountSn);

//        double discountValue = (double)productDiscountEntity.getDiscountValue();
        Integer discountValueInteger = (Integer)stringObjectMap.get("discount_value");
        Double discountValue = discountValueInteger.doubleValue();


        assert shopSearchMiddleEntity.getTakeSelfDiscountExc() == discountValue/10;
        assert pickup.getType() == ShopPromoteEnum.INDEX_PRODUCT_DISCOUNT.getType();
        assert pickup.getShowContent().equals(shopSearchMiddleEntity.getTakeSelfDiscountTagExc());

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        return Stream.of(Arguments.of(PandaLeagueDataProvider.getCommonDataProvider()));
    }
}
