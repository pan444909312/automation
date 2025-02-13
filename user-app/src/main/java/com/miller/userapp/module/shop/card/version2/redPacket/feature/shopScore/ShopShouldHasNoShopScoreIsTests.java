package com.miller.userapp.module.shop.card.version2.redPacket.feature.shopScore;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hungrypanda.app.server.entity.search.ShopSearchMiddleEntity;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.search.ShopSearchMiddleMapper;
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

@Scenario(scenarioID = "01JE88B2J6482ZXB6ZFYBXQX7B",
        scenarioName = "商卡(中文)_普通店铺配送商卡-红包适用商家列表_辅助信息_店铺评分_首页-商卡二期：店铺评分-无数据",
        author = "shandongdong@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasNoShopScoreIsTests {
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
    @DisplayName("商卡(中文)_普通店铺配送商卡-红包适用商家列表_辅助信息_店铺评分_首页-商卡二期：店铺评分-无数据")
     void couponGodDsicount(ShopListRequestDTO shopListRequestDTO) {
          ShopListResponseDTO shopList = ShopListFlow.getShopList(shopListRequestDTO);
          ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();
                assert shopIndexVO.getPraiseAverage().equals("0");
     }

/*
          * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        
        return Stream.of(Arguments.of(shopListRequestDTO));
    }

}
