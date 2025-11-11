package com.miller.userapp.module.shop.card.version3.search.promotion.pandaLeagueCouponV3;

import com.hungrypanda.app.server.common.enums.ShopPromoteEnum;
import com.hungrypanda.app.server.entity.redpacket.UserCdKeyEntity;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.hungrypanda.app.server.vo.index.ShopPromoteVO;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.data.activity.UserCdkeyInfoSql;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import com.miller.userapp.module.shop.card.version3.search.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version3.search.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version3.search.response.ShopListResponseDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.miller.service.framework.util.JsonUnitUtils.assertThat;

@Scenario(scenarioID = "01K8MAF5JTREX4ANGG1X3VPGBE", scenarioName = "搜索列表商卡-SKYX01_优惠标签_神券_搜索列表-商卡二期-SKYX实验组：熊猫联盟券40 - 折扣红包 (不加码)",
        author = "yancancan@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasPandaDiscountCouponTests {
     private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version3.shopId"));
    UserLoginRequestDTO userLoginRequestDTO;
    private final Long userId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version3.userId"));
    UserCdKeyEntity userCdKeyEntity;
     @BeforeAll
    void beforeAll() {

        //   用户登录
        userLoginRequestDTO = new UserLoginRequestDTO();
        userLoginRequestDTO.setAccount(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version3.account"));
        userLoginRequestDTO.setPassword(MD5Util.string2MD5(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version3.password")));
        userLoginRequestDTO.setDistinctId(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.for.shop.card.version2.godCoupon.distinctId"));
        userLoginRequestDTO.setType(Integer.valueOf(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.of.public.login.type")));
        userLoginRequestDTO.setAreaCode(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.of.user002.account.callingCode"));
        UserLoginFlow.loginAndPutToken(userLoginRequestDTO);

         //临时仅保留账户内满减券
         UserCdkeyInfoSql userCdkeyInfoSql = new UserCdkeyInfoSql();
         userCdkeyInfoSql.updateRedPacketUsedStatusExclude(String.valueOf(userId),Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version3.redpacketId3")));
         userCdkeyInfoSql.updateRedPacketUsedStatus(String.valueOf(userId),Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version3.redpacketId3")), (byte) 0);
     }
     @AfterAll
     void afterAll(){
         //神券信息修改:恢复账户内神券红包
         UserCdkeyInfoSql userCdkeyInfoSql = new UserCdkeyInfoSql();
         userCdkeyInfoSql.updateRedPacketUsedStatus(String.valueOf(userId),Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version3.redpacketId")), (byte) 0);
         userCdkeyInfoSql.updateRedPacketUsedStatus(String.valueOf(userId),Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version3.redpacketId2")), (byte) 0);

     }
     @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("搜索列表商卡-SKYX01_优惠标签_神券_搜索列表-商卡二期-SKYX实验组：熊猫联盟券40 - 折扣红包 (不加码)")
     void couponGodDsicount(ShopListRequestDTO shopListRequestDTO) {
          ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO,shopId);
          ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();
      ShopPromoteVO shopPromoteVO = shopIndexVO.getShopPromoteList().stream().
              filter(item -> item.getType().equals(ShopPromoteEnum.PANDA_LEAGUE.getType())).findFirst().get();
      assertThat(shopPromoteVO.getShowContent()).isEqualTo("5折");

      assertThat(shopPromoteVO.getTagType()).isEqualTo(1);



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
