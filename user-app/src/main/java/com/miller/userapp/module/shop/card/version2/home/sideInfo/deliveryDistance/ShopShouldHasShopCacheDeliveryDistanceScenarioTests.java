package com.miller.userapp.module.shop.card.version2.home.sideInfo.deliveryDistance;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.entity.data.AdsSearchDistanceMatrixEntity;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.http.HttpUtils;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.mapper.shop.AdsHpSearchDistanceMatrix;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import com.miller.userapp.module.shop.card.version2.home.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version2.home.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version2.home.response.ShopListResponseDTO;
import com.miller.userapp.util.RequestUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author heyuan
 * @version 1.0
 * @since 2024/9/25 19:39
 */
@Scenario(scenarioID = "01J8MFQJYPKS8X8R4MENRKGDFY",
        scenarioName = "商卡(中文)_普通店铺配送商卡_辅助信息_配送距离_首页-商卡二期：配送距离 - 取缓存距离",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasShopCacheDeliveryDistanceScenarioTests {
   private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.02.shopId"));
   UserLoginRequestDTO userLoginRequestDTO;
   private static final String uri = BusinessConstant.DOMAIN + "/api/user/v2/index/shopList";
   private AdsHpSearchDistanceMatrix adsHpSearchDistanceMatrix;
   private Double cacheDistance;



   @BeforeAll
   void beforeAll() {
      userLoginRequestDTO = new UserLoginRequestDTO();
      userLoginRequestDTO.setAccount(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.for.shop.card.version2.no.coupon.user.account"));
      userLoginRequestDTO.setPassword(MD5Util.string2MD5(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.for.shop.card.version2.no.coupon.user.password")));
      userLoginRequestDTO.setDistinctId(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.distinctId"));
      userLoginRequestDTO.setType(Integer.valueOf(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.public.login.type")));
      userLoginRequestDTO.setAreaCode(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.callingCode"));

      UserLoginFlow.loginAndPutToken(userLoginRequestDTO);
      SqlSession sqlSession = com.miller.userapp.util.DBUtils.getDBOfPandaTest();
      adsHpSearchDistanceMatrix = sqlSession.getMapper(AdsHpSearchDistanceMatrix.class);
   }

   @MethodSource("DataProvider")
   @ParameterizedTest
   @DisplayName("普通店铺配送商卡_辅助信息_配送距离_首页-商卡二期：配送距离 - 取缓存距离 ")
   void shouldShowPandLeagueFullSubCouponLabel(ShopListRequestDTO shopListRequestDTO) {
      RequestUtils.getHeaders().put("Content-Type", "application/json");
      RequestUtils.getHeaders().put("latitude", "29.7514799");
      RequestUtils.getHeaders().put("longitude", "115.9541");
      ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO,shopId);

      String distance= shopList.getResult().getShopList().stream()
              .filter(item -> item.getShopId().equals(shopId)).findFirst().get().getDistance();
      AdsSearchDistanceMatrixEntity adsSearchDistanceMatrixEntity = adsHpSearchDistanceMatrix.selectOne(new QueryWrapper<AdsSearchDistanceMatrixEntity>().eq("start_tudustr", "115.984,29.669").eq("target_tudustr", "115.954,29.751"));

          cacheDistance = (adsSearchDistanceMatrixEntity.getDistance().doubleValue())/1000;
      assertThat(distance).isEqualTo(cacheDistance+"km");





   }

   /**
    * 测试用例数据提供者
    */
   static Stream<Arguments> DataProvider() {
      ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
      // 可以不用传参数
      shopListRequestDTO.setFiltering(false); // 开发代码Bug，没有对 null 进行判断，应该默认给false的

      return Stream.of(Arguments.of(shopListRequestDTO));
   }

}
