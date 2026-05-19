package com.miller.userapp.module.shop.card.version3.redPacketShopList.promotion.fullSubCouponV3;

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
import com.miller.userapp.module.shop.card.version3.redPacketShopList.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version3.redPacketShopList.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version3.redPacketShopList.response.ShopListResponseDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.miller.service.framework.util.JsonUnitUtils.assertThat;

@Scenario(scenarioID = "01KE9JG2QWDYJ9ERQ4HSYH8C47", scenarioName = "普通店铺配送商卡-SKYX01_优惠标签_神券_红包适用商家列表-商卡二期-SKYX实验组：账户内最大红包43｜店铺加码",
        author = "yancancan@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasReceivedIncreaseCouponTests {
     private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version3.increase.shopId"));
    UserLoginRequestDTO userLoginRequestDTO;
    private final Long userId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version3.userId"));
    UserCdKeyEntity userCdKeyEntity;
     @BeforeAll
    void beforeAll() throws InterruptedException {

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
         userCdkeyInfoSql.updateRedPacketUsedStatusExclude(String.valueOf(userId),Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version3.redpacketId4")));
         userCdkeyInfoSql.updateRedPacketUsedStatus(String.valueOf(userId),Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version3.redpacketId4")), (byte) 0);
         Thread.sleep(5000);

     }
     @AfterAll
     void afterAll(){
         //神券信息修改:恢复账户内神券红包、联盟券
         UserCdkeyInfoSql userCdkeyInfoSql = new UserCdkeyInfoSql();
         userCdkeyInfoSql.updateRedPacketUsedStatus(String.valueOf(userId),Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version3.redpacketId")), (byte) 0);
         userCdkeyInfoSql.updateRedPacketUsedStatus(String.valueOf(userId),Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version3.redpacketId2")), (byte) 0);
         userCdkeyInfoSql.updateRedPacketUsedStatus(String.valueOf(userId),Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version3.redpacketId3")), (byte) 0);

     }
     @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡-SKYX01_优惠标签_神券_红包适用商家列表-商卡二期-SKYX实验组：账户内最大红包43｜店铺加码")
     void couponGodDsicount(ShopListRequestDTO shopListRequestDTO) {
          ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO,shopId);
          ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();
      List<ShopPromoteVO> shopPromoteVOS=shopIndexVO.getShopPromoteList().stream().filter(item -> item.getType().equals(ShopPromoteEnum.USER_RED_PACKET.getType())).toList();
      assertThat(shopPromoteVOS.size()).isEqualTo(1);
      assertThat(shopPromoteVOS.get(0).getTagType()).isEqualTo(1);
      assertThat(shopPromoteVOS.get(0).getShowContent()).isEqualTo("¥51无门槛");


     }

/*
          * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setCityName("九江市");
        shopListRequestDTO.setShopCategoryIds("[3896,3914,5486]");// 开发代码Bug，没有对 null 进行判断，应该默认给false的
        return Stream.of(Arguments.of(shopListRequestDTO));
    }

}
