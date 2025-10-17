package com.miller.userapp.module.shop.card.version3.userPack.feature.highSale;

import com.hungrypanda.app.server.common.enums.index.shopFeature.ShopFeatureEnum;
import com.hungrypanda.app.server.vo.index.ShopFeatureVO;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version3.userPack.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version3.userPack.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version3.userPack.response.ShopListResponseDTO;
import com.miller.userapp.util.PandaTestDBHelpful;
import com.miller.userapp.util.RedisUtils;
import com.miller.userapp.util.RequestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Scenario(scenarioID = "01K7JWZ6K5KT6A6ZF9DMT7D6YV",
        scenarioName = "普通店铺配送商卡-自取频道-SKYX01_营销标_人气销量标签_不属于前10店铺，不展示",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 10, manualTestTime = 10)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasNoHighSaleCauseNotInTopTenFeature {

    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.highSaleNoTop10.shopId"));


    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
        Map<String, Object> headers = RequestUtils.getHeaders();

        // 数据目前造在温州，所以吧经纬度改到了温州
        headers.put("latitude", "27.98596");
        headers.put("longitude", "120.70423");

    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡-自取频道-SKYX01_营销标_人气销量标签_不属于前10店铺，不展示")
    void shouldExistEvaluationFeature(ShopListRequestDTO shopListRequestDTO) {
        shopListRequestDTO.setLatitude("27.98596");
        shopListRequestDTO.setLongitude("120.70423");

        ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO, shopId);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();

        ShopFeatureVO shopFeatureVO = shopIndexVO.getShopFeatureList().stream().
                filter(item -> item.getType().equals(ShopFeatureEnum.POPULAR_STORE.getType())).findFirst().orElse(null);

        List<Map<String, Object>> maps = PandaTestDBHelpful.executeSelectListSql("SELECT monthly_sales FROM hp_data_shop_home_recommend_label where shop_id = " + shopId);
        Map<String, Object> map = maps.get(0);
        int cardMonthSaleNum = Integer.parseInt(RedisUtils.getSysAppConfigValue("CARD_MONTH_SALE_NUM"));

        // 月售是满足的
        assertThat((int) map.get("monthly_sales") > cardMonthSaleNum).isEqualTo(true);
        // 但是因为不是top10的店铺会查不到 返回null
        assertThat(shopFeatureVO).isNull();
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
