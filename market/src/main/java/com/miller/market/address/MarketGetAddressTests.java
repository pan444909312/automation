package com.miller.market.address;

import com.miller.market.address.flow.MarketGetAddressFlow;
import com.miller.market.address.response.MarketGetAddressResponseDTO;
import com.miller.market.constants.ResponseConstant;
import com.miller.market.constants.TestCaseDataForMarketConstant;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 收货地址
 */
@EnvTag.Test
@TestFramework
@DisplayName("收货地址列表")
public class MarketGetAddressTests {


    @Test
    @DisplayName("正常流程_获取收货地址列表")
    void getAddressSuccessfully() {
        MarketGetAddressResponseDTO marketGetAddressResponseDTO = MarketGetAddressFlow.getAddresses();

        assertThat(marketGetAddressResponseDTO.getCode()).isEqualTo(ResponseConstant.code);
        assertThat(marketGetAddressResponseDTO.getData()).isNotNull();

        TestCaseDataForMarketConstant.addressId = marketGetAddressResponseDTO.getData().getAddressDTOList().get(0).getAddressId();

    }

}
