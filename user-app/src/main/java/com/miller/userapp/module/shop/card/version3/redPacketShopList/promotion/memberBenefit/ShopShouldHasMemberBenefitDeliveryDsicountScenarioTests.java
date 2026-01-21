package com.miller.userapp.module.shop.card.version3.redPacketShopList.promotion.memberBenefit;

import com.hungrypanda.app.server.common.enums.ShopPromoteEnum;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.hungrypanda.app.server.vo.index.ShopPromoteVO;
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

@Scenario(scenarioID = "01KE9JG2QWDYJ9ERQ4HSYH8C4D",
        scenarioName = "普通店铺配送商卡-SKYX01_优惠标签_会员权益_品类频道-商卡二期：会员权益32-会员运费减免",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 20 + 30, manualTestTime = 15)

@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasMemberBenefitDeliveryDsicountScenarioTests {
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId"));

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
    @DisplayName("普通店铺配送商卡-SKYX01_优惠标签_会员权益_品类频道-商卡二期：会员权益32-会员运费免减")
    void memberBenefitDeliveryDsicount(ShopListRequestDTO shopListRequestDTO) {

        ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO, shopId);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();

        //遍历店铺的ShopPromoteList列表，
        ShopPromoteVO memberPacket = shopIndexVO.getShopPromoteList().stream().
                filter(item -> item.getShowContent().contains("运费")).findFirst().orElseThrow();
        assert memberPacket.getType().equals(ShopPromoteEnum.INDEX_MEMBER_PACKET.getType());


    }

    /**
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

