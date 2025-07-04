package com.miller.userapp.module.data.pay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hungrypanda.app.server.api.req.payment.AliWalletInstallReq;
import com.hungrypanda.app.server.api.req.payment.AppWalletInstallReq;
import com.hungrypanda.app.server.api.req.payment.WechatWalletInstallReq;
import com.hungrypanda.app.server.api.res.payment.PaymentPatternDTO;
import com.hungrypanda.app.server.entity.order.OrderEntity;
import com.hungrypanda.app.server.entity.user.UserEntity;
import com.hungrypanda.app.server.entity.user.UserLogEntity;
import com.hungrypanda.app.server.service.pay.pandapay.payment.core.model.PayData;
import com.hungrypanda.app.server.service.secure.SecureService;
import com.hungrypanda.app.server.service.secure.impl.SecureServiceImpl;
import com.hungrypanda.payserver.api.res.PaymentMethodInfoDTO;
import com.miller.common.util.MD5Util;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.service.framework.cache.CacheUtils;
import com.miller.service.framework.cache.remote.redis.RedisService;
import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.constants.PaymentConstant;
import com.miller.userapp.module.data.pay.db.OrderSql;
import com.miller.userapp.module.data.user.db.UserLogSql;
import com.miller.userapp.module.data.user.db.UserSql;
import com.miller.userapp.module.home.login.flow.UserLoginFlow;
import com.miller.userapp.module.home.login.request.UserLoginRequestDTO;
import com.miller.userapp.module.home.login.response.UserLoginResponseDTO;
import com.miller.userapp.module.order.create.response.CreateOrderResponseDTO;
import com.miller.userapp.module.pay.card.stripe.flow.GetPaymentMethodsFlow;
import com.miller.userapp.module.pay.checkout.flow.PaymentPatternCheckOutFlow;
import com.miller.userapp.module.pay.checkout.response.PaymentPatternCheckOutResponseDTO;
import com.miller.userapp.module.pay.payment.flow.StripePaymentFlow;
import com.miller.userapp.module.pay.payment.request.StripePaymentRequest;
import com.miller.userapp.module.pay.payment.response.StripePaymentResponse;
import com.miller.userapp.util.DBUtils;
import com.miller.userapp.util.RequestUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OrderAutoPaySuccessByStripe {
    private static final String uri = BusinessConstant.DOMAIN + "/api/user/pandaPay/biz/payment";
    private static final String SDKURL ="https://api.stripe.com/v1/payment_intents/${paymentIntentId}/confirm";
    private static SqlSession sqlSession = DBUtils.getDBOfPandaTest();
//    public OrderAutoPaySuccessByStripe(){
//
//    }
    //按照订单号查找用户信息
    //查找登陆authorization信息，如果没有重新登陆
    //调用支付接口，生成交易号
    //获取stripe 卡列表，如果没有绑张卡
    //使用stripe支付
    private static final String orderSn = "4204598579239760551214"; //合单也传外卖单子订单号,不支持会员自动续费合单
    //没有登陆需要使用密码登陆
    private static final String PWD = "12345678";//password
    public static void main(String[] args){
        OrderAutoPaySuccessByStripe orderAutoPaySuccessByStripe = new OrderAutoPaySuccessByStripe();
        orderAutoPaySuccessByStripe.requireAuthorization(orderSn,PWD);
        orderAutoPaySuccessByStripe.stringPaymentPattern(orderSn);
    }
    private void requireAuthorization(String orderSn,String passWord){
        boolean isLogin = false;
        String accessToken="";
        OrderSql orderSql = new OrderSql(sqlSession);
        UserSql userSql = new UserSql(sqlSession);
        UserLogSql userLogSql = new UserLogSql(sqlSession);
        OrderEntity orderEntity = orderSql.getOrderEntity(orderSn);
        if(orderEntity.getOrderStatusNew() == 8 || orderEntity.getPayStatus() != 0){
            throw new RuntimeException("订单已经取消或者已经被支付");
        }
        Long userId = orderEntity.getUserId();
        UserEntity userEntity = userSql.getUser(userId);
        UserLogEntity userLogEntity = userLogSql.getUserLogEntity(userId);
        String userName = userEntity.getUserName();
        if(Objects.nonNull(userLogEntity)) isLogin = true;
        if(isLogin) {
            accessToken = userLogEntity.getAccessToken();
        }else {
            //重新登陆
            UserLoginRequestDTO user = new UserLoginRequestDTO();
            user.setAreaCode("86");
            user.setAccount(userName);
            user.setPassword(MD5Util.string2MD5(passWord));
            user.setType(2);
            user.setDistinctId("ed99f8b03a64c6c1");
            UserLoginResponseDTO userLoginResponseDTO = UserLoginFlow.loginReturnBodyObject(user);
            accessToken =userLoginResponseDTO.getResult().getAccessToken();
        }
        var headers = new HashMap<String, Object>();
        headers.put("Content-Type", "application/json");
        headers.put("authorization", accessToken);

        // 更新全局请求头参数。设置测试用例的默认用户。
        RequestUtils.setHeaders(headers);
    }
    private void stringPaymentPattern(String orderSn){
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
        StripeSDKPayment(stripePaymentRequest,orderSn);
    }
    //stripe支付成功
    private void StripeSDKPayment(StripePaymentRequest stripePaymentRequest,String orderSn){
        StripePaymentResponse result = stripePayment(stripePaymentRequest,orderSn);
        PayData payData = result.getResult();
        String paymentIntentId = payData.getStripePayData().getPaymentIntentId();
        String clientSecret = payData.getStripePayData().getClientSecret();
        String publishableApiKey = payData.getStripePayData().getPublishableApiKey();
        Map<String,Object> headers = new HashMap<>();
        headers.put("Content-Type","application/x-www-form-urlencoded");
        headers.put("Authorization","Bearer "+publishableApiKey);
        Map<String,Object>  paramsBody = new HashMap<>();
        paramsBody.put("expand[0]","payment_method");
        paramsBody.put("payment_method_data[card][exp_year]","2050");
        paramsBody.put("payment_method_data[card][exp_month]","09");
        paramsBody.put("payment_method_data[card][cvc]","737");
        paramsBody.put("payment_method_data[type]","card");
        paramsBody.put("payment_method_data[card][number]", PaymentConstant.CARDNUMBER.replace(" ",""));
        paramsBody.put("return_url","pandastripe://stripe-redirect");
        paramsBody.put("client_secret",clientSecret);
        paramsBody.put("use_stripe_sdk",true);
        Map<String,Object> confirmResult = HttpUtils.sendPostRequest(SDKURL.replace("${paymentIntentId}",paymentIntentId),paramsBody,headers,null,null);
//        System.out.println("confirmResult: "+JSON.toJSON(confirmResult));
    }
    private  StripePaymentResponse stripePayment(StripePaymentRequest stripePaymentRequest,String orderSn) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        RequestUtils.getHeaders().put("countryCode","SG");
//        stripePaymentRequest.setBlackBox(RequestUtils.getHeaders().get("blackbox"));
        stripePaymentRequest.setCountryCode(RequestUtils.getHeaders().get("countryCode").toString());
        RequestUtils.getHeaders().put("channel","50");
        RequestUtils.getHeaders().put("version","99.99.99");

        PaymentPatternCheckOutResponseDTO ppcResponseDTO = PaymentPatternCheckOutFlow.getOrderCombineInfo(orderSn);

        PaymentMethodInfoDTO paymentMethod= GetPaymentMethodsFlow.getPaymentMethod();
        if(Objects.nonNull(paymentMethod)){
            stripePaymentRequest.setPaymentMethodId(paymentMethod.getPaymentMethodId());
        }
        if(ppcResponseDTO.getSuccess()){
            stripePaymentRequest.setPaymentType(ppcResponseDTO.getResult().getPaymentType());
            if(Objects.nonNull(ppcResponseDTO.getResult().getCheckStandCombinedDTO())){
                //合单
                orderSn = ppcResponseDTO.getResult().getCheckStandCombinedDTO().getOrderCombinedSn();
            }
            List<PaymentPatternDTO> patternDTOList= ppcResponseDTO.getResult().getPatternDTOList();
            PaymentPatternDTO paymentPatternDTO= patternDTOList.stream().filter(p->p.getPayType().equals(50)).findAny().orElse(null);
            if (Objects.nonNull(paymentPatternDTO)){
                stripePaymentRequest.setChannelRecordId(paymentPatternDTO.getChannelRecordIdStr());
                stripePaymentRequest.setRoutingPayChannel(paymentPatternDTO.getPayChannel());
                stripePaymentRequest.setRoutingFloatingRate(paymentPatternDTO.getFloatingRate());
            }
        }
        stripePaymentRequest.setOrderSn(orderSn);
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfForm(stripePaymentRequest), null, StripePaymentResponse.class);

    }
}
