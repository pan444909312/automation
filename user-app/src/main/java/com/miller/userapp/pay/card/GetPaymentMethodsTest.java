package com.miller.userapp.pay.card;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONPath;
import com.hungrypanda.payserver.api.res.PaymentMethodInfoDTO;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.PaymentConstant;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.pay.card.flow.AddCardRecordFlow;
import com.miller.userapp.pay.card.flow.DetachPaymentMethodFlow;
import com.miller.userapp.pay.card.flow.GetPaymentMethodsFlow;
import com.miller.userapp.pay.card.request.AddCardRecordRequestDTO;
import com.miller.userapp.pay.card.request.DetachPaymentMethodRequestDTO;
import com.miller.userapp.pay.card.request.GetPaymentMethodsRequestDTO;
import com.miller.userapp.pay.card.response.AddCardRecordResponseDTO;
import com.miller.userapp.pay.card.response.GetPaymentMethodsResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@DisplayName("获取Stripe卡")
public class GetPaymentMethodsTest {
    @MethodSource("com.miller.userapp.pay.card.provider.GetPaymentMethodsDataProvider#getPaymentMethodsDataProvider")
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
}
