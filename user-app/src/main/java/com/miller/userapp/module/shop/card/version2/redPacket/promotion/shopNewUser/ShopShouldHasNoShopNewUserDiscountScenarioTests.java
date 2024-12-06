package com.miller.userapp.module.shop.card.version2.redPacket.promotion.shopNewUser;

import com.hungrypanda.app.server.common.enums.ShopPromoteEnum;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
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

import java.util.stream.Stream;

/**
 * @author panjuxiang
 * @since 2024/7/30 13:43
 */
@Scenario(scenarioID = "01JE84BAG72WBQ99GSV62N8VKJ",
        scenarioName = "商卡(中文)_普通店铺配送商卡-红包适用商家列表_优惠标签_新客爆品_首页-商卡二期：新客爆品25-不展示",
        developmentTime = 40, maintenanceTime = 0, manualTestTime = 10)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasNoShopNewUserDiscountScenarioTests {
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.blank.compare.shopId"));

    @BeforeAll
    void beforeAll(){
        UserLoginFlow.loginByDefaultUser();
    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡-红包适用商家列表_优惠标签_新客爆品_首页-商卡二期：新客爆品25-不展示")
    void shouldNotExistShopNewUserDiscount(ShopListRequestDTO shopListRequestDTO) {

        ShopListResponseDTO shopList = ShopListFlow.getShopList(shopListRequestDTO);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();

        //遍历店铺的ShopPromoteList列表，如果没有type=25的优惠类型则返回true
        boolean flag = shopIndexVO.getShopPromoteList().stream().
                noneMatch(item -> item.getType() == ShopPromoteEnum.INDEX_MIN_NEW_USER_SKU.getType());

        assert flag;

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        
        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}
