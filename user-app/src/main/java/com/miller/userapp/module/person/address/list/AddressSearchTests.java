package com.miller.userapp.module.person.address.list;

import com.hungrypanda.app.server.api.common.ResultCode;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.module.person.address.list.flow.AddressSearchFlow;
import com.miller.userapp.module.person.address.list.response.AddressSearchResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author panjuxiang
 * @since 2024/5/31 16:34
 */

public class AddressSearchTests {

    @DisplayName("正常流程_搜索地址")
    @Test
    public void shouldSearchAddressSuccessfully() {
        AddressSearchResponseDTO addressSearchResponseDTO = new AddressSearchFlow().searchAddress();

        assertThat(addressSearchResponseDTO.getSuccess()).isTrue();
        assert addressSearchResponseDTO.getResultCode() == ResultCode.SUCCESS.getCode();

    }
}
