package com.miller.market.index.getIndex;

import com.miller.market.constants.ResponseConstant;
import com.miller.market.index.getIndex.flow.MarketGetIndexWithoutLoginFlow;
import com.miller.market.index.getIndex.response.MarketGetIndexResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 * 首页
 */
@Scenario(scenarioID = "01JWGCJP4G4VPK0S8836JDW6XE",
        scenarioName = "正常流程_未登录_获取首页",
        author = "zhangpei@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@EnvTag.Test
@DisplayName("PF_首页")
public class MarketGetIndexWithoutLoginScenarioTests {

    @Test
    @DisplayName("PF_正常流程_未登录_获取首页")
    void getIndexSuccessfully() {
        MarketGetIndexResponseDTO marketGetIndexResponseDTO = MarketGetIndexWithoutLoginFlow.getIndex();

        Assertions.assertThat(marketGetIndexResponseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(marketGetIndexResponseDTO.getData()).isNotNull();
        Assertions.assertThat(marketGetIndexResponseDTO.getData().getIndexList()).isNotNull();

    }

}
