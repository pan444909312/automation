package com.miller.market.pay.getPaymentMethods;

import com.miller.market.constants.BusinessConstant;
import com.miller.market.constants.ResponseConstant;
import com.miller.market.pay.getPaymentMethods.flow.MarketGetPaymentMethodsFlow;
import com.miller.market.pay.getPaymentMethods.response.MarketGetPaymentMethodsResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 * PaymentMethods查询接口
 */
@EnvTag.Test
@TestFramework
@DisplayName("PaymentMethods查询")
public class MarketGetPaymentMethodsTests {

    @Test
    @DisplayName("正常流程_已登录_查询PaymentMethods")
    void getPaymentMethodsSuccessfully() {
        MarketGetPaymentMethodsResponseDTO responseDTO = MarketGetPaymentMethodsFlow.getPaymentMethods();

        Assertions.assertThat(responseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(responseDTO.getData()).isNotNull();

        BusinessConstant.paymentMethodId = responseDTO.getData().getPaymentMethodList().get(0).getPaymentMethodId();
    }

}
