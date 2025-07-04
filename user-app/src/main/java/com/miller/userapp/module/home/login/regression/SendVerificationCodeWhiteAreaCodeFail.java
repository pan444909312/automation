package com.miller.userapp.module.home.login.regression;

import com.hungrypanda.app.server.common.result.Result;
import com.hungrypanda.message.api.req.SmsSendVerificationCodeReq;
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

@Scenario(scenarioID = "01JYJWRY2YSPDAQ90V0NJPGDJG",
        scenarioName = "获取验证码失败-区号不在白名单",
        author = "yancancan@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 5)
@EnvTag.Test
@DisplayName("/api/app/user/sendVerificationCode")
@Slf4j
public class SendVerificationCodeWhiteAreaCodeFail {

    /**
     * 接口_获取验证码接口
     */
    private final String uri = BusinessConstant.DOMAIN + "/api/app/user/sendVerificationCode";

    private Integer checkCode = 15;

    private String tel = "17712341234";

//    private String captchaToken = "6274196c69aa47b0959fcb2c6dd0a90d";

    @BeforeAll
    void beforeAll() {
        // 获取token
        var headers = new HashMap<String, Object>();
        headers.put("Content-Type", "application/json");
        // 更新全局请求头参数。设置测试用例的默认用户。
        RequestUtils.setHeaders(headers);

//        RedisUtils.getRedisInstance().set("message-server:IMG_CAPTCHA:" + captchaToken + 1, checkCode, 6000L);

    }


    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("获取验证码-行为验证失败，继续触发")
    void testCase(SmsSendVerificationCodeReq smsSendVerificationCodeReq) {

        Result result = HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(),
                smsSendVerificationCodeReq, null,
                Result.class);

        assertThat(result.getResultCode()).isEqualTo(2026);
        assertThat(result.getReason()).isEqualTo("非常抱歉，我们暂不支持该国际区号下号码的验证码注册，麻烦您联系客服进行人工处理呢~");

    }

    /**
     * 测试用例数据提供者
     */
    Stream<Arguments> staticDataProvider() {
        SmsSendVerificationCodeReq smsSendVerificationCodeReq = new SmsSendVerificationCodeReq();
        smsSendVerificationCodeReq.setAreaCode("81");
        smsSendVerificationCodeReq.setPhoneNumber(tel);
        smsSendVerificationCodeReq.setSendType(0);
        smsSendVerificationCodeReq.setScene(102);
        return Stream.of(Arguments.of(smsSendVerificationCodeReq));
    }


}
