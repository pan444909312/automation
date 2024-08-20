package com.miller.market.login;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.miller.market.constants.ResponseConstant;
import com.miller.market.login.flow.MarketLoginFlow;
import com.miller.market.login.request.MarketLoginRequestDTO;
import com.miller.market.login.response.MarketLoginResponseDTO;
import com.miller.market.mapper.user.UserMapper;
import com.miller.market.util.DBUtils;
import com.miller.market.util.RequestUtils;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.panda.market.common.enums.IsDeleteEnum;
import com.panda.market.dal.entity.User;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 中超客户端_登录
 */
@EnvTag.Test
@TestFramework
@DisplayName("用户-登录")
public class MarketLoginTests {
    private static String token;
    private static UserMapper userMapper;
    @BeforeAll
    static void beforeAll() {
        SqlSession sqlSession = DBUtils.getDBOfFreshTest();
        userMapper = sqlSession.getMapper(UserMapper.class);

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

    @MethodSource("com.miller.market.login.provider.MarketLoginDataProvider#loginDataProviderFromDB")
    @ParameterizedTest
    @DisplayName("正常流程_用户登录")
    void shouldLoginSuccessfully(MarketLoginRequestDTO marketLoginRequestDTO) {
        MarketLoginResponseDTO marketLoginResponseDTO = MarketLoginFlow.loginReturnBodyObject(marketLoginRequestDTO);

        assertThat(marketLoginResponseDTO.getCode()).isEqualTo(ResponseConstant.code);
        assertThat(marketLoginResponseDTO.getData().getToken()).isNotNull();
        // 获取token
        token = marketLoginResponseDTO.getData().getToken();
        QueryWrapper<User> userLambdaQueryWrapper = new QueryWrapper<>();
        userLambdaQueryWrapper.eq("user_name", "18968046019");
        User user1 = userMapper.selectOne(userLambdaQueryWrapper);
        System.out.println("-------------hhhhhhh-----------"+user1.getUserName());
    }

}
