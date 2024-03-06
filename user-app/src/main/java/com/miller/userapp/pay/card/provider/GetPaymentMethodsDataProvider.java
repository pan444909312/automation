package com.miller.userapp.pay.card.provider;

import com.miller.userapp.pay.card.request.GetPaymentMethodsRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class GetPaymentMethodsDataProvider {
    static Stream<Arguments> getPaymentMethodsDataProvider(){
        GetPaymentMethodsRequestDTO paymentMethodsInfo = new GetPaymentMethodsRequestDTO();
        return Stream.of(
                Arguments.of(paymentMethodsInfo)
        );
    }
}
