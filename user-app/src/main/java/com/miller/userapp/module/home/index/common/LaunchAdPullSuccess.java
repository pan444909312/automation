package com.miller.userapp.module.home.index.common;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.app.server.api.req.launchAd.PullLaunchAdReq;
import com.hungrypanda.app.server.api.res.launchAd.LaunchAdDetail;
import com.hungrypanda.app.server.api.res.launchAd.PullLaunchAdRes;
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

@Scenario(scenarioID = "01JVKR6DPY3AY792BTB6AE5DCE",
        scenarioName = "启动页广告拉取成功",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@EnvTag.Test
@DisplayName("/api/user/v1/launchAd/pull")
public class LaunchAdPullSuccess {

    /**
     * 接口_启动页广告
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/user/v1/launchAd/pull";

    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("启动页广告拉取成功")
    void testCase(PullLaunchAdReq pullLaunchAdReq ) {

        Result result = HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(pullLaunchAdReq), null, Result.class);
        PullLaunchAdRes pullLaunchAdRes = JSON.parseObject(result.getResult().toString(),PullLaunchAdRes.class);

        LaunchAdDetail launchAdDetail = pullLaunchAdRes.getLaunchAdList().stream().filter(item -> item.getLaId().equals(232L)).findFirst().orElse(null);

        assertThat(result.getResultCode()).isEqualTo(1000);
        assertThat(launchAdDetail).isNotNull();

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        PullLaunchAdReq pullLaunchAdReq = new PullLaunchAdReq();
        pullLaunchAdReq.setCityName("九江市");
        pullLaunchAdReq.setLastUpdateTime(0L);


        return Stream.of(Arguments.of(pullLaunchAdReq));
    }

}
