package com.miller.userapp.pay.card;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.payserver.api.res.PaymentMethodInfoDTO;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.PaymentConstant;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.pay.card.flow.CreatePaymentMethodFlow;
import com.miller.userapp.pay.card.flow.DetachPaymentMethodFlow;
import com.miller.userapp.pay.card.flow.GetPaymentMethodsFlow;
import com.miller.userapp.pay.card.request.CreatePaymentMethodRequestDTO;
import com.miller.userapp.pay.card.request.DetachPaymentMethodRequestDTO;
import com.miller.userapp.pay.card.request.GetPaymentMethodsRequestDTO;
import com.miller.userapp.pay.card.response.CreatePaymentMethodResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@DisplayName("绑定Stripe卡")
public class CreatePaymentMethodTest {
    @BeforeEach
    void beforeEach(){
        GetPaymentMethodsRequestDTO getPaymentMethodsRequestDTO = new GetPaymentMethodsRequestDTO();
        List<PaymentMethodInfoDTO> paymentMethodList= GetPaymentMethodsFlow.getPaymentMethodList(getPaymentMethodsRequestDTO);
        String cardMd5 = MD5Util.string2MD5(PaymentConstant.CARDNUMBER.replaceAll(" ",""));
        PaymentMethodInfoDTO paymentMethod=paymentMethodList.stream().filter(paymentMethodInfo -> paymentMethodInfo.getCardNoMd5().equals(cardMd5)).findAny().orElse(null);
        if(Objects.nonNull(paymentMethod)){
            DetachPaymentMethodRequestDTO detachPaymentMethodRequestDTO = new DetachPaymentMethodRequestDTO();
            detachPaymentMethodRequestDTO.setPaymentMethodId(paymentMethod.getPaymentMethodId());
            detachPaymentMethodRequestDTO.setCardNoMd5(paymentMethod.getCardNoMd5());
            DetachPaymentMethodFlow.detachPaymentMethod(detachPaymentMethodRequestDTO);
        }
    }
    @MethodSource("com.miller.userapp.pay.card.provider.CreatePaymentMethodDataProvider#createPaymentMethodDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_绑定Stripe卡")
    void shouldCreatePaymentMethodsSuccessfully(CreatePaymentMethodRequestDTO createPaymentMethodRequestDTO) {
        CreatePaymentMethodResponseDTO result = CreatePaymentMethodFlow.createPaymentMethod(createPaymentMethodRequestDTO);
        System.out.println("result: "+ JSON.toJSON(result));
        assertThat(result.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(result.getSuccess()).isTrue();
    }
}
