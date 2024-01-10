package com.miller.merchant.login.provider;

import com.miller.common.util.MD5Util;
import com.miller.merchant.login.request.LoginRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * 登录数据提供者
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/14 21:10:12
 */
@SuppressWarnings(value = "unused")
public class LoginDataProvider {
    /**
     * 登陆测试用例数据提供者，数据来自于DB
     */
    static Stream<Arguments> loginDataProviderFromDB() {
        LoginRequestDTO user1 = new LoginRequestDTO();
        user1.setAreaCode("86");
        user1.setAccount("18722220001");
        user1.setPassword(MD5Util.string2MD5("wJ3X8JHJ4"));

        return Stream.of(
                arguments(user1)
        );
    }
}
