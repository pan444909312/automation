package com.miller.userapp.module.shop.card.version2.pandaLeague.feature.categoryName;

import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version2.pandaLeague.dataProvider.PandaLeagueDataProvider;
import com.miller.userapp.module.shop.card.version2.pandaLeague.response.ShopListResponseDTO;
import com.miller.userapp.module.shop.card.version2.pandaLeague.flow.ShopListPandaLeagueFlow;
import com.miller.userapp.module.shop.card.version2.pandaLeague.request.ShopListPandaLeagueRequestDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test

@TestFramework
@Scenario(scenarioID = "01JDR9SSRCPFQ00ECRA2RG74G1", scenarioName = "用户-首页店铺流-商卡(中文)-普通店铺配送商卡-熊猫联盟频道-辅助信息-类目-熊猫联盟频道-商卡二期：类目 - 无数据"
        , author = "shandongdong@hungrypandagroup.com", developmentTime = 15, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("用户-首页店铺流-商卡(中文)-普通店铺配送商卡-熊猫联盟频道-辅助信息-类目-熊猫联盟频道-商卡二期：类目 - 无数据")
public class ShopShouldNotHasCategoryNameScenarioTests {

    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.blank.02.compare.shopId"));

    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
    }

    @DisplayName("用户-首页店铺流-商卡(中文)-普通店铺配送商卡-熊猫联盟频道-辅助信息-类目-熊猫联盟频道-商卡二期：类目 - 无数据")
    @MethodSource("DataProvider")
    @ParameterizedTest()
    void hasCategoryName(ShopListPandaLeagueRequestDTO shopListRequestDTO) {
        ShopListResponseDTO shopListResponseDTO = ShopListPandaLeagueFlow.getShopList(shopListRequestDTO);
        String merchantCategoryName = shopListResponseDTO.getResult().getShopList().stream().filter(item -> item.getShopId().equals(shopId)).findFirst().map(ShopIndexVO::getMerchantCategoryName).orElseThrow();
        Integer merchantCategoryId = shopListResponseDTO.getResult().getShopList().stream().filter(item -> item.getShopId().equals(shopId)).findFirst().map(ShopIndexVO::getMerchantCategoryId).orElseThrow();
        assertThat(merchantCategoryName).isEqualTo("");
        assertThat(merchantCategoryId).isEqualTo(0);

    }
    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> DataProvider() {
        return Stream.of(Arguments.of(PandaLeagueDataProvider.getCommonDataProvider()));
    }
}
