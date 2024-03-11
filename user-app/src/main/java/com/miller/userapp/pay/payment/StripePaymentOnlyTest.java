package com.miller.userapp.pay.payment;


import com.alibaba.fastjson.JSON;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.pay.payment.flow.StripePaymentFlow;
import com.miller.userapp.pay.payment.request.StripePaymentRequest;
import com.miller.userapp.pay.payment.response.StripePaymentResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@DisplayName("Stripe支付")
public class StripePaymentOnlyTest {
    @MethodSource("com.miller.userapp.pay.payment.provider.StripePaymentDataProvider#stripePaymentDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_Stripe支付")
    void shouldStripePaymentSuccessfully(StripePaymentRequest stripePaymentRequest) {
        StripePaymentFlow.StripeSDKPayment(stripePaymentRequest);
//        System.out.println("result: "+ JSON.toJSON(result));
//        assertThat(Objects.nonNull(result)).isTrue();
    }
}
