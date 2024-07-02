package com.miller.userapp.module.home.login;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hungrypanda.app.server.entity.device.DeviceLoginInfoEntity;
import com.hungrypanda.app.server.entity.user.UserEntity;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.ApplicationPropertiesUtils;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.mapper.device.DeviceLoginInfoMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.mapper.user.UserMapper;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import com.miller.userapp.module.home.login.response.UserLoginResponseDTO;
import com.miller.userapp.util.DBUtils;
import com.miller.userapp.util.RequestUtils;
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
import static org.junit.jupiter.params.provider.Arguments.arguments;


/**
 * 测试用例_用户登录
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/7 20:31:39
 */
//@ApiDoc("http://10.1.6.46:3000/project/60/interface/api/3288")
@EnvTag.Test
@TestFramework
@DisplayName("用户-登录")
public class UserLoginTests {
    private static String token;

    private static UserMapper userMapper;
    private static DeviceLoginInfoMapper deviceLoginInfoMapper;

    @BeforeAll
    static void beforeAll() {
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        userMapper = sqlSession.getMapper(UserMapper.class);
        deviceLoginInfoMapper = sqlSession.getMapper(DeviceLoginInfoMapper.class);
    }

    @AfterAll
    static void afterAll() {
        // 获取token
        var headers = new HashMap<String, Object>();
        headers.put("Content-Type", "application/json");
        headers.put("authorization", token);

        // 更新全局请求头参数。设置测试用例的默认用户。
        RequestUtils.setHeaders(headers);
        assertThat(RequestUtils.getHeaders().get("authorization")).isNotNull();
    }

    @MethodSource("loginDataDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_用户登录")
    void shouldLoginSuccessfully(UserLoginRequestDTO userLoginRequestDTO) {
        UserLoginResponseDTO userLoginResponseDTO = UserLoginFlow.loginReturnBodyObject(userLoginRequestDTO);

        assertThat(userLoginResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(userLoginResponseDTO.getResult().getAccessToken()).isNotNull();
        assertThat(userLoginResponseDTO.getSuccess()).isTrue();
        assertThat(userLoginResponseDTO.getResult().getUserName()).isNotNull().isEqualTo(userLoginRequestDTO.getAccount());

        // 获取token
        token = userLoginResponseDTO.getResult().getAccessToken();
    }

    static Stream<Arguments> loginDataDataProvider() {
        String userId = ApplicationPropertiesUtils.loadProperties().getProperty("user.app.account.of.user002.account.id");
        String passWord = ApplicationPropertiesUtils.loadProperties().getProperty("user.app.account.of.public.password");
        String loginType = ApplicationPropertiesUtils.loadProperties().getProperty("user.app.account.of.public.login.type");

        // 构造查询条件，从数据库查询数据
        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserEntity::getUserId, userId);
        UserEntity userEntity = userMapper.selectOne(queryWrapper);

        // 构造查询条件，从数据库查询数据
        LambdaQueryWrapper<DeviceLoginInfoEntity> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2
                // 查询设备ID不为空的数据
                .ne(DeviceLoginInfoEntity::getDeviceId, "null")
                // 并且 user_id 为指定ID的用户
                .eq(DeviceLoginInfoEntity::getUserId, userId)
                // 取一条数据
                .last("limit 1");
        DeviceLoginInfoEntity deviceLoginInfoEntity = deviceLoginInfoMapper.selectOne(queryWrapper2);

        // 构造请求数据，从数据库查询结果作为请求数据
        UserLoginRequestDTO user = new UserLoginRequestDTO();
        user.setAreaCode(userEntity.getCallingCode());
        user.setAccount(userEntity.getUserTelphone());
        user.setPassword(MD5Util.string2MD5(passWord));
        user.setType(Integer.valueOf(loginType));

        user.setDistinctId(deviceLoginInfoEntity.getDeviceId());

        return Stream.of(arguments(user));
    }

}
