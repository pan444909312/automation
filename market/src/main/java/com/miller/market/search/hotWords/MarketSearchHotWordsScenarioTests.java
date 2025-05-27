package com.miller.market.search.hotWords;

import com.miller.market.constants.ResponseConstant;
import com.miller.market.search.hotWords.flow.MarketSearchHotWordsWithoutLoginFlow;
import com.miller.market.search.hotWords.response.MarketSearchHotWordsResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



/**
 * 客户端搜索
 */
@Scenario(scenarioID = "01JVY46FBMHR77KCE4N1FBGG91",
        scenarioName = "正常流程_未登录_热搜词",
        author = "zhangpei@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@EnvTag.Test
@DisplayName("PF_客户端搜索")
public class MarketSearchHotWordsScenarioTests {

    @Test
    @DisplayName("PF_正常流程-热搜词")
    void searchSuccessfully() {
        MarketSearchHotWordsResponseDTO marketSearchHotWordsResponseDTO = MarketSearchHotWordsWithoutLoginFlow.search();

        Assertions.assertThat(marketSearchHotWordsResponseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(marketSearchHotWordsResponseDTO.getData()).isNotNull();
    }







}
