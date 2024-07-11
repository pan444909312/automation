package com.miller.userapp.module.pay.payment;

import com.hungrypanda.app.server.api.req.payment.AliWalletInstallReq;
import com.hungrypanda.app.server.api.req.payment.AppWalletInstallReq;
import com.hungrypanda.app.server.api.req.payment.WechatWalletInstallReq;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.cache.CacheUtils;
import com.miller.userapp.module.order.create.response.CreateOrderResponseDTO;
import com.miller.userapp.module.pay.payment.flow.DefaultPaymentFlow;
import com.miller.userapp.module.pay.payment.flow.StripePaymentFlow;
import com.miller.userapp.module.pay.payment.request.DefaultPaymentRequest;
import com.miller.userapp.module.pay.payment.request.StripePaymentRequest;
import com.miller.userapp.module.pay.payment.response.DefaultPaymentResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@EnvTag.Test
@TestFramework
@DisplayName("支付统一接口")
public class DefaultPaymentTest {
    @MethodSource("elePaymentDataProvider")
    @ParameterizedTest
    @DisplayName("elePay PayPay支付")
    void elePayOfPayPayTest(DefaultPaymentRequest defaultPaymentRequest,String countryCode ,String channel) {
        DefaultPaymentResponse result = DefaultPaymentFlow.payment(defaultPaymentRequest,countryCode,channel);
        Assertions.assertThat(result.getResultCode()).isEqualTo(1000);
    }

    static Stream<Arguments> elePaymentDataProvider(){
        DefaultPaymentRequest defaultPaymentRequest = new DefaultPaymentRequest();
        defaultPaymentRequest.setSysType("2");
        AppWalletInstallReq appWalletInstallReq = new AppWalletInstallReq();
        AliWalletInstallReq aliWalletInstallReq = new AliWalletInstallReq();
        aliWalletInstallReq.setInstallAliCN(1);
        aliWalletInstallReq.setInstallAliHK(1);
        WechatWalletInstallReq wechatWalletInstallReq = new WechatWalletInstallReq();
        wechatWalletInstallReq.setInstallWechatApp(1);
        appWalletInstallReq.setAliWalletInstallReq(aliWalletInstallReq);
        appWalletInstallReq.setWechatWalletInstallReq(wechatWalletInstallReq);
        defaultPaymentRequest.setAppWalletInstallReq(appWalletInstallReq);
//        defaultPaymentRequest.setBlackBox("7GPU1709693602737GdlJURplc");
        defaultPaymentRequest.setFloatingAmount(0);
        defaultPaymentRequest.setIsSidePayment("0");
        defaultPaymentRequest.setRoutingFloatingType(0);
        defaultPaymentRequest.setCityId("0");
        defaultPaymentRequest.setChannelRecordId("968393834629812224");
        defaultPaymentRequest.setPaymentType(2);
        defaultPaymentRequest.setRoutingPayChannel("elePay");
        defaultPaymentRequest.setCountryCode("JP");
        defaultPaymentRequest.setRoutingFloatingRate(1.0);
        CreateOrderResponseDTO createOrderResponseDTO = CacheUtils.get(TestCaseDataForUserConstant.ORDER_ID_OBJECT_KEY, CreateOrderResponseDTO.class);
        String orderSn = createOrderResponseDTO.getResult().getOrderSn();
        defaultPaymentRequest.setOrderSn(orderSn);
        return Stream.of(
                Arguments.of(defaultPaymentRequest,"JP","137")
        );
    }
}
