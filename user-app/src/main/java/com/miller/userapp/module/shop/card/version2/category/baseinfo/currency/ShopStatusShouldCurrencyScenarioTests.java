package com.miller.userapp.module.shop.card.version2.category.baseinfo.currency;

import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version2.category.baseinfo.currency.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version2.category.baseinfo.currency.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version2.category.baseinfo.currency.response.ShopListResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@Scenario(scenarioID = "01J87H7YDD0SRZDXX18B0D13D3",
        scenarioName = "商卡(中文)_普通店铺配送商卡_基础信息_首页-商卡二期：货币符号",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 10, maintenanceTime = 0, manualTestTime = 3)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopStatusShouldCurrencyScenarioTests {
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId"));

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("商卡(中文)_普通店铺配送商卡_基础信息_首页-商卡二期：货币符号")
    void ShowCurrency(ShopListRequestDTO shopListRequestDTO) {
         UserLoginFlow.loginByDefaultUser();
//        请求首页店铺数据;
     ShopListResponseDTO shopList= ShopListFlow.getShopList(shopListRequestDTO);
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
        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}
