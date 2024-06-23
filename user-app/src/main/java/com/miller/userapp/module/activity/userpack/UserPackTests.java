package com.miller.userapp.module.activity.userpack;

import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.module.activity.userpack.flow.UserPackFlow;
import com.miller.userapp.module.activity.userpack.request.UserPackRequestDTO;
import com.miller.userapp.module.activity.userpack.response.UserPackResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@DisplayName("首页-自取tab商家列表")
public class UserPackTests {
    /**
     * @param UserPackRequestDTO :首页-自取tab商家列表请求入参
     */
    @MethodSource("provideUserPackData")
    @ParameterizedTest
    @DisplayName("首页-自取tab商家列表")
    void shouldReturnUserPackShopList(UserPackRequestDTO UserPackRequestDTO){
        UserPackResponseDTO UserPackResponseDTO= UserPackFlow.flowUserPack(UserPackRequestDTO);
        System.out.println(UserPackResponseDTO);
        assertThat(UserPackResponseDTO.getResult().getShopList().size()).isGreaterThan(0);
    }
    static Stream<Arguments> provideUserPackData(){
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
