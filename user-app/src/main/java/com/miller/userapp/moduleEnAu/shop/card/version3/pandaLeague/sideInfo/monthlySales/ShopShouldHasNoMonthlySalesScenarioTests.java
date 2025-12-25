package com.miller.userapp.moduleEnAu.shop.card.version3.pandaLeague.sideInfo.monthlySales;

import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.moduleEnAu.shop.card.version3.pandaLeague.flow.ShopListFlow;
import com.miller.userapp.moduleEnAu.shop.card.version3.pandaLeague.request.ShopListRequestDTO;
import com.miller.userapp.moduleEnAu.shop.card.version3.pandaLeague.response.ShopListResponseDTO;
import com.miller.userapp.util.RequestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test

@TestFramework
@Scenario(scenarioID = "01K47416E1M9F1A2ZXMQYBDM8C", scenarioName = "用户-熊猫联盟频道店铺流-商卡(中文)-普通店铺配送商卡-熊猫联盟频道-辅助信息-月售-熊猫联盟频道-商卡二期：月售 - 月售展示开关禁用"
        , author = "yancancan@hungrypandagroup.com", developmentTime = 10, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("用户-熊猫联盟频道店铺流-商卡(中文)-普通店铺配送商卡-熊猫联盟频道-辅助信息-月售-熊猫联盟频道-商卡二期：月售 - 月售展示开关禁用")
public class ShopShouldHasNoMonthlySalesScenarioTests {
    //    测试店铺
    private final Long shopId = 820422420L;

    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();


    }
    @DisplayName("用户-熊猫联盟频道店铺流-商卡(中文)-普通店铺配送商卡-熊猫联盟频道-辅助信息-月售-熊猫联盟频道-商卡二期：月售 - 月售展示开关禁用")
    @MethodSource("showLabelDataProvider")
    @ParameterizedTest
    void hasSendMoneyInfo(ShopListRequestDTO ShopListRequestdto){
        // 沈阳经纬度
        RequestUtils.getHeaders().put("latitude", "41.80478");
        RequestUtils.getHeaders().put("longitude", "123.43297");

        ShopListResponseDTO ShopListResponsedto = ShopListFlow.getShopListByShopId(ShopListRequestdto,shopId);
        ShopIndexVO shopIndexVO  = ShopListResponsedto.getResult().getShopList().stream().filter(item -> item.getShopId().equals(shopId)).findFirst().get();
        assertThat(shopIndexVO.getShopMonthlySales()).isNull();

    }
    //    DataProvider改为在测试用例文件里写,提供测试数据
        static Stream<Arguments> showLabelDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        shopListRequestDTO.setFiltering(false);
        shopListRequestDTO.setTabType((byte) 1);
        shopListRequestDTO.setRedPacketList(new ArrayList<>());
        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}
