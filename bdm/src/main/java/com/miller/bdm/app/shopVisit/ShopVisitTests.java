package com.miller.bdm.app.shopVisit;

import com.miller.bdm.constants.BusinessConstantOfERP;
import com.miller.bdm.constants.ResponseConstantOfERP;
import com.miller.bdm.login.flow.ERPLoginFlow;
import com.miller.bdm.app.shopVisit.respones.ShopVisitResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.cache.CacheUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import com.miller.bdm.app.shopVisit.flow.ShopVisitFlow;
import static org.assertj.core.api.Assertions.assertThat;

import com.miller.bdm.app.shopVisit.request.ShopVisitRequestDTO;
@EnvTag.Test
@DisplayName("bdm-商家详情页拜访记录")


@Scenario(
        scenarioID="01J5N1X0M0JW76719ooorx",
        scenarioName = "bdm-商家详情页拜访记录",
        author = "lipan@hungrypandagroup.com", developmentTime = 30,
        maintenanceTime = 0 ,
        manualTestTime = 5
)
public class ShopVisitTests {

    @BeforeAll
    static void beforeAll() {
        // 测试前置条件
        ERPLoginFlow.loginByDefaultUser();
    }


    @MethodSource("com.miller.bdm.app.shopVisit.provider.ShopVisitProvider#ShopTagList")
    @ParameterizedTest
    @DisplayName("bdm-商家活动详情")

    void ShowVisit(ShopVisitRequestDTO shopVisitRequestDTO) {
        ShopVisitResponseDTO responseDTO = ShopVisitFlow.getShowVisit(shopVisitRequestDTO);
        assertThat(responseDTO.getCode()).isEqualTo(ResponseConstantOfERP.resultCode);
        assertThat(responseDTO.getMessage()).isEqualTo(ResponseConstantOfERP.resultMessage);
        CacheUtils.set(BusinessConstantOfERP.SHOP_TAG_KEY, responseDTO);
    }



}

