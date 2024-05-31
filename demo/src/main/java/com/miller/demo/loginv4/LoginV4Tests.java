package com.miller.demo.loginv4;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.miller.demo.constants.ResponseConstant;
import com.miller.demo.loginv2.flow.LoginV2Flow;
import com.miller.demo.loginv2.request.LoginV2RequestDTO;
import com.miller.demo.loginv2.response.LoginV2ResponseDTO;
import com.miller.demo.loginv4.mapper.UserMapper;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.util.ApplicationPropertiesUtils;
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
    private static final String mySqlUrl = ApplicationPropertiesUtils.loadProperties().getProperty("spring.datasource.url");
    private static final String userName = ApplicationPropertiesUtils.loadProperties().getProperty("spring.datasource.username");
    private static final String passWord = ApplicationPropertiesUtils.loadProperties().getProperty("spring.datasource.password");
    private static SqlSession sqlSession;
    private UserMapper userMapper;

    @BeforeAll
    public static void beforeAll() throws IOException {
        MyBatisPlusConfig myBatisPlusConfig = new MyBatisPlusConfig();
        sqlSession = myBatisPlusConfig.getSqlSession(new DataSourceConfig(mySqlUrl, userName, passWord).getDataSource());
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
        // 将修改提交到数据库，不提交则数据库不生效，如果仅仅只是测试 Mapper 接口可以不提交
        sqlSession.commit();
    }


    @MethodSource({"loginDataProvider"})
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
        userMapper = sqlSession.getMapper(UserMapper.class);

        // 使用 MyBatisPlus 进行数据库的操作,基础的增删改查不需要自己写sql了
        QueryWrapper<LoginV2RequestDTO> queryWrapper = new QueryWrapper<>(); // 构造查询条件
        queryWrapper.isNotNull("email");    // 查询邮箱不为空的用户
        List<LoginV2RequestDTO> userList = userMapper.selectList(queryWrapper); // 使用 MyBatisPlus 进行查询

        userList.forEach(user -> System.out.println(user.getEmail()));

        // 对密码进行二次处理
        userList.forEach((user) -> user.setPassword("123456"));

        return Stream.of(
                arguments(
                        userList.toArray()    // list to array
                )
        );
    }

}
