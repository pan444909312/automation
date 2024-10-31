package com.miller.market.address.location;

import com.miller.market.address.location.flow.MarketLocationAddressFlow;
import com.miller.market.address.location.request.MarketLocationAddressRequestDTO;
import com.miller.market.address.location.response.MarketLocationAddressResponseDTO;
import com.miller.market.constants.ResponseConstant;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


/**
 * 地址定位
 */
@EnvTag.Test
@TestFramework
@DisplayName("地址定位")
public class MarketLocationAddressTests {


    @MethodSource("staticAddressDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_地址定位")
    void addAddressSuccessfully(MarketLocationAddressRequestDTO requestDTO) {
        MarketLocationAddressResponseDTO responseDTO = MarketLocationAddressFlow.searchAddress(requestDTO);

        Assertions.assertThat(responseDTO.getCode()).isEqualTo(ResponseConstant.code);

    }


    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticAddressDataProvider() {
        MarketLocationAddressRequestDTO requestDTO = new MarketLocationAddressRequestDTO();
        requestDTO.setLatitude("30.20359992195884");
        requestDTO.setLongitude("120.2172301246771");
        return Stream.of(Arguments.of(requestDTO));
    }

}
