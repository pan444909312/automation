package com.miller.userapp.module.home.login.regression;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.app.server.common.result.Result;
import com.hungrypanda.message.api.req.SmsSendVerificationCodeReq;
import com.hungrypanda.message.api.res.SmsSendVerificationCodeRes;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.util.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Scenario(scenarioID = "01JVKR6DPY3AY792BTB6AE5DD4",
        scenarioName = "获取验证码触发行为验证",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 60, maintenanceTime = 0, manualTestTime = 15)
@EnvTag.Test
@DisplayName("/api/app/user/sendVerificationCode")
@Slf4j
public class SendVerificationCodeTriggerAct {

    /**
     * 接口_获取验证码接口
     */
    private final String uri = BusinessConstant.DOMAIN + "/api/app/user/sendVerificationCode";

    private String tel = "13999900088";

    @BeforeAll
    void beforeAll() {
        // 获取token
        var headers = new HashMap<String, Object>();
        headers.put("Content-Type", "application/json");
        // 更新全局请求头参数。设置测试用例的默认用户。
        RequestUtils.setHeaders(headers);

    }



    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("获取验证码触发行为验证")
    void testCase(SmsSendVerificationCodeReq smsSendVerificationCodeReq) {

        Result result = HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(),
                smsSendVerificationCodeReq, null,
                Result.class);


        SmsSendVerificationCodeRes smsSendVerificationCodeRes = JSON.parseObject(result.getResult().toString(), SmsSendVerificationCodeRes.class);

        assertThat(result.getResultCode()).isEqualTo(10111);
        assertThat(result.getReason()).isEqualTo("触发行为验证");
        assertThat(smsSendVerificationCodeRes.getCaptchaType()).isEqualTo(2);
        assertThat(smsSendVerificationCodeRes.getCaptchaToken()).isNotNull();

    }

    /**
     * 测试用例数据提供者
     */
    Stream<Arguments> staticDataProvider() {

        SmsSendVerificationCodeReq smsSendVerificationCodeReq = new SmsSendVerificationCodeReq();
        smsSendVerificationCodeReq.setAreaCode("86");
        smsSendVerificationCodeReq.setPhoneNumber(tel);
        smsSendVerificationCodeReq.setSendType(0);
        smsSendVerificationCodeReq.setScene(102);
        return Stream.of(Arguments.of(smsSendVerificationCodeReq));
    }


}
