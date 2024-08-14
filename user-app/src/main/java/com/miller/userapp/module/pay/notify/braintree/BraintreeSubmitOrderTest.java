package com.miller.userapp.module.pay.notify.braintree;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hungrypanda.app.server.api.req.payment.AliWalletInstallReq;
import com.hungrypanda.app.server.api.req.payment.AppWalletInstallReq;
import com.hungrypanda.app.server.api.req.payment.WechatWalletInstallReq;
import com.hungrypanda.app.server.entity.user.UserEntity;
import com.hungrypanda.app.server.service.pay.pandapay.payment.braintree.BrainTreePaymentResponseDTO;
import com.hungrypanda.app.server.service.pay.pandapay.payment.braintree.BraintreeV2PaymentData;
import com.hungrypanda.payserver.biz.payment.braintreepay.model.BraintreeBaseTradeInfo;
import com.hungrypanda.payserver.entity.PayOrder;
import com.hungrypanda.payserver.entity.PayUserAccount;
import com.hungrypanda.payserver.utils.MD5Util;
import com.miller.common.util.Base64Utils;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.service.framework.annotation.EnvTag;
import com.miller.service.framework.annotation.TestFramework;
import com.miller.service.framework.cache.CacheUtils;
import com.miller.service.framework.depend.DependsOnMethod;
import com.miller.service.framework.util.JSONUtils;
import com.miller.service.framework.util.SleepUtils;
import com.miller.userapp.module.data.pay.db.PayOrderSql;
import com.miller.userapp.module.data.pay.db.PayUserAccountSql;
import com.miller.userapp.module.data.user.db.UserSql;
import com.miller.userapp.module.order.create.response.CreateOrderResponseDTO;
import com.miller.userapp.module.pay.notify.braintree.flow.BraintreeSubmitOrderFlow;
import com.miller.userapp.module.pay.notify.braintree.request.BTSubmitOrderRequest;
import com.miller.userapp.module.pay.payment.flow.DefaultPaymentFlow;
import com.miller.userapp.module.pay.payment.request.BTPaymentRequest;
import com.miller.userapp.module.pay.payment.request.DefaultPaymentRequest;
import com.miller.userapp.module.pay.payment.response.DefaultPaymentResponse;
import com.miller.userapp.util.DBUtils;
import com.panda.common.util.Base64Util;
import org.apache.ibatis.session.SqlSession;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.BeanUtils;

import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;
@EnvTag.Test
@TestFramework
@DisplayName("Braintree SubmitOrder测试")
public class BraintreeSubmitOrderTest {
    private static PayOrderSql payOrderSql;
    private static PayUserAccountSql payUserAccountSql;
    private static UserSql userSql;
    private static String orderSn;
    private static BrainTreePaymentResponseDTO.BrainTreePayData brainTreePayData;
    @BeforeAll
    public static void beforeAll(){
        SqlSession sqlPaySession = DBUtils.getDBOfPandaPayTest();
        SqlSession sqlSession = DBUtils.getDBOfPandaTest();
        payOrderSql = new PayOrderSql(sqlPaySession);
        payUserAccountSql = new PayUserAccountSql(sqlPaySession);
        userSql = new UserSql(sqlSession);
        CreateOrderResponseDTO createOrderResponseDTO = CacheUtils.get(TestCaseDataForUserConstant.ORDER_ID_OBJECT_KEY, CreateOrderResponseDTO.class);
        orderSn = createOrderResponseDTO.getResult().getOrderSn();
    }

//    @MethodSource("BTSubmitOrderDataProvider")
    @Test
    @DisplayName("BraintreePay Submit Order接口")
    @DependsOnMethod("BraintreePaymentTest")
    public void BTSubmitOrderTest(){
        BTSubmitOrderRequest request = new BTSubmitOrderRequest();
        BraintreeV2PaymentData braintreeV2PaymentData = brainTreePayData.getV2PaymentData();
        request.setBaseTradeInfoStr(braintreeV2PaymentData.getBaseTradeInfoStr());
        request.setSignStr(braintreeV2PaymentData.getSignStr());
        request.setAmountStr(braintreeV2PaymentData.getAmountStr());
        request.setTradeNo(braintreeV2PaymentData.getTradeNo());
        request.setAppStatus(0);
        String result  = BraintreeSubmitOrderFlow.BTSubmitOrder(request);
        Assertions.assertThat(result.contains("Payment failed. please try again or change payment method"));
        SleepUtils.sleep(1000);
        PayOrder payOrder = payOrderSql.getPayOrder(request.getTradeNo(),true,false);
        Assertions.assertThat(payOrder.getTradeStatus()).isEqualTo(2);


    }
    @MethodSource("BraintreePaymentDataProvider")
    @ParameterizedTest
    @DisplayName("BraintreePay支付")
    public void BraintreePaymentTest(DefaultPaymentRequest request, String countryCode , String channel){
        DefaultPaymentResponse result= DefaultPaymentFlow.payment(request,countryCode,channel);
        Object object = result.getResult();
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(object));
        brainTreePayData = jsonObject.getObject("brainTreePayData", BrainTreePaymentResponseDTO.BrainTreePayData.class);
        System.out.println("brainTreePayData: "+JSON.toJSON(brainTreePayData));
        Assertions.assertThat(result.getResultCode()).isEqualTo(1000);
    }
    static Stream<Arguments> BraintreePaymentDataProvider(){
        BTPaymentRequest btPaymentRequest = new BTPaymentRequest();
        btPaymentRequest.setSysType("2");
        AppWalletInstallReq appWalletInstallReq = new AppWalletInstallReq();
        AliWalletInstallReq aliWalletInstallReq = new AliWalletInstallReq();
        aliWalletInstallReq.setInstallAliCN(1);
        aliWalletInstallReq.setInstallAliHK(1);
        WechatWalletInstallReq wechatWalletInstallReq = new WechatWalletInstallReq();
        wechatWalletInstallReq.setInstallWechatApp(1);
        appWalletInstallReq.setAliWalletInstallReq(aliWalletInstallReq);
        appWalletInstallReq.setWechatWalletInstallReq(wechatWalletInstallReq);
        btPaymentRequest.setAppWalletInstallReq(appWalletInstallReq);
        btPaymentRequest.setFloatingAmount(0);
        btPaymentRequest.setIsSidePayment("0");
        btPaymentRequest.setRoutingFloatingType(0);
        btPaymentRequest.setCityId("0");
        btPaymentRequest.setChannelRecordId("676956499165732864");//card
        btPaymentRequest.setPaymentType(2);
        btPaymentRequest.setRoutingPayChannel("braintreePay");
        btPaymentRequest.setCountryCode("AU");
        btPaymentRequest.setRoutingFloatingRate(1.0);
        btPaymentRequest.setOrderSn(orderSn);
        btPaymentRequest.setCardNoMd5(MD5Util.md5("4111111111111111"));
        return Stream.of(
                Arguments.of(btPaymentRequest,"AU","60")
        );
    }
    /*static Stream<Arguments> BTSubmitOrderDataProvider(){
        PayOrder payOrder = payOrderSql.getPayOrder(orderSn,false);
        UserEntity userEntity = userSql.getUser(payOrder.getPayerAccount());
        PayUserAccount payUserAccount = payUserAccountSql.getPayUserAccount(payOrder.getPayerAccount(),payOrder.getCountryCode(),payOrder.getPayChannel());
        BraintreeBaseTradeInfo baseTradeInfo = new BraintreeBaseTradeInfo();
        baseTradeInfo.setAutoFlag(0);
        baseTradeInfo.setAutoRecordId(982857091348545536L);
        baseTradeInfo.setPayUserId(payUserAccount.getPayUserId());
        baseTradeInfo.setNextAction("openSDK");
        baseTradeInfo.setDeviceId("5b309138e2e1e984");
        baseTradeInfo.setUserId(payUserAccount.getUserId());
        baseTradeInfo.setAppPaymentTypeCode(60);
        baseTradeInfo.setExtraChannel("card");
        baseTradeInfo.setPaymentMethodToken("");
        baseTradeInfo.setPhone(userEntity.getUserTelphone());
        baseTradeInfo.setAccountNo("pandaAccount");
        baseTradeInfo.setBtCustomerId(payUserAccount.getCustomerId());
        baseTradeInfo.setCardNoMd5(MD5Util.md5("4111111111111111"));
        String baseTradeInfoStr = Base64Utils.objectToBase64(baseTradeInfo);
        String BTBody = """
                {
                "baseTradeInfoStr": "eyJhY2NvdW50Tm8iOiJwYW5kYUFjY291bnQiLCJhcHBQYXltZW50VHlwZUNvZGUiOjYwLCJhdXRvRmxhZyI6MCwiYXV0b1JlY29yZElkIjo5NDI0OTc4NTU2ODUxMzIyODgsImJ0Q3VzdG9tZXJJZCI6Ijg1NTMzMzI5NjE0IiwiY2FyZE5vTWQ1IjoiIiwiZGV2aWNlSWQiOiJhNGFiNzZlOTQzNjZlZmNmIiwiZXh0cmFDaGFubmVsIjoiY2FyZCIsIm5leHRBY3Rpb24iOiJzdWJtaXQiLCJwYXlVc2VySWQiOjkzNTkyNjg3MjQ5Mjg3MTY4MCwicGF5bWVudE1ldGhvZFRva2VuIjoicGpwM2tuc2MiLCJwaG9uZSI6IjY0NzMyMTk4MjgiLCJ1c2VySWQiOiI1NTMwNDgifQ==",
                "tradeNo": "PD982797567220678656",
                "amountStr": "8.92",
                "signStr": "2b0fe2e3495aa8b836c2282c16c9f305",
                "appStatus": 0
                }
                """;
        BTSubmitOrderRequest request = JSONUtils.jsonToObject(BTBody,BTSubmitOrderRequest.class);
//        request.setBraintreeBaseTradeInfo(baseTradeInfo);
        request.setBaseTradeInfoStr(baseTradeInfoStr);
        request.setAmountStr(String.valueOf(payOrder.getAmount()));
        request.setTradeNo(payOrder.getTradeNo());
        String sign = BraintreeSubmitOrderFlow.doSign(request.getTradeNo(), request.getAmountStr(), request.getBaseTradeInfoStr());
        request.setSignStr(sign);
        return Stream.of(
                Arguments.of(request));
    }*/
    public static void main(String[] args){
        String value = Base64Util.decode("eyJhY2NvdW50Tm8iOiJwYW5kYUFjY291bnQiLCJhcHBQYXltZW50VHlwZUNvZGUiOjYwLCJhdXRvRmxhZyI6MCwiYXV0b1JlY29yZElkIjo5ODI4NTcwOTEzNDg1NDU1MzYsImJ0Q3VzdG9tZXJJZCI6IjU1OTgxNDc4NTcxIiwiY2FyZE5vTWQ1IjoiZDAzZWNmMGNkNjNiMjkxMWEzNGM5ZjMyNTE2OWM4ODAiLCJkZXZpY2VJZCI6IjViMzA5MTM4ZTJlMWU5ODQiLCJleHRyYUNoYW5uZWwiOiJjYXJkIiwibmV4dEFjdGlvbiI6Im9wZW5TREsiLCJwYXlVc2VySWQiOjk4Mjg1NzA5MTI4MTQzNjY3MiwicGF5bWVudE1ldGhvZFRva2VuIjoiIiwicGhvbmUiOiI0NTEyMzQ3ODkiLCJ1c2VySWQiOiIxMzk4NzEzMjQwIn0=", StandardCharsets.UTF_8.toString());
        BraintreeBaseTradeInfo baseTradeInfo = JSONObject.parseObject(value, BraintreeBaseTradeInfo.class);
        System.out.println(JSON.toJSON(baseTradeInfo));
    }

}
