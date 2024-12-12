package com.miller.userapp.module.shop.card.version2.redPacket.feature.averagePurchase;

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
import com.miller.userapp.module.shop.card.version2.redPacket.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version2.redPacket.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version2.redPacket.response.ShopListResponseDTO;
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
@Scenario(scenarioID = "01JE88B2J6482ZXB6ZFYBXQX6F", scenarioName = "商卡(中文)-普通店铺配送商卡-红包适用商家列表-辅助信息-人均-首页-商卡二期：人均"
        , developmentTime = 20, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("商卡(中文)-普通店铺配送商卡-红包适用商家列表-辅助信息-人均-首页-商卡二期：人均")
public class ShopShouldHasAveragePurchaseScenarioTests {
    //    测试店铺
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(),"user.app.for.test.shop.card.version2.shopId"));

    @BeforeAll
    void beforeAll() throws InterruptedException {
        UserLoginFlow.loginByDefaultUser();
//        开启配置管理AVERAGE_PURCHASE_SWITCH=1
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        SysAppConfigMapper sysAppConfigMapper = sqlSession.getMapper(SysAppConfigMapper.class);
        sysAppConfigMapper.update(
                new SysAppConfigEntity(), 
                new LambdaUpdateWrapper<SysAppConfigEntity>().eq(SysAppConfigEntity::getConfigKey,"AVERAGE_PURCHASE_SWITCH").set(SysAppConfigEntity::getConfigValue,"{\"open\":1}")
        );
//        调用搜索索引定时任务
        XXLJobUtils.triggerJob(new PropertiesUtils().getProperty(this.getClass(), "user.app.job.increment.shop.index.update.id"));
    }
    @DisplayName("商卡(中文)-普通店铺配送商卡-红包适用商家列表-辅助信息-人均-首页-商卡二期：人均")
    @MethodSource("showLabelDataProvider")
    @ParameterizedTest
    void hasAveragePurchaseInfo(ShopListRequestDTO ShopListRequestdto){
        ShopListResponseDTO ShopListResponsedto= ShopListFlow.getShopList(ShopListRequestdto);
        String averagePurchase =ShopListResponsedto.getResult().getShopList().stream().filter(item -> item.getShopId().equals(shopId)).findFirst().map( ShopIndexVO::getAveragePurchase).orElseThrow();
        assertThat(averagePurchase).isEqualTo("人均¥55");

    }
    //    DataProvider改为在测试用例文件里写,提供测试数据
    static Stream<Arguments> showLabelDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
         // 开发代码Bug，没有对 null 进行判断，应该默认给false的
        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}
