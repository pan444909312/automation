package com.miller.userapp.module.shop.card.version3.redPacketShopList.sideInfo.deliveryTime;

import com.miller.common.util.MD5Util;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import com.miller.userapp.module.shop.card.version3.redPacketShopList.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version3.redPacketShopList.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version3.redPacketShopList.response.ShopListResponseDTO;
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
 * @since 2024/9/25 18:12
 */
@Scenario(scenarioID = "01KE9JG2QWDYJ9ERQ4HSYH8C5A",
        scenarioName = "商卡(中文)_普通店铺配送商卡-SKYX01_辅助信息_配送时间_品类频道-商卡二期：配送时间 - 取高峰期出餐时间",
        author = "yancancan@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasFastigiumDeliveryTimeScenarioTests {
   private final Long shopId = 669770737L;
   UserLoginRequestDTO userLoginRequestDTO;


   @BeforeAll
   void beforeAll() {
      userLoginRequestDTO = new UserLoginRequestDTO();
      userLoginRequestDTO.setAccount(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.for.shop.card.version2.no.coupon.user.account"));
      userLoginRequestDTO.setPassword(MD5Util.string2MD5(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.for.shop.card.version2.no.coupon.user.password")));
      userLoginRequestDTO.setDistinctId(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.distinctId"));
      userLoginRequestDTO.setType(Integer.valueOf(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.public.login.type")));
      userLoginRequestDTO.setAreaCode(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.callingCode"));

      UserLoginFlow.loginAndPutToken(userLoginRequestDTO);

   }

   @MethodSource("DataProvider")
   @ParameterizedTest
   @DisplayName("普通店铺配送商卡-SKYX01_辅助信息_配送时间_品类频道-商卡二期：配送时间 - 取高峰期出餐时间 ")
   void shouldShowPandLeagueFullSubCouponLabel(ShopListRequestDTO shopListRequestDTO) {
      ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO,shopId);

      Integer predictDeliveryTime= shopList.getResult().getShopList().stream()
              .filter(item -> item.getShopId().equals(shopId)).findFirst().get().getPredictDeliveryTime();
      Integer realDeliveryTime =75;
      assertThat(predictDeliveryTime).isEqualTo(realDeliveryTime);






   }

   /**
    * 测试用例数据提供者
    */
   static Stream<Arguments> DataProvider() {
      ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
      // 可以不用传参数
      shopListRequestDTO.setCityName("九江市");// 开发代码Bug，没有对 null 进行判断，应该默认给false的 // 开发代码Bug，没有对 null 进行判断，应该默认给false的
      shopListRequestDTO.setShopCategoryIds("[3896,3914,5486]");

      return Stream.of(Arguments.of(shopListRequestDTO));
   }

}
  