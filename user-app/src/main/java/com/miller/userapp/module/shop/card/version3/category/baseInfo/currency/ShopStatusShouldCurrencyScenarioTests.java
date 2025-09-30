package com.miller.userapp.module.shop.card.version3.category.baseInfo.currency;

import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version3.category.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version3.category.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version3.category.response.ShopListResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@Scenario(scenarioID = "01K4W3SA1RN1TZ25HQY56TVHXH",
        scenarioName = "普通店铺配送商卡-品类频道页-SKYX01_基础信息_品类频道页-商卡二期：货币符号",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 10, maintenanceTime = 5, manualTestTime = 3)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopStatusShouldCurrencyScenarioTests {
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId"));

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡-品类频道页-SKYX01_基础信息_品类频道页-商卡二期：货币符号")
    void ShowCurrency(ShopListRequestDTO shopListRequestDTO) {
        UserLoginFlow.loginByDefaultUser();
//        请求品类频道页店铺数据;
        ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO, shopId);
//        请求的国家=CN，因此断言currency=¥
        assert shopList.getCurrency().equals("¥");

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
