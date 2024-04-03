package com.miller.pos.login.provider;


import com.miller.pos.login.request.PosLoginRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static com.miller.pos.constants.BusinessConstant.app_key;
import static com.miller.pos.constants.BusinessConstant.app_secret;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * 登录数据提供者
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/14 21:10:12
 */
@SuppressWarnings(value = "unused")
public class PosLoginDataProvider {
    /**
     * 登陆测试用例数据提供者，数据来自于DB
     */
    static Stream<Arguments> loginDataProviderFromDB() {
        PosLoginRequestDTO user1 = new PosLoginRequestDTO();
        user1.setApp_key(app_key);
        user1.setApp_secret(app_secret);
        System.out.println(Stream.of(arguments(user1) ));


        return Stream.of(
                arguments(user1)
        );
    }
}
