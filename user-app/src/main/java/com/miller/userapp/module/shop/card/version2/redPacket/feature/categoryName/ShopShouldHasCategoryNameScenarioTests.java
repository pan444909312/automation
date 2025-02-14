package com.miller.userapp.module.shop.card.version2.redPacket.feature.categoryName;

import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version2.redPacket.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version2.redPacket.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version2.redPacket.response.ShopListResponseDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test

@TestFramework
@Scenario(scenarioID = "01JE88B2J6482ZXB6ZFYBXQX6H", scenarioName = "商卡(中文)-普通店铺配送商卡-红包适用商家列表-辅助信息-类目-首页-商卡二期：类目"
        , author = "yancancan@hungrypandagroup.com", developmentTime = 15, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("商卡(中文)-普通店铺配送商卡-红包适用商家列表-辅助信息-类目-首页-商卡二期：类目")
public class ShopShouldHasCategoryNameScenarioTests {

    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.blank.compare.shopId"));

    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
    }

    @DisplayName("商卡(中文)-普通店铺配送商卡-红包适用商家列表-辅助信息-类目-首页-商卡二期：类目")
    @MethodSource("showLabelDataProvider")
    @ParameterizedTest()
    void hasCategoryName(ShopListRequestDTO shopListRequestDTO) {
        ShopListResponseDTO shopListResponseDTO = ShopListFlow.getShopList(shopListRequestDTO);
        String merchantCategoryName = shopListResponseDTO.getResult().getShopList().stream().filter(item -> item.getShopId().equals(shopId)).findFirst().map(ShopIndexVO::getMerchantCategoryName).orElseThrow();
        Integer merchantCategoryId = shopListResponseDTO.getResult().getShopList().stream().filter(item -> item.getShopId().equals(shopId)).findFirst().map(ShopIndexVO::getMerchantCategoryId).orElseThrow();
        assertThat(merchantCategoryName).isEqualTo("自动化测试勿动");
        assertThat(merchantCategoryId).isEqualTo(12414);
    }

    static Stream<Arguments> showLabelDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
         // 开发代码Bug，没有对 null 进行判断，应该默认给false的
        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}
