package com.miller.deliveryapp.driver.online;

import com.miller.deliveryapp.constants.ResponseConstant;
import com.miller.deliveryapp.driver.online.flow.DriverOnlineFlow;
import com.miller.deliveryapp.driver.online.request.DriverOnlineRequestDTO;
import com.miller.deliveryapp.driver.online.response.DriverOnlineResponseDTO;
import com.miller.deliveryapp.util.RequestUtils;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 骑手上线测试用例
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2023/12/21 20:31:39
 */
// @ApiDoc(value = "http://10.1.6.46:3000/project/60/interface/api/3288")
@EnvTag.Test
@TestFramework
@DisplayName("骑手-上线")
public class DriverOnlineTests {
    private static String token;

    @MethodSource("com.miller.deliveryapp.driver.online.provider.DriverOnlineDataProvider#driverOnlineDataProvider")
    @ParameterizedTest
    @DisplayName("骑手上线-正常流程")
    void shouldOnlineSuccessfully(DriverOnlineRequestDTO loginRequestDTO) {
        DriverOnlineResponseDTO driverOnlineResponseDTO = DriverOnlineFlow.driverOnline(loginRequestDTO);

        assertThat(driverOnlineResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
    }

}
