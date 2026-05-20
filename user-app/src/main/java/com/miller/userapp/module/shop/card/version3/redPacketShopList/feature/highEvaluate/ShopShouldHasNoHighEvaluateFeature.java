package com.miller.userapp.module.shop.card.version3.redPacketShopList.feature.highEvaluate;

import com.hungrypanda.app.server.common.enums.index.shopFeature.ShopFeatureEnum;
import com.hungrypanda.app.server.vo.index.ShopFeatureVO;
import com.hungrypanda.app.server.vo.index.ShopIndexVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.shop.card.version3.redPacketShopList.flow.ShopListFlow;
import com.miller.userapp.module.shop.card.version3.redPacketShopList.request.ShopListRequestDTO;
import com.miller.userapp.module.shop.card.version3.redPacketShopList.response.ShopListResponseDTO;
import com.miller.userapp.util.PandaTestDBHelpful;
import com.miller.userapp.util.RedisUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Scenario(scenarioID = "01K4WC5KTMV5WHM851SZEK1Z65",
        scenarioName = "普通店铺配送商卡-红包适用商家列表-SKYX01_营销标_评价标签_评价人数不满足配置，不展示",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 15, manualTestTime = 10)
@EnvTag.Test
@DisplayName("商卡(中文)")
public class ShopShouldHasNoHighEvaluateFeature {


    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.02.shopId"));



    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("普通店铺配送商卡-红包适用商家列表-SKYX01_营销标_评价标签_评价人数不满足配置，不展示")
    void shouldExistEvaluationFeature(ShopListRequestDTO shopListRequestDTO) {

        ShopListResponseDTO shopList = ShopListFlow.getShopListByShopId(shopListRequestDTO,shopId);
        ShopIndexVO shopIndexVO = shopList.getResult().getShopList().stream()
                .filter(item -> item.getShopId().equals(shopId)).findFirst().get();

        ShopFeatureVO shopFeatureVO = shopIndexVO.getShopFeatureList().stream().
                filter(item -> item.getType().equals(ShopFeatureEnum.EVALUATE_NUM.getType())).findFirst().orElse(null);
        List<Map<String, Object>> maps = PandaTestDBHelpful.executeSelectListSql(" SELECT evaluation_over_4_usercnt_180d FROM hp_data_shop_home_recommend_label where shop_id = " + shopId);

        long evaluationOver4UserCnt180dByShopId = (long) maps.get(0).get("evaluation_over_4_usercnt_180d");


        long cardEvaluateNum = Long.parseLong(RedisUtils.getRedisInstance().get("CARD_EVALUATE_NUM").toString());

        assertThat(evaluationOver4UserCnt180dByShopId < cardEvaluateNum).isTrue();
        assertThat(shopFeatureVO).isNull();
    }


    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        ShopListRequestDTO shopListRequestDTO = new ShopListRequestDTO();
        // 可以不用传参数
        shopListRequestDTO.setCityName("九江市");// 开发代码Bug，没有对 null 进行判断，应该默认给false的
        shopListRequestDTO.setShopCategoryIds("[3896,3914,5486]");

        return Stream.of(Arguments.of(shopListRequestDTO));
    }

}
