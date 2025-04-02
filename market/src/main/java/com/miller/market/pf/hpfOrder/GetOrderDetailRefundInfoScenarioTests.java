package com.miller.market.pf.hpfOrder;



import com.miller.market.pf.hpfOrder.flow.GetOrderDetailFlow;
import com.miller.market.pf.hpfOrder.request.GetOrderDetailRequestDTO;
import com.miller.market.pf.hpfOrder.response.GetOrderDetailResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@Scenario(scenarioID = "01JQRBQGHJJ6R1RM3D15172Q17", scenarioName = "APP-进入用户首页-检查pf融合订单详情：特殊单"
        , author = "zhangpei@hungrypandagroup.com", developmentTime = 20, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("APP-pf融合订单详情")
public class GetOrderDetailRefundInfoScenarioTests {

    static GetOrderDetailRequestDTO requestDTO = new GetOrderDetailRequestDTO();
    @BeforeAll
    public static void beforeAll() throws InterruptedException {
        //开启融合开关
//        XXLConfUtils.updateConfig(XXLConfigEnvEnum.TEST.getEnv(), "user-app-server.hpf.switch", "PF融合开关", true);
//        Thread.sleep(3000L);
    }
    static Stream<Arguments> marketShopDataProvider() {
        requestDTO.setOrderSn("PFZC2503041503302845");
        return Stream.of(
                Arguments.arguments(requestDTO)
        );
    }

    @MethodSource("marketShopDataProvider")
    @ParameterizedTest
    @DisplayName("APP-进入用户首页-检查pf融合订单详情：特殊单")
    public void getShopCardInfoTests(GetOrderDetailRequestDTO requestDTO){
        GetOrderDetailResponseDTO responseDTO = GetOrderDetailFlow.getShopCardInfoFlow(requestDTO);
        assertThat(responseDTO.getData().getRefundInfo()).isNotNull();
        //pf融合单只有退款特殊单（类型为1）
        assertThat(responseDTO.getData().getRefundInfo().getRefundList().get(0).getRefundType()).isEqualTo(1);
        assertThat(responseDTO.getData().getRefundInfo().getRefundList().get(0).getRefundAmountStr()).isNotEmpty();
        assertThat(responseDTO.getData().getRefundInfo().getRefundList().get(0).getRefundStatusStr()).isNotEmpty();
        assertThat(responseDTO.getData().getRefundInfo().getRefundList().get(0).getTargetId()).isNotEmpty();
        assertThat(responseDTO.getData().getRefundInfo().getRefundList().get(0).getRefundStatus()).isNotNull();

    }

}
