package com.miller.deliveryapp.drivercenter.info.provider;

import com.miller.deliveryapp.drivercenter.info.request.DriverInfoRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * 个人信息接口数据提供者
 *
 * @author penglulu
 * @version 1.0
 * @since 2024/04/25 21:10:12
 */
@SuppressWarnings(value = "unused")
public class DriverInfoDataProvider {
    /**
     * 个人信息用例数据提供者
     */
    static Stream<Arguments> driverInfoDataProvider() {

        DriverInfoRequestDTO driverInfoRequestDTO = new DriverInfoRequestDTO();
        return Stream.of(
                arguments(driverInfoRequestDTO)
        );
    }
}
