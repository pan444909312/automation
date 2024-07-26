package com.miller.userapp.module.pay.notify.ronghan;

import com.alibaba.fastjson.JSON;
import com.hungrypanda.app.server.api.req.payment.AliWalletInstallReq;
import com.hungrypanda.app.server.api.req.payment.AppWalletInstallReq;
import com.hungrypanda.app.server.api.req.payment.WechatWalletInstallReq;
import com.hungrypanda.payserver.entity.PayOrder;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.cache.CacheUtils;
import com.miller.service.framework.depend.DependsOnMethod;
import com.miller.userapp.module.data.pay.db.PayOrderSql;
import com.miller.userapp.module.order.create.response.CreateOrderResponseDTO;
import com.miller.userapp.module.pay.notify.ronghan.flow.RonghanPayNotificationFlow;
import com.miller.userapp.module.pay.notify.ronghan.request.RonghanPayNotificationRequest;
import com.miller.userapp.module.pay.payment.flow.DefaultPaymentFlow;
import com.miller.userapp.module.pay.payment.request.DefaultPaymentRequest;
import com.miller.userapp.module.pay.payment.response.DefaultPaymentResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@DisplayName("rongHanPay回调接口")
public class RonghanPayNotificationTest {
    private static PayOrderSql payOrderSql;
    private static String orderSn;
    @BeforeAll
    void beforeAll(){
        payOrderSql = new PayOrderSql();
        CreateOrderResponseDTO createOrderResponseDTO = CacheUtils.get(TestCaseDataForUserConstant.ORDER_ID_OBJECT_KEY, CreateOrderResponseDTO.class);
        orderSn = createOrderResponseDTO.getResult().getOrderSn();
    }
    @MethodSource("rongHanPayNotificationRequest")
    @ParameterizedTest
    @DisplayName("rongHanPay回调接口测试")
    @DependsOnMethod("rongHanPaymentTest")
    public void rongHanPayNotificationTest(RonghanPayNotificationRequest request){
        String result = RonghanPayNotificationFlow.rongHanPayNotification(request);
        assertThat(result).isEqualTo(request.getTransactionId());
        try{
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }
        PayOrder payOrder = payOrderSql.getPayOrder(request.getMerchantTxnId(),true,true);
        assertThat(payOrder.getTradeStatus()).isEqualTo(1);

    }
    @MethodSource("rongHanPaymentDataProvider")
    @ParameterizedTest
    @DisplayName("rongHanPayment支付")
    void rongHanPaymentTest(DefaultPaymentRequest request, String countryCode , String channel) {
        DefaultPaymentResponse result= DefaultPaymentFlow.payment(request,countryCode,channel);
        Assertions.assertThat(result.getResultCode()).isEqualTo(1000);
    }
    static Stream<Arguments> rongHanPaymentDataProvider() {
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
        defaultPaymentRequest.setFloatingAmount(0);
        defaultPaymentRequest.setIsSidePayment("0");
        defaultPaymentRequest.setRoutingFloatingType(0);
        defaultPaymentRequest.setCityId("0");
        defaultPaymentRequest.setChannelRecordId("927403045497901056");// card
        defaultPaymentRequest.setPaymentType(2);
        defaultPaymentRequest.setRoutingPayChannel("rongHanPay");
        defaultPaymentRequest.setCountryCode("GB");
        defaultPaymentRequest.setRoutingFloatingRate(1.0);
        defaultPaymentRequest.setOrderSn(orderSn);
//        defaultPaymentRequest.setPaymentCardToken("24d3308346a870788e64f3c378a1c775f106148d5fdedff7a464a036576bb1b9");
        return Stream.of(
                Arguments.of(defaultPaymentRequest, "GB", "134") //apple pay  135为card
        );
    }
    static Stream<Arguments> rongHanPayNotificationRequest(){
        String rongHanNotificationContent = "{\"reason\":\"{\\\"respCode\\\":\\\"20000\\\",\\\"respMsg\\\":\\\"Success\\\"}\",\"txnTimeZone\":\"+08:00\",\"orderCurrency\":\"GBP\",\"responseTime\":\"2024-07-17 23:58:58\",\"sign\":\"31c5b9bd5abc5827ffc519261c27caea2306fe63ada9d9964845004eaa27416a\",\"merchantTxnId\":\"PD973583203734155264\",\"txnType\":\"SALE\",\"transactionId\":\"1813604297712476160\",\"orderAmount\":\"29.69\",\"notifyType\":\"TXN\",\"txnTime\":\"2024-07-17 23:58:54\",\"paymentMethod\":\"MASTERCARD\",\"txnAmount\":\"\",\"merchantNo\":\"800663\",\"status\":\"S\"}";
        PayOrder payOrder = payOrderSql.getPayOrder(orderSn,false);
        RonghanPayNotificationRequest request = JSON.parseObject(rongHanNotificationContent, RonghanPayNotificationRequest.class);
        BigDecimal amount = BigDecimal.valueOf(payOrder.getAmount()).divide(new BigDecimal(100),2, RoundingMode.HALF_UP);
        request.setOrderAmount(String.valueOf(amount));
        request.setTransactionId(payOrder.getChannelTransactionNo());
        request.setMerchantTxnId(payOrder.getTradeNo());
        return Stream.of(
                Arguments.of(request)
        );
    }

}
