package com.miller.demo.loginv3;

import com.miller.demo.constants.ResponseConstant;
import com.miller.demo.dto.external.User;
import com.miller.demo.loginv2.flow.LoginV2Flow;
import com.miller.demo.loginv2.request.LoginV2RequestDTO;
import com.miller.demo.loginv2.response.LoginV2ResponseDTO;
import com.miller.demo.loginv3.mapper.UserMapper;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * 测试用例_登录
 * <p>
 * 使用 MyBatis 进行数据库操作
 * </p>
 *
 * @author Miller Shan
 * @version 2.0
 * @since 2024/5/27 15:10:12
 */
@EnvTag.Test
@TestFramework
@DisplayName("登录V3-使用MyBatis")
public class LoginV3Tests {
    private static SqlSession sqlSession;
    private UserMapper userMapper;

    @BeforeAll
    public static void beforeAll() throws IOException {
        // 从 mybatis-config-test.xml 读取 MyBatis 配置，在配置文件中指定数据库环境
        Reader reader = Resources.getResourceAsReader("mybatis-config-test.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        sqlSession = sqlSessionFactory.openSession();
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


    @MethodSource({"loginDataProvider", "loginDataProviderByQueryFilter"})  // 使用两组数据提供者作为输入源
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
        // 使用 MyBatis 进行数据库的操作
        List<LoginV2RequestDTO> userList = userMapper.getUserList();
        // 对密码进行二次处理
        userList.forEach((user) -> user.setPassword("123456"));

        return Stream.of(
                arguments(
                        userList.toArray()    // list to array
                )
        );
    }

    /**
     * 使用条件过滤器筛选数据
     *
     * @return Stream<Arguments>
     */
    private Stream<Arguments> loginDataProviderByQueryFilter() {
        // 构造数据之前需要先获取对象
        userMapper = sqlSession.getMapper(UserMapper.class);
        // 使用 MyBatis 进行数据库的操作
        LoginV2RequestDTO queryFilter = new LoginV2RequestDTO();
        queryFilter.setUserId("Miller");    // 筛选出用户ID为 Miller 的用户
        List<LoginV2RequestDTO> loginV2RequestDTOS = userMapper.selectByCondition(queryFilter);

        // 对密码进行二次处理
        loginV2RequestDTOS.forEach((user) -> user.setPassword("123456"));

        return Stream.of(
                arguments(
                        loginV2RequestDTOS.toArray()    // list to array
                )
        );
    }
}
