package com.miller.market.address.search;

import com.miller.market.address.getAddress.flow.MarketGetAddressFlow;
import com.miller.market.address.getAddress.response.MarketGetAddressResponseDTO;
import com.miller.market.address.search.flow.MarketSearchAddressFlow;
import com.miller.market.address.search.request.MarketSearchAddressRequestDTO;
import com.miller.market.address.search.response.MarketSearchAddressResponseDTO;
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
 * 地址搜索
 */
@EnvTag.Test
@TestFramework
@DisplayName("搜索地址")
public class MarketSearchAddressTests {


    @MethodSource("staticAddressDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_搜索地址")
    void addAddressSuccessfully(MarketSearchAddressRequestDTO requestDTO) {
        MarketSearchAddressResponseDTO responseDTO = MarketSearchAddressFlow.searchAddress(requestDTO);

        Assertions.assertThat(responseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(responseDTO.getData().getResult().get(0).getAddressSpecific()).contains(requestDTO.getAddCode());

    }


    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticAddressDataProvider() {
        MarketSearchAddressRequestDTO requestDTO = new MarketSearchAddressRequestDTO();
        requestDTO.setAddCode("江陵路");
        return Stream.of(Arguments.of(requestDTO));
    }

}
