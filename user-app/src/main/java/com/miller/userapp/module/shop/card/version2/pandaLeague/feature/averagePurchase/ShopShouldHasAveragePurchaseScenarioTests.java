package com.miller.userapp.module.shop.card.version2.pandaLeague.feature.averagePurchase;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hungrypanda.app.server.entity.config.SysAppConfigEntity;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.service.util.XXLJobUtils;
import com.miller.userapp.mapper.shop.SysAppConfigMapper;
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
@Scenario(scenarioID = "01JDR9SSRB28118W2EDV5WVDF0", scenarioName = "用户-首页店铺流-商卡(中文)-普通店铺配送商卡-熊猫联盟频道-辅助信息-人均-熊猫联盟频道-商卡二期：人均"
        , author = "yancancan@hungrypandagroup.com", developmentTime = 20, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("用户-首页店铺流-商卡(中文)-普通店铺配送商卡-熊猫联盟频道-辅助信息-人均-熊猫联盟频道-商卡二期：人均")
public class ShopShouldHasAveragePurchaseScenarioTests {
    //    测试店铺
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(),"user.app.for.test.shop.card.version2.shopId"));

    @BeforeAll
    void beforeAll()  {
        UserLoginFlow.loginByDefaultUser();
//        开启配置管理AVERAGE_PURCHASE_SWITCH=1
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        SysAppConfigMapper sysAppConfigMapper = sqlSession.getMapper(SysAppConfigMapper.class);
        sysAppConfigMapper.update(null, new LambdaUpdateWrapper<SysAppConfigEntity>().eq(SysAppConfigEntity::getConfigKey,"AVERAGE_PURCHASE_SWITCH").set(SysAppConfigEntity::getConfigValue,"{\"open\":1}")
        );
//        调用搜索索引定时任务
        XXLJobUtils.triggerJob(new PropertiesUtils().getProperty(this.getClass(), "user.app.job.increment.shop.index.update.id"));
    }
    @DisplayName("用户-首页店铺流-商卡(中文)-普通店铺配送商卡-熊猫联盟频道-辅助信息-人均-熊猫联盟频道-商卡二期：人均")
    @MethodSource("DataProvider")
    @ParameterizedTest
    void hasAveragePurchaseInfo(ShopListPandaLeagueRequestDTO ShopListRequestdto){
        ShopListResponseDTO ShopListResponsedto= ShopListPandaLeagueFlow.getShopList(ShopListRequestdto);
        String averagePurchase =ShopListResponsedto.getResult().getShopList().stream().filter(item -> item.getShopId().equals(shopId)).findFirst().map( ShopIndexVO::getAveragePurchase).orElseThrow();
        assertThat(averagePurchase).isEqualTo("人均¥55");

    }
    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> DataProvider() {
        return Stream.of(Arguments.of(PandaLeagueDataProvider.getCommonDataProvider()));
    }
}
