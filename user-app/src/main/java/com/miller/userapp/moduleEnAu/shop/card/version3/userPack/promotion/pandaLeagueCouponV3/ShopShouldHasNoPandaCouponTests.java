package com.miller.userapp.moduleEnAu.shop.card.version3.userPack.promotion.pandaLeagueCouponV3;

import com.hungrypanda.app.server.common.enums.ShopPromoteEnum;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.hungrypanda.app.server.vo.index.ShopPromoteVO;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.data.activity.UserCdkeyInfoSql;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import com.miller.userapp.moduleEnAu.shop.card.version3.userPack.flow.ShopListFlow;
import com.miller.userapp.moduleEnAu.shop.card.version3.userPack.request.ShopListRequestDTO;
import com.miller.userapp.moduleEnAu.shop.card.version3.userPack.response.ShopListResponseDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.miller.service.framework.util.JsonUnitUtils.assertThat;

//@Scenario(scenarioID = "01K7GRRHECP95GTDHKQYSN6TP1", scenarioName = "普通店铺自取商卡-SKYX01_优惠标签_神券_自取频道-商卡二期-SKYX实验组：熊猫联盟券40 - 折扣红包 - 不展示：对照组",
//        author = "yancancan@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasNoPandaCouponTests {
     private static final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(ShopShouldHasNoPandaCouponTests.class, "user.app.for.test.shop.card.version3.shopId"));
    private static UserLoginRequestDTO userLoginRequestDTO;
    private static final Long userId = Long.parseLong(new PropertiesUtils().getProperty(ShopShouldHasNoPandaCouponTests.class, "user.app.for.test.shop.card.version3.userId"));
     @BeforeAll
    static void beforeAll() {

        //   用户登录
        userLoginRequestDTO = new UserLoginRequestDTO();
        userLoginRequestDTO.setAccount(new PropertiesUtils().getProperty(ShopShouldHasNoPandaCouponTests.class, "user.app.for.test.shop.card.version3.account"));
        userLoginRequestDTO.setPassword(MD5Util.string2MD5(new PropertiesUtils().getProperty(ShopShouldHasNoPandaCouponTests.class, "user.app.for.test.shop.card.version3.password")));
        userLoginRequestDTO.setDistinctId(new PropertiesUtils().getProperty(ShopShouldHasNoPandaCouponTests.class, "user.app.account.for.shop.card.version2.godCoupon.distinctId"));
        userLoginRequestDTO.setType(Integer.valueOf(new PropertiesUtils().getProperty(ShopShouldHasNoPandaCouponTests.class, "user.app.account.of.public.login.type")));
        userLoginRequestDTO.setAreaCode(new PropertiesUtils().getProperty(ShopShouldHasNoPandaCouponTests.class, "user.app.account.of.user002.account.callingCode"));
        UserLoginFlow.loginAndPutToken(userLoginRequestDTO);

         //临时删除账户内神券红包、联盟券红包
         UserCdkeyInfoSql userCdkeyInfoSql = new UserCdkeyInfoSql();
         userCdkeyInfoSql.updateRedPacketUsedStatus(String.valueOf(userId),Long.parseLong(new PropertiesUtils().getProperty(ShopShouldHasNoPandaCouponTests.class, "user.app.for.test.shop.card.version3.redpacketId")), (byte) 1);
         userCdkeyInfoSql.updateRedPacketUsedStatus(String.valueOf(userId),Long.parseLong(new PropertiesUtils().getProperty(ShopShouldHasNoPandaCouponTests.class, "user.app.for.test.shop.card.version3.redpacketId2")), (byte) 1);
         userCdkeyInfoSql.updateRedPacketUsedStatus(String.valueOf(userId),Long.parseLong(new PropertiesUtils().getProperty(ShopShouldHasNoPandaCouponTests.class, "user.app.for.test.shop.card.version3.redpacketId3")), (byte) 1);


     }
     @AfterAll
     static void afterAll(){
         //神券信息修改:恢复账户内神券红包
         UserCdkeyInfoSql userCdkeyInfoSql = new UserCdkeyInfoSql();
         userCdkeyInfoSql.updateRedPacketUsedStatus(String.valueOf(userId),Long.parseLong(new PropertiesUtils().getProperty(ShopShouldHasNoPandaCouponTests.class, "user.app.for.test.shop.card.version3.redpacketId")), (byte) 0);
         userCdkeyInfoSql.updateRedPacketUsedStatus(String.valueOf(userId),Long.parseLong(new PropertiesUtils().getProperty(ShopShouldHasNoPandaCouponTests.class, "user.app.for.test.shop.card.version3.redpacketId2")), (byte) 0);
         userCdkeyInfoSql.updateRedPacketUsedStatus(String.valueOf(userId),Long.parseLong(new PropertiesUtils().getProperty(ShopShouldHasNoPandaCouponTests.class, "user.app.for.test.shop.card.version3.redpacketId3")), (byte) 0);

     }
    @MethodSource("com.miller.userapp.module.shop.card.version3.userPack.dataProvider.StaticDataProvider#StaticDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺自取商卡-SKYX01_优惠标签_神券_自取频道-商卡二期-SKYX实验组：熊猫联盟券40 - 折扣红包 - 不展示：对照组")
     void couponGodDsicount(ShopListRequestDTO shopListRequestDTO) {
          ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO,shopId);
          ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();
      List<ShopPromoteVO> shopPromoteVOS=shopIndexVO.getShopPromoteList().stream().filter(item -> item.getType().equals(ShopPromoteEnum.PANDA_LEAGUE.getType())).toList();
      assertThat(shopPromoteVOS.size()).isEqualTo(0);


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
