package com.miller.userapp.module.shop.card.version2.promotion.memberShopPacket;

import com.hungrypanda.app.server.common.enums.ShopPromoteEnum;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;

import com.miller.userapp.module.shop.card.version2.home.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version2.home.response.ShopListResponseDTO;
import com.miller.userapp.module.shop.card.version2.promotion.memberShopPacket.flow.ShopListABFow;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Scenario(scenarioID = "01J5N6H101R6PZ47NDZY6GHEFJ",
        scenarioName = "普通店铺配送商卡_优惠标签_新会员优惠标签_首页-商卡二期：新会员优惠标签37-不展示",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasNoNewMemberDiscountScenarioTests {
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId"));

    @BeforeAll
    void BeforeAll() {


        UserLoginRequestDTO userLoginRequestDTO = new UserLoginRequestDTO();
        userLoginRequestDTO.setAccount(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.for.shop.card.version2.member.account"));
        userLoginRequestDTO.setPassword(MD5Util.string2MD5(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.for.shop.card.version2.member.password")));
        userLoginRequestDTO.setType(Integer.valueOf(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.of.public.login.type")));
        userLoginRequestDTO.setAreaCode(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.of.user002.account.callingCode"));
        UserLoginFlow.loginAndPutToken(userLoginRequestDTO);
}

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡_优惠标签_新会员优惠标签_首页-商卡二期：新会员优惠标签37-不展示")
    void memberBenefitDeliveryDsicount(ShopListRequestDTO shopListRequestDTO) {


        ShopListResponseDTO shopList = ShopListABFow.getShopList(shopListRequestDTO);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();

//        //遍历店铺的ShopPromoteList列表，
//        ShopPromoteVO memberPacket = shopIndexVO.getShopPromoteList().stream().
//                filter(item -> item.getType() == ShopPromoteEnum.MEMBER_SHOP_PACKET.getType()).findFirst().get();

        //断言-的ShopPromoteList列表， 没有会员权益标签
        assertThat(shopIndexVO.getShopPromoteList().stream().noneMatch(item -> item.getType() == ShopPromoteEnum.MEMBER_SHOP_PACKET.getType())).isTrue();

    }
    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setFiltering(false);
        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}

