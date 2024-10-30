package com.miller.market.goods.getCategory;

import com.miller.market.constants.ResponseConstant;
import com.miller.market.goods.getCategory.flow.MarketGetCategoryWithoutLoginFlow;
import com.miller.market.goods.getCategory.response.MarketGetCategoryResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 * 分类页
 */
@Scenario(scenarioID = "01J5SGFNY03AZH1TY0GQ8Q7E76",
        scenarioName = "正常流程_未登录_获取分类页",
        developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@EnvTag.Test
@DisplayName("分类页")
public class MarketGetCategoryWithoutLoginScenarioTests {

    @Test
    @DisplayName("正常流程_未登录_获取分类页")
    void getCategoryWithoutLoginSuccessfully() {
        MarketGetCategoryResponseDTO marketGetCategoryResponseDTO = MarketGetCategoryWithoutLoginFlow.getCategory();

        Assertions.assertThat(marketGetCategoryResponseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(marketGetCategoryResponseDTO.getData()).isNotNull();
        Assertions.assertThat(marketGetCategoryResponseDTO.getData().getCategoryList()).isNotNull();

    }

}
