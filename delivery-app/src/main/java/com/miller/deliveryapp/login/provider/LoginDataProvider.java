package com.miller.deliveryapp.login.provider;

import com.miller.common.util.MD5Util;
import com.miller.deliveryapp.login.request.LoginRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * 登录数据提供者
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/21 21:10:12
 */
@SuppressWarnings(value = "unused")
public class LoginDataProvider {
    /**
     * 登陆测试用例数据提供者，数据来自于DB
     */
    static Stream<Arguments> loginDataProviderFromDB() {
        // TODO 假设这里的数据是从数据库或Redis查询出来的数据。后续会提供数据自动注入，这样就不用自己set数据了。
        LoginRequestDTO user1 = new LoginRequestDTO();
        user1.setAreaCode("86");
        user1.setAccount("18733330001");
        user1.setPassword(MD5Util.string2MD5("Test123456"));

        return Stream.of(
                arguments(user1)
        );
    }
}
