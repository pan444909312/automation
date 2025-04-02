package com.miller.userapp.module.pf.getHpfConfig;


import com.miller.service.dto.XXLConfigEnvEnum;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.util.XXLConfUtils;
import com.miller.userapp.module.pf.getHpfConfig.flow.GetHpfConfigPFAppFlow;
import com.miller.userapp.module.pf.getHpfConfig.response.GetHpfConfigResponseDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@Scenario(scenarioID = "01JQR2V8EC7JY65703JTYQNHY5", scenarioName = "APP-进入用户首页-融合开关关闭PFapp依旧返回达达店铺id"
        , author = "zhangpei@hungrypandagroup.com", developmentTime = 20, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("APP-pf融合开关")
public class GetHpfConfigClosePFAppScenarioTests {
    @BeforeAll
    public static void beforeAll() throws InterruptedException {
        //关闭融合开关
        XXLConfUtils.updateConfig(XXLConfigEnvEnum.TEST.getEnv(), "user-app-server.hpf.switch", "PF融合开关", false);
        Thread.sleep(3000L);
    }

    @Test
    @DisplayName("APP-进入用户首页-融合开关关闭PFapp依旧返回达达店铺id")
    public void getHpfConfigTests(){
        GetHpfConfigResponseDTO GetHpfConfigResponseDTO = GetHpfConfigPFAppFlow.getHpfConfigFlow();
//        校验融合开关关闭
        assertThat(GetHpfConfigResponseDTO.getResult().getHpfLogic()).isFalse();
        assertThat(GetHpfConfigResponseDTO.getResult().getDefaultShopId()).isNotNull();
        assertThat(GetHpfConfigResponseDTO.getResult().getPortalId()).isNull();

    }

    @AfterAll
    public static void afterAll() throws InterruptedException {
        //开启融合开关
        XXLConfUtils.updateConfig(XXLConfigEnvEnum.TEST.getEnv(), "user-app-server.hpf.switch", "PF融合开关", true);
    }

}
