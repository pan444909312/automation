package com.miller.market.user.login;

import com.miller.market.constants.BusinessConstant;
import com.miller.market.constants.ResponseConstant;
import com.miller.market.mapper.user.UserMapper;
import com.miller.market.user.login.flow.MarketLoginFlow;
import com.miller.market.user.login.request.MarketLoginRequestDTO;
import com.miller.market.user.login.response.MarketLoginResponseDTO;
import com.miller.market.util.DBUtils;
import com.miller.market.util.RequestUtils;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.Scenario;
import com.miller.service.framework.annotation.TestFramework;
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
 * 中超客户端_登录
 */
@Scenario(scenarioID = "01JA4ZPKGN7P6S2ZP8J8CTQ2KM",
        scenarioName = "【主干场景】发送验证码 - 登录",
        author = "zhangpei@hungrypandagroup.com", developmentTime = 15, maintenanceTime = 0, manualTestTime = 5)
@DisplayName("PF_用户-使用验证码登录")
public class MarketLoginWithCodeTests {
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

    @MethodSource("staticUserDataProvider")
    @ParameterizedTest
    @DisplayName("PF_正常流程_用户验证码登录")
    void shouldLoginSuccessfully(MarketLoginRequestDTO marketLoginRequestDTO) {
        MarketLoginResponseDTO marketLoginResponseDTO = MarketLoginFlow.loginReturnBodyObject(marketLoginRequestDTO);

        assertThat(marketLoginResponseDTO.getCode()).isEqualTo(ResponseConstant.code);
        assertThat(marketLoginResponseDTO.getData().getToken()).isNotNull();
        // 获取token
        token = marketLoginResponseDTO.getData().getToken();
        BusinessConstant.userId = marketLoginResponseDTO.getData().getUser().getUserId();

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticUserDataProvider() {
        MarketLoginRequestDTO user1 = new MarketLoginRequestDTO();
        user1.setAreaCode("86");
        user1.setPhone(BusinessConstant.phone);
        user1.setCode(BusinessConstant.code);
        user1.setRegistrationId("171976fa8b8cf2806d9");
        user1.setDistinctId(BusinessConstant.deviceNumber);

        return Stream.of(
                arguments(user1)
        );
    }
}
