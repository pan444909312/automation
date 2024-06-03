package com.miller.userapp.pay.nopassword;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.payserver.api.res.PaymentMethodInfoDTO;
import com.miller.common.util.MD5Util;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.userapp.constants.PaymentConstant;
import com.miller.userapp.constants.ResponseConstant;
import com.miller.userapp.pay.card.flow.GetPaymentMethodsFlow;
import com.miller.userapp.pay.card.request.GetPaymentMethodsRequestDTO;
import com.miller.userapp.pay.card.response.GetPaymentMethodsResponseDTO;
import com.miller.userapp.pay.nopassword.flow.NoPasswordListFlow;
import com.miller.userapp.pay.nopassword.request.NoPasswordListRequestDTO;
import com.miller.userapp.pay.nopassword.response.NoPasswordListResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@DisplayName("免密支付方式列表")
public class NoPasswordListTest {

    @MethodSource("com.miller.userapp.pay.nopassword.provider.NoPasswordListProvider#noPasswordListProvider")
    @ParameterizedTest
    @DisplayName("正常流程_免密支付方式列表")
    void noPasswordList(NoPasswordListRequestDTO requestDTO) {
        System.out.println(JSON.toJSON(requestDTO));
        NoPasswordListResponseDTO result = NoPasswordListFlow.noPasswordList(requestDTO);
//        System.out.println(JSON.toJSON(result));
        assertThat(result.getResultCode()).isEqualTo(ResponseConstant.resultCode);
        assertThat(result.getResult().getNoPasswordList().isEmpty()).isEqualTo(false);



    }
}
