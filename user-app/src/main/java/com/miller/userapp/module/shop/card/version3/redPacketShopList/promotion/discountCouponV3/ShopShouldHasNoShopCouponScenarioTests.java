package com.miller.userapp.module.shop.card.version3.redPacketShopList.promotion.discountCouponV3;

import com.hungrypanda.app.server.common.enums.ShopPromoteEnum;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.hungrypanda.app.server.vo.index.ShopPromoteVO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.miller.service.framework.util.JsonUnitUtils.assertThat;

/**
 * author: yancancan
 * date: 2025/07/26
 */
@Scenario(scenarioID = "01KE9JG2QPPC9TX67MZTJYXW99", scenarioName = "商卡(中文)_普通店铺配送商卡-SKYX01_优惠标签_店铺红包红包适用商家列表-商卡二期-SKYX实验组：无店铺红包",
        author = "yancancan@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 10)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasNoShopCouponScenarioTests {
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.open.no.pre"));
    UserLoginRequestDTO userLoginRequestDTO;
    @BeforeAll
    public void beforeAll() {
        //登陆任意账号
        UserLoginFlow.loginByDefaultUser();
    }
    @MethodSource("couponDataProvider")
    @ParameterizedTest
    @DisplayName("商卡(中文)_普通店铺配送商卡-SKYX01_优惠标签_店铺红包红包适用商家列表-商卡二期-SKYX实验组：无店铺红包")
    void shouldShowPandLeagueFullSubCouponLabel(ShopListRequestDTO shopListRequestDTO) {
        ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO,shopId);

        ShopIndexVO shopIndexVO= shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();
        //校验店铺红包存在多个标签
        List<ShopPromoteVO> shopPromoteList = new ArrayList<>();
        shopPromoteList=shopIndexVO.getShopPromoteList().stream().filter(item -> item.getType() == ShopPromoteEnum.SHOP_RED_PACKET.getType()).toList();
        assertThat(shopPromoteList.size()).isEqualTo(0);




    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> couponDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setCityName("九江市");
        shopListRequestDTO.setShopCategoryIds("[3896,3914,5486]");// 开发代码Bug，没有对 null 进行判断，应该默认给false的 // 开发代码Bug，没有对 null 进行判断，应该默认给false的

        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}
