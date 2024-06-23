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
 * @since 2024/3/29 10:51
 */
@EnvTag.Test
@TestFramework
@DisplayName("用户-地址删除")
public class AddressDeleteTests {

    @MethodSource("com.miller.userapp.address.create.provider.AddressDataProvider#addressDeleteData")
    @ParameterizedTest
    @DisplayName("正常流程_删除收货地址")
    public void shouldDeleteAddressSuccessfully(AddressRequestDTO addressRequestDTO){
        AddressResponseDTO addressResponseDTO = new AddressEditFlow().deleteAddress(addressRequestDTO);

        assert addressResponseDTO.getResultCode() == ResultCode.SUCCESS.getCode();

    }
}
