package com.miller.userapp.module.home.login.regression;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.api.req.user.UserLoginReq;
import com.hungrypanda.app.server.common.result.Result;
import com.hungrypanda.app.server.entity.user.UserLogEntity;
import com.miller.erp.util.EncodeUtils;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.http.HttpUtils;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.module.home.login.UserLoginTests;
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

@Scenario(scenarioID = "01JVKR6DPY3AY792BTB6AE5DD7",
        scenarioName = "验证码输入错误，注册失败",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 30, maintenanceTime = 0, manualTestTime = 15)
@EnvTag.Test
@DisplayName("/api/user/combine/login")
@Slf4j
public class RegisterFailCauseWrongVerification {

    /**
     * 接口_登录注册接口
     */
    private final String uri = BusinessConstant.DOMAIN + "/api/user/combine/login";

    private String deviceId = new PropertiesUtils().getProperty(UserLoginTests.class, "user.app.login.device.id");

    private String tel = "13999900088";

    private Integer checkCode = 15;

    private String captchaToken = "6274196c69aa47b0959fcb2c6dd0a90d";


    @BeforeAll
    void beforeAll() {
        var headers = new HashMap<String, Object>();
        headers.put("Content-Type", "application/json");
        // 更新全局请求头参数。设置测试用例的默认用户。
        RequestUtils.setHeaders(headers);
        RequestUtils.getHeaders().remove("cityName");
        RequestUtils.getHeaders().put("uniquetoken", deviceId);

        RedisUtils.getRedisInstance().set("message-server:IMG_CAPTCHA:" + captchaToken, checkCode, 6000L);

    }


    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("验证码输入错误，注册失败")
    void testCase(UserLoginReq userLoginReq) {


        SendVerificationCodeSuccess sendVerificationCodeSuccess = new SendVerificationCodeSuccess();
        sendVerificationCodeSuccess.testCase(sendVerificationCodeSuccess.getSmsSendVerificationCodeReq(tel));


        String telMask = EncodeUtils.encodePhone(tel);

        QueryWrapper<UserLogEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("telephone", telMask);
        queryWrapper.orderByDesc("create_time");
        queryWrapper.last("limit 1");

        userLoginReq.setVerification("111111");

        Result result = HttpUtils.sendPostRequestReturnJavaObject(uri,
                null,
                RequestUtils.getHeaders(),
                userLoginReq, null,
                Result.class);


        assertThat(result.getResultCode()).isEqualTo(2011);
        assertThat(result.getReason()).isEqualTo("验证码错误，请重新输入");

    }

    /**
     * 测试用例数据提供者
     */
    Stream<Arguments> staticDataProvider() {

        UserLoginReq userLoginReq = new UserLoginReq();
        userLoginReq.setAccount(tel);
        userLoginReq.setAreaCode("86");
        userLoginReq.setChannel(0);
        userLoginReq.setType(1);
        userLoginReq.setCityName("%E6%9D%AD%E5%B7%9E%E5%B8%82");
        userLoginReq.setDistinctId(deviceId);


        return Stream.of(Arguments.of(userLoginReq));
    }


}
