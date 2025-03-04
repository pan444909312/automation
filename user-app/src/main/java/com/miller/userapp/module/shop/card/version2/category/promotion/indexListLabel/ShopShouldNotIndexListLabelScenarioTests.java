package com.miller.userapp.module.shop.card.version2.category.promotion.indexListLabel;

import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.hungrypanda.app.server.vo.index.ShopPromoteVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version2.category.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version2.category.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version2.category.response.ShopListResponseDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author huyang
 * @since 2024/12/20 18:07
 */
@Scenario(scenarioID = "01JFHS6Q8084RYB7JK3JTCESMT",
        scenarioName = "商卡(中文)_普通店铺配送商卡-品类频道_优惠标签_营销标签_首页-商卡二期：营销标签tpye=22-不展示",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 20, maintenanceTime = 0, manualTestTime = 10)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldNotIndexListLabelScenarioTests {
    //    采用店铺2的数据，标签类型：22，content:无
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(),"user.app.for.test.shop.card.version2.blank.compare.shopId"));
    private final Integer type=22;
    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
    }

    @DisplayName("普通店铺配送商卡-品类频道_优惠标签_营销标签_首页-商卡二期：营销标签tpye=22-不展示")
    @MethodSource("showLabelDataProvider")
    @ParameterizedTest
    void hasSelfTakeTag(ShopListRequestDTO ShopListRequestdto){
        ShopListResponseDTO ShopListResponsedto= ShopListFlow.getShopList(ShopListRequestdto);
        List<ShopPromoteVO> shopPromoteList =ShopListResponsedto.getResult().getShopList().stream().filter(item -> item.getShopId().equals(shopId)).findFirst().map( ShopIndexVO::getShopPromoteList).orElseThrow();
        List <ShopPromoteVO> shopPromoteTypeList=shopPromoteList.stream().filter(item -> item.getType().equals(type)).toList();
        // System.out.println(shopPromoteTypeList);
        assertThat(shopPromoteTypeList.size()).isEqualTo(0);

    }

    static Stream<Arguments> showLabelDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setFiltering(false); // 开发代码Bug，没有对 null 进行判断，应该默认给false的
        return Stream.of(Arguments.of(shopListRequestDTO));
    }
}
