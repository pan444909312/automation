package com.miller.userapp.pay.card.provider;

import com.miller.userapp.constants.PaymentConstant;
import com.miller.userapp.pay.card.request.AddCardRecordRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static com.miller.common.util.MD5Util.string2MD5;

public class AddCardRecordDataProvider {
//    private static final String CARD="4242 4242 4242 4242";
    private static final String CARDLAST4="4242";

    static Stream<Arguments> addCardRecordDataProvider(){
        AddCardRecordRequestDTO addCardRecordRequestDTO = new AddCardRecordRequestDTO();

        String cardNumberMd5 = string2MD5(PaymentConstant.CARDNUMBER.replaceAll(" ",""));
        addCardRecordRequestDTO.setCardMd5(cardNumberMd5);
        addCardRecordRequestDTO.setCardLast4(CARDLAST4);
        return Stream.of(
                Arguments.of(addCardRecordRequestDTO)
        );
    }
}
