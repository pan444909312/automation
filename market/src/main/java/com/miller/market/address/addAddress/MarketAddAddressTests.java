package com.miller.market.address.addAddress;

import com.miller.market.address.addAddress.flow.MarketAddAddressFlow;
import com.miller.market.address.addAddress.request.MarketAddAddressRequestDTO;
import com.miller.market.address.addAddress.response.MarketAddAddressResponseDTO;
import com.miller.market.constants.ResponseConstant;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


/**
 * 收货地址
 */
@EnvTag.Test
@TestFramework
@DisplayName("新增收货地址")
public class MarketAddAddressTests {

    @MethodSource("com.miller.market.address.addAddress.provider.MarketAddAddressDataProvider#marketAddressDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_新增收货地址")
    void addAddressSuccessfully(MarketAddAddressRequestDTO marketAddAddressRequestDTO) {
        MarketAddAddressResponseDTO marketAddAddressResponseDTO = MarketAddAddressFlow.addAddress(marketAddAddressRequestDTO);

        Assertions.assertThat(marketAddAddressResponseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(marketAddAddressResponseDTO.getData().getResult()).isTrue();


    }

}
