package com.miller.userapp.module.shop.card.version3.search.sideInfo.averagePurchase;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hungrypanda.app.server.entity.config.SysAppConfigEntity;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.service.util.XXLJobUtils;
import com.miller.userapp.mapper.base.SysAppConfigMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version3.search.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version3.search.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version3.search.response.ShopListResponseDTO;
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
@Scenario(scenarioID = "01K9VDRN7G073PR8K6K45NKE4H", scenarioName = "用户-搜索列表店铺流-商卡(中文)-搜索列表商卡-SKYX01-辅助信息-人均-搜索列表-商卡二期：人均"
        , author = "yancancan@hungrypandagroup.com", developmentTime = 20, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("用户-搜索列表店铺流-商卡(中文)-搜索列表商卡-SKYX01-辅助信息-人均-搜索列表-商卡二期：人均")
public class ShopShouldHasAveragePurchaseScenarioTests {
    //    测试店铺
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(),"user.app.for.test.shop.card.version2.shopId"));

    @BeforeAll
    void beforeAll() throws InterruptedException {
        UserLoginFlow.loginByDefaultUser();
//        开启配置管理AVERAGE_PURCHASE_SWITCH=1
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        SysAppConfigMapper sysAppConfigMapper = sqlSession.getMapper(SysAppConfigMapper.class);
        sysAppConfigMapper.update(null, new LambdaUpdateWrapper<SysAppConfigEntity>().eq(SysAppConfigEntity::getConfigKey,"AVERAGE_PURCHASE_SWITCH").set(SysAppConfigEntity::getConfigValue,"{\"open\":1}")
        );
//        调用搜索索引定时任务
        XXLJobUtils.triggerJob(new PropertiesUtils().getProperty(this.getClass(), "user.app.job.increment.shop.index.update.id"));
        Thread.sleep(140000);
    }
    @DisplayName("用户-搜索列表店铺流-商卡(中文)-搜索列表商卡-SKYX01-辅助信息-人均-搜索列表-商卡二期：人均")
    @MethodSource("showLabelDataProvider")
    @ParameterizedTest
    void hasAveragePurchaseInfo(ShopListRequestDTO ShopListRequestdto){
        ShopListResponseDTO ShopListResponsedto= ShopListFlow.getShopListByShopId(ShopListRequestdto,shopId);
        if (ShopListResponsedto.getResult().getShopList().isEmpty()) {
            System.out.println("01K0NWTNTH9883XEDG5C5M94ZT，人均调试信息：没有店铺数据");
            System.out.println("期望的店铺ID: " + shopId);
        } else {
            System.out.println("01K0NWTNTH9883XEDG5C5M94ZT，人均调试信息：获取到店铺列表");
        }
        
        String averagePurchase = ShopListResponsedto.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId))
                .findFirst()
                .map(ShopIndexVO::getAveragePurchase)
                .orElse(null);
                
        if (averagePurchase == null) {
            System.out.println("01K0NWTNTH9883XEDG5C5M94ZT，人均调试信息：未找到对应店铺的人均消费数据");
            System.out.println("目标店铺ID: " + shopId);
        } else if (averagePurchase.isEmpty()) {
            System.out.println("01K0NWTNTH9883XEDG5C5M94ZT，人均调试信息：找到店铺但人均消费数据为空");
            System.out.println("店铺ID: " + shopId + ", averagePurchase值: '" + averagePurchase + "'");
        } else {
            System.out.println("01K0NWTNTH9883XEDG5C5M94ZT，人均调试信息：成功获取人均消费数据");
            System.out.println("店铺ID: " + shopId + ", averagePurchase值: '" + averagePurchase + "'");
        }
        
        assertThat(averagePurchase).as("店铺人均消费信息校验失败").isNotNull().isNotEmpty();
        assertThat(averagePurchase).as("店铺人均消费数值校验失败").isEqualTo("人均¥55");

    }
    //    DataProvider改为在测试用例文件里写,提供测试数据
    static Stream<Arguments> showLabelDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setFiltering(false);// 开发代码Bug，没有对 null 进行判断，应该默认给false的
        shopListRequestDTO.setKeywords("商卡测试");
        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}
