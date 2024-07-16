package com.miller.userapp.module.pay.card.stripe;

import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;

import com.miller.userapp.constants.PaymentConstant;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.module.pay.card.stripe.flow.AddCardRecordFlow;
import com.miller.userapp.module.pay.card.stripe.request.AddCardRecordRequestDTO;
import com.miller.userapp.module.pay.card.stripe.response.AddCardRecordResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.miller.common.util.MD5Util.string2MD5;
import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@DisplayName("添加卡")
public class AddCardRecordTest {
    private static final String CARDLAST4="4242";

    @MethodSource("addCardRecordDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_添加卡")
    void shouldAddCardRecordSuccessfully(AddCardRecordRequestDTO addCardRecordRequestDTO) {
        AddCardRecordResponseDTO result = AddCardRecordFlow.addCardRecord(addCardRecordRequestDTO);
//        System.out.println("result: "+ JSON.toJSON(result));
        assertThat(result.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(result.getSuccess()).isTrue();
    }

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
