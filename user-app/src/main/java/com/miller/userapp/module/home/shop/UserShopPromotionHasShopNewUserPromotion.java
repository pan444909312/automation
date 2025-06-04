package com.miller.userapp.module.home.shop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.hungrypanda.app.server.dto.shop.ShopBaseReqDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.http.HttpUtils;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.util.RequestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Scenario(scenarioID = "01JVKR6DPY3AY792BTB6AE5DCP",
        scenarioName = "店铺的优惠信息包含门店新客优惠",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@EnvTag.Test
@DisplayName("/api/app/user/shop/promotion")
public class UserShopPromotionHasShopNewUserPromotion {

    /**
     * 接口_店铺的优惠信息查询
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/app/user/shop/promotion";
    private final Long shopId = Long.parseLong(new PropertiesUtils().getProperty(this.getClass(), "user.app.for.test.shop.card.version2.shopId"));


    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("店铺的优惠信息包含门店新客优惠")
    void testCase(ShopBaseReqDTO shopBaseReqDTO) {
        shopBaseReqDTO.setShopId(shopId);
        String result = HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(shopBaseReqDTO), null, String.class);

        Integer resultCode = (Integer) JSONPath.eval(JSON.parse(result), "$.resultCode");
        String shopNewUserPromotion =  JSONPath.eval(JSON.parse(result), "$.result.shopPromotionList[?(@.promoteType == 1)]").toString();
        Integer value = (Integer) JSONPath.eval(JSON.parse(result), "$.result.shopPromotionList[?(@.promoteType == 1)].discountInfoList[0].value");


        assertThat(resultCode).isEqualTo(1000);
        assertThat(shopNewUserPromotion).isNotNull();
        assertThat(value).isEqualTo(990);


    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        ShopBaseReqDTO shopBaseReqDTO = new ShopBaseReqDTO();
        shopBaseReqDTO.setDeliveryType(1);

        return Stream.of(Arguments.of(shopBaseReqDTO));
    }

}
