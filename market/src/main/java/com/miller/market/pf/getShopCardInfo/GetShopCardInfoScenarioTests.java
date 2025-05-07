package com.miller.market.pf.getShopCardInfo;



import com.miller.market.pf.getShopCardInfo.request.GetShopCardInfoRequestDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.market.pf.getShopCardInfo.flow.GetShopCardInfoFlow;
import com.miller.market.pf.getShopCardInfo.response.GetShopCardInfoResponseDTO;
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
@Scenario(scenarioID = "01JQR7TBKSTSSE8RJB1XC7SP6N", scenarioName = "APP-进入用户首页-检查pf融合商卡接口返回:必有字段检查"
        , author = "zhangpei@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@DisplayName("APP-pf融合商卡接口")
public class GetShopCardInfoScenarioTests {

    static GetShopCardInfoRequestDTO requestDTO = new GetShopCardInfoRequestDTO();
    @BeforeAll
    public static void beforeAll() throws InterruptedException {
        //开启融合开关
//        XXLConfUtils.updateConfig(XXLConfigEnvEnum.TEST.getEnv(), "user-app-server.hpf.switch", "PF融合开关", true);
//        Thread.sleep(3000L);
    }
    static Stream<Arguments> marketShopDataProvider() {
        requestDTO.setCityName("杭州市");
        requestDTO.setLatitude("30.20109");
        requestDTO.setLongitude("120.22150");
        requestDTO.setPortalId(3L);
        requestDTO.setRegionId(3L);
        requestDTO.setUcUserId(249222L);
        List<Long> getShopIdList = new ArrayList<>();
        getShopIdList.add(1314L);
        requestDTO.setShopIdList(getShopIdList);
        return Stream.of(
                Arguments.arguments(requestDTO)
        );
    }

    @MethodSource("marketShopDataProvider")
    @ParameterizedTest
    @DisplayName("APP-进入用户首页-检查pf融合商卡接口返回：必有字段检查")
    public void getShopCardInfoTests(GetShopCardInfoRequestDTO requestDTO){
        GetShopCardInfoResponseDTO responseDTO = GetShopCardInfoFlow.getShopCardInfoFlow(requestDTO);
        assertThat(responseDTO.getData().getDataList()).isNotNull();
        assertThat(responseDTO.getData().getDataList().get(0).getShopId()).isEqualTo(requestDTO.getShopIdList().get(0));
        //融合商卡固定4.9分
        assertThat(responseDTO.getData().getDataList().get(0).getPraiseAverage()).isEqualTo("4.9");
        //pf后台配置必有字段检查（logo、品类、店铺名称）
        assertThat(responseDTO.getData().getDataList().get(0).getShopLogo()).isNotEmpty();
        assertThat(responseDTO.getData().getDataList().get(0).getShopName()).isNotEmpty();
        assertThat(responseDTO.getData().getDataList().get(0).getMerchantCategoryName()).isNotEmpty();


    }

}
