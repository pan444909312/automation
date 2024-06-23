package com.miller.userapp.module.pay.nopassword;

import com.hungrypanda.app.server.api.res.payment.NoPasswordDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.module.pay.nopassword.response.NoPasswordListResponseDTO;
import com.miller.userapp.module.pay.nopassword.flow.NoPasswordListFlow;
import com.miller.userapp.module.pay.nopassword.flow.NoPasswordStatusFlow;
import com.miller.userapp.module.pay.nopassword.request.NoPasswordListRequestDTO;
import com.miller.userapp.module.pay.nopassword.request.NoPasswordStatusRequestDTO;
import com.miller.userapp.module.pay.nopassword.response.NoPasswordStatusResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@DisplayName("支付方式是否开启免密，0关闭1开启")
public class NoPasswordStatusTest {
    @MethodSource("noPasswordStatusProvider")
    @ParameterizedTest
    @DisplayName("正常流程_支付方式是否开启免密，0关闭1开启")
    void updateNoPasswordStatus(NoPasswordStatusRequestDTO requestDTO){
        NoPasswordListRequestDTO noPasswordListRequestDTO = new NoPasswordListRequestDTO();
        NoPasswordListResponseDTO noPasswordListResponseDTO = NoPasswordListFlow.noPasswordList(noPasswordListRequestDTO);
        List<NoPasswordDTO> noPasswordList =noPasswordListResponseDTO.getResult().getNoPasswordList();
        NoPasswordDTO noPasswordDTO = noPasswordList.get(new Random().nextInt(noPasswordList.size()));
        String paymentPattern = noPasswordDTO.getPaymentPattern();
        Integer openStatus = noPasswordDTO.getOpenStatus();
        Integer setOpenStatus = openStatus.equals(0)?1:0;
        requestDTO.setOpenStatus(setOpenStatus);
        requestDTO.setPaymentPattern(paymentPattern);
        NoPasswordStatusResponseDTO result = NoPasswordStatusFlow.noPasswordStatusFlow(requestDTO);
        //第二次请求查看是否关闭
        NoPasswordListResponseDTO noPasswordListResponseDTO2 = NoPasswordListFlow.noPasswordList(noPasswordListRequestDTO);
        List<NoPasswordDTO> noPasswordList2 =noPasswordListResponseDTO2.getResult().getNoPasswordList();
        NoPasswordDTO noPasswordDTO2 = noPasswordList2.stream().filter(dto->dto.getPaymentPattern().equals(paymentPattern))
                .toList().get(0);

        assertThat(result.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(noPasswordDTO2.getOpenStatus()).isEqualTo(setOpenStatus);
        requestDTO.setOpenStatus(openStatus);
        NoPasswordStatusResponseDTO result2 = NoPasswordStatusFlow.noPasswordStatusFlow(requestDTO);
        assertThat(result2.getResultCode()).isEqualTo(ResponseConstant.resultCode);
    }
    static Stream<Arguments> noPasswordStatusProvider() {
        NoPasswordStatusRequestDTO requestDTO = new NoPasswordStatusRequestDTO();
        return Stream.of(
                Arguments.of(requestDTO)
        );
    }
}
