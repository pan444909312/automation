package com.miller.userapp.module.shop.card.version3.redPacketShopList.promotion.memberBenefit;

import com.hungrypanda.app.server.common.enums.ShopPromoteEnum;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.common.util.MD5Util;
import com.miller.erp.moudle.login.flow.ERPLoginFlow;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.member.MemberCityMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import com.miller.userapp.module.shop.card.version3.redPacketShopList.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version3.redPacketShopList.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version3.redPacketShopList.response.ShopListResponseDTO;
import com.miller.userapp.util.RequestUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Scenario(scenarioID = "01KE9JG2QWDYJ9ERQ4HSYH8C4F",
        scenarioName = "普通店铺配送商卡-SKYX01_优惠标签_会员权益_红包适用商家列表-商卡二期：会员权益32-不展示",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 30, manualTestTime = 10)

@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasNoMemberBenefitScenarioTests {
    //        private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId"));
    //     用户未登录，会员店铺配置了运费减免，展示运费减免红包
    private final Long memberCityID = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.memberCityId"));
    private final Long packageId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shop.redPacketId"));
    private static MemberCityMapper shopMemberCityMapper;

    @BeforeAll
    void beforeAll() {
        ERPLoginFlow.loginByDefaultUser();
        UserLoginRequestDTO userLoginRequestDTO = new UserLoginRequestDTO();
        userLoginRequestDTO.setAccount(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.for.shop.card.version2.new.user03.account"));
        userLoginRequestDTO.setPassword(MD5Util.string2MD5((new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.for.shop.card.version2.new.user03.password"))));
        userLoginRequestDTO.setDistinctId(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.distinctId"));
        userLoginRequestDTO.setAreaCode(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.callingCode"));
        userLoginRequestDTO.setType(Integer.valueOf(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.public.login.type")));
        UserLoginFlow.loginAndPutToken(userLoginRequestDTO);
//        shopMemberCityMapper = DBUtils.getDBOfPandaTest().getMapper(MemberCityMapper.class);
//        shopMemberCityMapper.update(new UpdateWrapper<MemberCityEntity>()
//                .eq("member_city_id",memberCityID)
//                .set("online_status",0));

//       执行定时定时任务-店铺数据更新
//        XXLJobUtils.triggerJob(new PropertiesUtils().getProperty(ShopShouldHasLabelScenarioTests.class, "user.app.job.increment.shop.index.update.id"));

    }

    @AfterAll
    void afterAll() {
//        shopMemberCityMapper.update(new UpdateWrapper<MemberCityEntity>()
//                .eq("member_city_id",memberCityID)
//                .set("online_status",1));
//
//
////       执行定时定时任务-店铺数据更新
//        XXLJobUtils.triggerJob(new PropertiesUtils().getProperty(ShopShouldHasLabelScenarioTests.class, "user.app.job.increment.shop.index.update.id"));


    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡-SKYX01_优惠标签_会员权益_红包适用商家列表-商卡二期：会员权益32-不展示")
    void memberBenefitShopAllianCoupon(ShopListRequestDTO shopListRequestDTO) {

        // 使用城市 烟台
        RequestUtils.getHeaders().put("latitude", "37.4456");
        RequestUtils.getHeaders().put("longitude", "121.4205");
//        获取红包适用商家列表店铺列表数据
        ShopListResponseDTO shopList = ShopListFlow.getShopList(shopListRequestDTO);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().get(0);


        //断言-的ShopPromoteList列表， 没有会员红包权益
        assertThat(shopIndexVO.getShopPromoteList().stream().noneMatch(item -> item.getType() == ShopPromoteEnum.INDEX_MEMBER_PACKET.getType())).isTrue();
        assertThat(shopIndexVO.getShopPromoteList().stream().noneMatch(item -> item.getType() == ShopPromoteEnum.MEMBER_SHOP_PACKET.getType())).isTrue();


    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setCityName("烟台市");
        shopListRequestDTO.setShopCategoryIds("[19571]");
        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}

