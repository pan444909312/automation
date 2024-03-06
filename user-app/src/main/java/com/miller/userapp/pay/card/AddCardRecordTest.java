package com.miller.userapp.pay.card;

import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;

import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.pay.card.flow.AddCardRecordFlow;
import com.miller.userapp.pay.card.request.AddCardRecordRequestDTO;
import com.miller.userapp.pay.card.response.AddCardRecordResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@DisplayName("添加卡")
public class AddCardRecordTest {
    @MethodSource("com.miller.userapp.pay.card.provider.AddCardRecordDataProvider#addCardRecordDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_添加卡")
    void shouldAddCardRecordSuccessfully(AddCardRecordRequestDTO addCardRecordRequestDTO) {
        AddCardRecordResponseDTO result = AddCardRecordFlow.addCardRecord(addCardRecordRequestDTO);
//        System.out.println("result: "+ JSON.toJSON(result));
        assertThat(result.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(result.getSuccess()).isTrue();
    }
}
