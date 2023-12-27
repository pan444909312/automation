package com.miller.erp.login.provider;

import com.miller.erp.constants.BusinessConstant;
import com.miller.erp.login.request.LoginRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * 登录数据提供者
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/26 18:10:12
 */
@SuppressWarnings(value = "unused")
public class LoginDataProvider {
    /**
     * 登陆测试用例数据提供者，数据来自于DB
     */
    static Stream<Arguments> loginDataProviderFromDB() {
        LoginRequestDTO user = new LoginRequestDTO();
        user.setUserName(BusinessConstant.USERNAME);
        // ERP 个人账号，不使用明文
        user.setPassword(BusinessConstant.PASSWORD);
        return Stream.of(
                arguments(user)
        );
    }
}
