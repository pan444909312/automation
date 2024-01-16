package com.miller.userapp.login.provider;

import com.miller.common.util.MD5Util;
import com.miller.userapp.login.request.UserLoginRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * 数据提供者_登录
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/07 21:10:12
 */
@SuppressWarnings(value = "unused")
public class UserLoginDataProvider {
    /**
     * 登陆测试用例数据提供者，数据来自于DB
     */
    static Stream<Arguments> loginData() {
        // TODO 假设这里的数据是从数据库或Redis查询出来的数据。后续会提供数据自动注入，这样就不用自己set数据了。
        UserLoginRequestDTO user1 = new UserLoginRequestDTO();
        user1.setAreaCode("86");
        user1.setAccount("18711110002");
        user1.setPassword(MD5Util.string2MD5("Test123456"));
        user1.setCityName("九江市");
        user1.setType(2);
        user1.setDistinctId("0721CD44-5090-42F5-A0B1-8D2F29B85BF5");

        return Stream.of(
                arguments(user1)
        );
    }
}
