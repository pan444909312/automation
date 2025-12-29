package com.miller.userapp.moduleEnAu.shop.card.version3.userPack.feature.orderRemind;

import com.hungrypanda.app.server.common.enums.index.shopFeature.ShopFeatureEnum;
import com.hungrypanda.app.server.vo.index.ShopFeatureVO;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.cache.remote.redis.RedisService;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.moduleEnAu.shop.card.version3.userPack.flow.ShopListFlow;
import com.miller.userapp.moduleEnAu.shop.card.version3.userPack.request.ShopListRequestDTO;
import com.miller.userapp.moduleEnAu.shop.card.version3.userPack.response.ShopListResponseDTO;
import com.miller.userapp.util.RedisUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Scenario(scenarioID = "01K7JWZ6K5KT6A6ZF9DMT7D6YZ",
        scenarioName = "普通店铺配送商卡-自取频道-SKYX01_营销标_用户下单提醒_不满足条件时不展示｜24h无人下单",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasNoOrderRemindFeature {

    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.blank.02.compare.shopId"));

    RedisService redisInstance = RedisUtils.getRedisInstance();


    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
    }


    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡-自取频道-SKYX01_营销标_用户下单提醒_不满足条件时不展示｜24h无人下单")
    void shouldExistEvaluationFeature(ShopListRequestDTO shopListRequestDTO) {

        ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO, shopId);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();

        ShopFeatureVO shopFeatureVO = shopIndexVO.getShopFeatureList().stream().
                filter(item -> item.getType().equals(ShopFeatureEnum.USER_ORDER_REMIND.getType())).findFirst().orElse(null);

        assertThat(redisInstance.get("RECENT_ORDER_USER:" + shopId)).isNull();

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
