package com.miller.demo.login;

import com.miller.demo.login.flow.LoginFlow;
import com.miller.demo.login.flow.LogoutFlow;
import com.miller.demo.login.request.LoginRequestDTO;
import com.miller.demo.login.response.LoginResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.concurrent.TimeUnit;

/**
 * 场景_登入->登出
 *
 * <p>
 * 复杂场景。用例之间需要额外操作，或用例无法组成一个闭环的场景等，那就需要通过FLow层组装成新的测试用例。
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/11/01 20:11:12
 */
@Slf4j
@DisplayName("登陆场景_登入->登出")
public class ComplexLoginThenLogoutScenarioTests {

    @MethodSource("com.miller.demo.login.provider.LoginDataProvider#loginScenarioDataProvider")
    @ParameterizedTest
    @EnvTag.Test
    @DisplayName("登入->登出")
    void loginThenLogout(LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO responseUser =
                LoginFlow.loginReturnJavaObject(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
        // 比如：提取数据给下一个流程使用
        responseUser.getData().getToken();
        // 需要等待另一个用例或组件状态，sql执行, 消息队列等
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            log.error("sleep error", e);
            throw new RuntimeException(e);
        }
        LogoutFlow.shouldLogoutSuccessful();
    }
}
