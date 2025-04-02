package com.miller.market.pf.redPacket.exchangeRedPacket;

import com.miller.market.pf.redPacket.exchangeRedPacket.flow.ExchangeRedPacketFlow;
import com.miller.market.pf.redPacket.exchangeRedPacket.request.ExchangeRedPacketRequestDTO;
import com.miller.market.pf.redPacket.exchangeRedPacket.response.ExchangeRedPacketResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.panda.market.common.enums.BaseResponseCodeEnum;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


/**
 * 兑换红包
 */
@EnvTag.Test
@TestFramework
@Scenario(scenarioID = "01JQTKF1EQDH2XEXTK4MS8M7J4", scenarioName = "APP-进入用户首页-pf融合兑换pf红包:兑换码不存在"
        , author = "zhangpei@hungrypandagroup.com", developmentTime = 20, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("APP-pf融合兑换pf红包")
public class ExchangeRedPacketFailScenarioTests {

    @MethodSource("staticRedPacketDataProvider")
    @ParameterizedTest
    @DisplayName("PF融合_兑换pf红包：兑换码不存在")
    void exchangeRedPacketSuccessfully(ExchangeRedPacketRequestDTO requestDTO) {
        ExchangeRedPacketResponseDTO responseDTO = ExchangeRedPacketFlow.exchangeRedPacket(requestDTO);

        Assertions.assertThat(responseDTO.getData().getResult()).isFalse();
        Assertions.assertThat(responseDTO.getData().getErrorMsg()).isEqualTo(BaseResponseCodeEnum.RED_PACKET_IS_RECEIVE.getMsg());

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticRedPacketDataProvider() {
        ExchangeRedPacketRequestDTO requestDTO = new ExchangeRedPacketRequestDTO();
        //以下均为必填字段
        requestDTO.setCdKey("PFbucunzai");
        requestDTO.setRedPacketCustomerType(2);
        requestDTO.setUcUserId(249222L);
        requestDTO.setPortalId(3L);
        requestDTO.setRegionId(3L);
        requestDTO.setLatitude("30.20111");
        requestDTO.setLongitude("120.22136");
        return Stream.of(Arguments.of(requestDTO));
    }

}
