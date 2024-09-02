package com.miller.userapp.module.pay.notify.airwallex;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.hungrypanda.app.server.api.req.payment.AliWalletInstallReq;
import com.hungrypanda.app.server.api.req.payment.AppWalletInstallReq;
import com.hungrypanda.app.server.api.req.payment.WechatWalletInstallReq;
import com.hungrypanda.payserver.entity.PayOrder;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.cache.CacheUtils;
import com.miller.service.framework.depend.DependsOnMethod;
import com.miller.service.framework.util.ResourceUtils;
import com.miller.userapp.module.data.pay.db.PayOrderSql;
import com.miller.userapp.module.order.create.response.CreateOrderResponseDTO;
import com.miller.userapp.module.pay.notify.airwallex.flow.AirwallexPayNotificationFlow;
import com.miller.userapp.module.pay.notify.airwallex.request.AirwallexPayNotificationRequest;
import com.miller.userapp.module.pay.notify.airwallex.request.Card;
import com.miller.userapp.module.pay.payment.flow.DefaultPaymentFlow;
import com.miller.userapp.module.pay.payment.request.DefaultPaymentRequest;
import com.miller.userapp.module.pay.payment.response.DefaultPaymentResponse;
import com.miller.userapp.util.DBUtils;
import org.apache.ibatis.session.SqlSession;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@EnvTag.Test
@TestFramework
@DisplayName("AirwallexPay回调接口")
public class AirwallexPayNotificationTest {
    private static PayOrderSql payOrderSql;
    private static String orderSn;
    @BeforeAll
    public void beforeAll(){
        SqlSession sqlPaySession = DBUtils.getDBOfPandaPayTest();
        payOrderSql = new PayOrderSql(sqlPaySession);
        CreateOrderResponseDTO createOrderResponseDTO = CacheUtils.get(TestCaseDataForUserConstant.ORDER_ID_OBJECT_KEY, CreateOrderResponseDTO.class);
        orderSn = createOrderResponseDTO.getResult().getOrderSn();
    }
    @MethodSource("airwallexPayNotificationDataProvider")
    @ParameterizedTest
    @DisplayName("AirwallexPay回调接口")
    @DependsOnMethod("airwallexPaymentTest")
    void airwallexPayNotificationTest(AirwallexPayNotificationRequest request) {
        AirwallexPayNotificationFlow.AirwallexPayNotification(request);
        //修改表有延迟
        try{
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }
        PayOrder payOrder = payOrderSql.getPayOrder(request.getData().getObject().getMerchantOrderId(),true,true);
        assertThat(payOrder.getTradeStatus()).isEqualTo(1);

    }
    @MethodSource("airwallexPaymentDataProvider")
    @ParameterizedTest
    @DisplayName("AirwallexPayment支付")
    void airwallexPaymentTest(DefaultPaymentRequest request,String countryCode ,String channel) {
        DefaultPaymentResponse result= DefaultPaymentFlow.payment(request,countryCode,channel);
        Assertions.assertThat(result.getResultCode()).isEqualTo(1000);
    }

    static Stream<Arguments> airwallexPayNotificationDataProvider(){
        String airwallexPayNotificationJSON = new ResourceUtils().readTestCaseDataFromResourcesPath(AirwallexPayNotificationTest.class,
                 "airwallex_card_notification.json" );
        PayOrder payOrder = payOrderSql.getPayOrder(orderSn,false);
        AirwallexPayNotificationRequest request = JSON.parseObject(airwallexPayNotificationJSON, AirwallexPayNotificationRequest.class);
        request.getData().getObject().setMerchantOrderId(payOrder.getTradeNo());
        request.getData().getObject().getLatestPaymentAttempt().setMerchantOrderId(payOrder.getTradeNo());
        Card card = new Card();
        request.getData().getObject().getLatestPaymentAttempt().getPaymentMethodOptions().setCard(card);
        request.getData().getObject().setAmount(payOrder.getAmount());
        request.getData().getObject().setBaseAmount(payOrder.getAmount());
        request.getData().getObject().setCapturedAmount(payOrder.getAmount());
        request.getData().getObject().getLatestPaymentAttempt().setAmount(payOrder.getAmount());
        request.setSourceId(payOrder.getChannelTransactionNo());
        request.getData().getObject().getLatestPaymentAttempt().setPaymentIntentId(payOrder.getChannelTransactionNo());
        request.getData().getObject().setId(payOrder.getChannelTransactionNo());
        return Stream.of(
                Arguments.of(request)
        );
    }
    static Stream<Arguments> airwallexPaymentDataProvider(){
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
        defaultPaymentRequest.setChannelRecordId("923078916885848064");// card:960427970520068096
        defaultPaymentRequest.setPaymentType(2);
        defaultPaymentRequest.setRoutingPayChannel("airwallexPay");
        defaultPaymentRequest.setCountryCode("AU");
        defaultPaymentRequest.setRoutingFloatingRate(1.0);
        defaultPaymentRequest.setOrderSn(orderSn);
        return Stream.of(
                Arguments.of(defaultPaymentRequest,"AU","132") //apple pay  135为card
        );
    }
    public static void main(String[] args){

        String airwallexPayNotificationJSON = new ResourceUtils().readFileFromResourcesPath(AirwallexPayNotificationTest.class,
                "airwallex_card_notification.json" );
        System.out.println(airwallexPayNotificationJSON);
        SerializeConfig config = new SerializeConfig();
        config.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        AirwallexPayNotificationRequest request = JSON.parseObject(airwallexPayNotificationJSON, AirwallexPayNotificationRequest.class);
        System.out.println("request : "+JSON.toJSONString(request, config));
        String result= AirwallexPayNotificationFlow.AirwallexPayNotification(request);
        System.out.println("result: "+result);
    }

}
