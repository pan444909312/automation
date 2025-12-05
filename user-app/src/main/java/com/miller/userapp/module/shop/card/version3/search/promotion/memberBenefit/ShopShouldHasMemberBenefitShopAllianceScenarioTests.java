package com.miller.userapp.module.shop.card.version3.search.promotion.memberBenefit;

import com.hungrypanda.app.server.common.enums.ShopPromoteEnum;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.hungrypanda.app.server.vo.index.ShopPromoteVO;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import com.miller.userapp.module.shop.card.version3.search.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version3.search.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version3.search.response.ShopListResponseDTO;
import com.miller.userapp.util.RequestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


@Scenario(scenarioID = "01K9VH77WRQ9TZT8XD8AAK6N9V",
        scenarioName = "搜索列表商卡-SKYX01_优惠标签_会员权益_搜索列表-商卡二期：会员权益32-店铺联盟券",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 5 + 30, manualTestTime = 15)

@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasMemberBenefitShopAllianceScenarioTests {
    private final Long shopId = 911598059L;
    private final Long memberCityID = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.memberCityId"));
    private final Long packageId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shop.redPacketId"));


    @BeforeAll
    void beforeAll() {
        UserLoginRequestDTO userLoginRequestDTO = new UserLoginRequestDTO();
        userLoginRequestDTO.setAccount(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.for.shop.card.version2.new.user03.account"));
        userLoginRequestDTO.setPassword(MD5Util.string2MD5((new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.for.shop.card.version2.new.user03.password"))));
        userLoginRequestDTO.setDistinctId(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.distinctId"));
        userLoginRequestDTO.setAreaCode(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.callingCode"));
        userLoginRequestDTO.setType(Integer.valueOf(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.public.login.type")));
        UserLoginFlow.loginAndPutToken(userLoginRequestDTO);


    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("搜索列表商卡-SKYX01_优惠标签_会员权益_搜索列表-商卡二期：会员权益32-店铺联盟券")
    void memberBenefitShopAllianCoupon(ShopListRequestDTO shopListRequestDTO) {

        //沈阳经纬度
        RequestUtils.getHeaders().put("latitude", "41.80478");
        RequestUtils.getHeaders().put("longitude", "123.43297");

//        请求搜索列表店铺数据
        ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO, shopId);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();

        //遍历店铺的ShopPromoteList列表，
        ShopPromoteVO memberPacket = shopIndexVO.getShopPromoteList().stream().
                filter(item -> item.getType() == ShopPromoteEnum.INDEX_MEMBER_PACKET.getType()).findFirst().get();
        assert memberPacket.getType().equals(ShopPromoteEnum.INDEX_MEMBER_PACKET.getType());
        assert memberPacket.getShowContent().contains("红包");

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setFiltering(false);
        shopListRequestDTO.setKeywords("newcity测试");
        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}

