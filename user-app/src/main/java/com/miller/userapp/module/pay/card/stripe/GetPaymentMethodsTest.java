package com.miller.userapp.module.pay.card.stripe;


import com.alibaba.fastjson.JSON;
import com.hungrypanda.payserver.api.res.PaymentMethodInfoDTO;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.PaymentConstant;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.module.pay.card.stripe.flow.GetPaymentMethodsFlow;
import com.miller.userapp.module.pay.card.stripe.response.GetPaymentMethodsResponseDTO;
import com.miller.userapp.module.pay.card.stripe.request.GetPaymentMethodsRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@DisplayName("获取Stripe卡")
public class GetPaymentMethodsTest {
    @MethodSource("getPaymentMethodsDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_获取Stripe卡")
    void shouldGetPaymentMethodsSuccessfully(GetPaymentMethodsRequestDTO paymentMethodsInfo) {
        GetPaymentMethodsResponseDTO result = GetPaymentMethodsFlow.getPaymentMethods(paymentMethodsInfo);
        System.out.println("result: "+ JSON.toJSON(result));
        assertThat(result.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(result.getSuccess()).isTrue();


        List<PaymentMethodInfoDTO> paymentMethodList= result.getResult().getPaymentMethodList();
        String cardMd5 = MD5Util.string2MD5(PaymentConstant.CARDNUMBER.replaceAll(" ",""));
        PaymentMethodInfoDTO paymentMethod=paymentMethodList.stream().filter(paymentMethodInfo -> paymentMethodInfo.getCardNoMd5().equals(cardMd5)).findAny().orElse(null);
        assertThat(Objects.nonNull(paymentMethod)).isTrue();


    }
    static Stream<Arguments> getPaymentMethodsDataProvider(){
        GetPaymentMethodsRequestDTO paymentMethodsInfo = new GetPaymentMethodsRequestDTO();
        return Stream.of(
                Arguments.of(paymentMethodsInfo)
        );
    }
}
