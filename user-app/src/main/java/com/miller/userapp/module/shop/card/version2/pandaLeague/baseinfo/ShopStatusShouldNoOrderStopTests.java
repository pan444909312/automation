package com.miller.userapp.module.shop.card.version2.pandaLeague.baseinfo;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hungrypanda.app.server.entity.shop.ShopEntity;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.service.util.XXLJobUtils;
import com.miller.userapp.mapper.shop.ShopMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version2.pandaLeague.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version2.pandaLeague.request.ShopListRequestDTO;
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

@Scenario(scenarioID = "01J86YH7Y0T8WKQASENM6Z7D7Z",
        scenarioName = "商卡(中文)_普通店铺配送商卡_基础信息_下次营业时间_首页-商卡二期：下次营业时间-无数据",
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 3)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopStatusShouldNoOrderStopTests {
//店铺已配置营业时间
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId"));

@BeforeAll
void beforeAll() {
     UserLoginFlow.loginByDefaultUser();
     SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        ShopMapper shopMapper = sqlSession.getMapper(ShopMapper.class);
//设置为正常营业，无暂停营业
       shopMapper.update(new LambdaUpdateWrapper<ShopEntity>()
                .eq(ShopEntity::getShopId, shopId)
                 .set(ShopEntity::getShopOrderStopStatus, 0)
                 .set(ShopEntity::getShopOrderStopKeepTime, 0)
                 .set(ShopEntity::getShopOrderStopStartTime,0));

//       执行定时定时任务-店铺数据更新
        XXLJobUtils.triggerJob(new PropertiesUtils().getProperty(ShopShouldHasLabelScenarioTests.class, "user.app.job.increment.shop.index.update.id"));

}
    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("商卡(中文)_普通店铺配送商卡_基础信息_下次营业时间_首页-商卡二期：下次营业时间-无数据")
    void OrderStop(ShopListRequestDTO shopListRequestDTO) {
//        请求首页店铺数据
     ShopListResponseDTO shopList= ShopListFlow.getShopList(shopListRequestDTO);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();
        //遍历店铺的ShopPromoteList列表，
        assertThat( shopIndexVO.getShopStatusTimeStr()).isNull();
    }
    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setFiltering(false);
        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}
