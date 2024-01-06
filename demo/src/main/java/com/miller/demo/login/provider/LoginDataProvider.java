package com.miller.demo.login.provider;

import com.miller.demo.login.request.LoginRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * 数据提供者_登录
 *
 * @author Miller Shan
 * @version 1.0
 * @see <a href="https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests-display-names">Customizing Display Names</a>
 * @since 2023/11/01 12:10:12
 */
@SuppressWarnings(value = "unused")
public class LoginDataProvider {
    /**
     * 登陆测试用例需要的数据
     */
    static Stream<Arguments> loginDataProvider() {
        return Stream.of(
                // 账号，密码，返回code， 返回message
                Arguments.of("", "123456", 401, "未知账号"),
                Arguments.of("miller.shan@aliyun.com", "1234567", 403, "密码错误"),
                Arguments.of("miller.shan@aliyun.com", "123456", 0, "success"));
    }

    /**
     * 登陆测试用例数据提供者，数据来自于DB
     */
    static Stream<Arguments> loginDataProviderFromDB() {
        // TODO 假设这里的数据是从数据库或Redis查询出来的数据。后续会提供数据自动注入，这样就不用自己set数据了。
        LoginRequestDTO millerUserTestCaseDataDTO = new LoginRequestDTO();
        millerUserTestCaseDataDTO.setEmail("miller.shan@aliyun.com");
        millerUserTestCaseDataDTO.setPassword("123456");

        LoginRequestDTO adminUserTestCaseDataDTO = new LoginRequestDTO();
        adminUserTestCaseDataDTO.setEmail("admin@aliyun.com");
        adminUserTestCaseDataDTO.setPassword("123456");

        ArrayList<LoginRequestDTO> users = new ArrayList<>();
        users.add(millerUserTestCaseDataDTO);
        users.add(adminUserTestCaseDataDTO);

        return Stream.of(arguments(millerUserTestCaseDataDTO),
                // 自定义 DisplayName 需要使用 Arguments.arguments
                arguments(named(adminUserTestCaseDataDTO.getEmail(), adminUserTestCaseDataDTO)));
    }

    /**
     * 登陆场景需要的数据
     */
    static Stream<Arguments> loginScenarioDataProvider() {
        LoginRequestDTO adminUserTestCaseDataDTO = new LoginRequestDTO();
        adminUserTestCaseDataDTO.setEmail("admin@aliyun.com");
        adminUserTestCaseDataDTO.setPassword("123456");

        return Stream.of(
                arguments(adminUserTestCaseDataDTO)
        );
    }
}
