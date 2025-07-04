package com.miller.userapp.module.home.login.regression;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hungrypanda.app.server.common.result.Result;
import com.hungrypanda.app.server.entity.user.UserThirdLoginEntity;
import com.hungrypanda.app.server.vo.user.UserTokenSimpleVO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.http.HttpUtils;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.mapper.user.UserThirdLoginMapper;
import com.miller.userapp.module.home.login.UserLoginTests;
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

@Scenario(scenarioID = "01JVKR6DPY3AY792BTB6AE5DD8",
        scenarioName = "用户apple登陆登录成功",
        author = "panjuxiang@hungrypandagroup.com", developmentTime = 45, maintenanceTime = 15, manualTestTime = 15)
@EnvTag.Test
@DisplayName("/api/third/apple/login")
@Slf4j
public class ThirdAppleLoginSuccess {

    /**
     * 接口_登录注册接口
     */
    private static final String uri = BusinessConstant.DOMAIN + "/api/third/apple/login";
    private SqlSession sqlSession = com.miller.userapp.util.DBUtils.getDBOfPandaTest();

    private UserThirdLoginMapper userThirdLoginMapper = sqlSession.getMapper(UserThirdLoginMapper.class);

    private String thirdUserId = "000867.19aa3fa2efdd4cd9a240aa20a08eb27b.0922";

    private String deviceId = new PropertiesUtils().getProperty(UserLoginTests.class, "user.app.login.device.id");


    @BeforeAll
    void beforeAll() {
        var headers = new HashMap<String, Object>();
        headers.put("Content-Type", "application/json");
        // 更新全局请求头参数。设置测试用例的默认用户。
        RequestUtils.setHeaders(headers);
        RequestUtils.getHeaders().remove("cityName");
        RequestUtils.getHeaders().put("uniquetoken", deviceId);

    }


    @MethodSource("staticDataProvider")
    @ParameterizedTest
    @DisplayName("用户apple登陆登录成功")
    void testCase() {
        UserThirdLoginEntity userThirdLoginEntity = userThirdLoginMapper.selectOne
                (new QueryWrapper<UserThirdLoginEntity>()
                        .eq("third_user_id", thirdUserId)
                        .eq("is_deleted",0).last("limit 1"));
        if (userThirdLoginEntity == null){
            userThirdLoginEntity = new UserThirdLoginEntity();
            userThirdLoginEntity.setThirdUserId(thirdUserId);
            userThirdLoginEntity.setUserId(1398717272L);
            userThirdLoginEntity.setType(1);
            userThirdLoginEntity.setIsDeleted(0);
            userThirdLoginEntity.setSite("HP");
            userThirdLoginEntity.setCreateTime(System.currentTimeMillis());
            userThirdLoginEntity.setUpdateTime(System.currentTimeMillis());
            userThirdLoginMapper.insert(userThirdLoginEntity);
        }

        String reqStr = "{\"thirdId\":\"" + thirdUserId + "\",\"type\":2}";

        Result result = HttpUtils.sendPostRequestReturnJavaObject(uri,
                null,
                RequestUtils.getHeaders(),
                reqStr, null,
                Result.class);

        UserTokenSimpleVO userTokenSimpleVO = JSON.parseObject(result.getResult().toString(), UserTokenSimpleVO.class);

        assertThat(result.getResultCode()).isEqualTo(1000);
        assertThat(userTokenSimpleVO.getAccessToken()).isNotNull();
        assertThat(userTokenSimpleVO.getRefresh_token()).isNotNull();

    }

    /**
     * 测试用例数据提供者
     */
    Stream<Arguments> staticDataProvider() {

        return Stream.of(Arguments.of());
    }


}
