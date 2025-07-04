package com.miller.userapp.module.home.login.regression;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.api.req.user.UserLoginReq;
import com.hungrypanda.app.server.common.result.Result;
import com.hungrypanda.app.server.entity.account.AccountEntity;
import com.hungrypanda.app.server.entity.account.UserAccountEntity;
import com.hungrypanda.app.server.entity.device.DeviceLoginInfoEntity;
import com.hungrypanda.app.server.entity.user.IntegralEntity;
import com.hungrypanda.app.server.entity.user.UserEntity;
import com.hungrypanda.app.server.entity.user.UserLogEntity;
import com.hungrypanda.app.server.entity.user.UserRegInfoEntity;
import com.hungrypanda.app.server.vo.user.UserTokenSimpleVO;
import com.miller.erp.util.EncodeUtils;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.http.HttpUtils;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.mapper.redpacket.UserCdKeyMapper;
import com.miller.userapp.mapper.user.*;
import com.miller.userapp.module.home.login.UserLoginTests;
import com.miller.userapp.util.RedisUtils;
import com.miller.userapp.util.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Scenario(scenarioID = "01JVKR6DPY3AY792BTB6AE5DD8",
        scenarioName = "验证码登录成功",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 60, maintenanceTime = 10, manualTestTime = 15)
@EnvTag.Test
@DisplayName("/api/user/combine/login")
@Slf4j
public class VerifyCodeLoginSuccess {

    /**
     * 接口_登录注册接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/user/combine/login";

    private SqlSession sqlSession = com.miller.userapp.util.DBUtils.getDBOfPandaTest();


    private UserLogMapper userLogMapper = sqlSession.getMapper(UserLogMapper.class);

    private String deviceId = new PropertiesUtils().getProperty(UserLoginTests.class, "user.app.login.device.id");
    private String tel = "13999900001";

    private static Integer checkCode = 15;

    private static String captchaToken = "6274196c69aa47b0959fcb2c6dd0a90d";


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
    @DisplayName("验证码登录成功")
    void testCase(UserLoginReq userLoginReq) {

        // 获取验证码
        SendVerificationCodeSuccess sendVerificationCodeSuccess = new SendVerificationCodeSuccess();
        sendVerificationCodeSuccess.testCase(sendVerificationCodeSuccess.getSmsSendVerificationCodeReq(tel));

        // 构造查询条件
        QueryWrapper<UserLogEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("telephone", EncodeUtils.encodePhone(tel));
        queryWrapper.orderByDesc("create_time");
        queryWrapper.last("limit 1");

        UserLogEntity userLog = userLogMapper.selectOne(queryWrapper);
        userLoginReq.setVerification(String.valueOf(userLog.getVerifycode()));

        Result result = HttpUtils.sendPostRequestReturnJavaObject(uri,
                null,
                RequestUtils.getHeaders(),
                userLoginReq, null,
                Result.class);

        UserTokenSimpleVO userTokenSimpleVO = JSON.parseObject(result.getResult().toString(), UserTokenSimpleVO.class);

        assertThat(result.getResultCode()).isEqualTo(1000);
        assertThat(userTokenSimpleVO.getIsRegister()).isEqualTo(0);
        assertThat(userTokenSimpleVO.getAccessToken()).isNotNull();
        assertThat(userTokenSimpleVO.getRefresh_token()).isNotNull();
        assertThat(userTokenSimpleVO.getUserName()).isEqualTo(tel);

    }

    /**
     * 测试用例数据提供者
     */
    Stream<Arguments> staticDataProvider() {

        UserLoginReq userLoginReq = new UserLoginReq();
        userLoginReq.setAccount(tel);
        userLoginReq.setAreaCode("86");
        userLoginReq.setChannel(0);
        userLoginReq.setDistinctId(deviceId);
        userLoginReq.setType(1);


        return Stream.of(Arguments.of(userLoginReq));
    }


}
