package com.miller.userapp.module.home.login.regression;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hungrypanda.app.server.entity.device.DeviceLoginInfoEntity;
import com.hungrypanda.app.server.entity.user.UserEntity;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.userapp.mapper.device.DeviceLoginInfoMapper;
import com.miller.userapp.mapper.user.UserMapper;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import com.miller.userapp.module.home.login.response.UserLoginResponseDTO;
import com.miller.userapp.util.DBUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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
@Scenario(scenarioID = "01JYJWRY2YSPDAQ90V0NJPGDJE",
        scenarioName = "用户-登录失败-密码不正确",
        author = "yancancang@hungrypandagroup.com", developmentTime = 20, maintenanceTime = 10, manualTestTime = 1)
@DisplayName("用户-登录失败-密码不正确")
public class PasswordLoginFail {
    private static String token;
    private static UserMapper userMapper;
    private static DeviceLoginInfoMapper deviceLoginInfoMapper;

    @BeforeAll
    static void beforeAll() {
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        userMapper = sqlSession.getMapper(UserMapper.class);
        deviceLoginInfoMapper = sqlSession.getMapper(DeviceLoginInfoMapper.class);
    }

    @MethodSource("loginDataDataProvider")
    @ParameterizedTest
    @DisplayName("用户-登录失败-密码不正确")
    void shouldLoginSuccessfully(UserLoginRequestDTO userLoginRequestDTO) {
        UserLoginResponseDTO userLoginResponseDTO = UserLoginFlow.loginReturnBodyObject(userLoginRequestDTO);
        assertThat(userLoginResponseDTO.getResultCode()).isEqualTo(2010);
        assertThat(userLoginResponseDTO.getError()).isEqualTo("用户或密码错误，请重新输入");

        // 获取token
    }

    /**
     * 测试用例数据
     *
     * @return Stream<UserLoginRequestDTO>
     */
    static Stream<Arguments> loginDataDataProvider() {
        String userId = new PropertiesUtils().getProperty(PasswordLoginFail.class, "user.app.account.of.user002.account.id");
        //这里输入错误密码
        String passWord = "223344";
        String loginType = new PropertiesUtils().getProperty(PasswordLoginFail.class, "user.app.account.of.public.login.type");

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
