package com.miller.bdm.app.shopAccountInfo;

import com.miller.bdm.app.shopAccountInfo.flow.ShopAccountInfoFlow;
import com.miller.bdm.app.shopAccountInfo.request.ShopAccountInfoRequestDTO;
import com.miller.bdm.app.shopAccountInfo.respones.ShopAccountInfoResponseDTO;

import com.miller.bdm.constants.BusinessConstantOfERP;
import com.miller.bdm.constants.ResponseConstantOfERP;
import com.miller.bdm.login.flow.ERPLoginFlow;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.cache.CacheUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@DisplayName("bdm-商家账号信息")


@Scenario(
        scenarioID="01J5N1X0wM0JW76719ooorx",
        scenarioName = "bdm-商家账号信息",
        developmentTime = 30,
        maintenanceTime = 0 ,
        manualTestTime = 5
)
public class ShopAccountInfoTests {

    @BeforeAll
    static void beforeAll() {
        // 测试前置条件
        ERPLoginFlow.loginByDefaultUser();
    }


    @MethodSource("com.miller.bdm.app.shopAccountInfo.provider.ShopAccountInfoProvider#ShopTagList")
    @ParameterizedTest
    @DisplayName("bdm-商家账号信息")

    void ShowVisit(ShopAccountInfoRequestDTO shopAccountInfoRequestDTO) {
        ShopAccountInfoResponseDTO responseDTO = ShopAccountInfoFlow.getShowAccountInfo(shopAccountInfoRequestDTO);
        assertThat(responseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
        assertThat(responseDTO.getMessage()).isEqualTo(ResponseConstantOfERP.resultMessage);
        CacheUtils.set(BusinessConstantOfERP.SHOP_TAG_KEY, responseDTO);
    }



}

