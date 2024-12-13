package com.miller.market.address.updateAddress;

import com.miller.market.address.getAddress.flow.MarketGetAddressFlow;
import com.miller.market.address.getAddress.response.MarketGetAddressResponseDTO;
import com.miller.market.address.updateAddress.flow.MarketUpdateAddressFlow;
import com.miller.market.address.updateAddress.request.MarketUpdateAddressRequestDTO;
import com.miller.market.address.updateAddress.response.MarketUpdateAddressResponseDTO;
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
 * 修改收货地址
 */
@EnvTag.Test
@TestFramework
@DisplayName("PF_修改收货地址")
public class MarketUpdateAddressTests {


    @MethodSource("staticAddressDataProvider")
    @ParameterizedTest
    @DisplayName("PF_正常流程_修改收货地址")
    void addAddressSuccessfully(MarketUpdateAddressRequestDTO marketUpdateAddressRequestDTO) {
        MarketUpdateAddressResponseDTO marketUpdateAddressResponseDTO = MarketUpdateAddressFlow.updateAddress(marketUpdateAddressRequestDTO);

        Assertions.assertThat(marketUpdateAddressResponseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(marketUpdateAddressResponseDTO.getData().getResult()).isTrue();


    }


    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticAddressDataProvider() {
        MarketUpdateAddressRequestDTO requestDTO = new MarketUpdateAddressRequestDTO();
        //通过获取地址列表接口，取一个地址进行修改
        MarketGetAddressResponseDTO marketGetAddressResponseDTO = MarketGetAddressFlow.getAddresses();
        requestDTO.setAddressId(marketGetAddressResponseDTO.getData().getAddressDTOList().get(0).getAddressId());
        requestDTO.setAddressCity("杭州市");
        requestDTO.setAddressProvince("浙江省");
        requestDTO.setAddressDistrict("滨江区");
        requestDTO.setAddressStreet("江陵路");
        requestDTO.setBuildingName("星光大道");
        requestDTO.setContact("修改联系人");
        requestDTO.setCountryCode("86");
        requestDTO.setContactTelephone("17700004444");
        requestDTO.setLocation("中国浙江省杭州市滨江区江陵路1519号 邮政编码: 311200");
        requestDTO.setPostcode("311200");
        requestDTO.setLatitude("30.2037576");
        requestDTO.setLongitude("120.2168624");

        requestDTO.setHouseNumber("门牌号"+ System.currentTimeMillis());

        return Stream.of(Arguments.of(requestDTO));
    }

}
