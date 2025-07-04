package com.miller.userapp.module.home.login.regression;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.common.enums.EntryInfoEnum;
import com.hungrypanda.app.server.common.result.Result;
import com.hungrypanda.app.server.dto.user.UserUnregisterDTO;
import com.hungrypanda.app.server.entity.user.UserLogEntity;
import com.hungrypanda.app.server.vo.user.UnRegisterVO;
import com.miller.common.util.MD5Util;
import com.miller.erp.util.EncodeUtils;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.http.HttpUtils;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.mapper.user.*;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import com.miller.userapp.util.RedisUtils;
import com.miller.userapp.util.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Scenario(scenarioID = "01JVKR6DPY3AY792BTB6AE5DDB",
        scenarioName = "注销成功",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 45, maintenanceTime = 0, manualTestTime = 10)
@EnvTag.Test
@DisplayName("/api/app/user/unregister")
@Slf4j
public class UnregisterSuccess {

    /**
     * 接口_注销接口
     */
    private final String uri = BusinessConstant.DOMAIN + "/api/app/user/unregister";

    private SqlSession sqlSession = com.miller.userapp.util.DBUtils.getDBOfPandaTest();

    private UserLogMapper userLogMapper = sqlSession.getMapper(UserLogMapper.class);

    private String tel = new PropertiesUtils().getProperty(this.getClass(), "user.app.account.for.shop.card.version2.new.user03.account");

    private Integer checkCode = 15;

    private String captchaToken = "6274196c69aa47b0959fcb2c6dd0a90d";


    @BeforeAll
    void beforeAll() {
        UserLoginRequestDTO userLoginRequestDTO = new UserLoginRequestDTO();
        userLoginRequestDTO.setAccount(tel);
        userLoginRequestDTO.setPassword(MD5Util.string2MD5(new PropertiesUtils().getProperty(this.getClass(), "user.app.account.for.shop.card.version2.new.user03.password")));
        userLoginRequestDTO.setDistinctId(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.distinctId"));
        userLoginRequestDTO.setType(Integer.valueOf(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.public.login.type")));
        userLoginRequestDTO.setAreaCode(new PropertiesUtils().getProperty(UserLoginFlow.class, "user.app.account.of.user002.account.callingCode"));
        UserLoginFlow.loginAndPutToken(userLoginRequestDTO);
        RequestUtils.getHeaders().remove("cityName");

        RedisUtils.getRedisInstance().set("message-server:IMG_CAPTCHA:" + captchaToken, checkCode, 6000L);


    }


    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("注销成功")
    void testCase(UserUnregisterDTO userUnregisterDTO) {

        SendVerificationCodeSuccess sendVerificationCodeSuccess = new SendVerificationCodeSuccess();
        sendVerificationCodeSuccess.testCase(sendVerificationCodeSuccess.getSmsSendVerificationCodeReq(tel,107));

        String telMask = EncodeUtils.encodePhone(tel);

        QueryWrapper<UserLogEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("telephone", telMask);
        queryWrapper.orderByDesc("create_time");
        queryWrapper.last("limit 1");

        UserLogEntity userLog = userLogMapper.selectOne(queryWrapper);
        userUnregisterDTO.setCaptcha(String.valueOf(userLog.getVerifycode()));

        Result result = HttpUtils.sendPostRequestReturnJavaObject(uri,
                null,
                RequestUtils.getHeaders(),
                userUnregisterDTO, null,
                Result.class);


        UnRegisterVO userRegisterVO = JSON.parseObject(result.getResult().toString(), UnRegisterVO.class);

        assertThat(result.getResultCode()).isEqualTo(1000);
        assertThat(userRegisterVO.getMessage()).isEqualTo(EntryInfoEnum.UNREGISTER_SUCCESS.getRemark());

    }

    /**
     * 测试用例数据提供者
     */
    Stream<Arguments> staticDataProvider() {

        UserUnregisterDTO userUnregisterDTO = new UserUnregisterDTO();
        userUnregisterDTO.setTelephone(tel);

        return Stream.of(Arguments.of(userUnregisterDTO));
    }


}
