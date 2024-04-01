package com.miller.userapp.userpack.provider;

import com.miller.userapp.userpack.request.UserPackRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * 用户自取列表接口参数构造：目前写死的地理位置为滨江
 */
public class UserPackDataProvider {
    public static Stream<Arguments> provideUserPackData(){
        UserPackRequestDTO UserPackRequestDTO=new UserPackRequestDTO();
        UserPackRequestDTO.setIsNeedMarketCategory(0);
        UserPackRequestDTO.setMarketCategoryId(51);
        UserPackRequestDTO.setLatitude("30.20339");
        List<Integer> requestList=new ArrayList<>();
        requestList.add(2);
        UserPackRequestDTO.setFilters(requestList);
        UserPackRequestDTO.setMapRadius(0);
        UserPackRequestDTO.setLongitude("120.216675");
        return Stream.of(Arguments.of(UserPackRequestDTO));
    }
}
