package com.miller.userapp.module.shop.card.version2.pandaLeague.feature.shopScore;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hungrypanda.app.server.entity.search.ShopSearchMiddleEntity;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.search.ShopSearchMiddleMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
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

@Scenario(scenarioID = "01JDKTPH8NG6ZQGYDP9R7X7QRJ",
        scenarioName = "普通店铺配送商卡-熊猫联盟频道_辅助信息_店铺评分_首页-商卡二期：店铺评分-无数据",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class PandaShopShouldHasNoShopEvaluationTests {
     private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId"));

     @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
         ShopSearchMiddleMapper shopSearchMiddleMapper = sqlSession.getMapper(ShopSearchMiddleMapper.class);
//update evaluation_score=100 where shopid=xxxx  没有则不展示
         shopSearchMiddleMapper.update(null, new LambdaUpdateWrapper<ShopSearchMiddleEntity>()
                 .eq(ShopSearchMiddleEntity::getShopId, shopId)
                 .set(ShopSearchMiddleEntity::getPraiseAverage,0)
                 .set(ShopSearchMiddleEntity::getShowShopEvaluation,0)
                 .set(ShopSearchMiddleEntity::getPraiseAverageNew,0));

     }
     @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡-熊猫联盟频道_辅助信息_店铺评分_首页-商卡二期：店铺评分-无数据")
     void couponGodDsicount(ShopListPandaLeagueRequestDTO shopListPandaLeagueRequestDTO) {

        ShopListResponseDTO shopList = ShopListPandaLeagueFlow.getShopList(shopListPandaLeagueRequestDTO);
          ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();
                assert shopIndexVO.getPraiseAverage().equals("0");


     }

/*
          * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
ShopListPandaLeagueRequestDTO shopListPandaLeagueRequestDTO = new ShopListPandaLeagueRequestDTO();
        // 可以不用传参数
        shopListPandaLeagueRequestDTO.setFiltering(false); // 开发代码Bug，没有对 null 进行判断，应该默认给false的

        return Stream.of(Arguments.of(shopListPandaLeagueRequestDTO));
    }

}
