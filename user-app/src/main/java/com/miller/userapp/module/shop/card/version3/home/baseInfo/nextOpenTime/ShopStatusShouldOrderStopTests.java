package com.miller.userapp.module.shop.card.version3.home.baseInfo.nextOpenTime;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hungrypanda.app.server.common.utils.DateUtils;
import com.hungrypanda.app.server.entity.shop.ShopEntity;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.service.util.XXLJobUtils;
import com.miller.userapp.mapper.shop.ShopMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version3.home.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version3.home.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version3.home.response.ShopListResponseDTO;
import com.miller.userapp.module.shop.card.version3.home.baseInfo.shopLabel.ShopShouldHasLabelScenarioTests;
import com.miller.userapp.util.DBUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@Scenario(scenarioID = "01K0V4X8N88VZ1PAGJWNPVC689",
        scenarioName = "普通店铺配送商卡-SKYX01_基础信息_下次营业时间_首页-商卡二期：下次营业时间-暂停接单不可预约",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 5, manualTestTime = 10)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopStatusShouldOrderStopTests {
    //获取当前时间为毫秒
    private final long nowDate = DateUtils.getNowTime();
    //        设置为15分钟暂停接单，转换后毫秒；当前时间+15分钟=结果
    private final long resultTime = nowDate + 15 * 60 * 1000;
    //        "CTT", "Asia/Shanghai"  结果为上海时间HH:MM
    private final String resultDate = DateUtils.getHourAndMinuteByLongTime(resultTime, "Asia/Shanghai");
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId"));

    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        ShopMapper shopMapper = sqlSession.getMapper(ShopMapper.class);
//update shop_order_stop_status=0,shop_order_stop_keep_time=15,shop_order_stop_start_time=nowDate where shopid=
        shopMapper.update(null, new LambdaUpdateWrapper<ShopEntity>()
                .eq(ShopEntity::getShopId, shopId)
                .set(ShopEntity::getShopOrderStopStatus, 1)
                .set(ShopEntity::getShopOrderStopKeepTime, 15)
                .set(ShopEntity::getShopOrderStopStartTime, nowDate));

//       执行定时定时任务-店铺数据更新
        XXLJobUtils.triggerJob(new PropertiesUtils().getProperty(ShopShouldHasLabelScenarioTests.class, "user.app.job.increment.shop.index.update.id"));

    }

    @AfterAll
    void afterAll() {
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        ShopMapper shopMapper = sqlSession.getMapper(ShopMapper.class);
//        回复店铺数据=营业
        shopMapper.update(null, new LambdaUpdateWrapper<ShopEntity>()
                .eq(ShopEntity::getShopId, shopId)
                .set(ShopEntity::getShopOrderStopStatus, 0)
                .set(ShopEntity::getShopOrderStopKeepTime, 0)
                .set(ShopEntity::getShopOrderStopStartTime, 0));

    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡-SKYX01_基础信息_下次营业时间_首页-商卡二期：下次营业时间-暂停接单不可预约")
    void OrderStop(ShopListRequestDTO shopListRequestDTO) {
//        请求首页店铺数据
        ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO, shopId);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();
        //遍历店铺的ShopPromoteList列表，
        //ShopStatusTimeStr=HH:MM  修改时间+15分钟
        assert shopIndexVO.getShopStatusTimeStr().equals(resultDate);


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
