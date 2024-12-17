package com.miller.market.pay.payment;

import com.miller.market.constants.BusinessConstant;
import com.miller.market.constants.ResponseConstant;
import com.miller.market.pay.payment.flow.MarketPaymentFlow;
import com.miller.market.pay.payment.request.MarketPaymentRequestDTO;
import com.miller.market.pay.payment.response.MarketPaymentResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;


/**
 * 支付
 */
@EnvTag.Test
@TestFramework
@DisplayName("PF_支付")
public class MarketPaymentTests {

    @MethodSource("staticPayDataProvider")
    @ParameterizedTest
    @DisplayName("PF_正常流程_已登录_支付")
    void getPaymentPatternSuccessfully(MarketPaymentRequestDTO requestDTO) {
        MarketPaymentResponseDTO responseDTO = MarketPaymentFlow.getPaymentPattern(requestDTO);

        Assertions.assertThat(responseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(responseDTO.getData()).isNotNull();

        BusinessConstant.payOrderNo = responseDTO.getData().getPayOrderNo();

    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticPayDataProvider() {
        MarketPaymentRequestDTO requestDTO = new MarketPaymentRequestDTO();

        requestDTO.setOrderSn(BusinessConstant.orderSn);
        requestDTO.setChannelRecordId(BusinessConstant.payItemBean.getChannelRecordId());
        requestDTO.setRoutingFloatingRate(BigDecimal.valueOf(BusinessConstant.payItemBean.getFloatingRate()));
        requestDTO.setRoutingPayChannel(BusinessConstant.payItemBean.getPayChannel());
        requestDTO.setFloatingAmount((int) BusinessConstant.payItemBean.getFloatingAmount());
        requestDTO.setPaymentMethodId(BusinessConstant.paymentMethodId);
        requestDTO.setRoutingFloatingType(BusinessConstant.payItemBean.getFloatingType());
        requestDTO.setSysType("1");
        requestDTO.setCountryCode("CN");
        return Stream.of(Arguments.of(requestDTO));
    }

}
