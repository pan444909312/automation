package com.miller.demo.loginv4;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.miller.demo.constants.ResponseConstant;
import com.miller.demo.loginv2.flow.LoginV2Flow;
import com.miller.demo.loginv2.request.LoginV2RequestDTO;
import com.miller.demo.loginv2.response.LoginV2ResponseDTO;
import com.miller.demo.loginv4.mapper.UserMapper;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.PropertiesUtils;
import com.miller.service.framework.db.mybatis.DataSourceConfig;
import com.miller.service.framework.db.mybatis.MyBatisPlusConfig;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * 测试用例_登录
 * <p>
 * 使用 MyBatisPlus 进行数据库操作
 * </p>
 *
 * @author Miller Shan
 * @version 2.0
 * @since 2024/5/29 15:45:12
 */
@EnvTag.Test
@TestFramework
@DisplayName("登录V4-使用MyBatisPlus")
public class LoginV4Tests {
    private static final String mySqlUrl = PropertiesUtils.loadProperties().getProperty("spring.datasource.url");
    private static final String userName = PropertiesUtils.loadProperties().getProperty("spring.datasource.username");
    private static final String passWord = PropertiesUtils.loadProperties().getProperty("spring.datasource.password");
    private static SqlSession sqlSession;
    private static UserMapper userMapper;

    @BeforeAll
    public static void beforeAll() throws IOException {
        MyBatisPlusConfig myBatisPlusConfig = new MyBatisPlusConfig();
        sqlSession = myBatisPlusConfig.getSqlSession(new DataSourceConfig(mySqlUrl, userName, passWord).getDataSource());
        userMapper = sqlSession.getMapper(UserMapper.class);

    }

    @AfterAll
    public static void afterAll() {
        sqlSession.close();
    }

    @BeforeEach
    public void beforeEach() {
    }

    @AfterEach
    public void afterEach() {
    }

    @MethodSource({"loginDataProvider", "loginDataProviderByQueryFilter"})
    @ParameterizedTest
    @DisplayName("Should Login Successfully")
    void shouldLoginSuccessfully(LoginV2RequestDTO loginV2RequestDTO) {
        // Given

        // When. 将响应体映射为 Java Object
        LoginV2ResponseDTO response = LoginV2Flow.loginReturnJavaObject(loginV2RequestDTO.getEmail(), loginV2RequestDTO.getPassword());

        // Then. 通过 Java 对象操作数据，更加符合 OOP 原则，避免通过 JSON Path 解析字符串
        assertThat(response.getCode(), is(ResponseConstant.CODE_SUCCESS));
        assertThat(response.getData().getUser().getEmail(), is(loginV2RequestDTO.getEmail()));  // 断言接口响应字段与数据库字段值一致
    }

    /**
     * 登陆测试用例数据提供者
     */
    private Stream<Arguments> loginDataProvider() {
        // 构造数据之前需要先获取对象
        // userMapper = sqlSession.getMapper(UserMapper.class);

        // 使用 MyBatisPlus 进行数据库的操作,基础的增删改查不需要自己写sql了
        QueryWrapper<LoginV2RequestDTO> queryWrapper = new QueryWrapper<>(); // 构造查询条件
        queryWrapper.isNotNull("email");    // 查询邮箱不为空的用户
        List<LoginV2RequestDTO> userList = userMapper.selectList(queryWrapper); // 使用 MyBatisPlus 进行查询

        // 对密码进行二次处理
        userList.forEach((user) -> user.setPassword(PropertiesUtils.loadProperties().getProperty("demo.user.password.default")));

        return Stream.of(
                arguments(
                        userList.toArray()    // list to array
                )
        );
    }

    /**
     * 使用条件过滤器筛选数据。测试 MyBatisPlus 进行查询
     *
     * @return Stream<Arguments>
     */
    private Stream<Arguments> loginDataProviderByQueryFilter() {
        // 构造数据之前需要先获取对象
        // userMapper = sqlSession.getMapper(UserMapper.class);
        // 使用 MyBatis 进行数据库的操作
        LoginV2RequestDTO queryFilter = new LoginV2RequestDTO();
        // 使用配置文件中的配置获取用户ID
        queryFilter.setUserId(PropertiesUtils.loadProperties().getProperty("demo.user.id.default"));    // 筛选出用户ID为 Miller 的用户
        List<LoginV2RequestDTO> loginV2RequestDTOS = userMapper.selectByCondition(queryFilter);

        // 对密码进行二次处理
        loginV2RequestDTOS.forEach((user) -> user.setPassword(PropertiesUtils.loadProperties().getProperty("demo.user.password.default")));

        return Stream.of(
                arguments(
                        loginV2RequestDTOS.toArray()    // list to array
                )
        );
    }

    /**
     * 这个仅仅是一个测试方法，用于测试 MyBatisPlus 的更新操作
     */
    @Disabled
    @Test
    void testUpdate() {
        // 设置需要更新的条件
        UpdateWrapper<LoginV2RequestDTO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", "Miller");  // 设置条件

        // 方式一: 通过set() 方法设置字段值
        updateWrapper.set("status", "33");
        // 方式二: 通过对象设置字段值
        LoginV2RequestDTO loginV2RequestDTO = new LoginV2RequestDTO();
        loginV2RequestDTO.setStatus("33");

        userMapper.update(loginV2RequestDTO, updateWrapper);
    }

}
