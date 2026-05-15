package com.miller.userapp.moduleEnAu.shop.card.version3.home.promotion.vipDiscount;

import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.hungrypanda.app.server.vo.index.ShopPromoteVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.moduleEnAu.shop.card.version3.home.flow.ShopListFlow;
import com.miller.userapp.moduleEnAu.shop.card.version3.home.request.ShopListRequestDTO;
import com.miller.userapp.moduleEnAu.shop.card.version3.home.response.ShopListResponseDTO;
import com.miller.userapp.util.RequestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static com.miller.service.framework.util.JsonUnitUtils.assertThat;

/**
 * 商卡(英文)_普通店铺配送商卡-SKYX01_优惠标签_会员折扣_首页-商卡二期：会员折扣43
 *
 * @author yancancan@hungrypandagroup.com
 */
@Scenario(scenarioID = "TODO_FILL_SCENARIO_ID",
        scenarioName = "商卡(英文)_普通店铺配送商卡-SKYX01_优惠标签_会员折扣_首页-商卡二期：会员折扣44-未登录",
        author = "yancancan@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@EnvTag.Test
@TestFramework
@DisplayName("商卡(中文)")
public class ShopShouldHasVipDiscountTests {

    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version3.sort.member.discount.shopId"));

    private static final String distinctId = new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.distinctId");

    @BeforeAll
    void beforeAll() {
        // 未登录场景，只设置基础请求头，不携带 token
        var headers = new HashMap<String, Object>();
        headers.put("Content-Type", "application/json");
        headers.put("uniqueToken", distinctId);
        RequestUtils.setHeaders(headers);
    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡-SKYX01_优惠标签_会员折扣_首页-商卡二期：会员折扣44-未登录")
    void shouldHasVipDiscount(ShopListRequestDTO shopListRequestDTO) {
        ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO, shopId, "8.82.5");
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId))
                .findFirst()
                .orElseThrow();

        // 过滤 type=44的标签
        List<ShopPromoteVO> vipDiscountList = shopIndexVO.getShopPromoteList().stream()
                .filter(item -> item.getType().equals(44))
                .toList();
        assertThat(vipDiscountList.size()).isEqualTo(1);
        ShopPromoteVO vipDiscount = vipDiscountList.get(0);
        // tagtype=2, tagTypeSort=0
        assertThat(vipDiscount.getTagType()).isEqualTo(2);
        assertThat(vipDiscount.getTagTypeSort()).isEqualTo(0);
//        assertThat(vipDiscount.getmemberLevel()).isEqualTo(0);
        // content 校验
        assertThat(vipDiscount.getShowContent()).isEqualTo("会员3折");
    }

    static Stream<Arguments> staticDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        shopListRequestDTO.setFiltering(false);
        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}