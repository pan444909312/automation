package com.miller.market.system.getPortal;

import com.miller.market.constants.ResponseConstant;
import com.miller.market.system.getPortal.flow.MarketGetPortalWithoutLoginFlow;
import com.miller.market.system.getPortal.response.MarketGetPortalResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 * 获取站点
 */
@Scenario(scenarioID = "01JA4ZPKGMEJSHP04GZWZZPQE3",
        scenarioName = "正常流程_未登录_获取站点信息",
        developmentTime = 10, maintenanceTime = 0, manualTestTime = 0)
@EnvTag.Test
@DisplayName("获取站点")
public class MarketGetPortalWithoutLoginScenarioTests {

    @Test
    @DisplayName("正常流程_未登录_获取站点")
    void getPortalSuccessfully() {
        MarketGetPortalResponseDTO marketGetIndexResponseDTO = MarketGetPortalWithoutLoginFlow.getPortal();

        Assertions.assertThat(marketGetIndexResponseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(marketGetIndexResponseDTO.getData()).isNotNull();
    }

}
