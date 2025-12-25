package com.miller.userapp.moduleEnAu.shop.card.version3.pandaLeague.promotion.takeself;

import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.hungrypanda.app.server.vo.index.ShopPromoteVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.moduleEnAu.shop.card.version3.pandaLeague.flow.ShopListFlow;
import com.miller.userapp.moduleEnAu.shop.card.version3.pandaLeague.request.ShopListRequestDTO;
import com.miller.userapp.moduleEnAu.shop.card.version3.pandaLeague.response.ShopListResponseDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@Scenario(scenarioID = "01K47416DZGHBWMAFMZG74WZXQ", scenarioName = "用户-熊猫联盟频道店铺流-商卡(中文)-普通店铺配送商卡-熊猫联盟频道-优惠标签-自取折扣-熊猫联盟频道-商卡二期：自取折扣30 - 无独享商品折扣"
        , author = "yancancan@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("用户-熊猫联盟频道店铺流-商卡(中文)-普通店铺配送商卡-熊猫联盟频道-优惠标签-自取折扣-熊猫联盟频道-商卡二期：自取折扣30 - 无独享商品折扣")
public class ShopShouldHasSelfTakeDiscountTagScenarioTests {
//    采用店铺2的数据，标签类型：30，自取折扣
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(),"user.app.for.test.shop.card.version2.04.shopId"));
    private final Integer type=30;
    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
    }

    @DisplayName("用户-熊猫联盟频道店铺流-商卡(中文)-普通店铺配送商卡-熊猫联盟频道-优惠标签-自取折扣-熊猫联盟频道-商卡二期：自取折扣30 - 无独享商品折扣")
    @MethodSource("showLabelDataProvider")
    @ParameterizedTest
    void hasSelfTakeTag(ShopListRequestDTO ShopListRequestdto){
        ShopListResponseDTO ShopListResponsedto= ShopListFlow.getShopListByShopId(ShopListRequestdto,shopId);
        List<ShopPromoteVO> shopPromoteList =ShopListResponsedto.getResult().getShopList().stream().filter(item -> item.getShopId().equals(shopId)).findFirst().map( ShopIndexVO::getShopPromoteList).orElseThrow();
        String showContent=shopPromoteList.stream().filter(item -> item.getType().equals(type)).findFirst().map( ShopPromoteVO::getShowContent).orElseThrow();
        Integer tagType=shopPromoteList.stream().filter(item -> item.getType().equals(type)).findFirst().map( ShopPromoteVO::getTagType).orElseThrow();
        assertThat(showContent).isEqualTo("自取再享9折");
        assertThat(tagType).isEqualTo(2);
    }
    //    DataProvider改为在测试用例文件里写,提供测试数据
        static Stream<Arguments> showLabelDataProvider() {
            ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
            shopListRequestDTO.setFiltering(false);
            shopListRequestDTO.setTabType((byte) 1);
            shopListRequestDTO.setRedPacketList(new ArrayList<>());
            return Stream.of(Arguments.of(shopListRequestDTO));
    }
}
