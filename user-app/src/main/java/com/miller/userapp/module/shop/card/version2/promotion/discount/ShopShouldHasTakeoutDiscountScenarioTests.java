package com.miller.userapp.module.shop.card.version2.promotion.discount;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.entity.search.ShopSearchMiddleEntity;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.hungrypanda.app.server.vo.index.ShopPromoteVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.db.DBUtils;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.search.ShopSearchMiddleMapper;
import com.miller.userapp.mapper.shop.ProductDiscountMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version2.orinary.logo.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version2.orinary.logo.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version2.orinary.logo.response.ShopListResponseDTO;
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
@EnvTag.Test
@TestFramework
@DisplayName("商卡(中文)")
public class ShopShouldHasTakeoutDiscountScenarioTests {

    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId"));
    private ShopSearchMiddleMapper shopSearchMiddleMapper;
    private DBUtils dbUtils;
    //单品折扣使用渠道为外卖的活动sn
    private String discountSn = "5452C0FN0T";

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
    @DisplayName("普通店铺配送商卡-优惠标签-商品折扣-首页-商卡二期:商品折扣28-外卖可用")
    void shouldExistTakeoutDiscount(ShopListRequestDTO shopListRequestDTO) {

        ShopListResponseDTO shopList = ShopListFlow.getShopList(shopListRequestDTO);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();

        ShopPromoteVO pickup = shopIndexVO.getShopPromoteList().stream().filter(item -> item.getShowContent().contains("外卖")).findFirst().get();
        ShopSearchMiddleEntity shopDetail = shopSearchMiddleMapper.selectOne(new QueryWrapper<ShopSearchMiddleEntity>().eq("shop_id", shopId));



        String sql = "SELECT * FROM hp_product_discount where shop_id = ? and discount_sn = ?";
        Map<String, Object> stringObjectMap = dbUtils.queryOneObjectReturnMap(sql,shopId,discountSn);

//        double discountValue = (double)productDiscountEntity.getDiscountValue();
        Integer discountValueInteger = (Integer)stringObjectMap.get("discount_value");
        Double discountValue = discountValueInteger.doubleValue();

        assert shopDetail.getDiscountExc() == discountValue/10;
        assert pickup.getType() == 28;
        assert pickup.getShowContent().equals(shopDetail.getDiscountTagExc());

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
