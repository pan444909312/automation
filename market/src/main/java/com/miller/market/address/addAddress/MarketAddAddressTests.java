package com.miller.market.address.addAddress;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.miller.market.address.addAddress.flow.MarketAddAddressFlow;
import com.miller.market.address.addAddress.request.MarketAddAddressRequestDTO;
import com.miller.market.address.addAddress.response.MarketAddAddressResponseDTO;
import com.miller.market.constants.ResponseConstant;
import com.miller.market.mapper.user.AddressMapper;
import com.miller.market.util.DBUtils;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.panda.market.common.enums.IsDeleteEnum;
import com.panda.market.dal.entity.Address;
import org.apache.ibatis.session.SqlSession;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


/**
 * 收货地址
 */
@EnvTag.Test
@TestFramework
@DisplayName("新增收货地址")
public class MarketAddAddressTests {

    static AddressMapper addressMapper ;
    static MarketAddAddressRequestDTO address1 = new MarketAddAddressRequestDTO();

    static Stream<Arguments> marketAddressDataProvider() {
        // 地址标签：0-无标签，1-家，2-公司，3-学校
        address1.setTagId(1L);
        address1.setContact("接口测试人"+System.currentTimeMillis());
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

    @MethodSource("marketAddressDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_新增收货地址")
    void addAddressSuccessfully(MarketAddAddressRequestDTO marketAddAddressRequestDTO) {
        MarketAddAddressResponseDTO marketAddAddressResponseDTO = MarketAddAddressFlow.addAddress(marketAddAddressRequestDTO);

        Assertions.assertThat(marketAddAddressResponseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(marketAddAddressResponseDTO.getData().getResult()).isTrue();



    }


    @AfterAll
    static void afterAll() {
        SqlSession sqlSession = DBUtils.getDBOfFreshTest();
        addressMapper = sqlSession.getMapper(AddressMapper.class);
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("contact",address1.getContact());
        queryWrapper.eq("contact_telephone",address1.getContactTelephone());
        queryWrapper.last("limit 1");
        Address address = addressMapper.selectOne(queryWrapper);

        //删除新增的地址
        new LambdaUpdateChainWrapper<>(addressMapper)
                .set(Address::getIsDel, IsDeleteEnum.DELETE.getCode())
                .eq(Address::getAddressId,address.getAddressId())
                .update();

    }

}
