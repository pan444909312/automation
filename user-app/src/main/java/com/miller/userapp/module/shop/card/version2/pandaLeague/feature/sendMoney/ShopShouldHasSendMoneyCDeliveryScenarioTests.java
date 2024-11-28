package com.miller.userapp.module.shop.card.version2.pandaLeague.feature.sendMoney;

import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version2.pandaLeague.flow.ShopListPandaLeagueFlow;
import com.miller.userapp.module.shop.card.version2.pandaLeague.request.ShopListPandaLeagueRequestDTO;
import com.miller.userapp.module.shop.card.version2.pandaLeague.response.ShopListResponseDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test

@TestFramework
@Scenario(scenarioID = "01JDR9SSRB28118W2EDV5WVDEY", scenarioName = "用户-首页店铺流-商卡(中文)-普通店铺配送商卡-熊猫联盟频道-辅助信息-配送价格-首页-商卡二期：配送价格 - C配"
        , developmentTime = 5, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("用户-首页店铺流-商卡(中文)-普通店铺配送商卡-熊猫联盟频道-辅助信息-配送价格-首页-商卡二期：配送价格 - C配")
public class ShopShouldHasSendMoneyCDeliveryScenarioTests {
    //    测试店铺:04
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(),"user.app.for.test.shop.card.version2.04.shopId"));

    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
    }
    @DisplayName("用户-首页店铺流-商卡(中文)-普通店铺配送商卡-熊猫联盟频道-辅助信息-配送价格-首页-商卡二期：配送价格 - C配")
    @MethodSource("DataProvider")
    @ParameterizedTest
    void hasSendMoneyInfo(ShopListPandaLeagueRequestDTO ShopListRequestdto){
        ShopListResponseDTO ShopListResponsedto= ShopListPandaLeagueFlow.getShopList(ShopListRequestdto);
        Integer sendMoney =ShopListResponsedto.getResult().getShopList().stream().filter(item -> item.getShopId().equals(shopId)).findFirst().map( ShopIndexVO::getSendMoney).orElseThrow();
        String sendMoneyMsg =ShopListResponsedto.getResult().getShopList().stream().filter(item -> item.getShopId().equals(shopId)).findFirst().map( ShopIndexVO::getSendMoneyMsg).orElseThrow();
        assertThat(sendMoney).isEqualTo(210);
        assertThat(sendMoneyMsg).isEqualTo("配送¥2.1起");}
    static Stream<Arguments> DataProvider() {
        ShopListPandaLeagueRequestDTO shopListPandaLeagueRequestDTO = new ShopListPandaLeagueRequestDTO();
        // 可以不用传参数
        shopListPandaLeagueRequestDTO.setFiltering(false); // 开发代码Bug，没有对 null 进行判断，应该默认给false的
        return Stream.of(Arguments.of(shopListPandaLeagueRequestDTO));
    }
}
