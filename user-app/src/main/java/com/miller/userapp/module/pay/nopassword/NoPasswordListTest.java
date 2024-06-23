package com.miller.userapp.module.pay.nopassword;

import com.alibaba.fastjson.JSON;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.module.pay.nopassword.flow.NoPasswordListFlow;
import com.miller.userapp.module.pay.nopassword.response.NoPasswordListResponseDTO;
import com.miller.userapp.module.pay.nopassword.request.NoPasswordListRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@DisplayName("免密支付方式列表")
public class NoPasswordListTest {

    @MethodSource("noPasswordListProvider")
    @ParameterizedTest
    @DisplayName("正常流程_免密支付方式列表")
    void noPasswordList(NoPasswordListRequestDTO requestDTO) {
        System.out.println(JSON.toJSON(requestDTO));
        NoPasswordListResponseDTO result = NoPasswordListFlow.noPasswordList(requestDTO);
//        System.out.println(JSON.toJSON(result));
        assertThat(result.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(result.getResult().getNoPasswordList().isEmpty()).isEqualTo(false);
    }
    static Stream<Arguments> noPasswordListProvider(){
        NoPasswordListRequestDTO requestDTO = new NoPasswordListRequestDTO();
        return Stream.of(
                Arguments.of(requestDTO)
        );
    }
}
