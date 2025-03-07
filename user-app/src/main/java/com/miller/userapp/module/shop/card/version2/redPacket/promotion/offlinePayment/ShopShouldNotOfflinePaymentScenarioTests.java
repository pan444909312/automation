package com.miller.userapp.module.shop.card.version2.redPacket.promotion.offlinePayment;

import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.hungrypanda.app.server.vo.index.ShopPromoteVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version2.redPacket.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version2.redPacket.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version2.redPacket.response.ShopListResponseDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author huyang
 * @since 2024/8/16 17:47
 */
@Scenario(scenarioID = "01JE84BAG72WBQ99GSV62N8VKF",
        scenarioName = "商卡(中文)_普通店铺配送商卡-红包适用商家列表_优惠标签_货到付款_首页-商卡二期：货到付款34-不展示",
        author = "huyang@hungrypandagroup.com", developmentTime = 40, maintenanceTime = 0, manualTestTime = 10)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldNotOfflinePaymentScenarioTests {
    //    采用店铺2的数据，标签类型：34，content:无
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.blank.compare.shopId"));
    private final Integer type = 34;

    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
    }

    @DisplayName("普通店铺配送商卡-红包适用商家列表_优惠标签_货到付款_首页-商卡二期：货到付款34-不展示")
    @MethodSource("showLabelDataProvider")
    @ParameterizedTest
    void hasSelfTakeTag(ShopListRequestDTO ShopListRequestdto) {
        ShopListResponseDTO ShopListResponsedto = ShopListFlow.getShopList(ShopListRequestdto);
        List<ShopPromoteVO> shopPromoteList = ShopListResponsedto.getResult().getShopList().stream().filter(item -> item.getShopId().equals(shopId)).findFirst().map(ShopIndexVO::getShopPromoteList).orElseThrow();
        List<ShopPromoteVO> shopPromoteTypeList = shopPromoteList.stream().filter(item -> item.getType().equals(type)).toList();
        // System.out.println(shopPromoteTypeList);
        assertThat(shopPromoteTypeList.size()).isEqualTo(0);

    }

    static Stream<Arguments> showLabelDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
         // 开发代码Bug，没有对 null 进行判断，应该默认给false的
        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}
