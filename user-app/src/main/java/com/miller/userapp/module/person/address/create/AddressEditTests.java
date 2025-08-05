package com.miller.userapp.module.person.address.create;

import com.hungrypanda.app.server.api.common.ResultCode;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.cache.CacheUtils;
import com.miller.service.framework.util.JSONUtils;
import com.miller.userapp.module.person.address.create.flow.AddressEditFlow;
import com.miller.userapp.module.person.address.create.request.AddressRequestDTO;
import com.miller.userapp.module.person.address.create.response.AddressResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * @author panjuxiang
 * @since 2024/3/29 10:33
 */
@EnvTag.Test
@TestFramework
@DisplayName("用户-地址编辑")
public class AddressEditTests {

    @MethodSource("addressEditData")
    @ParameterizedTest
    @DisplayName("正常流程_编辑收货地址")
    public void shouldEditAddressSuccessfully(AddressRequestDTO addressRequestDTO){
        AddressResponseDTO addressResponseDTO = new AddressEditFlow().editAddress(addressRequestDTO);

        assert addressResponseDTO.getResultCode() == ResultCode.SUCCESS.getCode();

    }


    static Stream<Arguments> addressEditData() {

        String str = "{\n" +
                "    \"address\": \"China, Zhejiang, Hangzhou, Xiaoshan District, 盈丰路\",\n" +
                "    \"addressRemark\": \"\",\n" +
                "    \"gender\": 0,\n" +
                "    \"latitude\": \"30.22593\",\n" +
                "    \"postcode\": \"000000\",\n" +
                "    \"telephone\": \"13960000003\",\n" +
                "    \"addTag\": 0,\n" +
                "    \"type\": 2,\n" +
                "    \"addressId\": " + CacheUtils.get("addressId") + ",\n" +
                "    \"buildingName\": \"盈丰路\",\n" +
                "    \"isDefault\": 0,\n" +
                "    \"countryCode\": \"86\",\n" +
                "    \"verify\": 1,\n" +
                "    \"shopId\": 0,\n" +
                "    \"contacts\": \"auto-name-edit\",\n" +
                "    \"houseNum\": \"望京\",\n" +
                "    \"longitude\": \"120.24833\"\n" +
                "}";

        AddressRequestDTO addressRequestDTO = JSONUtils.jsonToObject(str, AddressRequestDTO.class);

        return Stream.of(
                Arguments.of(addressRequestDTO));
    }


}
