package com.miller.userapp.module.shop.card.version3.userPack.promotion.discount;

import com.hungrypanda.app.server.common.enums.ShopPromoteEnum;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version3.userPack.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version3.userPack.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version3.userPack.response.ShopListResponseDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * @author panjuxiang
 * @since 2024/7/25 15:06
 */
@Scenario(scenarioID = "01K7EE2BWWCR8C428NEZ2BDPCB",
        scenarioName = "商卡(中文)_普通店铺自取商卡-SKYX01_优惠标签_商品折扣_自取频道-商卡二期:商品折扣3-不展示",
        author = "yancancan@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@EnvTag.Test
@TestFramework
@DisplayName("商卡(中文)")
public class ShopShouldHasNoDiscountScenarioTests {
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version3.increase.shopId"));

    @BeforeAll
    void beforeAll(){
        UserLoginFlow.loginByDefaultUser();
    }

    @MethodSource("com.miller.userapp.module.shop.card.version3.userPack.dataProvider.StaticDataProvider#StaticDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺自取商卡-SKYX01_优惠标签_商品折扣_自取频道-商卡二期:商品折扣3-不展示")
    void shouldNotExistDiscount(ShopListRequestDTO shopListRequestDTO) {

        ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO,shopId);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();

        //遍历店铺的ShopPromoteList列表，如果没有type=3的优惠类型则返回true
        boolean flag = shopIndexVO.getShopPromoteList().stream().
                noneMatch(item -> item.getType() == ShopPromoteEnum.DISCOUNT.getType());

        assert flag;

    }

}
