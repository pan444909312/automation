package com.miller.userapp.module.shop.card.version2.pandaLeague.promotion.godCoupon;

import com.hungrypanda.app.server.common.enums.ShopPromoteEnum;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.hungrypanda.app.server.vo.index.ShopPromoteVO;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import com.miller.userapp.module.shop.card.version2.home.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version2.home.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version2.home.response.ShopListResponseDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@Scenario(scenarioID = "01JD75MYWPPNK52B1F5HW1PXNX",
        scenarioName = "普通店铺配送商卡_优惠标签_会员权益_首页-商卡二期：神券标签",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasGodCouponTests {
     private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId"));
    UserLoginRequestDTO userLoginRequestDTO;

     @BeforeAll
    void beforeAll() {

        //   用户登录 user:17048460002  非会员、非新人，可展示会员神券
        userLoginRequestDTO = new UserLoginRequestDTO();
        userLoginRequestDTO.setAccount(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.for.shop.card.version2.godCoupon.account"));
        userLoginRequestDTO.setPassword(MD5Util.string2MD5(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.for.shop.card.version2.godCoupon.password")));
        userLoginRequestDTO.setDistinctId(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.for.shop.card.version2.godCoupon.distinctId"));
        userLoginRequestDTO.setType(Integer.valueOf(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.of.public.login.type")));
        userLoginRequestDTO.setAreaCode(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.of.user002.account.callingCode"));
        UserLoginFlow.loginAndPutToken(userLoginRequestDTO);
     }
     @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡_优惠标签_新会员优惠标签_首页-商卡二期：神券标签")
     void couponGodDsicount(ShopListRequestDTO shopListRequestDTO) {
          ShopListResponseDTO shopList = ShopListFlow.getShopList(shopListRequestDTO);
          ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();

      ShopPromoteVO shopPromoteVO = shopIndexVO.getShopPromoteList().stream().
              filter(item -> item.getType().equals(ShopPromoteEnum.SUPER_COUPON.getType())).findFirst().get();
                assert shopPromoteVO.getShowContent().equals("最高膨至¥10");

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
