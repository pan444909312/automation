package com.miller.userapp.address.list;

import com.hungrypanda.app.server.api.common.ResultCode;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.address.list.flow.AddressSearchConfirmFlow;
import com.miller.userapp.address.list.response.AddressSearchConfirmResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author panjuxiang
 * @since 2024/5/31 17:26
 */
@EnvTag.Test
@TestFramework
@DisplayName("用户-搜索地址确认")
public class AddressSearchConfirmTests {

    @DisplayName("正常流程_搜索地址确认")
    @Test
    public void shouldSearchAddressConfirmSuccessfully() {
        AddressSearchConfirmResponseDTO addressSearchConfirmResponseDTO = new AddressSearchConfirmFlow().searchAddress();

        assertThat(addressSearchConfirmResponseDTO.getSuccess()).isTrue();
        assert addressSearchConfirmResponseDTO.getResultCode() == ResultCode.SUCCESS.getCode();

    }
}
