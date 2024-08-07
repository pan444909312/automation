package com.miller.userapp.module.shop.card.version2.promotion.memberBenefit;

import com.hungrypanda.app.server.common.enums.ShopPromoteEnum;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.hungrypanda.app.server.vo.index.ShopPromoteVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.shop.card.version2.promotion.memberBenefit.flow.ShopListFlowNoLogin;
import com.miller.userapp.module.shop.card.version2.promotion.memberBenefit.response.ShopListResponseDTO;
import com.miller.userapp.module.shop.card.version2.promotion.memberBenefit.request.ShopListRequestDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@EnvTag.Test
@TestFramework
@DisplayName("商卡(中文)")
public class ShopShouldHasMemberBenefitDeliveryDsicountTests {
     private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId"));
//     用户未登录，会员店铺配置了运费减免，展示运费减免红包


    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡_优惠标签_会员权益_首页-商卡二期：会员权益32-会员运费免减")
    void memberBenefitDeliveryDsicount(ShopListRequestDTO shopListRequestDTO) {

        ShopListResponseDTO shopList = ShopListFlowNoLogin.getShopList(shopListRequestDTO);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();

        //遍历店铺的ShopPromoteList列表，
        ShopPromoteVO memberPacket = shopIndexVO.getShopPromoteList().stream().
                filter(item -> item.getType() == ShopPromoteEnum.INDEX_MEMBER_PACKET.getType()).findFirst().get();
        assert memberPacket.getShowContent().equals("会员运费减免");

    }
    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setFiltering(false);
        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}

