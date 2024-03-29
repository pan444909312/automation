package com.miller.userapp.address.create.provider;

import com.miller.service.framework.cache.CacheUtils;
import com.miller.service.framework.util.JSONUtils;
import com.miller.userapp.address.create.request.AddressRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * @author panjuxiang
 * @since 2024/3/22 18:30
 */
@SuppressWarnings("unused")
public class AddressDataProvider {


    static Stream<Arguments> addressAddData() {

        String str = "{\n" +
                "    \"address\": \"China, Zhejiang, Hangzhou, Binjiang District, Feihong Road, 奥体博览中心主体育场\",\n" +
                "    \"addressRemark\": \"\",\n" +
                "    \"gender\": 0,\n" +
                "    \"latitude\": \"30.227698\",\n" +
                "    \"postcode\": \"000000\",\n" +
                "    \"telephone\": \"13960000003\",\n" +
                "    \"addTag\": 0,\n" +
                "    \"type\": 1,\n" +
                "    \"buildingName\": \"奥体博览中心主体育场\",\n" +
                "    \"isDefault\": 0,\n" +
                "    \"countryCode\": \"86\",\n" +
                "    \"verify\": 1,\n" +
                "    \"shopId\": 0,\n" +
                "    \"contacts\": \"auto-name\",\n" +
                "    \"houseNum\": \"1号\",\n" +
                "    \"longitude\": \"120.227514\"\n" +
                "}";

        AddressRequestDTO addressRequestDTO = JSONUtils.jsonToObject(str, AddressRequestDTO.class);

        return Stream.of(
                Arguments.of(addressRequestDTO));
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

    static Stream<Arguments> addressDeleteData() {

//        String str = "{\"contacts\":\"13960000003\",\"gender\":0,\"addressId\":" + CacheUtils.get("addressId") + ",\"telephone\":\"13960000003\",\"type\":\"3\"}";
        String str = "{\"addressId\":" + CacheUtils.get("addressId") + ",\"type\":\"3\"}";

        AddressRequestDTO addressRequestDTO = JSONUtils.jsonToObject(str, AddressRequestDTO.class);

        return Stream.of(
                Arguments.of(addressRequestDTO));
    }

}
