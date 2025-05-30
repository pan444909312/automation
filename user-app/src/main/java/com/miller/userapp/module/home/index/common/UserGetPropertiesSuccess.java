package com.miller.userapp.module.home.index.common;

import com.alibaba.fastjson.JSON;
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

import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Scenario(scenarioID = "01JVKR6DPY3AY792BTB6AE5DCM",
        scenarioName = "获取属性配置开关成功",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@EnvTag.Test
@DisplayName("/api/app/user/getProperties")
public class UserGetPropertiesSuccess {

    /**
     * 接口_获取属性配置开关
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/app/user/getProperties";

    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("获取属性配置开关成功")
    void testCase(String str) {

        Result result = HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                str, null, Result.class);
        Map<String, Object> map = JSON.parseObject(result.getResult().toString(), Map.class);

        assertThat(result.getResultCode()).isEqualTo(1000);
        assertThat(map.get("shopCardOptimizationTest")).isEqualTo(true);
        assertThat(map.get("shopCardStyleTest")).isEqualTo(true);

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        String str = "{}";

        return Stream.of(Arguments.of(str));
    }

}
