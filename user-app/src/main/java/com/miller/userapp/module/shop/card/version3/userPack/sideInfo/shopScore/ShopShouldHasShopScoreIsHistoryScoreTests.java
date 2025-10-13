package com.miller.userapp.module.shop.card.version3.userPack.sideInfo.shopScore;

import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.shop.EvaluationMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version3.userPack.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version3.userPack.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version3.userPack.response.ShopListResponseDTO;
import com.miller.userapp.util.DBUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.miller.service.framework.util.JsonUnitUtils.assertThat;

@Scenario(scenarioID = "01K7E8CYQA1G8S6THZH26FJZ36",
        scenarioName = "商卡(中文)_普通店铺自取商卡-SKYX01_辅助信息_店铺评分_自取频道-商卡二期：店铺评分-历史评分",
        author = "yancancan@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@EnvTag.Test
@DisplayName("商卡(中文)_普通店铺自取商卡-SKYX01_辅助信息_店铺评分_自取频道-商卡二期：店铺评分-历史评分")
public class ShopShouldHasShopScoreIsHistoryScoreTests {
     private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.02.shopId"));

     @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        EvaluationMapper evaluationMapper = sqlSession.getMapper(EvaluationMapper.class);
////update evaluation_score=100 where shopid=xxxx  设置历史分数为100  则为100/2
//         evaluationMapper.update(new EvaluationEntity(), new LambdaUpdateWrapper<EvaluationEntity>()
//                 .eq(EvaluationEntity::getShopId, shopId)
//                         .set(EvaluationEntity::getComposite,100)
//                         .set(EvaluationEntity::getCompositeManager,0));
////       执行定时定时任务-店铺数据更新
//        XXLJobUtils.triggerJob(new PropertiesUtils().getProperty(this.getClass(), "user.app.job.increment.shop.index.update.id"));

     }
     @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("商卡(中文)_普通店铺自取商卡-SKYX01_辅助信息_店铺评分_自取频道-商卡二期：店铺评分-历史评分")
     void couponGodDsicount(ShopListRequestDTO shopListRequestDTO) {
          ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO,shopId);
          ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();
          assertThat(shopIndexVO.getPraiseAverage()).isEqualTo("4.9");


     }

/*
          * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 自取频道店铺流必须传经纬度
        shopListRequestDTO.setFiltering(false); // 开发代码Bug，没有对 null 进行判断，应该默认给false的
        shopListRequestDTO.setLongitude("115.954100");
        shopListRequestDTO.setLatitude("29.660580");
        shopListRequestDTO.setIsNeedMarketCategory(1);
        shopListRequestDTO.setMarketCategoryId(0);
        return Stream.of(Arguments.of(shopListRequestDTO));
    }

}
