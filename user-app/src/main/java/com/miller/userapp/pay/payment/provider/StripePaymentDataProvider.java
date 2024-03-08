package com.miller.userapp.pay.payment.provider;

import com.hungrypanda.app.server.api.req.payment.AliWalletInstallReq;
import com.hungrypanda.app.server.api.req.payment.AppWalletInstallReq;
import com.hungrypanda.app.server.api.req.payment.WechatWalletInstallReq;
import com.miller.userapp.pay.payment.request.StripePaymentRequest;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class StripePaymentDataProvider {
    static Stream<Arguments> stripePaymentDataProvider(){
        StripePaymentRequest stripePaymentRequest = new StripePaymentRequest();
        stripePaymentRequest.setSysType("2");
        AppWalletInstallReq appWalletInstallReq = new AppWalletInstallReq();
        AliWalletInstallReq aliWalletInstallReq = new AliWalletInstallReq();
        aliWalletInstallReq.setInstallAliCN(1);
        aliWalletInstallReq.setInstallAliHK(1);
        WechatWalletInstallReq wechatWalletInstallReq = new WechatWalletInstallReq();
        wechatWalletInstallReq.setInstallWechatApp(1);
        appWalletInstallReq.setAliWalletInstallReq(aliWalletInstallReq);
        appWalletInstallReq.setWechatWalletInstallReq(wechatWalletInstallReq);
        stripePaymentRequest.setAppWalletInstallReq(appWalletInstallReq);
        stripePaymentRequest.setBlackBox("7GPU1709693602737GdlJURplc");
        stripePaymentRequest.setFloatingAmount(0);
        stripePaymentRequest.setIsSidePayment("0");
        stripePaymentRequest.setRoutingFloatingType(0);
        stripePaymentRequest.setCityId("0");
        return Stream.of(
                Arguments.of(stripePaymentRequest)
        );
    }
}
