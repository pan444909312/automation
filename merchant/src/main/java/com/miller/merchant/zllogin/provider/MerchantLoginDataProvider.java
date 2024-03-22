package com.miller.merchant.zllogin.provider;

import com.miller.common.util.MD5Util;
import com.miller.merchant.zllogin.request.MerchantLoginRequestDTO;
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
public class MerchantLoginDataProvider {
    /**
     * 登陆测试用例数据提供者，数据来自于DB
     */
    static Stream<Arguments> loginDataProviderFromDB() {
        MerchantLoginRequestDTO user1 = new MerchantLoginRequestDTO();
        user1.setAreaCode("86");
        user1.setAccount("664504868");
        user1.setPassword(MD5Util.string2MD5("123qweasd"));

        return Stream.of(
                arguments(user1)
        );
    }
}
