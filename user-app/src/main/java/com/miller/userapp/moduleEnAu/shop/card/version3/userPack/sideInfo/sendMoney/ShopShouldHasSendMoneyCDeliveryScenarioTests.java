package com.miller.userapp.moduleEnAu.shop.card.version3.userPack.sideInfo.sendMoney;

import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.moduleEnAu.shop.card.version3.userPack.flow.ShopListFlow;
import com.miller.userapp.moduleEnAu.shop.card.version3.userPack.request.ShopListRequestDTO;
import com.miller.userapp.moduleEnAu.shop.card.version3.userPack.response.ShopListResponseDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test

@TestFramework
@Scenario(scenarioID = "01K7E8DGRKHDHZ82MNYV5CJ78P", scenarioName = "用户-自取频道店铺流-商卡(中文)-普通店铺自取商卡-SKYX01-辅助信息-自取价格-自取频道-商卡二期：自取价格 - C配"
        , author = "yancancan@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("用户-自取频道店铺流-商卡(中文)-普通店铺自取商卡-SKYX01-辅助信息-自取价格-自取频道-商卡二期：自取价格 - C配")
public class ShopShouldHasSendMoneyCDeliveryScenarioTests {
    //    测试店铺:04
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(),"user.app.for.test.shop.card.version2.blank.02.compare.shopId"));

    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
    }
    @DisplayName("用户-自取频道店铺流-商卡(中文)-普通店铺自取商卡-SKYX01-辅助信息-自取价格-自取频道-商卡二期：自取价格 - C配")
    @MethodSource("showLabelDataProvider")
    @ParameterizedTest
    void hasSendMoneyInfo(ShopListRequestDTO ShopListRequestdto){
        ShopListResponseDTO ShopListResponsedto = ShopListFlow.getShopListByShopId(ShopListRequestdto,shopId);
        // 一次性获取ShopIndexVO对象，避免重复流操作
        ShopIndexVO shopIndexVO = ShopListResponsedto.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId))
                .findFirst()
                .orElse(null); // 使用orElse避免NoSuchElementException

        // 校验sendMoney为空
        assertThat(shopIndexVO).isNotNull(); // 确保找到了对应的店铺
        assertThat(shopIndexVO.getSendMoney()).isNull();
        assertThat(shopIndexVO.getSendMoneyMsg()).isNull();
    }
    //    DataProvider改为在测试用例文件里写,提供测试数据
    static Stream<Arguments> showLabelDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 自取频道店铺流必须传经纬度
        shopListRequestDTO.setFiltering(false); // 开发代码Bug，没有对 null 进行判断，应该默认给false的
        shopListRequestDTO.setLongitude("115.954100");
        shopListRequestDTO.setLatitude("29.660580");
        shopListRequestDTO.setIsNeedMarketCategory(1);
        shopListRequestDTO.setMarketCategoryId(0);
        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}
