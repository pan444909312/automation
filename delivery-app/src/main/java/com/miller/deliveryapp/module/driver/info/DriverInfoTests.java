package com.miller.deliveryapp.module.driver.info;

import com.miller.deliveryapp.constants.ResponseConstant;
import com.miller.deliveryapp.module.driver.info.flow.DriverInfoFlow;
import com.miller.deliveryapp.module.driver.info.request.DriverInfoRequestDTO;
import com.miller.deliveryapp.module.driver.info.response.DriverInfoResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;


/**
 * 测试用例_骑手个人信息获取
 *
 * @author penglulu
 * @version 1.0
 * @since 2024/12/21 20:31:39
 */
// @ApiDoc(value = "http://10.1.6.46:3000/project/60/interface/api/3288")
@EnvTag.Test
@TestFramework
@DisplayName("骑手-个人信息获取")
public class DriverInfoTests {
    @MethodSource("driverInfoDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_个人信息获取")
    void shouldGetDriverInfoSuccessfully(DriverInfoRequestDTO driverInfoRequestDTO) {
        DriverInfoResponseDTO driverInfoResponseDTO = DriverInfoFlow.driverInfoFlow(driverInfoRequestDTO);

        assertThat(driverInfoResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
    }

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

// /Users/admin/Documents/Applications/gitlab/automation/delivery-app/src/main/java/com/miller/deliveryapp/drivercenter/info/provider/DriverInfoDataProvider.java