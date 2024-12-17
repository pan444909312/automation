package com.miller.market.user.center;

import com.miller.market.constants.ResponseConstant;
import com.miller.market.user.center.flow.MarketCenterFlow;
import com.miller.market.user.center.response.MarketCenterResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 中超客户端_个人中心
 */
@EnvTag.Test
@TestFramework
@DisplayName("PF_个人中心")
public class MarketCenterTests {


    @Test
    @DisplayName("PF_正常流程_个人中心")
    void LoginOutSuccessfully() {
        MarketCenterResponseDTO marketCenterResponseDTO = MarketCenterFlow.center();

        assertThat(marketCenterResponseDTO.getCode()).isEqualTo(ResponseConstant.code);
        assertThat(marketCenterResponseDTO.getData().getOrderInfoList()).isNotNull();
    }

}
