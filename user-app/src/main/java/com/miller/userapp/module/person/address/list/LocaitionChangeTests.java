package com.miller.userapp.module.person.address.list;

import com.hungrypanda.app.server.api.common.ResultCode;
import com.miller.userapp.module.person.address.list.flow.LocationChangeFlow;
import com.miller.userapp.module.person.address.list.request.LocationChangeRequestDTO;
import com.miller.userapp.module.person.address.list.response.LocationChangeResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author panjuxiang
 * @since 2024/6/3 9:35
 */
public class LocaitionChangeTests {

    @DisplayName("正常流程_用户位置变更")
    @MethodSource("com.miller.userapp.address.list.provider.LocationChangeProvider#locationChangeData")
    @ParameterizedTest
    public void locationChangeSuccessfully(LocationChangeRequestDTO locationChangeRequestDTO) {
        LocationChangeResponseDTO locationChangeResponseDTO = new LocationChangeFlow().locationChange(locationChangeRequestDTO);

        assertThat(locationChangeResponseDTO.getSuccess()).isTrue();
        assert locationChangeResponseDTO.getResultCode() == ResultCode.SUCCESS.getCode();

    }
}
