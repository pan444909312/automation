package com.miller.userapp.module.person.address.create;

import com.hungrypanda.app.server.api.common.ResultCode;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.cache.CacheUtils;
import com.miller.service.framework.util.JSONUtils;
import com.miller.userapp.module.person.address.create.flow.AddressEditFlow;
import com.miller.userapp.module.person.address.create.request.AddressRequestDTO;
import com.miller.userapp.module.person.address.create.response.AddressResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * @author panjuxiang
 * @since 2024/3/29 10:51
 */
@EnvTag.Test
@TestFramework
@DisplayName("用户-地址删除")
public class AddressDeleteTests {

    @MethodSource("addressDeleteData")
    @ParameterizedTest
    @DisplayName("正常流程_删除收货地址")
    public void shouldDeleteAddressSuccessfully(AddressRequestDTO addressRequestDTO){
        AddressResponseDTO addressResponseDTO = new AddressEditFlow().deleteAddress(addressRequestDTO);

        assert addressResponseDTO.getResultCode() == ResultCode.SUCCESS.getCode();

    }
    static Stream<Arguments> addressDeleteData() {

//        String str = "{\"contacts\":\"13960000003\",\"gender\":0,\"addressId\":" + CacheUtils.get("addressId") + ",\"telephone\":\"13960000003\",\"type\":\"3\"}";
        String str = "{\"addressId\":" + CacheUtils.get("addressId") + ",\"type\":\"3\"}";

        AddressRequestDTO addressRequestDTO = JSONUtils.jsonToObject(str, AddressRequestDTO.class);

        return Stream.of(
                Arguments.of(addressRequestDTO));
    }
}
