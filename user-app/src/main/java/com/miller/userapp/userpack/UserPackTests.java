package com.miller.userapp.userpack;

import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.userpack.flow.UserPackFlow;
import com.miller.userapp.userpack.request.UserPackRequestDTO;
import com.miller.userapp.userpack.response.UserPackResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@DisplayName("首页-自取tab商家列表")
public class UserPackTests {
    @MethodSource("com.miller.userapp.userpack.provider.UserPackDataProvider#provideUserPackData")
    @ParameterizedTest
    @DisplayName("首页-自取tab商家列表")
    void shouldReturnUserPackShopList(UserPackRequestDTO UserPackRequestDTO){
        UserPackResponseDTO UserPackResponseDTO= UserPackFlow.flowUserPack(UserPackRequestDTO);
        System.out.println(UserPackResponseDTO);
//        assertThat(UserPackResponseDTO.getResult().getShopList().size());

    }
}
