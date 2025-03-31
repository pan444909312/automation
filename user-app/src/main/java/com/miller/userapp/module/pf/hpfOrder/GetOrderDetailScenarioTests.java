package com.miller.userapp.module.pf.hpfOrder;



import com.miller.service.dto.XXLConfigEnvEnum;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.util.XXLConfUtils;
import com.miller.userapp.module.pf.hpfOrder.flow.GetOrderDetailFlow;
import com.miller.userapp.module.pf.hpfOrder.request.GetOrderDetailRequestDTO;
import com.miller.userapp.module.pf.hpfOrder.response.GetOrderDetailResponseDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@Scenario(scenarioID = "01JQRACP5KVAN1SJ3R3SD05014", scenarioName = "APP-进入用户首页-检查pf融合订单详情：费用项"
        , author = "zhangpei@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("APP-pf融合订单详情")
public class GetOrderDetailScenarioTests {

    static GetOrderDetailRequestDTO requestDTO = new GetOrderDetailRequestDTO();
    @BeforeAll
    public static void beforeAll() throws InterruptedException {
        //开启融合开关
//        XXLConfUtils.updateConfig(XXLConfigEnvEnum.TEST.getEnv(), "user-app-server.hpf.switch", "PF融合开关", true);
//        Thread.sleep(3000L);
    }
    static Stream<Arguments> marketShopDataProvider() {
        requestDTO.setOrderSn("PFZC2504011454542615");
        return Stream.of(
                Arguments.arguments(requestDTO)
        );
    }

    @MethodSource("marketShopDataProvider")
    @ParameterizedTest
    @DisplayName("APP-进入用户首页-检查pf融合订单详情：费用项")
    public void getShopCardInfoTests(GetOrderDetailRequestDTO requestDTO){
        GetOrderDetailResponseDTO responseDTO = GetOrderDetailFlow.getShopCardInfoFlow(requestDTO);
        assertThat(responseDTO.getData().getPriceInfo()).isNotNull();


    }

}
