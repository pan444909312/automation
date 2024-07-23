package com.miller.pos.token.provider;


import com.miller.pos.token.request.AccessTokenRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static com.miller.pos.constants.BusinessConstant.app_key;
import static com.miller.pos.constants.BusinessConstant.app_secret;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * 登录数据提供者
 *
 * @author zhangli
 * @version 1.0
 * @since 2024/4/3 11:50:12
 */
@SuppressWarnings(value = "unused")
public class PosLoginDataProvider {
    /**
     * 登陆测试用例数据提供者，数据来自于DB
     */
    static Stream<Arguments> loginDataProviderFromDB() {
        AccessTokenRequestDTO user1 = new AccessTokenRequestDTO();
        user1.setAppKey(app_key);
        user1.setAppSecret(app_secret);
        return Stream.of(
                arguments(user1)
        );
    }
}
