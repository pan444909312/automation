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
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version2.pandaLeague.flow.ShopListFlow;
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
 * @since 2024/7/25 15:02
 */
@Scenario(scenarioID = "01J3VJ3JN37RWFDN1HG4498PDQ",
        scenarioName = "商卡(中文)_普通店铺配送商卡_基础信息_店铺角标_首页-商卡二期:商品折扣28-外卖可用",
        developmentTime = 40, maintenanceTime = 0, manualTestTime = 15)
@EnvTag.Test
@TestFramework
@DisplayName("商卡(中文)")
public class ShopShouldHasTakeoutDiscountScenarioTests {

    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId"));
    private ShopSearchMiddleMapper shopSearchMiddleMapper;
    private DBUtils dbUtils;
    //单品折扣使用渠道为外卖的活动sn
    private String discountSn = new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.takeout.discountSn");

    @BeforeAll
    void beforeAll() {
        dbUtils = new DBUtils(new PropertiesUtils().getProperty(this.getClass(), "spring.datasource.url"),
                new PropertiesUtils().getProperty(this.getClass(), "spring.datasource.username"),
                new PropertiesUtils().getProperty(this.getClass(), "spring.datasource.password"));
        UserLoginFlow.loginByDefaultUser();
        SqlSession sqlSession = com.miller.userapp.util.DBUtils.getDBOfPandaTest();
        shopSearchMiddleMapper = sqlSession.getMapper(ShopSearchMiddleMapper.class);
    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡_优惠标签_商品折扣_首页-商卡二期:商品折扣28-外卖可用")
    void shouldExistTakeoutDiscount(ShopListRequestDTO shopListRequestDTO) {

        ShopListResponseDTO shopList = ShopListFlow.getShopList(shopListRequestDTO);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();

        ShopPromoteVO pickup = shopIndexVO.getShopPromoteList().stream().
                filter(item -> item.getShowContent().contains("外卖")).findFirst().get();
        ShopSearchMiddleEntity shopSearchMiddleEntity = shopSearchMiddleMapper.selectOne(new QueryWrapper<ShopSearchMiddleEntity>().eq("shop_id", shopId));


        String sql = "SELECT * FROM hp_product_discount where shop_id = ? and discount_sn = ?";
        Map<String, Object> stringObjectMap = dbUtils.queryOneObjectReturnMap(sql, shopId, discountSn);

        Integer discountValueInteger = (Integer) stringObjectMap.get("discount_value");
        double discountValue = discountValueInteger.doubleValue();

        assert shopSearchMiddleEntity.getDiscountExc() == discountValue / 10;
        assert pickup.getType() == ShopPromoteEnum.INDEX_PRODUCT_DISCOUNT.getType();
        assert pickup.getShowContent().equals(shopSearchMiddleEntity.getDiscountTagExc());

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setFiltering(false); // 开发代码Bug，没有对 null 进行判断，应该默认给false的

        return Stream.of(Arguments.of(shopListRequestDTO));
    }

}
