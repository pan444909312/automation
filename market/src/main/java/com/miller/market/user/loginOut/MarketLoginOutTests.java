package com.miller.market.user.loginOut;

import com.miller.market.constants.ResponseConstant;
import com.miller.market.user.loginOut.flow.MarketLoginOutFlow;
import com.miller.market.user.loginOut.response.MarketLoginOutResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 中超客户端_登出
 */
@EnvTag.Test
@TestFramework
@DisplayName("PF_用户-登出")
public class MarketLoginOutTests {


    @Test
    @DisplayName("PF_正常流程_用户登出")
    void LoginOutSuccessfully() {
        MarketLoginOutResponseDTO marketLoginOutResponseDTO = MarketLoginOutFlow.loginOut();

        assertThat(marketLoginOutResponseDTO.getCode()).isEqualTo(ResponseConstant.code);
    }

}
