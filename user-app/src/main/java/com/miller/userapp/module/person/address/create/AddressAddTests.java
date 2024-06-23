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
 * 目前各个场景只先做了一个主流程的 添加、编辑、删除，以及仅对code码做了校验
 * todo 优化各个场景的校验规则，添加更多的不同场景case
 * @author panjuxiang
 * @since 2024/3/22 18:19
 */

@EnvTag.Test
@TestFramework
@DisplayName("用户-地址新增")
public class AddressAddTests {


    @MethodSource("com.miller.userapp.address.create.provider.AddressDataProvider#addressAddData")
    @ParameterizedTest
    @DisplayName("正常流程_创建收货地址")
    public void shouldAddAddressSuccessfully(AddressRequestDTO addressRequestDTO){
        AddressResponseDTO addressResponseDTO = new AddressEditFlow().addAddress(addressRequestDTO);

        assert addressResponseDTO.getResultCode() == ResultCode.SUCCESS.getCode();

    }


}
