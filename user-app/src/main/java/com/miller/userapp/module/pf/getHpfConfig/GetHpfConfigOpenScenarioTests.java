package com.miller.userapp.module.pf.getHpfConfig;



import com.miller.service.dto.XXLConfigEnvEnum;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.util.XXLConfUtils;
import com.miller.userapp.module.pf.getHpfConfig.flow.GetHpfConfigFlow;
import com.miller.userapp.module.pf.getHpfConfig.response.GetHpfConfigResponseDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@Scenario(scenarioID = "01JQR10MSZYSGRZCNRX0RY7XZC", scenarioName = "APP-进入用户首页-检查融合开关开启"
        , author = "zhangpei@hungrypandagroup.com", developmentTime = 20, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("APP-pf融合开关")
public class GetHpfConfigOpenScenarioTests {
    @BeforeAll
    public static void beforeAll() throws InterruptedException {
        //开启融合开关
        XXLConfUtils.updateConfig(XXLConfigEnvEnum.TEST.getEnv(), "user-app-server.hpf.switch", "PF融合开关", true);
        Thread.sleep(3000L);
    }

    @Test
    @DisplayName("APP-进入用户首页-检查融合开关开启")
    public void getHpfConfigTests(){
        GetHpfConfigResponseDTO GetHpfConfigResponseDTO = GetHpfConfigFlow.getHpfConfigFlow();
//        校验融合开关开启
        assertThat(GetHpfConfigResponseDTO.getResult().getHpfLogic()).isTrue();
        assertThat(GetHpfConfigResponseDTO.getResult().getDefaultShopId()).isNotNull();
        assertThat(GetHpfConfigResponseDTO.getResult().getPortalId()).isNotNull();

    }

}
