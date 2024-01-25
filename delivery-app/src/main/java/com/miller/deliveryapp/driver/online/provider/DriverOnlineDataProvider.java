package com.miller.deliveryapp.driver.online.provider;

import com.miller.deliveryapp.driver.online.request.DriverOnlineRequestDTO;
import com.panda.delivery.app.server.common.enums.DriverOnOffLineEnum;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * 骑手上线数据提供者
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/21 21:10:12
 */
@SuppressWarnings(value = "unused")
public class DriverOnlineDataProvider {
    /**
     * 骑手上线测试用例数据提供者
     */
    static Stream<Arguments> driverOnlineDataProvider() {
        // TODO 假设这里的数据是从数据库或Redis查询出来的数据。后续会提供数据自动注入，这样就不用自己set数据了。
        DriverOnlineRequestDTO driverOnlineRequestDTO = new DriverOnlineRequestDTO();
        driverOnlineRequestDTO.setIsOnline(Byte.valueOf(String.valueOf(DriverOnOffLineEnum.YES.getType())));

        return Stream.of(
                arguments(driverOnlineRequestDTO)
        );
    }
}
