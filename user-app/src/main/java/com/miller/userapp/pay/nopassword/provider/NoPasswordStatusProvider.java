package com.miller.userapp.pay.nopassword.provider;


import com.hungrypanda.app.server.api.res.payment.NoPasswordDTO;
import com.miller.userapp.pay.nopassword.flow.NoPasswordListFlow;
import com.miller.userapp.pay.nopassword.request.NoPasswordListRequestDTO;
import com.miller.userapp.pay.nopassword.request.NoPasswordStatusRequestDTO;
import com.miller.userapp.pay.nopassword.response.NoPasswordListResponseDTO;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class NoPasswordStatusProvider {
    static Stream<Arguments> noPasswordStatusProvider() {
        NoPasswordStatusRequestDTO requestDTO = new NoPasswordStatusRequestDTO();
        return Stream.of(
                Arguments.of(requestDTO)
        );
    }

}
