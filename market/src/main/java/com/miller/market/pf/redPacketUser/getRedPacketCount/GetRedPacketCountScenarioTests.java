package com.miller.market.pf.redPacketUser.getRedPacketCount;

import com.miller.market.pf.redPacketUser.getRedPacketCount.flow.GetRedPacketCountFlow;
import com.miller.market.pf.redPacketUser.getRedPacketCount.request.GetRedPacketCountRequestDTO;
import com.miller.market.pf.redPacketUser.getRedPacketCount.response.GetRedPacketCountResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


/**
 * 获取用户pf生效红包数量
 */
@EnvTag.Test
@TestFramework
@Scenario(scenarioID = "01JQTR9Q3ARQ9F4HD7CTM6VWSX", scenarioName = "APP-进入用户首页-pf融合获取用户pf生效红包数量"
        , author = "zhangpei@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("APP-pf融合获取用户pf生效红包数量")
public class GetRedPacketCountScenarioTests {

    @MethodSource("staticRedPacketDataProvider")
    @ParameterizedTest
    @DisplayName("PF融合_获取用户pf生效红包数量")
    void exchangeRedPacketSuccessfully(GetRedPacketCountRequestDTO requestDTO) {
        GetRedPacketCountResponseDTO responseDTO = GetRedPacketCountFlow.getRedPacketCount(requestDTO);

        Assertions.assertThat(responseDTO.getCode()).isEqualTo(1);
        Assertions.assertThat(responseDTO.getData().getAllCount()).isNotNull();
        Assertions.assertThat(responseDTO.getData().getRedPacketText()).contains("PandaFresh专享红包");
        Assertions.assertThat(responseDTO.getData().getShopLogo()).isNotNull();

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticRedPacketDataProvider() {
        GetRedPacketCountRequestDTO requestDTO = new GetRedPacketCountRequestDTO();
        //以下均为必填字段
        //("1有效  2失效")
        requestDTO.setSelectType(1);
        requestDTO.setSelectMethod(1);
        requestDTO.setUcUserId(249222L);
        requestDTO.setPortalId(3L);
        requestDTO.setRegionId(3L);
        requestDTO.setLatitude("30.20111");
        requestDTO.setLongitude("120.22136");
        return Stream.of(Arguments.of(requestDTO));
    }

}
