package com.miller.userapp.module.home.login.regression;

import com.hungrypanda.app.server.common.result.Result;
import com.hungrypanda.message.api.dto.captcha.CaptchaCheckDTO;
import com.hungrypanda.message.api.dto.captcha.ImageCaptchaCheckDTO;
import com.hungrypanda.message.api.req.SmsSendVerificationCodeReq;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.util.RedisUtils;
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

@Scenario(scenarioID = "01JVKR6DPY3AY792BTB6AE5DD5",
        scenarioName = "获取验证码成功",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 60, maintenanceTime = 0, manualTestTime = 15)
@EnvTag.Test
@DisplayName("/api/app/user/sendVerificationCode")
@Slf4j
public class SendVerificationCodeSuccess {

    /**
     * 接口_获取验证码接口
     */
    private  final String uri = BusinessConstant.DOMAIN + "/api/app/user/sendVerificationCode";

    private static Integer checkCode = 15;

    private String tel = "13999900088";

    private static String captchaToken = "6274196c69aa47b0959fcb2c6dd0a90d";

    @BeforeAll
    void beforeAll() {
        // 获取token
        var headers = new HashMap<String, Object>();
        headers.put("Content-Type", "application/json");
        // 更新全局请求头参数。设置测试用例的默认用户。
        RequestUtils.setHeaders(headers);

        RedisUtils.getRedisInstance().set("message-server:IMG_CAPTCHA:" + captchaToken, checkCode, 6000L);

    }


    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("获取验证码成功")
    void testCase(SmsSendVerificationCodeReq smsSendVerificationCodeReq) {

        Result result = HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(),
                smsSendVerificationCodeReq, null,
                Result.class);

        assertThat(result.getResultCode()).isEqualTo(1000);
        assertThat(result.getReason()).isEqualTo("成功");

    }

    /**
     * 测试用例数据提供者
     */
    Stream<Arguments> staticDataProvider() {
        return Stream.of(Arguments.of(getSmsSendVerificationCodeReq(tel)));
    }

    public SmsSendVerificationCodeReq getSmsSendVerificationCodeReq(String phone) {
        CaptchaCheckDTO captchaCheckInfo = new CaptchaCheckDTO();
        ImageCaptchaCheckDTO imageCheckInfo = new ImageCaptchaCheckDTO();
        imageCheckInfo.setCheckCode(checkCode.toString());
        captchaCheckInfo.setCaptchaType(2);
        captchaCheckInfo.setImageCheckInfo(imageCheckInfo);


        SmsSendVerificationCodeReq smsSendVerificationCodeReq = new SmsSendVerificationCodeReq();
        smsSendVerificationCodeReq.setAreaCode("86");
        smsSendVerificationCodeReq.setPhoneNumber(phone);
        smsSendVerificationCodeReq.setSendType(0);
        smsSendVerificationCodeReq.setScene(102);
        smsSendVerificationCodeReq.setCaptchaCheckInfo(captchaCheckInfo);
        smsSendVerificationCodeReq.setCaptchaToken(captchaToken);
        return smsSendVerificationCodeReq;
    }


}
