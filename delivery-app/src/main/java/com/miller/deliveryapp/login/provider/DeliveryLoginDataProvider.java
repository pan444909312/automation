package com.miller.deliveryapp.login.provider;

import com.miller.common.util.MD5Util;
import com.miller.deliveryapp.login.request.DeliveryLoginRequestDTO;
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
public class DeliveryLoginDataProvider {
    /**
     * 登陆测试用例数据提供者，数据来自于DB
     */
    static Stream<Arguments> loginDataProvider() {
        DeliveryLoginRequestDTO user1 = new DeliveryLoginRequestDTO();
        user1.setAreaCode("86");
        user1.setAccount("13251016325");
        user1.setPassword(MD5Util.string2MD5("Test1234"));

        return Stream.of(
                arguments(user1)
        );
    }
}
