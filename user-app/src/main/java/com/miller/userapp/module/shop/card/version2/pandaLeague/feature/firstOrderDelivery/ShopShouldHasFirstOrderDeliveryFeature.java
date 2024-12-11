package com.miller.userapp.module.shop.card.version2.pandaLeague.feature.firstOrderDelivery;

import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import com.miller.userapp.module.shop.card.version2.pandaLeague.dataProvider.PandaLeagueDataProvider;
import com.miller.userapp.module.shop.card.version2.pandaLeague.flow.ShopListPandaLeagueFlow;
import com.miller.userapp.module.shop.card.version2.pandaLeague.request.ShopListPandaLeagueRequestDTO;
import com.miller.userapp.module.shop.card.version2.pandaLeague.response.ShopListResponseDTO;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author panjuxiang
 * @since 2024/8/28 17:54
 */
@Scenario(scenarioID = "01JD1Z7S4GG6MCKTMCZ1FBYEC7",
        scenarioName = "商卡(中文)_普通店铺配送商卡-熊猫联盟频道_营销标_首单优先送_熊猫联盟频道-商卡二期：首单优先送",
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 10)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasFirstOrderDeliveryFeature {
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId"));
    private UserLoginRequestDTO userLoginRequestDTO ;


    @BeforeAll
    void beforeAll() {
        userLoginRequestDTO = new UserLoginRequestDTO();
        userLoginRequestDTO.setAccount(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.for.shop.card.version2.new.user02.account"));
        userLoginRequestDTO.setPassword(MD5Util.string2MD5(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.for.shop.card.version2.new.user02.password")));
        userLoginRequestDTO.setDistinctId(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.distinctId"));
        userLoginRequestDTO.setType(Integer.valueOf(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.public.login.type")));
        userLoginRequestDTO.setAreaCode(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.callingCode"));

        UserLoginFlow.loginAndPutToken(userLoginRequestDTO);
    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡-熊猫联盟频道_营销标_首单优先送_熊猫联盟频道-商卡二期：首单优先送")
    void shouldExistFirstOrderDeliveryFeature(ShopListPandaLeagueRequestDTO shopListPandaLeagueRequestDTO) {

        ShopListResponseDTO shopList = ShopListPandaLeagueFlow.getShopList(shopListPandaLeagueRequestDTO);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();


        assertThat(shopIndexVO.getFirstOrderDelivery()).isEqualTo(1);
    }


    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {

        return Stream.of(Arguments.of(PandaLeagueDataProvider.getCommonDataProvider()));
    }
}
