package com.miller.userapp.module.pay.payment;


import com.hungrypanda.app.server.api.req.payment.AliWalletInstallReq;
import com.hungrypanda.app.server.api.req.payment.AppWalletInstallReq;
import com.hungrypanda.app.server.api.req.payment.WechatWalletInstallReq;
import com.hungrypanda.payserver.entity.PayOrder;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.cache.CacheUtils;
import com.miller.service.framework.util.SleepUtils;
import com.miller.userapp.module.data.pay.db.PayOrderSql;
import com.miller.userapp.module.order.create.response.CreateOrderResponseDTO;
import com.miller.userapp.module.pay.payment.flow.StripePaymentFlow;
import com.miller.userapp.module.pay.payment.request.StripePaymentRequest;
import com.miller.userapp.util.DBUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@DisplayName("Stripe支付")
public class StripePaymentOnlyTest {
    @MethodSource("stripePaymentDataProvider")
    @ParameterizedTest
    @DisplayName("正常流程_Stripe支付")
    void shouldStripePaymentSuccessfully(StripePaymentRequest stripePaymentRequest) {
        StripePaymentFlow.StripeSDKPayment(stripePaymentRequest);
        SleepUtils.sleep(1000);
        CreateOrderResponseDTO createOrderResponseDTO = CacheUtils.get(TestCaseDataForUserConstant.ORDER_ID_OBJECT_KEY, CreateOrderResponseDTO.class);
        String orderSn = createOrderResponseDTO.getResult().getOrderSn();
        PayOrderSql payOrderSql = new PayOrderSql(DBUtils.getDBOfPandaPayTest());
        PayOrder payOrder = payOrderSql.getPayOrder(orderSn);
        assertThat(payOrder.getTradeStatus()).isEqualTo(1);
//        System.out.println("result: "+ JSON.toJSON(result));
//        assertThat(Objects.nonNull(result)).isTrue();
    }
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
