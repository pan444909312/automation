package com.miller.userapp.module.shop.card.version3.pandaLeague.promotion.offlinePayment;

import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.hungrypanda.app.server.vo.index.ShopPromoteVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version3.pandaLeague.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version3.pandaLeague.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version3.pandaLeague.response.ShopListResponseDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author huyang
 * @since 2024/8/16 17:47
 */
@Scenario(scenarioID = "01K47416DYTE34FK0ZBRFPPQMN", scenarioName = "商卡(中文)_普通店铺配送商卡-熊猫联盟频道_优惠标签_货到付款_熊猫联盟频道-商卡二期：货到付款34",
        author = "huyang@hungrypandagroup.com", developmentTime = 40, maintenanceTime = 0, manualTestTime = 10)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasOfflinePaymentScenarioTests {
//    测试店铺：店铺1,测试标签类型：34，content：货到付款
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(),"user.app.for.test.shop.card.version2.shopId"));
    private final Integer type=34;
    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
    }

    @DisplayName("普通店铺配送商卡-熊猫联盟频道_优惠标签_货到付款_熊猫联盟频道-商卡二期：货到付款34")
    @MethodSource("showLabelDataProvider")
    @ParameterizedTest
    void hasSelfTakeTag(ShopListRequestDTO ShopListRequestdto){
        ShopListResponseDTO ShopListResponsedto= ShopListFlow.getShopListByShopId(ShopListRequestdto,shopId);
        List<ShopPromoteVO> shopPromoteList =ShopListResponsedto.getResult().getShopList().stream().filter(item -> item.getShopId().equals(shopId)).findFirst().map( ShopIndexVO::getShopPromoteList).orElseThrow();
        String showContent=shopPromoteList.stream().filter(item -> item.getType().equals(type)).findFirst().map( ShopPromoteVO::getShowContent).orElseThrow();
        assertThat(showContent).isEqualTo("货到付款");
        Integer tagType=shopPromoteList.stream().filter(item -> item.getType().equals(type)).findFirst().map( ShopPromoteVO::getTagType).orElseThrow();
        assertThat(tagType).isEqualTo(2);
    }

        static Stream<Arguments> showLabelDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        shopListRequestDTO.setFiltering(false);
        shopListRequestDTO.setTabType((byte) 1);
        shopListRequestDTO.setRedPacketList(new ArrayList<>());
        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}
