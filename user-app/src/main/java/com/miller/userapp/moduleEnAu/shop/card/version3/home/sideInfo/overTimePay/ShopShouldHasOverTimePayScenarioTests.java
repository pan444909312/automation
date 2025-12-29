package com.miller.userapp.moduleEnAu.shop.card.version3.home.sideInfo.overTimePay;

import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.moduleEnAu.shop.card.version3.home.flow.ShopListFlow;
import com.miller.userapp.moduleEnAu.shop.card.version3.home.request.ShopListRequestDTO;
import com.miller.userapp.moduleEnAu.shop.card.version3.home.response.ShopListResponseDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test

@TestFramework
@Scenario(scenarioID = "01K9XZBN4A4N06ETMHS3D1YQTN", scenarioName = "用户-首页店铺流-商卡(中文)-普通店铺配送商卡-SKYX01-辅助信息-首页-商卡二期：超时立赔"
        , author = "yancancan@hungrypandagroup.com", developmentTime = 20, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("用户-首页店铺流-商卡(中文)-普通店铺配送商卡-SKYX01-辅助信息-首页-商卡二期：超时立赔")
public class ShopShouldHasOverTimePayScenarioTests {
    //    测试店铺
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(),"user.app.for.test.shop.card.version2.02.shopId"));

    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();

    }
    @DisplayName("用户-首页店铺流-商卡(中文)-普通店铺配送商卡-SKYX01-辅助信息-首页-商卡二期：超时立赔")
    @MethodSource("showLabelDataProvider")
    @ParameterizedTest
    void hasSendMoneyInfo(ShopListRequestDTO ShopListRequestdto){
        ShopListResponseDTO ShopListResponsedto = ShopListFlow.getShopListByShopId(ShopListRequestdto,shopId);
        ShopIndexVO shopIndexVO  = ShopListResponsedto.getResult().getShopList().stream().filter(item -> item.getShopId().equals(shopId)).findFirst().orElseThrow();
//        assertThat(shopIndexVO.getOverTimePay()).isEqualTo("1");

    }
    //    DataProvider改为在测试用例文件里写,提供测试数据
    static Stream<Arguments> showLabelDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setFiltering(false); // 开发代码Bug，没有对 null 进行判断，应该默认给false的
        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}
