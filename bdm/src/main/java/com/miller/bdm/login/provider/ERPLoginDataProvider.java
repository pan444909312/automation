package com.miller.bdm.login.provider;

import com.miller.bdm.constants.BusinessConstantOfERP;
import com.miller.bdm.login.request.ERPLoginRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * 数据提供者_登录
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/26 18:10:12
 */
@SuppressWarnings(value = "unused")
public class ERPLoginDataProvider {
    /**
     * 登陆测试用例数据提供者，数据来自于DB
     */
    static Stream<Arguments> loginDataProviderFromDB() {
        ERPLoginRequestDTO user = new ERPLoginRequestDTO();
        user.setUserName(BusinessConstantOfERP.USERNAME);
        // ERP 个人账号，不使用明文
        user.setPassword(BusinessConstantOfERP.PASSWORD);
        return Stream.of(
                arguments(user)
        );
    }
}
