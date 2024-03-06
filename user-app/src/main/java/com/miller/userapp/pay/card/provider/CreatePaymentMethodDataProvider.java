package com.miller.userapp.pay.card.provider;

import com.miller.userapp.constants.PaymentConstant;
import com.miller.userapp.pay.card.request.CreatePaymentMethodRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class CreatePaymentMethodDataProvider {
//    private static final String CARD="4242 4242 4242 4242";
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
