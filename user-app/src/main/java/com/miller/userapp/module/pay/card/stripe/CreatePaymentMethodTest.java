package com.miller.userapp.module.pay.card.stripe;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.payserver.api.res.PaymentMethodInfoDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.PaymentConstant;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.module.pay.card.stripe.flow.CreatePaymentMethodFlow;
import com.miller.userapp.module.pay.card.stripe.flow.DetachPaymentMethodFlow;
import com.miller.userapp.module.pay.card.stripe.flow.GetPaymentMethodsFlow;
import com.miller.userapp.module.pay.card.stripe.request.CreatePaymentMethodRequestDTO;
import com.miller.userapp.module.pay.card.stripe.request.DetachPaymentMethodRequestDTO;
import com.miller.userapp.module.pay.card.stripe.response.CreatePaymentMethodResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Objects;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@DisplayName("绑定Stripe卡")
public class CreatePaymentMethodTest {
    @BeforeEach
    void beforeEach(){
        PaymentMethodInfoDTO paymentMethod= GetPaymentMethodsFlow.getPaymentMethod();
        if(Objects.nonNull(paymentMethod)){
            DetachPaymentMethodRequestDTO detachPaymentMethodRequestDTO = new DetachPaymentMethodRequestDTO();
            detachPaymentMethodRequestDTO.setPaymentMethodId(paymentMethod.getPaymentMethodId());
            detachPaymentMethodRequestDTO.setCardNoMd5(paymentMethod.getCardNoMd5());
            DetachPaymentMethodFlow.detachPaymentMethod(detachPaymentMethodRequestDTO);
        }
    }
    @MethodSource("createPaymentMethodDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_绑定Stripe卡")
    void shouldCreatePaymentMethodsSuccessfully(CreatePaymentMethodRequestDTO createPaymentMethodRequestDTO) {
        CreatePaymentMethodResponseDTO result = CreatePaymentMethodFlow.createPaymentMethod(createPaymentMethodRequestDTO);
        System.out.println("result: "+ JSON.toJSON(result));
        assertThat(result.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(result.getSuccess()).isTrue();
    }
    static Stream<Arguments> createPaymentMethodDataProvider(){
        CreatePaymentMethodRequestDTO createPaymentMethodRequestDTO = new CreatePaymentMethodRequestDTO();
        createPaymentMethodRequestDTO.setCvc("737");
        createPaymentMethodRequestDTO.setExpMonth("09");
        createPaymentMethodRequestDTO.setExpYear("2050");
        createPaymentMethodRequestDTO.setPostalCode("310000");
        createPaymentMethodRequestDTO.setCardNumber(PaymentConstant.CARDNUMBER);
        return Stream.of(
                Arguments.of(createPaymentMethodRequestDTO)
        );
    }
}
