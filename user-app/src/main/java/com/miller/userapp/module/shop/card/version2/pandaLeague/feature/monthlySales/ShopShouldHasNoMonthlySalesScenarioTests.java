package com.miller.userapp.module.shop.card.version2.pandaLeague.feature.monthlySales;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hungrypanda.app.server.entity.address.CityFunctionConfigEntity;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.shop.CityFunctionConfigMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version2.pandaLeague.dataProvider.PandaLeagueDataProvider;
import com.miller.userapp.module.shop.card.version2.pandaLeague.flow.ShopListPandaLeagueFlow;
import com.miller.userapp.module.shop.card.version2.pandaLeague.request.ShopListPandaLeagueRequestDTO;
import com.miller.userapp.module.shop.card.version2.pandaLeague.response.ShopListResponseDTO;
import com.miller.userapp.util.DBUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test

@TestFramework
@Scenario(scenarioID = "01JDR9SSRB28118W2EDV5WVDEW", scenarioName = "用户-首页店铺流-商卡(中文)-普通店铺配送商卡-熊猫联盟频道-辅助信息-月售-熊猫联盟频道-商卡二期：月售 - 月售展示开关禁用"
        , developmentTime = 10, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("用户-首页店铺流-商卡(中文)-普通店铺配送商卡-熊猫联盟频道-辅助信息-月售-熊猫联盟频道-商卡二期：月售 - 月售展示开关禁用")
public class ShopShouldHasNoMonthlySalesScenarioTests {
    //    测试店铺
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(),"user.app.for.test.shop.card.version2.shopId"));

    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
//        开启地址配置-城市功能管理-九江市-商卡月售开关
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        CityFunctionConfigMapper cityFunctionConfigMapper = sqlSession.getMapper(CityFunctionConfigMapper.class);
        cityFunctionConfigMapper.update(
                                new LambdaUpdateWrapper<CityFunctionConfigEntity>().eq(CityFunctionConfigEntity::getCityId,508).eq(CityFunctionConfigEntity::getType,7).set(CityFunctionConfigEntity::getStatus,0)
        );

    }
    @DisplayName("用户-首页店铺流-商卡(中文)-普通店铺配送商卡-熊猫联盟频道-辅助信息-月售-熊猫联盟频道-商卡二期：月售 - 月售展示开关禁用")
    @MethodSource("DataProvider")
    @ParameterizedTest
    void hasSendMoneyInfo(ShopListPandaLeagueRequestDTO ShopListRequestdto){
        ShopListResponseDTO ShopListResponseDto= ShopListPandaLeagueFlow.getShopList(ShopListRequestdto);
        ShopIndexVO shopIndexVO  = ShopListResponseDto.getResult().getShopList().stream().filter(item -> item.getShopId().equals(shopId)).findFirst().get();
        assertThat(shopIndexVO.getShopMonthlySales()).isNull();

    }
    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> DataProvider() {
        return Stream.of(Arguments.of(PandaLeagueDataProvider.getCommonDataProvider()));
    }
}
