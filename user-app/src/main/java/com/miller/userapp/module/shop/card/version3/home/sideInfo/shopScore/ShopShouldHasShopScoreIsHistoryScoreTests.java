package com.miller.userapp.module.shop.card.version3.home.sideInfo.shopScore;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hungrypanda.app.server.entity.shop.EvaluationEntity;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.service.util.XXLJobUtils;
import com.miller.userapp.mapper.shop.EvaluationMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version3.home.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version3.home.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version3.home.response.ShopListResponseDTO;
import com.miller.userapp.util.DBUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@Scenario(scenarioID = "01K0P3M5STNATNPH60FSN4KKDH",
        scenarioName = "商卡(中文)_普通店铺配送商卡-SKYX01_辅助信息_店铺评分_首页-商卡二期：店铺评分-历史评分",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@EnvTag.Test
@DisplayName("商卡(中文)_普通店铺配送商卡-SKYX01_辅助信息_店铺评分_首页-商卡二期：店铺评分-历史评分")
public class ShopShouldHasShopScoreIsHistoryScoreTests {
     private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId"));

     @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        EvaluationMapper evaluationMapper = sqlSession.getMapper(EvaluationMapper.class);
//update evaluation_score=100 where shopid=xxxx  设置历史分数为100  则为100/2
         evaluationMapper.update(new EvaluationEntity(), new LambdaUpdateWrapper<EvaluationEntity>()
                 .eq(EvaluationEntity::getShopId, shopId)
                         .set(EvaluationEntity::getComposite,100)
                         .set(EvaluationEntity::getCompositeManager,0));
//       执行定时定时任务-店铺数据更新
        XXLJobUtils.triggerJob(new PropertiesUtils().getProperty(this.getClass(), "user.app.job.increment.shop.index.update.id"));

     }
     @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("商卡(中文)_普通店铺配送商卡-SKYX01_辅助信息_店铺评分_首页-商卡二期：店铺评分-历史评分")
     void couponGodDsicount(ShopListRequestDTO shopListRequestDTO) {
          ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO,shopId);
          ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();

                assert shopIndexVO.getPraiseAverage().equals("5.0");


     }

/*
          * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setFiltering(false);
        return Stream.of(Arguments.of(shopListRequestDTO));
    }

}
