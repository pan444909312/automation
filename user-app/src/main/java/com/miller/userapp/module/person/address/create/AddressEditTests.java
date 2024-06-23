package com.miller.userapp.module.person.address.create;

import com.hungrypanda.app.server.api.common.ResultCode;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.module.person.address.create.flow.AddressEditFlow;
import com.miller.userapp.module.person.address.create.request.AddressRequestDTO;
import com.miller.userapp.module.person.address.create.response.AddressResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author panjuxiang
 * @since 2024/3/29 10:33
 */
@EnvTag.Test
@TestFramework
@DisplayName("用户-地址编辑")
public class AddressEditTests {

    @MethodSource("com.miller.userapp.address.create.provider.AddressDataProvider#addressEditData")
    @ParameterizedTest
    @DisplayName("正常流程_编辑收货地址")
    public void shouldEditAddressSuccessfully(AddressRequestDTO addressRequestDTO){
        AddressResponseDTO addressResponseDTO = new AddressEditFlow().editAddress(addressRequestDTO);

        assert addressResponseDTO.getResultCode() == ResultCode.SUCCESS.getCode();

    }

}
