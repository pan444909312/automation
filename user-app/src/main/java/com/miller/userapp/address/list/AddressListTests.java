package com.miller.userapp.address.list;

import com.hungrypanda.app.server.api.common.ResultCode;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.address.list.flow.AddressListFlow;
import com.miller.userapp.address.list.response.AddressListResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author panjuxiang
 * @since 2024/3/21 19:54
 */
@EnvTag.Test
@TestFramework
@DisplayName("用户-地址")
public class AddressListTests {

    @DisplayName("正常流程_获取所有地址")
    @Test
    public void shouldGetAllAddressSuccessfully() {
        AddressListResponseDTO addressListResponseDTO = new AddressListFlow().getAddressList();
//        System.out.println(addressListResponseDTO);
        assertThat(addressListResponseDTO.getSuccess()).isTrue();
        assert addressListResponseDTO.getResultCode() == ResultCode.SUCCESS.getCode();

    }
}
