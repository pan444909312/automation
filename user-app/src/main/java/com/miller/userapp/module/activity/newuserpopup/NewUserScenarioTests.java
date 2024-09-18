package com.miller.userapp.module.activity.newuserpopup;

import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.module.activity.newuserpopup.flow.NewUserPopupFlow;
import com.miller.userapp.module.activity.newuserpopup.request.NewUserPopupRequestDTO;
import com.miller.userapp.module.activity.newuserpopup.response.NewUserPopupResponseDTO;
import com.miller.userapp.module.data.device.db.DeviceAutoRenewSql;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@Scenario(scenarioID = "01J820W2DA6A0BHJ4F0WQW3BFM", scenarioName = "用户-点捷营销-新人权益-未登录新人弹窗"
        , developmentTime = 30, maintenanceTime = 0, manualTestTime = 0)
public class NewUserScenarioTests {

    static PropertiesUtils propertiesUtils=new PropertiesUtils();
    static String distinctId = propertiesUtils.getProperty(UserLoginFlow.class, "user.app.account.for.shop.card.version2.first.order.user.distinctId");

    @BeforeAll
    static void beforeAll(){
        //        清除设备对应的活动数据
        DeviceAutoRenewSql deviceAutoRenewSql = new DeviceAutoRenewSql();
        deviceAutoRenewSql.deviceAutoRenew(distinctId);
    }
    @MethodSource("setNewUserPopupData")
    @ParameterizedTest
    @DisplayName("新人权益-弹窗")
    void newUserPopupTest(NewUserPopupRequestDTO NewUserPopupRequestdto){
        NewUserPopupResponseDTO NewUserPopupResponsedto = NewUserPopupFlow.flowToNewUserPop(NewUserPopupRequestdto);
        assertThat(NewUserPopupResponsedto.getResult().getType()).isEqualTo(4);
    }

    static Stream<Arguments> setNewUserPopupData(){
        NewUserPopupRequestDTO NewUserPopupRequestdto=new NewUserPopupRequestDTO();
        NewUserPopupRequestdto.setShowModuleAd(0);
        NewUserPopupRequestdto.setShowEntry(0);
        return Stream.of(Arguments.of(NewUserPopupRequestdto));
    }
}
