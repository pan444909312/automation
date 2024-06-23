package com.miller.userapp.module.pay.payment.flow;

import com.hungrypanda.app.server.api.res.payment.PaymentPatternDTO;
import com.hungrypanda.app.server.service.pay.pandapay.payment.core.model.PayData;
import com.hungrypanda.payserver.api.res.PaymentMethodInfoDTO;
import com.miller.data.center.user.TestCaseDataForUserConstant;
import com.miller.service.framework.cache.CacheUtils;
import com.miller.service.framework.http.HttpUtils;
import com.miller.userapp.constants.BusinessConstant;
import com.miller.userapp.constants.PaymentConstant;
import com.miller.userapp.module.order.create.response.CreateOrderResponseDTO;
import com.miller.userapp.module.pay.card.flow.GetPaymentMethodsFlow;
import com.miller.userapp.module.pay.checkout.flow.PaymentPatternCheckOutFlow;
import com.miller.userapp.module.pay.checkout.response.PaymentPatternCheckOutResponseDTO;
import com.miller.userapp.module.pay.payment.request.StripePaymentRequest;
import com.miller.userapp.module.pay.payment.response.StripePaymentResponse;
import com.miller.userapp.util.RequestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StripePaymentFlow {
    private static final String uri = BusinessConstant.DOMAIN + "/api/user/pandaPay/biz/payment";
    private static final String SDKURL ="https://api.stripe.com/v1/payment_intents/${paymentIntentId}/confirm";

    /**
     * stripe payment
     * @param stripePaymentRequest
     * @return
     */
    public static StripePaymentResponse stripePayment(StripePaymentRequest stripePaymentRequest) {
        // 更改请求头中的Content-Type参数。不要重新调用 RequestUtils.setHeaders(header)，因为请求头中已经包含了token
        RequestUtils.getHeaders().put("Content-Type", "application/json");
        RequestUtils.getHeaders().put("countryCode","SG");
//        stripePaymentRequest.setBlackBox(RequestUtils.getHeaders().get("blackbox"));
        stripePaymentRequest.setCountryCode(RequestUtils.getHeaders().get("countryCode").toString());
        RequestUtils.getHeaders().put("channel","50");
        CreateOrderResponseDTO createOrderResponseDTO = CacheUtils.get(TestCaseDataForUserConstant.ORDER_ID_OBJECT_KEY, CreateOrderResponseDTO.class);
        String orderSn = createOrderResponseDTO.getResult().getOrderSn();
        stripePaymentRequest.setOrderSn(orderSn);
        PaymentPatternCheckOutResponseDTO ppcResponseDTO = PaymentPatternCheckOutFlow.getOrderCombineInfo(orderSn);

        PaymentMethodInfoDTO paymentMethod= GetPaymentMethodsFlow.getPaymentMethod();
        if(Objects.nonNull(paymentMethod)){
            stripePaymentRequest.setPaymentMethodId(paymentMethod.getPaymentMethodId());
        }
        if(ppcResponseDTO.getSuccess()){
            stripePaymentRequest.setPaymentType(ppcResponseDTO.getResult().getPaymentType());
            List<PaymentPatternDTO> patternDTOList= ppcResponseDTO.getResult().getPatternDTOList();
            PaymentPatternDTO paymentPatternDTO= patternDTOList.stream().filter(p->p.getPayType().equals(50)).findAny().orElse(null);
            if (Objects.nonNull(paymentPatternDTO)){
                stripePaymentRequest.setChannelRecordId(paymentPatternDTO.getChannelRecordIdStr());
                stripePaymentRequest.setRoutingPayChannel(paymentPatternDTO.getPayChannel());
                stripePaymentRequest.setRoutingFloatingRate(paymentPatternDTO.getFloatingRate());
            }
        }
        return HttpUtils.sendPostRequestReturnJavaObject(uri, null, RequestUtils.getHeaders(),
                RequestUtils.putBodyOfForm(stripePaymentRequest), null, StripePaymentResponse.class);

    }
    //stripe支付成功
    public static void StripeSDKPayment(StripePaymentRequest stripePaymentRequest){
        StripePaymentResponse result = stripePayment(stripePaymentRequest);
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
}
