package com.miller.userapp.module.home.index.common;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.app.server.api.req.message.AppInsideMsgPushReq;
import com.hungrypanda.app.server.api.res.message.AppInsideMsgListVO;
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

@Scenario(scenarioID = "01JVKR6DPY3AY792BTB6AE5DCD",
        scenarioName = "消息环信轮询成功",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@EnvTag.Test
@DisplayName("/api/app/user/message/polling")
public class UserMessagePollingSuccess {

    /**
     * 接口_消息环信轮询
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/app/user/message/polling";

    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("消息环信轮询成功")
    void testCase(AppInsideMsgPushReq appInsideMsgPushReq) {

        Result result = HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(appInsideMsgPushReq), null, Result.class);
        AppInsideMsgListVO appInsideMsgListVO = JSON.parseObject(result.getResult().toString(), AppInsideMsgListVO.class);

        assertThat(result.getResultCode()).isEqualTo(1000);
        assertThat(appInsideMsgListVO.getMsgList()).isNotNull();

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        AppInsideMsgPushReq appInsideMsgPushReq = new AppInsideMsgPushReq();
        appInsideMsgPushReq.setLastTime(0L);

        return Stream.of(Arguments.of(appInsideMsgPushReq));
    }

}
