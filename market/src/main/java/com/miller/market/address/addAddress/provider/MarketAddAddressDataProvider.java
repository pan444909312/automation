package com.miller.market.address.addAddress.provider;

import com.miller.market.address.addAddress.request.MarketAddAddressRequestDTO;
import com.miller.market.constants.TestCaseDataForMarketConstant;
import com.miller.market.order.createOrder.request.MarketCreateOrderRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * 数据提供者_收货地址数据
 *
 */
@SuppressWarnings(value = "unused")
public class MarketAddAddressDataProvider {

    static Stream<Arguments> marketAddressDataProvider() {
        MarketAddAddressRequestDTO address1 = new MarketAddAddressRequestDTO();
        // 地址标签：0-无标签，1-家，2-公司，3-学校
        address1.setTagId(1L);
        address1.setContact("接口测试人");
        address1.setLongitude("120.2168624");
        address1.setLatitude("30.2037576");
        address1.setPostcode("311200");
        address1.setBuildingName("建筑名称");
        address1.setContactSex(0);
        address1.setCountryCode("86");
        address1.setAddressStreet("江陵路");
        address1.setAddressDistrict("滨江区");
        address1.setAddressCity("杭州市");
        address1.setAddressProvince("浙江省");
        address1.setContactTelephone("17700004444");
        address1.setHouseNumber("门牌号");
        address1.setLocation("中国浙江省杭州市滨江区江陵路1519号 邮政编码: 311200");
        // 使用默认值，1页10条
        return Stream.of(
                Arguments.arguments(address1)
        );
    }

}
