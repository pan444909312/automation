package com.miller.userapp.module.shop.card.version3.searchList.baseInfo.shopTag;

import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import com.miller.userapp.module.shop.card.version3.searchList.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version3.searchList.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version3.searchList.response.ShopListResponseDTO;
import com.miller.userapp.util.PandaTestDBHelpful;
import com.miller.userapp.util.RequestUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.miller.service.framework.util.JsonUnitUtils.assertThat;

@Scenario(scenarioID = "01K9RWTX9S9QKGYT6AKTVP5936",
        scenarioName = "搜索列表商卡-SKYX01_基础信息_店前标签：会场模版标签_搜索列表-商卡二期：会场模版标签 - 会场关闭，不返回标签",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)

@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasNoVenueTagCauseVenueClose {
    private final Long shopId = 633077616L;


    @BeforeAll
    void beforeAll() {
        UserLoginRequestDTO userLoginRequestDTO = new UserLoginRequestDTO();
        userLoginRequestDTO.setAccount(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.for.shop.card.version2.new.user03.account"));
        userLoginRequestDTO.setPassword(MD5Util.string2MD5((new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.for.shop.card.version2.new.user03.password"))));
        userLoginRequestDTO.setDistinctId(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.distinctId"));
        userLoginRequestDTO.setAreaCode(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.callingCode"));
        userLoginRequestDTO.setType(Integer.valueOf(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.public.login.type")));
        UserLoginFlow.loginAndPutToken(userLoginRequestDTO);


        PandaTestDBHelpful.executeInsertOrUpdateOrDelete("update hp_venue set venue_status = 0 where id = 411");

    }

    @AfterAll
    void afterAll() {
        PandaTestDBHelpful.executeInsertOrUpdateOrDelete("update hp_venue set venue_status = 2 where id = 411");
    }


    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("搜索列表商卡-SKYX01_基础信息_店前标签：会场模版标签_搜索列表-商卡二期：会场模版标签 - 会场关闭，不返回标签")
    void memberBenefitDeliveryDsicount(ShopListRequestDTO shopListRequestDTO) {

        // 经纬度为沈阳
        RequestUtils.getHeaders().put("latitude","41.80478");
        RequestUtils.getHeaders().put("longitude","123.43297");

        ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO, shopId);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();

        assertThat(shopIndexVO.getActivityTagType()).isNotEqualTo(6);

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setFiltering(false);
        shopListRequestDTO.setKeywords("自动化测试");
        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}

