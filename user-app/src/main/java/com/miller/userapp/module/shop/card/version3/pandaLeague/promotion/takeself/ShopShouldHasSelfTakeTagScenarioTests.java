package com.miller.userapp.module.shop.card.version3.pandaLeague.promotion.takeself;

import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.hungrypanda.app.server.vo.index.ShopPromoteVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
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

@EnvTag.Test

@TestFramework
@Scenario(scenarioID = "01K47416DZGHBWMAFMZG74WZXR", scenarioName = "用户-熊猫联盟频道店铺流-商卡(中文)-普通店铺配送商卡-熊猫联盟频道-优惠标签-可自取-熊猫联盟频道-商卡二期：可自取33（已删除）"
        , author = "yancancan@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("用户-熊猫联盟频道店铺流-商卡(中文)-普通店铺配送商卡-熊猫联盟频道-优惠标签-可自取-熊猫联盟频道-商卡二期：可自取33（已删除）")
public class ShopShouldHasSelfTakeTagScenarioTests {
//    测试店铺：店铺1,测试标签类型：33，content：可自取
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(),"user.app.for.test.shop.card.version2.shopId"));
    private final Integer type=33;
    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
    }
    @DisplayName("用户-熊猫联盟频道店铺流-商卡(中文)-普通店铺配送商卡-熊猫联盟频道-优惠标签-可自取-不展示自取标签（已删除）")
    @MethodSource("showLabelDataProvider")
    @ParameterizedTest
    void hasSelfTakeTag(ShopListRequestDTO ShopListRequestdto){
        ShopListResponseDTO ShopListResponsedto= ShopListFlow.getShopListByShopId(ShopListRequestdto,shopId);
        List<ShopPromoteVO> shopPromoteList =ShopListResponsedto.getResult().getShopList().stream().filter(item -> item.getShopId().equals(shopId)).findFirst().map( ShopIndexVO::getShopPromoteList).orElseThrow();
        //遍历店铺的ShopPromoteList列表，如果没有type=33的优惠类型则返回true
        boolean flag = shopPromoteList.stream().
                noneMatch(item -> item.getType() == type);

        assert flag;
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
