package com.miller.userapp.module.home.index.common;

import com.hungrypanda.app.server.api.req.user.DeviceReq;
import com.hungrypanda.app.server.common.result.Result;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.util.RequestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Scenario(scenarioID = "01JVKR6DPY3AY792BTB6AE5DCF",
        scenarioName = "绑定用户设备关系成功",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@EnvTag.Test
@DisplayName("/api/app/user/v1/device/save")
public class DeviceSaveSuccess {

    /**
     * 接口_绑定用户设备关系
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/app/user/v1/device/save";

    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("绑定用户设备关系成功")
    void testCase(DeviceReq deviceReq) {

        Result result = HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(deviceReq), null, Result.class);


        assertThat(result.getResultCode()).isEqualTo(1000);

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        DeviceReq deviceReq = new DeviceReq();

        deviceReq.setPushState(1);
        deviceReq.setSts(0);
        deviceReq.setDistinctId("test-distinct-id");
        deviceReq.setRegistrationId("test");

        return Stream.of(Arguments.of(deviceReq));
    }

}
