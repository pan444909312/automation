package com.miller.userapp.module.shop.card.version3.redPacketShopList.feature.highSale;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.common.enums.index.shopFeature.ShopFeatureEnum;
import com.hungrypanda.app.server.entity.search.ShopSearchMiddleEntity;
import com.hungrypanda.app.server.vo.index.ShopFeatureVO;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.search.ShopSearchMiddleMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version3.redPacketShopList.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version3.redPacketShopList.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version3.redPacketShopList.response.ShopListResponseDTO;
import com.miller.userapp.util.PandaTestDBHelpful;
import com.miller.userapp.util.RedisUtils;
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

@Scenario(scenarioID = "01K4WC5K8Z22VT5Z970KFDXN14",
        scenarioName = "普通店铺配送商卡-品类频道页-SKYX01_营销标_人气销量标签_满足条件时，返回：高月售人气门店",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 10, manualTestTime = 10)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasHighSaleFeature {

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
    @DisplayName("普通店铺配送商卡-品类频道页-SKYX01_营销标_人气销量标签_满足条件时，返回：高月售人气门店")
    void shouldExistEvaluationFeature(ShopListRequestDTO shopListRequestDTO) {

        ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO,shopId);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();

        ShopFeatureVO shopFeatureVO = shopIndexVO.getShopFeatureList().stream().
                filter(item -> item.getType().equals(ShopFeatureEnum.POPULAR_STORE.getType())).findFirst().orElse(null);

        ShopSearchMiddleEntity shopSearchMiddleEntity = shopSearchMiddleMapper.selectOne(new QueryWrapper<ShopSearchMiddleEntity>().eq("shop_id", shopId));
        List<Map<String, Object>> maps = PandaTestDBHelpful.executeSelectListSql("SELECT monthly_sales FROM hp_data_shop_home_recommend_label where shop_id = " + shopId);
        Map<String, Object> map = maps.get(0);
        int cardMonthSaleNum = Integer.parseInt(RedisUtils.getRedisInstance().get("CARD_MONTH_SALE_NUM").toString());

        assertThat((int)map.get("monthly_sales") > cardMonthSaleNum).isEqualTo(true);
        assertThat(shopSearchMiddleEntity.getHighSaleShop()).isEqualTo(1);
        assertThat(shopFeatureVO.getShowContent()).isEqualTo("高月售人气门店");
    }


    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setCityName("九江市");// 开发代码Bug，没有对 null 进行判断，应该默认给false的
        shopListRequestDTO.setShopCategoryIds("[3896,3914,5486]");

        return Stream.of(Arguments.of(shopListRequestDTO));
    }

}
