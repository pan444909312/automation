package com.miller.userapp.module.pf.getHpfConfig;


import com.miller.service.dto.XXLConfigEnvEnum;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.util.XXLConfUtils;
import com.miller.userapp.module.pf.getHpfConfig.flow.GetHpfConfigFlow;
import com.miller.userapp.module.pf.getHpfConfig.response.GetHpfConfigResponseDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@Scenario(scenarioID = "01JQR1GBX3G9XG84P7XMAFST4J", scenarioName = "APP-进入用户首页-检查融合开关关闭"
        , author = "zhangpei@hungrypandagroup.com", developmentTime = 20, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("APP-pf融合开关")
public class GetHpfConfigCloseScenarioTests {
    @BeforeAll
    public static void beforeAll() throws InterruptedException {
        //关闭融合开关
        XXLConfUtils.updateConfig(XXLConfigEnvEnum.TEST.getEnv(), "user-app-server.hpf.switch", "PF融合开关", false);
        Thread.sleep(3000L);
    }

    @Test
    @DisplayName("APP-进入用户首页-检查融合开关关闭")
    public void getHpfConfigTests(){
        GetHpfConfigResponseDTO GetHpfConfigResponseDTO = GetHpfConfigFlow.getHpfConfigFlow();
//        校验融合开关关闭
        assertThat(GetHpfConfigResponseDTO.getResult().getHpfLogic()).isFalse();
        assertThat(GetHpfConfigResponseDTO.getResult().getDefaultShopId()).isNull();
        assertThat(GetHpfConfigResponseDTO.getResult().getPortalId()).isNull();

    }

    @AfterAll
    public static void afterAll() throws InterruptedException {
        //开启融合开关
        XXLConfUtils.updateConfig(XXLConfigEnvEnum.TEST.getEnv(), "user-app-server.hpf.switch", "PF融合开关", true);
    }

}
