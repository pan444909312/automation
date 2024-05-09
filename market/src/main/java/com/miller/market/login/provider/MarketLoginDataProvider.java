package com.miller.market.login.provider;

import com.miller.market.constants.BusinessConstant;
import com.miller.market.login.request.MarketLoginRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * 登录数据提供者
 *
 */
@SuppressWarnings(value = "unused")
public class MarketLoginDataProvider {
    /**
     * 登陆测试用例数据提供者，数据来自于DB
     */
    static Stream<Arguments> loginDataProviderFromDB() {
        MarketLoginRequestDTO user1 = new MarketLoginRequestDTO();
        user1.setAreaCode("86");
        user1.setPhone("18968046019");
        user1.setCode("888888");
        user1.setRegistrationId("171976fa8b8cf2806d9");
        user1.setDistinctId(BusinessConstant.deviceNumber);

        return Stream.of(
                arguments(user1)
        );
    }
}
