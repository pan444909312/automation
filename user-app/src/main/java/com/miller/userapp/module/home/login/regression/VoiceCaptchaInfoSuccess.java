package com.miller.userapp.module.home.login.regression;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.app.server.common.result.Result;
import com.hungrypanda.app.server.dto.user.UserCaptchaDTO;
import com.hungrypanda.app.server.vo.captcha.VoiceCaptchaInfoVO;
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

@Scenario(scenarioID = "01JWWGDV76TB3P1Y294CWT8B3P",
        scenarioName = "获取语音验证码状态成功",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@EnvTag.Test
@DisplayName("/api/app/user/sendVerificationCode")
@Slf4j
public class VoiceCaptchaInfoSuccess {

    /**
     * 接口_获取语音验证码状态接口
     */
    private final String uri = BusinessConstant.DOMAIN + "/api/app/user/voice/captcha/info";

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
    @DisplayName("获取语音验证码状态成功")
    void testCase(UserCaptchaDTO userCaptchaDTO) {

        Result result = HttpUtils.sendPostRequestReturnJavaObject(uri, null,
                RequestUtils.getHeaders(),
                userCaptchaDTO, null,
                Result.class);

        VoiceCaptchaInfoVO voiceCaptchaInfoVO = JSON.parseObject(result.getResult().toString(),VoiceCaptchaInfoVO.class);


        assertThat(result.getResultCode()).isEqualTo(1000);
        assertThat(voiceCaptchaInfoVO.getStatus()).isEqualTo(1);

    }

    /**
     * 测试用例数据提供者
     */
    Stream<Arguments> staticDataProvider() {

        UserCaptchaDTO userCaptchaDTO= new UserCaptchaDTO();

        userCaptchaDTO.setAreaCode("86");
        userCaptchaDTO.setTelephone(tel);

        return Stream.of(Arguments.of(userCaptchaDTO));
    }


}
