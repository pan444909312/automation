package com.miller.userapp.pay.nopassword.provider;

import com.miller.userapp.constants.PaymentConstant;
import com.miller.userapp.pay.card.request.AddCardRecordRequestDTO;
import com.miller.userapp.pay.nopassword.request.NoPasswordListRequestDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static com.miller.common.util.MD5Util.string2MD5;

public class NoPasswordListProvider {
    static Stream<Arguments> noPasswordListProvider(){
        NoPasswordListRequestDTO requestDTO = new NoPasswordListRequestDTO();
        return Stream.of(
                Arguments.of(requestDTO)
        );
    }
}
