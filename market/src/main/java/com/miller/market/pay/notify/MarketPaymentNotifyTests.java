package com.miller.market.pay.notify;

import com.miller.market.constants.BusinessConstant;
import com.miller.market.constants.ResponseConstant;
import com.miller.market.pay.notify.flow.MarketPaymentNotifyFlow;
import com.miller.market.pay.notify.request.MarketPaymentNotifyRequestDTO;
import com.miller.market.pay.notify.response.MarketPaymentNotifyResponseDTO;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;


/**
 * 客户端通知接口
 */
@EnvTag.Test
@TestFramework
@DisplayName("PF_支付客户端通知接口")
public class MarketPaymentNotifyTests {

    @MethodSource("staticPayDataProvider")
    @ParameterizedTest
    @DisplayName("PF_正常流程_已登录_支付通知回调")
    void getPaymentPatternSuccessfully(MarketPaymentNotifyRequestDTO requestDTO) throws InterruptedException {
        Thread.sleep(10000);
        MarketPaymentNotifyResponseDTO responseDTO = MarketPaymentNotifyFlow.getPaymentNotify(requestDTO);

        Assertions.assertThat(responseDTO.getCode()).isEqualTo(ResponseConstant.code);
        Assertions.assertThat(responseDTO.getData()).isNotNull();


    }

    /**
     * 测试用例数据提供者
     */
    static Stream<Arguments> staticPayDataProvider() {
        MarketPaymentNotifyRequestDTO requestDTO = new MarketPaymentNotifyRequestDTO();

        requestDTO.setOrderId(BusinessConstant.orderId);
        requestDTO.setPayOrderNo(BusinessConstant.payOrderNo);
//        requestDTO.setResult(true);
        return Stream.of(Arguments.of(requestDTO));
    }

}
