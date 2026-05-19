package com.miller.userapp.module.shop.card.version3.redPacketShopList.baseInfo.nextOpenTime;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hungrypanda.app.server.entity.shop.ShopEntity;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.service.util.XXLJobUtils;
import com.miller.userapp.mapper.shop.ShopMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version3.redPacketShopList.baseInfo.shopLabel.ShopShouldHasLabelScenarioTests;
import com.miller.userapp.module.shop.card.version3.redPacketShopList.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version3.redPacketShopList.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version3.redPacketShopList.response.ShopListResponseDTO;
import com.miller.userapp.util.DBUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Scenario(scenarioID = "01KE9JG2QNGFPC88E6BRHRK6FH",
        scenarioName = "普通店铺配送商卡-红包适用商家列表-SKYX01_基础信息_下次营业时间_红包适用商家列表-商卡二期：下次营业时间-无数据",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 10, maintenanceTime = 5, manualTestTime = 3)
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
        shopMapper.update(null, new LambdaUpdateWrapper<ShopEntity>()
                .eq(ShopEntity::getShopId, shopId)
                .set(ShopEntity::getShopOrderStopStatus, 0)
                .set(ShopEntity::getShopOrderStopKeepTime, 0)
                .set(ShopEntity::getShopOrderStopStartTime, 0));

//       执行定时定时任务-店铺数据更新
        XXLJobUtils.triggerJob(new PropertiesUtils().getProperty(ShopShouldHasLabelScenarioTests.class, "user.app.job.increment.shop.index.update.id"));

    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡-红包适用商家列表-SKYX01_基础信息_下次营业时间_红包适用商家列表-商卡二期：下次营业时间-无数据")
    void OrderStop(ShopListRequestDTO shopListRequestDTO) {
//        请求红包适用商家列表店铺数据
        ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO, shopId);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();
        //遍历店铺的ShopPromoteList列表，
        assertThat(shopIndexVO.getShopStatusTimeStr()).isNull();
    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setCityName("九江市");// 开发代码Bug，没有对 null 进行判断，应该默认给false的
        shopListRequestDTO.setShopCategoryIds("[3896,3914,5486]");
        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}
