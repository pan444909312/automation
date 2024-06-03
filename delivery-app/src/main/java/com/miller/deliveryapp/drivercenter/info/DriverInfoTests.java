package com.miller.deliveryapp.drivercenter.info;

import com.miller.deliveryapp.constants.ResponseConstant;
import com.miller.deliveryapp.drivercenter.info.flow.DriverInfoFlow;
import com.miller.deliveryapp.drivercenter.info.request.DriverInfoRequestDTO;
import com.miller.deliveryapp.drivercenter.info.response.DriverInfoResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;


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
    @MethodSource("com.miller.deliveryapp.drivercenter.info.provider.DriverInfoDataProvider#driverInfoDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_个人信息获取")
    void shouldGetDriverInfoSuccessfully(DriverInfoRequestDTO driverInfoRequestDTO) {
        DriverInfoResponseDTO driverInfoResponseDTO = DriverInfoFlow.driverInfoFlow(driverInfoRequestDTO);

        assertThat(driverInfoResponseDTO.getResultCode()).isEqualTo(ResponseConstant.resultCode);
    }

}

// /Users/admin/Documents/Applications/gitlab/automation/delivery-app/src/main/java/com/miller/deliveryapp/drivercenter/info/provider/DriverInfoDataProvider.java