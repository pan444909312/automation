package com.miller.userapp.module.shop.card.version2.category.feature.startSendMoney;

import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version2.category.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version2.category.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version2.category.response.ShopListResponseDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test

@TestFramework
@Scenario(scenarioID = "01JDR9SSRCPFQ00ECRA2RG74GB", scenarioName = "用户-首页店铺流-商卡(中文)-普通店铺配送商卡-品类频道-辅助信息-起送价-品类频道-商卡二期：起送价"
        , author = "yancancan@hungrypandagroup.com", developmentTime = 5, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("用户-首页店铺流-商卡(中文)-普通店铺配送商卡-品类频道-辅助信息-起送价-品类频道-商卡二期：起送价")
public class ShopShouldHasStartSendMoneyScenarioTests {

    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId"));

    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
    }

    @DisplayName("用户-首页店铺流-商卡(中文)-普通店铺配送商卡-品类频道-辅助信息-起送价-品类频道-商卡二期：起送价")
    @MethodSource("showLabelDataProvider")
    @ParameterizedTest()
    void hasStartSendMoney(ShopListRequestDTO shopListRequestDTO) {
        ShopListResponseDTO shopListResponseDTO = ShopListFlow.getShopList(shopListRequestDTO);
        Integer startSendMoney = shopListResponseDTO.getResult().getShopList().stream().filter(item -> item.getShopId().equals(shopId)).findFirst().map(ShopIndexVO::getStartSendMoney).orElseThrow();
        assertThat(startSendMoney).isEqualTo(1010);
    }

    static Stream<Arguments> showLabelDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setFiltering(false); // 开发代码Bug，没有对 null 进行判断，应该默认给false的
        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}

