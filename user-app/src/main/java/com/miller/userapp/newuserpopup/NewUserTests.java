package com.miller.userapp.newuserpopup;

import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.newuserpopup.flow.NewUserPopupFlow;
import com.miller.userapp.newuserpopup.request.NewUserPopupRequestDTO;
import com.miller.userapp.newuserpopup.response.NewUserPopupResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@DisplayName("新人权益弹窗-老")
public class NewUserTests {
    @MethodSource("com.miller.userapp.newuserpopup.provider.NewUserPopupDataProvider#setNewUserPopupData")
    @ParameterizedTest
    @DisplayName("新人权益-弹窗")
    void newUserPopupTest(NewUserPopupRequestDTO NewUserPopupRequestdto){
        NewUserPopupResponseDTO NewUserPopupResponsedto = NewUserPopupFlow.NewUserPopupflow(NewUserPopupRequestdto);
        System.out.println(NewUserPopupResponsedto.getResult().getType());
        assertThat(NewUserPopupResponsedto.getResult().getType()).isEqualTo(4);
    }
}
