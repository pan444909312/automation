package com.miller.userapp.module.shop.card.version2.pandaLeague.coupon.discountCoupon;

import com.hungrypanda.app.server.common.enums.ShopPromoteEnum;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.hungrypanda.app.server.vo.index.ShopPromoteVO;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import com.miller.userapp.module.shop.card.version2.pandaLeague.dataProvider.PandaLeagueDataProvider;
import com.miller.userapp.module.shop.card.version2.pandaLeague.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version2.pandaLeague.flow.ShopListPandaLeagueFlow;
import com.miller.userapp.module.shop.card.version2.pandaLeague.request.ShopListPandaLeagueRequestDTO;
import com.miller.userapp.module.shop.card.version2.pandaLeague.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version2.pandaLeague.response.ShopListResponseDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * @author heyuan
 * @version 1.0
 * @since 2024/8/6 19:45
 */
@EnvTag.Test
@Scenario(scenarioID = "01JBV2ASGTK95DEAHA7FWKWHS4",
        scenarioName = "商卡(中文)_普通店铺配送商卡-熊猫联盟频道-品类频道_优惠标签_已领折扣红包_首页-商卡二期：已领折扣红包39",
        author = "shandongdong@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("商卡(中文)")
public class ShopShouldHasShopDiscountCouponScenarioTests {
   private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId"));
   UserLoginRequestDTO userLoginRequestDTO;


   @BeforeAll
   void beforeAll() {
      userLoginRequestDTO = new UserLoginRequestDTO();
      userLoginRequestDTO.setAccount(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.for.shop.card.version2.pandaDiscount.user.account.xmlm02"));
      userLoginRequestDTO.setPassword(MD5Util.string2MD5(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.for.shop.card.version2.pandaDiscount.user.password.xmlm02")));
      userLoginRequestDTO.setDistinctId(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.distinctId"));
      userLoginRequestDTO.setType(Integer.valueOf(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.public.login.type")));
      userLoginRequestDTO.setAreaCode(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.callingCode"));

      UserLoginFlow.loginAndPutToken(userLoginRequestDTO);

   }

   @MethodSource("couponDataProvider")
   @ParameterizedTest
   @DisplayName("普通店铺配送商卡-熊猫联盟频道_优惠标签_已领折扣红包_首页-商卡二期：已领折扣红包39 ")
   void shouldShowPandLeagueFullSubCouponLabel(ShopListPandaLeagueRequestDTO shopListPandaLeagueRequestDTO) {
      ShopListResponseDTO shopList = ShopListPandaLeagueFlow.getShopList(shopListPandaLeagueRequestDTO);

      ShopIndexVO shopIndexVO= shopList.getResult().getShopList().stream()
              .filter(item -> item.getShopId().equals(shopId)).findFirst().get();

      ShopPromoteVO shopPromoteVO = shopIndexVO.getShopPromoteList().stream().
              filter(item -> item.getType().equals(ShopPromoteEnum.USER_PACKET_DISCOUNT.getType())).findFirst().get();


      assert shopPromoteVO.getType().equals(ShopPromoteEnum.USER_PACKET_DISCOUNT.getType());
      assert shopPromoteVO.getShowContent().equals("4折红包");




   }

   /**
    * 测试用例数据提供者
    */
   static Stream<Arguments> couponDataProvider() {
      return Stream.of(Arguments.of(PandaLeagueDataProvider.getCommonDataProvider()));
   }

}
  