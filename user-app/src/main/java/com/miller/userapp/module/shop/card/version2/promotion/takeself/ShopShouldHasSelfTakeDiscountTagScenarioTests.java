package com.miller.userapp.module.shop.card.version2.promotion.takeself;

import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.hungrypanda.app.server.vo.index.ShopPromoteVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.module.shop.card.version2.promotion.takeself.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version2.promotion.takeself.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version2.promotion.takeself.response.ShopListResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@DisplayName("用户-首页店铺流-商卡(中文)-普通店铺配送商卡-优惠标签-自取折扣-首页-商卡二期：自取折扣30 - 无独享商品折扣")
public class ShopShouldHasSelfTakeDiscountTagScenarioTests {
//    采用店铺2的数据，标签类型：30，自取折扣
    private final Long shopId = Long.parseLong("59750820");
    private final Integer type=30;

    @DisplayName("用户-首页店铺流-商卡(中文)-普通店铺配送商卡-优惠标签-自取折扣-首页-商卡二期：自取折扣30 - 无独享商品折扣")
    @MethodSource("showLabelDataProvider")
    @ParameterizedTest
    void hasSelfTakeTag(ShopListRequestDTO ShopListRequestdto){
        ShopListResponseDTO ShopListResponsedto= ShopListFlow.getShopList(ShopListRequestdto);
        List<ShopPromoteVO> shopPromoteList =ShopListResponsedto.getResult().getShopList().stream().filter(item -> item.getShopId().equals(shopId)).findFirst().map( ShopIndexVO::getShopPromoteList).orElseThrow();
        String showContent=shopPromoteList.stream().filter(item -> item.getType().equals(type)).findFirst().map( ShopPromoteVO::getShowContent).orElseThrow();
        assertThat(showContent).isEqualTo("自取再享9折");
    }
    //    DataProvider改为在测试用例文件里写,提供测试数据
    static Stream<Arguments> showLabelDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setFiltering(false); // 开发代码Bug，没有对 null 进行判断，应该默认给false的
        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}
