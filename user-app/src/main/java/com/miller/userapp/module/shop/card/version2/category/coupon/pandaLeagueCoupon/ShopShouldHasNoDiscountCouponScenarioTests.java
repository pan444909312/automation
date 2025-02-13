package com.miller.userapp.module.shop.card.version2.category.coupon.pandaLeagueCoupon;

import com.hungrypanda.app.server.common.enums.ShopPromoteEnum;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import com.miller.userapp.module.shop.card.version2.category.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version2.category.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version2.category.response.ShopListResponseDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * @author heyuan
 * @version 1.0
 * @since 2024/8/1 18:20
 */
@EnvTag.Test
@Scenario(scenarioID = "01J5AKPH46ETH3N1W167KCY6B4",
        scenarioName = "普通店铺配送商卡-品类频道_优惠标签_熊猫联盟券_首页-商卡二期：熊猫联盟券40 - 无折扣红包 - 不展示",
        author = "shandongdong@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@DisplayName("商卡(中文)")
public class ShopShouldHasNoDiscountCouponScenarioTests {
   private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId"));

   UserLoginRequestDTO userLoginRequestDTO;


   @BeforeAll
   void beforeAll() {
      userLoginRequestDTO = new UserLoginRequestDTO();
      userLoginRequestDTO.setAccount(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.for.shop.card.version2.pandaDiscount.user.account.xmlm02"));
      userLoginRequestDTO.setPassword(MD5Util.string2MD5(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.for.shop.card.version2.pandaDiscount.user.password.xmlm02")));
      userLoginRequestDTO.setDistinctId(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.distinctId"));
      userLoginRequestDTO.setType(Integer.valueOf(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.public.login.type")));
      userLoginRequestDTO.setAreaCode(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.callingCode"));

      UserLoginFlow.loginAndPutToken(userLoginRequestDTO);


   }

   @MethodSource("couponDataProvider")
   @ParameterizedTest
   @DisplayName("普通店铺配送商卡-品类频道_优惠标签_熊猫联盟券_首页-商卡二期：熊猫联盟券40 - 无折扣红包 - 不展示 ")
   void shouldShowPandLeagueFullSubCouponLabel(ShopListRequestDTO shopListRequestDTO) {

      ShopListResponseDTO shopList = ShopListFlow.getShopList(shopListRequestDTO);

      ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
              .filter(item -> item.getShopId().equals(shopId)).findFirst().get();

      boolean result = shopIndexVO.getShopPromoteList().stream().
              noneMatch(item -> item.getType().equals(ShopPromoteEnum.PANDA_LEAGUE.getType()));


      assert result;


   }

   /**
    * 测试用例数据提供者
    */
   static Stream<Arguments> couponDataProvider() {
      ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
      // 可以不用传参数
      shopListRequestDTO.setFiltering(false); // 开发代码Bug，没有对 null 进行判断，应该默认给false的

      return Stream.of(Arguments.of(shopListRequestDTO));
   }


}
  