package com.miller.market.pf.hpfOrder;



import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.market.pf.hpfOrder.flow.GetOrderDetailFlow;
import com.miller.market.pf.hpfOrder.request.GetOrderDetailRequestDTO;
import com.miller.market.pf.hpfOrder.response.GetOrderDetailResponseDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@Scenario(scenarioID = "01JQRBQHH5FYSYE2FX7QTDBN7H", scenarioName = "APP-进入用户首页-检查pf融合订单详情：三方物流信息"
        , author = "zhangpei@hungrypandagroup.com", developmentTime = 20, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("APP-pf融合订单详情")
public class GetOrderDetailTrackInfoScenarioTests {

    static GetOrderDetailRequestDTO requestDTO = new GetOrderDetailRequestDTO();
    @BeforeAll
    public static void beforeAll() throws InterruptedException {
        //开启融合开关
//        XXLConfUtils.updateConfig(XXLConfigEnvEnum.TEST.getEnv(), "user-app-server.hpf.switch", "PF融合开关", true);
//        Thread.sleep(3000L);
    }
    static Stream<Arguments> marketShopDataProvider() {
        requestDTO.setOrderSn("PFZC2502081811207556");
        return Stream.of(
                Arguments.arguments(requestDTO)
        );
    }

    @MethodSource("marketShopDataProvider")
    @ParameterizedTest
    @DisplayName("APP-进入用户首页-检查pf融合订单详情：三方物流信息")
    public void getShopCardInfoTests(GetOrderDetailRequestDTO requestDTO){
        GetOrderDetailResponseDTO responseDTO = GetOrderDetailFlow.getShopCardInfoFlow(requestDTO);
        assertThat(responseDTO.getData().getTrackInfo()).isNotNull();
        assertThat(responseDTO.getData().getTrackInfo().getTrackLink()).isNotNull();
        assertThat(responseDTO.getData().getTrackInfo().getTrackCompany()).isNotNull();
        assertThat(responseDTO.getData().getTrackInfo().getTrackNumber()).isNotNull();
    }

}
