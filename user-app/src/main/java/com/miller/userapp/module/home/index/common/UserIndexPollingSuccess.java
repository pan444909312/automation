package com.miller.userapp.module.home.index.common;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.app.server.api.req.index.IndexPollingReq;
import com.hungrypanda.app.server.common.result.Result;
import com.hungrypanda.app.server.vo.index.IndexPollingVO;
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

@Scenario(scenarioID = "01JVKR6DPY3AY792BTB6AE5DC8",
        scenarioName = "登陆环信轮询成功",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@EnvTag.Test
@DisplayName("/api/app/user/index/polling")
public class UserIndexPollingSuccess {

    /**
     * 接口_登陆环信轮询
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/app/user/index/polling";

    @BeforeAll
    void beforeAll() {
        UserLoginFlow.loginByDefaultUser();
    }

    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("登陆环信轮询成功")
    void testCase(IndexPollingReq indexPollingReq) {

        Result result = HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfJson(indexPollingReq), null, Result.class);
        IndexPollingVO indexPollingVO = JSON.parseObject(result.getResult().toString(),IndexPollingVO.class);

        assertThat(result.getResultCode()).isEqualTo(1000);
        assertThat(indexPollingVO.getUnreadMsgCount()).isEqualTo(0);
        assertThat(indexPollingVO.getUnreadMsgTip()).isEqualTo("");

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticDataProvider() {
        IndexPollingReq indexPollingReq = new IndexPollingReq();

        indexPollingReq.setLastReceivedTime(System.currentTimeMillis());

        return Stream.of(Arguments.of(indexPollingReq));
    }

}
