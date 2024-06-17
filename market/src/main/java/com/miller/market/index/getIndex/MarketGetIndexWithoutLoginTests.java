package com.miller.market.index.getIndex;

import com.miller.market.constants.ResponseConstant;
import com.miller.market.index.getIndex.flow.MarketGetIndexWithoutLoginFlow;
import com.miller.market.index.getIndex.response.MarketGetIndexResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 * 首页
 */
@EnvTag.Test
@TestFramework
@DisplayName("首页")
public class MarketGetIndexWithoutLoginTests {

    @Test
    @DisplayName("正常流程_未登录_获取首页")
    void getIndexSuccessfully() {
        MarketGetIndexResponseDTO marketGetIndexResponseDTO = MarketGetIndexWithoutLoginFlow.getIndex();

        Assertions.assertThat(marketGetIndexResponseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(marketGetIndexResponseDTO.getData()).isNotNull();
        Assertions.assertThat(marketGetIndexResponseDTO.getData().getIndexList()).isNotNull();

    }

}
