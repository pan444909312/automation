package com.miller.market.address.deleteAddress;

import com.miller.market.address.deleteAddress.flow.MarketDeleteAddressFlow;
import com.miller.market.address.deleteAddress.request.MarketDeleteAddressRequestDTO;
import com.miller.market.address.deleteAddress.response.MarketDeleteAddressResponseDTO;
import com.miller.market.address.getAddress.flow.MarketGetAddressFlow;
import com.miller.market.address.getAddress.response.MarketGetAddressResponseDTO;
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
 * 删除收货地址
 */
@EnvTag.Test
@TestFramework
@DisplayName("PF_删除收货地址")
public class MarketDeleteAddressTests {


    @MethodSource("staticAddressDataProvider")
    @ParameterizedTest
    @DisplayName("PF_正常流程_删除收货地址")
    void addAddressSuccessfully(MarketDeleteAddressRequestDTO requestDTO) {
        MarketDeleteAddressResponseDTO responseDTO = MarketDeleteAddressFlow.deleteAddress(requestDTO);

        Assertions.assertThat(responseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(responseDTO.getData().getResult()).isTrue();


    }


    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticAddressDataProvider() {
        MarketDeleteAddressRequestDTO requestDTO = new MarketDeleteAddressRequestDTO();
        //通过获取地址列表接口，取一个地址进行删除
        MarketGetAddressResponseDTO marketGetAddressResponseDTO = MarketGetAddressFlow.getAddresses();
        requestDTO.setAddressId(marketGetAddressResponseDTO.getData().getAddressDTOList().get(0).getAddressId());


        return Stream.of(Arguments.of(requestDTO));
    }

}
